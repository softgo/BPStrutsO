package com.javabase.base.remote;

/**
 * 返回的设置
 * 
 * @author bruce.
 *
 */
public class JsonResponseHeader {

    public static final int STATUS_OK = 200;
    public static final int STATUS_ERROR = 500;
    public static final int STATUS_INVALID_PARAMETER = 501;
    public static final int STATUS_SERVER_ERROR = 502;
    public static final int STATUS_ZZPAY_RESPONSE_ERROR=503;
    
    public static final int STATUS_BACKWARD_OK = 1;
    
    //登陆失败
    public static final int STATUS_LOGIN_PASSWORD_ERROR = 403;
    public static final int STATUS_LOGIN_ENDDATE_ERROR = 409;

    //帐号停用
    public static final int STATUS_LOGIN_STOP_ERROR = 410;

    public static final int STATUS_MALLID_ERROR = 600;
    //门店相关错误码
    public static final int STATUS_MENDIAN_NAME_NULL = 601;
    public static final int STATUS_MENDIAN_ADDRESS_NULL = 602;
    public static final int STATUS_MENDIAN_PHONE_NULL = 603;
    public static final int STATUS_MENDIAN_ID_ERROR = 604;
    public static final int STATUS_MENDIAN_DB_ERROR = 605;
    public static final int STATUS_MENDIAN_DESC_ERROR = 606;
    public static final int STATUS_MENDIAN_EXCEPTION = 610;
    
    //审核相关错误码
    public static final int STATUS_AUDIT_PUSHID_ERROR = 801;
    public static final int STATUS_AUDIT_STATE_ERROR = 802;
    public static final int STATUS_AUDIT_EXCEPTION = 803;
    public static final int STATUS_AUDIT_NOTEXIST_EXCEPTION = 804;
    
    //backworker创建相关
    public static final int STATUS_BACKWORKER_WORKEREXIST = 701;
    public static final int STATUS_BACKWORKER_MALLNAMEEXIST = 702;

    // 企业评论错误码
    public static final int STATUS_ENT_COMMENT_ERROR = 900;
    public static final int STATUS_ENT_COMMENT_PARAMETER_ERROR = 901;
    public static final int STATUS_ENT_COMMENT_IS_COMMENTED_ERROR = 902;
    
    //mall相关错误码
    public static final int STATUS_MALL_ERROR = 1000;
    public static final int STATUS_MALL_PARAM_ERROR = 1001;
    public static final int STATUS_MALL_SRPWORD_ERROR = 1002;	//b2c_mall表没有查到相关企业信息
    
    //手机绑定相关错误码
    public static final int STATUS_MOBILE_EXIST = 1100;
	public static final int STATUS_MOBILE_VERIFY_LIMIT = 1101;
	public static final int STATUS_MOBILE_VERIFY_ERROR = 1102;
    
    //部分支付宝支付相关错误码
    public static final int STATUS_PART_PAY_PARAM_ERROR=1200;//支付参数配置错误
    public static final int STATUS_PART_PAY_OK=1201;//账单已完成支付，无需返回url
    public static final int STATUS_PART_PAY_NO_CASH_VALUE=1202;//账单现金支付余额为0，无需返回url
    public static final int STATUS_PART_PAY_CREATE_SYORDER_FAIL=1203;//创建sy_order失败

    private int status;

    public JsonResponseHeader() {
        //
    }

    public JsonResponseHeader(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
