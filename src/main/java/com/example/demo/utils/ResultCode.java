package com.example.demo.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 公共错误 0---9999
 * 用户相关错误码 10000-10100
 * 流媒体相关错误码 20001-20100
 *
 */
public final class ResultCode {

	private final static Map<String,Object> msg = new HashMap<>();

	/**
	 * 正常响应
	 */
	public static final String RETURN_CODE_SUCCESS = "000";
	/**
	 * token验证失败
	 */
	public static final String RETURN_CODE_TOKEN_VALIDATE_FAIL = "10007";
	/**
	 * 参数错误
	 */
	public static final String RETURN_CODE_PARAMS_NOT_EMPTY = "30001";
	/**
	 * 未知错误
	 */
	public static final String RETURN_CODE_UNKNOW = "99998";
	/**
	 * 系统异常
	 */
	public static final String RETURN_CODE_EXCEPTION = "99999";
	/**
	 * 短信发送失败
	 */
	public static final String RETURN_CODE_SEND_MESSAGE_FAIL_CODE = "30002";


	/**
	 * 手机号码已经注册
	 */
	public static final String RETURN_CODE_FAIL_MOBILE_EXISTS = "2001";
	/**
	 * 手机号码未注册
	 */
	public static final String RETURN_CODE_FAIL_MOBILE_NO_EXISTS = "2002";
	/**
	 * 用户已经被停用
	 */
	public static final String RETURN_CODE_FAIL_USER_DISABLED_CODE = "2004";
	/**
	 * 用户不存在
	 */
	public static final String RETURN_CODE_FAIL_USER_NO_EXISTS = "1026";
	/**
	 * 密码错误
	 */
	public static final String RETURN_CODE_FAIL_ERROR_PASSWORD = "2003";
	/**
	 * 原密码不正确
	 */
	public static final String RETURN_CODE_FAIL_OLD_PASSWORD_INVALID_CODE = "1006";
	/**
	 * 两次输入的密码不一致
	 */
	public static final String RETURN_CODE_FAIL_CONFIRM_PASSWORD_INVALID_CODE = "1007";
	/**
	 * 验证码错误
	 */
	public static final String RETURN_CODE_FAIL_ERROR_VALID_CODE = "2008";
	/**
	 * 验证码失效
	 */
	public static final String RETURN_CODE_FAIL_INVALID_VALID_CODE = "2009";
	/**
	 * 手机号码不合法
	 */
	public static final String RETURN_CODE_FAIL_MOBILE_INVALID_CODE = "1003";
	/**
	 * 验证码发送失败
	 */
	public static final String RETURN_CODE_MOBILE_PSUH_FAIL_CODE = "2010";

	/**
	 * 会员码错误
	 */
	public static final String RETURN_CODE_VIPNO_ERROR = "50013";
	/**
	 * 会员码已使用
	 */
	public static final String RETURN_CODE_VIPNO_ERROR1 = "50014";
	/**
	 * 已经是会员了
	 */
	public static final String RETURN_CODE_VIPNO_ERROR2 = "50015";


	/**
	 *  医生已经存在审核记录
	 */
	public static final String RETURN_CODE_EXIST_DOCTOR_AUDIT = "100100";






	//流媒体相关错误码

	/**
	 * 视频不存在
	 */
	public static final String RETURN_CODE_VIDEO_NO_EXISTS = "20001";
	/**
	 * 标签已存在
	 */
	public static final String RETURN_CODE_LABEL_EXISTS = "20002";
	/**
	 * 分类已存在
	 */
	public static final String RETURN_CODE_CATE_EXISTS = "20003";
	/**
	 * 该分类下存在关联视频
	 */
	public static final String RETURN_CODE_DELETE_CATE_VIDEO = "20004";
	/**
	 * 分类名字重复
	 */
	public static final String RETURN_CODE_CATE_REPEAT_NAME = "20005";
	/**
	 * 修改失败,分类不存在
	 */
	public static final String RETURN_CODE_CATE_NO_EXISTS = "20006";
	/**
	 * 支付失败
	 */
	public static final String RETURN_PAY_FAL = "2901";



	/**
	 * 资讯不存在
	 */
	public static final String RETURN_CODE_INFO_NO_EXISTS = "20100";



	/**
	 * 您已经不能再更新会诊资料
	 */
	public static final String RETURN_CODE_CONSULTATION_NOREPLY = "100103";



