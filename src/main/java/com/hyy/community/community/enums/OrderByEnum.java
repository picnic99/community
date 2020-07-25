package com.hyy.community.community.enums;

public enum OrderByEnum {
    ORDER_BY_HOT(2),
    ORDER_BY_TIME(1);
    private Integer type;

    public Integer getType() {
        return type;
    }

    OrderByEnum(Integer type) {
        this.type = type;
    }
}
