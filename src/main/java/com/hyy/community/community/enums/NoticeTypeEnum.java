package com.hyy.community.community.enums;

public enum NoticeTypeEnum {
    LIKE_QUESTION(101,"赞了你的话题"),
    LIKE_COMMENT(102,"赞了你的评论"),
    REPLY_QUESTION(201,"回复了你的话题"),
    REPLY_COMMENT(202,"回复了你的评论");

    private Integer code;
    private String msg;

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    NoticeTypeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
