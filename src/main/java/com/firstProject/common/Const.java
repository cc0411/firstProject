package com.firstProject.common;

/**
 * created by 吴家俊 on 2018/7/24.
 */
public class Const {

    public static final String REGULAR_USER = "regularUser";

    public enum ExchangeStatusEnum{
        EXCHANGED(1,"已交换"),
        UNEXCHANGED(2,"未交换");
        ExchangeStatusEnum(int code,String value){
            this.code = code;
            this.value = value;
        }

        private String value;
        private int code;

        public String getValue() {
            return value;
        }

        public int getCode() {
            return code;
        }
    }

}