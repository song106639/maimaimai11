package com.song.maimaimai11.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.song.maimaimai11.bean.ShopCar;
import com.song.maimaimai11.exception.NoCountException;
import com.song.maimaimai11.exception.NoMoneyException;

import java.util.List;


public interface ShopCarService  extends IService<ShopCar> {
    //实现批量结算的功能
    boolean batchSettle(int userId, List<ShopCar> shopId) throws NoCountException, NoMoneyException;
}
