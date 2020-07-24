package com.hyy.community.community.exception;

public enum CustiomizeErrorCode implements ICustomizeErrorCode {

    QUESTION_NOT_FOUND("你找的话题不在了,要不换一个试试？"),
    COMMENT_NOT_FOUND("你回复的话题/评论不见了,要不换一个试试？"),
    USER_NOT_LOGIN("您还未登录,请先登录后重试！");

    @Override
    public String getMessage() {
        return message;
    }

    private String message;

    CustiomizeErrorCode(String message){
        this.message=message;
    }

}
