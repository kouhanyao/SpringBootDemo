package com.example.demo.service.DomainMongoRepository;

import com.example.demo.domain.StudentBean;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

/**
 * Created by 寇含尧 on 2017/11/5.
 */
public interface StudentRepositoryService extends MongoRepository<StudentBean,String>{
    //方法名查询
    StudentBean findByName(String name);

    //@query查询，查询参数构造json字符串即可
    @Query("{'age':?0}")
    List<StudentBean> withQueryFindByAge(Integer age);

}
