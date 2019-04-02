package com.imooc.miaosha.result;

public class CodeMsg {
    private int code;
    private String msg;

    public static CodeMsg SUCCESS = new CodeMsg(0, "success");
    public static CodeMsg SERVICE_ERROR = new CodeMsg(500100, "service error");
    public static CodeMsg PASSWORD_EMPTY = new CodeMsg(500200, "password can not empty");
    public static CodeMsg MOBILE_EMPTY = new CodeMsg(500201, "mobile phone can not empty");
    public static CodeMsg MOBILE_ERROR = new CodeMsg(500202, "mobile phone error");
    public static CodeMsg MOBILE_NOT_EXIST = new CodeMsg(500203, "USER NOT IN");
    public static CodeMsg PASSWORD_ERROR = new CodeMsg(500204, "password is wrong");
    public static CodeMsg Bind_ERROR = new CodeMsg(500205, "Bind is wrong:%s");



    private CodeMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public CodeMsg fillArgs(Object... args){
        int code=this.code;
        String message= String.format(this.msg,args);
        return  new CodeMsg(code,message);
    }



    @Override
    public String toString() {
        return "CodeMsg[code=" + code + ",msg=" + msg + "]";
    }
}
