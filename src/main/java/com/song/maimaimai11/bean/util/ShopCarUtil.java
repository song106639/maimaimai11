package com.song.maimaimai11.bean.util;

import com.song.maimaimai11.bean.ProductInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
@ApiModel(value = "商品信息的辅助工具类")
public class ShopCarUtil {
    @ApiModelProperty("商品信息的详细信息")
    private ProductInfo productInfo;
    @ApiModelProperty("该商品信息在购物车里面的数量")
    private int count;
}
