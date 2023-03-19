package com.song.maimaimai11.vo;

import lombok.Data;

@Data
public class LayuiVO {
    private int code;
    private String msg;
    private int count;
    private Object data;//商品数据

    public LayuiVO(int code, Object data) {
        this.code = code;
        this.data = data;
    }

    public LayuiVO(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
