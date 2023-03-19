package com.song.maimaimai11.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@ApiModel(description = "购物车实体")
@AllArgsConstructor
public class ShopCar {
    @ApiModelProperty("用户id")
    private String  id;
    @ApiModelProperty("商品id")
    private Integer pno;
    @ApiModelProperty("购物车商品的数量")
    private Integer count;
}

