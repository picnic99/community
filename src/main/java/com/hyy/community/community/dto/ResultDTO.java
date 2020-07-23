package com.hyy.community.community.dto;

import lombok.Data;

import java.util.Map;
@Data
public class ResultDTO {
    private Integer code;
    private String msg;
    private Object data;

    public ResultDTO(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}
