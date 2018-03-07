package com.example.demo.constant;

import com.example.demo.system.MessageResult;

/**
 * 状态代码
 * <p>
 * 返回码定义，不同业务使用不同的状态区间，50个状态码一个区间，例如：
 * [ 000, 049] 公共状态码
 * [ 050, 099] 系统管理相关业务
 * [ 100, 149] Web组件先关业务
 * [ 150, 199] 活动业务
 * [ 200, 249] 资源业务
 * <p>
 * 具体返回开发接口同志们在群里沟通即可，避免出现重复定义。
 * <p>
 * Created by liupj on 2017/1/23.
 */
public enum ResultCode {

    // ==========================
    //       公共状态码
    // ==========================
    STATUS_SUCCESS("000", "操作成功"),
    STATUS_ERROR("-1", "操作失败"),
    ERROR_REQUEST("001", "您正在使用不正当手段请求，请求信息已被记录在案！"),
    SERVER_BUSY("002", "服务器繁忙，请稍后再试"),
    VERIFY_CODE_ERROR("003", "验证码错误"),
    PAGESIZE_ERROR("004", "pageSize超过最大限制"),
    FILE_UPLOAD_ERROR("005", "文件上传失败"),
    USER_LOGIN_INVALID("008", "登录失效"),
    USER_NOT_EXISTS("009", "账号不存在"),
    USER_ACCOUNT_PASS_ERROR("010", "账号或者密码错误"),
    //登录相关错误
    NOT_LOGGED("011", "您还没有登录！"),
    USER_IS_EXISTS("012", "账号已经存在！"),
    USER_IS_NULL("013", "账号为空！"),
    ACTIVITY_MOBILE_IN_BLACKLIST("014", "手机号码在黑名单中!"),


    EXCEL_HEADER_ERRROR("006", "EXCEL表头校验错误"),


    // ==========================
    //       活动业务状态
    // ==========================
    ACTIVITY_NOT_EXISTS("150", "活动不存在！"),
    ACTIVITY_TEMPLATE_FORMAT_ERROR("151", "活动模板格式错误！"),
    ACTIVITY_TEMPLATE_EXISTS("152", "活动模板已经存在！"),
    ACTIVITY_IS_FINISHED("153", "活动已结束！"),
    ACTIVITY_NOT_START("154", "活动还没有开始哦！"),
    ACTIVITY_NEED_RE("155", "题库资源不足"),
    ACTIVITY_FULL("156", "名额已满"),
    ACTIVITY_OPTION_ERROR("157", "活动配置错误"),
    ACTIVITY_DATA_IS_SUBMIT("158", "数据已提交，不能再次提交"),
    ACTIVITY_IS_JOINED("159", "已经参加过了"),
    ACTIVITY_NOT_SC_MOBILE("160", "活动仅支持四川移动"),
    ACTIVITY_MANGAZINE_NOT_EXSITS("161", "周刊不存在!"),
    ACTIVITY_TICKET_NONE("162", "none 资源类型不做数据处理!"),
    ACTIVITY_CONTENT_NOT_EXISTS("163", "内容不存在!"),
    ACTIVITY_CONTENT_PUBLISHED("164", "已发布!"),
    ACTIVITY_DRAW_EMPTY("165", "来晚了，已经领取完了!"),

    // ==========================
    //       资源业务状态
    // ==========================
    RRESOURCE_COUPON_EXISTS("200", "奖券已经存在！"),
    RRESOURCE_VIP_EXISTS("201", "您已经订购！"),


    //服务器类错误
    SERVER_ERROR("500", "服务器错误"),


    //记录存在错误
    RECORD_EXISTS("601", "记录已存在"),

    ROLE_EXISTS("6012", "角色已存在"),

    USER_EXISTS("6013", "用户已存在"),

    NAME_EXISTS("6014", "名称已存在"),

    //记录不存在错误
    RECORD_NOT_EXISTS("602", "记录不存在"),
    MENU_PARENT_NOT_EXISTS("6021", "父类不存在"),

    //参数错误
    PARAM_ERROR("700", "参数错误"),

    PARAM_ERROR_NEEDID("701", "缺少id"),

    //保存中问题
    SAVE_ERROR("800", "保存失败"),

    //删除中问题
    DELETE_ERROR("810", "删除错误"),
    DELETE_ERROR_USED("811", "已被使用，不可删除！");

    private String code;

    private String msg;

    ResultCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getMsg() {
        return this.msg;
    }

    public String getCode() {
        return this.code;
    }


    /**
     * 判断状态是否相同
     *
     * @param result 返回对象
     * @return
     */
    public final boolean equals(MessageResult result) {
        if (result == null) {
            return false;
        }
        return this.code.equals(result.getCode());
    }

}
