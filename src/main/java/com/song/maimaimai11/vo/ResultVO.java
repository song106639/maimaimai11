package com.song.maimaimai11.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "返回的类型数据")
public class ResultVO {
    @ApiModelProperty(value = "响应码，200成功，510失败，500内部服务错误",example = "200")
    private int code;//响应码
    private String msg;
    @ApiModelProperty(value = "根据需要返回数据")
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
