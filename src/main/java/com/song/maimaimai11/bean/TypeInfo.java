package com.song.maimaimai11.bean;

import lombok.Data;

@Data
public class TypeInfo {
   private Integer tno;
   private String tname;
   private Integer status;

   public TypeInfo(String tname) {
      this.tname = tname;
   }
}

