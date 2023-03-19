package com.song.maimaimai11.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.models.auth.In;
import lombok.Data;
import org.omg.CORBA.INTERNAL;

@Data
public class ProductInfo {
    @TableId(type = IdType.AUTO)
    private Long pno;
    private String pname;
    private Integer tno;
    private Double price;
    private String intro;
    private String picture;
    private String descr;
    private Integer status;
    private Integer balance;
}
