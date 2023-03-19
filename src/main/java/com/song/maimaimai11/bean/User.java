package com.song.maimaimai11.bean;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.context.annotation.Bean;

@Data
@ApiModel(description = "用户实体类")
public class User {
    @ApiModelProperty("用户id")
    @TableId
    private String id;
    @ApiModelProperty("用户名称")
    private String userName;
    @ApiModelProperty("用户的钱")
    private Double money;
}