	/**
	 * 已经绑定过该住院号
	 */
	public static final String RETURN_CODE_HAS_BIND_INPATIENT = "3001";



	static{
		msg.put(RETURN_CODE_SUCCESS, "");
		msg.put(RETURN_CODE_PARAMS_NOT_EMPTY, "参数错误");
		msg.put(RETURN_CODE_EXCEPTION, "系统异常");
		msg.put(RETURN_CODE_UNKNOW, "未知错误");
		msg.put(RETURN_CODE_SEND_MESSAGE_FAIL_CODE, "短信发送失败");

		//用户相关错误码
//		msg.put(RETURN_CODE_FAIL_VIDEO_EXCEPTION, "服务器异常");
		msg.put(RETURN_CODE_FAIL_MOBILE_EXISTS, "手机号码已经注册");
		msg.put(RETURN_CODE_FAIL_MOBILE_NO_EXISTS, "手机号码未注册");
		msg.put(RETURN_CODE_FAIL_USER_DISABLED_CODE, "用户已经被停用");
		msg.put(RETURN_CODE_FAIL_USER_NO_EXISTS, "用户不存在");
		msg.put(RETURN_CODE_FAIL_ERROR_PASSWORD, "密码错误");
		msg.put(RETURN_CODE_FAIL_OLD_PASSWORD_INVALID_CODE, "原密码不正确");
		msg.put(RETURN_CODE_FAIL_CONFIRM_PASSWORD_INVALID_CODE, "两次输入的密码不一致");
		msg.put(RETURN_CODE_FAIL_ERROR_VALID_CODE, "验证码错误");
		msg.put(RETURN_CODE_FAIL_INVALID_VALID_CODE, "验证码失效");
		msg.put(RETURN_CODE_FAIL_MOBILE_INVALID_CODE, "手机号码不合法");
		msg.put(RETURN_CODE_MOBILE_PSUH_FAIL_CODE, "验证码发送失败");
		msg.put(RETURN_CODE_TOKEN_VALIDATE_FAIL, "登录已失效");
		msg.put(RETURN_CODE_VIPNO_ERROR, "会员码错误");
		msg.put(RETURN_CODE_VIPNO_ERROR1, "会员码已使用");
		msg.put(RETURN_CODE_VIPNO_ERROR2, "已经是会员了");
		msg.put(RETURN_PAY_FAL, "支付失败");
//		msg.put(RETURN_CODE_OLD_ERROR_PASSWORD, "原密码错误");


		//医生审核相关信息cd
		msg.put(RETURN_CODE_EXIST_DOCTOR_AUDIT, "医生已经存在审核记录");




		msg.put(RETURN_CODE_VIDEO_NO_EXISTS, "视频不存在");
		msg.put(RETURN_CODE_LABEL_EXISTS, "标签已存在");
		msg.put(RETURN_CODE_CATE_EXISTS, "分类已存在");
		msg.put(RETURN_CODE_DELETE_CATE_VIDEO, "该分类下存在关联视频");
		msg.put(RETURN_CODE_CATE_REPEAT_NAME, "分类名字重复");
		msg.put(RETURN_CODE_CATE_NO_EXISTS, "修改失败,分类不存在");

		msg.put(RETURN_CODE_INFO_NO_EXISTS, "资讯不存在");


		msg.put(RETURN_CODE_CONSULTATION_NOREPLY, "您已经不能再更新会诊资料");

	}

	public static String getMsg(String errorCode){
		Object result = msg.get(errorCode);
		if(result == null){
			return "";
		}
		return result.toString();
	}

	/*public static void main(String[] args) throws Exception {
		String url = "D:\\workspace\\Platform\\llhealth-web\\llhealth-bussiness-web\\llhealth-bussiness-web-core\\src\\main\\resources\\message.properties";
		Properties p = new Properties();
		p.load(new FileInputStream(url));
		Set<Map.Entry<Object, Object>> pSet = p.entrySet();
		Set<Map.Entry<String, Object>> msgSet = msg.entrySet();

		for (Map.Entry<Object, Object> s:pSet) {
			String code = (String) s.getKey();
			String value = (String) s.getValue();
			for (Map.Entry<String, Object> s2:msgSet ) {

				if(code.equals(s2.getKey())){
					if(!value.equals(s2.getValue())){
						System.out.println(code);
					}
				}

			}
		}
	}*/

}
