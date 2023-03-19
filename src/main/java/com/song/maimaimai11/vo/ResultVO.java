package com.song.maimaimai11.vo;

import lombok.Data;

@Data
public class ResultVO {
    private int code;//响应码
    private String msg;
    private Object data;

    public ResultVO(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ResultVO(int code, Object data) {
        this.code = code;
        this.data = data;
    }

    public ResultVO(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
