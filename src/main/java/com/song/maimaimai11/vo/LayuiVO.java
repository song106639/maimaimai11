package com.song.maimaimai11.vo;

import lombok.Data;

@Data
public class  LayuiVO<T> {
    private int code;
    private String msg;
    private T data;//商品数据

    public LayuiVO(int code, T data) {
        this.code = code;
        this.data = data;
    }

    public LayuiVO(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
