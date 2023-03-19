package com.song.maimaimai11.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TypeInfo {
   @TableId(type = IdType.AUTO)
   private Integer tno;
   private String tname;
   private Integer status;

   public TypeInfo(String tname) {
      this.tname = tname;
   }
}

