package com.song.maimaimai11.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;
import org.omg.CORBA.INTERNAL;

@Data
@ApiModel(description = "一些商品信息")
public class ProductInfo {
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("商品信息")
    private Long pno;
    private String pname;
    private Integer tno;
    private Double price;
    private String intro;
    private String picture;
    private String descr;
    private Integer status;
    private Integer balance;
    @TableField(exist = false)
    private String tname;
}
