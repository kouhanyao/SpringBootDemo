package com.example.demo.controller;

import com.example.demo.domain.TeacherBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.exceptions.JedisConnectionException;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Created by 寇含尧 on 2017/11/5.
 */
@RestController
@RequestMapping("redis")
public class RedisRepositoryController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    //stringRedisTemplate只针对键值都是字符型的数据进行操作
    //spring boot 为我们默认配置了StringRedisTemplate和RedisTemplate
    //以下导入的四个对象都可以对redis操作
    //redis类必须序列化
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    RedisTemplate<Object, Object> redisTemplate;

    @Resource(name = "stringRedisTemplate")
    ValueOperations<String, String> valOpsStr;

    @Resource(name = "redisTemplate")
    ValueOperations<Object, Object> valOps;

    @RequestMapping("save")
    public void save(TeacherBean teacherBean) {
        //PersonBean personBean = new PersonBean(1L,"khy",24,"成都");
        redisTemplate.opsForValue().set(teacherBean.getId(), teacherBean, 100L, TimeUnit.SECONDS);
        redisTemplate.opsForValue().set(teacherBean.getName(), teacherBean,100L);
    }

    @RequestMapping("deleteByName")
    public void deleteByName(TeacherBean teacherBean) {
        redisTemplate.delete(teacherBean.getName());
    }

    @RequestMapping("deleteById")
    public void deleteById(TeacherBean teacherBean) {
        redisTemplate.delete(teacherBean.getId());
    }

    @RequestMapping("getByName")
    public Object getByName(TeacherBean teacherBean) {
        return redisTemplate.opsForValue().get(teacherBean.getName());
    }

    @RequestMapping("getById")
    public Object getById(TeacherBean teacherBean) {
        return redisTemplate.opsForValue().get(teacherBean.getId());
    }


    // lock flag stored in redis
    private static final String LOCKED = "TRUE";
    // 默认时间1天  单位是秒
    private static final int DEFAULT_SINGLE_EXPIRE_TIME_DAY = 24 * 60 * 60;

    //test 分布式
    public void test() {
        if (!tryLock("test_key", 30L, TimeUnit.MILLISECONDS, DEFAULT_SINGLE_EXPIRE_TIME_DAY)) {
            //没有获取到锁
            logger.info("test_key=".concat(LOCKED).concat("已经执行过该业务方法."));
            return;
        }
    }

    /**
     * 锁在给定的等待时间内空闲，则获取锁成功 返回true， 否则返回false
     *
     * @param key        锁键
     * @param timeout    超时时间
     * @param unit       超时时间单位
     * @param expireTime lock锁的key的有效时间(s)
     * @return
     */
    public boolean tryLock(String key, long timeout, TimeUnit unit, long expireTime) {
        RedisConnection connection = redisTemplate.getConnectionFactory().getConnection();
        try {
            //系统计时器的当前值，以纳秒为单位。
            long nano = System.nanoTime();
            do {
                logger.debug("try lock key: " + key);
                //将 key 的值设为 空 byte  成功 或者 失败 ()
                Boolean status = connection.setNX(key.getBytes(), LOCKED.getBytes());
                if (status) {
                    connection.expire(key.getBytes(), expireTime);//设置过期时间
                    logger.debug("get lock, key: " + key + " , expire in " + expireTime + " seconds.");
                    // 成功获取锁，返回true
                    return Boolean.TRUE;
                } else { // 存在锁,循环等待锁
                    logger.debug("key: " + key + " locked by another business：");
                }
                if (timeout <= 0) {  // 没有设置超时时间，直接退出等待
                    break;
                }
                Thread.sleep(30);
            } while ((System.nanoTime() - nano) < unit.toNanos(timeout));
            return Boolean.FALSE;
        } catch (JedisConnectionException je) {
            logger.error(je.getMessage(), je);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            connection.close();//释放资源
        }
        return Boolean.FALSE;
    }

    /**
     * 锁在给定的等待时间内空闲，则获取锁成功 返回true， 否则返回false
     *
     * @param timeout
     * @param unit
     * @return
     */
    public boolean tryLock(List<String> keyList, long timeout, TimeUnit unit, long expireTime) {
        RedisConnection connection = redisTemplate.getConnectionFactory().getConnection();
        try {
            //需要的锁
            List<String> needLocking = new CopyOnWriteArrayList<String>();
            //得到的锁
            List<byte[]> locked = new CopyOnWriteArrayList<>();

            long nano = System.nanoTime();
            do {
                // 构建pipeline，批量提交
                connection.openPipeline();
                for (String key : keyList) {
                    needLocking.add(key);
                    connection.setNX(key.getBytes(), LOCKED.getBytes());
                }
                logger.debug("try lock keys: " + needLocking);
                // 提交redis执行计数,批量处理完成返回
                List<Object> results = connection.closePipeline();
                for (int i = 0; i < results.size(); ++i) {
                    Boolean result = (Boolean) results.get(i);
                    String key = needLocking.get(i);
                    if (result) { // setnx成功，获得锁
                        connection.expire(key.getBytes(), expireTime);
                        locked.add(key.getBytes());
                    }
                }
                needLocking.removeAll(locked); // 已锁定资源去除

                if (needLocking.size() == 0) { //成功获取全部的锁
                    return true;
                } else {
                    // 部分资源未能锁住
                    logger.debug("keys: " + needLocking + " locked by another business：");
                }

                if (timeout == 0) {
                    break;
                }
                Thread.sleep(500);
            } while ((System.nanoTime() - nano) < unit.toNanos(timeout));

            // 得不到锁，释放锁定的部分对象，并返回失败
            if (locked.size() > 0) {
                connection.del(locked.toArray(new byte[10][]));
            }
            return false;
        } catch (JedisConnectionException je) {
            logger.error(je.getMessage(), je);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            connection.close();
        }
        return true;
    }

    /**
     * 批量释放锁
     */
    public void unLock(List<String> keyList) {
        List<byte[]> keys = new CopyOnWriteArrayList();
        for (String key : keyList) {
            keys.add(key.getBytes());
        }
        RedisConnection connection = redisTemplate.getConnectionFactory().getConnection();
        try {
            if (keys.size() > 0) {
                connection.del(keys.toArray(new byte[0][]));
                logger.debug("release lock, keys :" + keys);
            }
        } catch (JedisConnectionException je) {
            logger.error(je.getMessage(), je);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            connection.close();
        }
    }

    /**
     * 批量单个锁
     */
    public void unLockSingle(String key) {
        redisTemplate.delete(key);
    }
}
