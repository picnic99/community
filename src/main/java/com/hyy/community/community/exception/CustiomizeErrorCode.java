package com.hyy.community.community.exception;

public enum CustiomizeErrorCode implements ICustomizeErrorCode {

    QUESTION_NOT_FOUND("你找的话题不在了,要不换一个试试？");



    @Override
    public String getMessage() {
        return null;
    }

    private String message;

    CustiomizeErrorCode(String message){
        this.message=message;
    }

}
