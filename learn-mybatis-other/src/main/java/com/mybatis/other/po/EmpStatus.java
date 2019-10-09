package com.mybatis.other.po;

/**
 * 员工状态
 * @ClassName: EmpStatus
 * @Description:
 * @Author: lin
 * @Date: 2019/10/8 12:07
 * @History:
 * @<version> 1.0
 */
public enum EmpStatus {
    /**
     * 登录
     */
    LOGIN(100,"用户登录"),
    /**
     * 登出
     */
    LOGOUT(200,"用户登出"),
    /**
     * 移除
     */
    REMOVE(300,"用户不存在");

    private Integer code;
    private String msg;

     EmpStatus(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * 按照状态码返回枚举对象
     * @param code
     * @return
     */
    public static  EmpStatus getEmpStatusByCode(Integer code){
         switch (code){
             case  100:
                 return  LOGIN;
             case  200:
                return  LOGOUT;
             case  300:
                 return  REMOVE;

             default:
                 return  LOGOUT;
         }
    }
}
