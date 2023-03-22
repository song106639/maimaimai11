package com.song.maimaimai11.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.song.maimaimai11.bean.ProductInfo;
import com.song.maimaimai11.bean.ShopCar;
import com.song.maimaimai11.bean.User;
import com.song.maimaimai11.exception.NoCountException;
import com.song.maimaimai11.exception.NoMoneyException;
import com.song.maimaimai11.mapper.ProductInfoMapper;
import com.song.maimaimai11.mapper.ShopCarMapper;
import com.song.maimaimai11.mapper.UserMapper;
import com.song.maimaimai11.service.ShopCarService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ShopCarServiceImpl extends ServiceImpl<ShopCarMapper, ShopCar> implements ShopCarService {
    @Resource
    UserMapper userMapper;
    @Resource
    ProductInfoMapper productInfoMapper;
    @Resource
    ShopCarMapper shopCarMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean batchSettle(int userId, List<ShopCar> shopList) throws NoCountException, NoMoneyException {
        double sum=0;
        //商品数量减减去对应的数量
        //同时清空购物车
        if(shopList.isEmpty()){
            //得到这个用户所有的购物车信息
            LambdaQueryWrapper<ShopCar> shopCarLqw = new LambdaQueryWrapper<>();
            shopCarLqw.eq(ShopCar::getId,userId);
            List<ShopCar> shopCars = shopCarMapper.selectList(shopCarLqw);
            for (ShopCar shopCar : shopCars) {
                ProductInfo productInfo = productInfoMapper.selectById(shopCar.getPno());
                if(productInfo.getBalance()-shopCar.getCount()<0) throw new NoCountException("商品"+productInfo.getPname()+"库存不足");
                productInfo.setBalance(productInfo.getBalance()-shopCar.getCount());
                productInfoMapper.updateById(productInfo);
                sum+=productInfo.getPrice()*shopCar.getCount();
                LambdaQueryWrapper<ShopCar> shopcarremove = new LambdaQueryWrapper<>();
                shopcarremove.eq(ShopCar::getId,userId).eq(ShopCar::getPno,shopCar.getPno());
                shopCarMapper.delete(shopcarremove);
            }

        }else {
            for (ShopCar shopCar : shopList) {
                ProductInfo productInfo = productInfoMapper.selectById(shopCar.getPno());
                if(productInfo.getBalance()-shopCar.getCount()<0) throw new NoCountException("商品"+productInfo.getPname()+"库存不足");
                productInfo.setBalance(productInfo.getBalance()-shopCar.getCount());
                productInfoMapper.updateById(productInfo);
                sum+=productInfo.getPrice()*shopCar.getCount();
                //清空对应的购物车--这里不能清空购物车，如果小于0才会清空
                LambdaQueryWrapper<ShopCar> shopCarLqw = new LambdaQueryWrapper<>();
                shopCarLqw.eq(ShopCar::getId,userId).eq(ShopCar::getPno,shopCar.getPno());
                ShopCar shopCar1 = shopCarMapper.selectOne(shopCarLqw);
                if(shopCar1==null) throw new NullPointerException();
                shopCar1.setCount(shopCar1.getCount()-shopCar.getCount());
                if(shopCar1.getCount()<=0)shopCarMapper.delete(shopCarLqw);
                else shopCarMapper.update(shopCar1,shopCarLqw);
            }
        }

        //计算商品价格
        User user = userMapper.selectById(userId);
        //结算
        if(user.getMoney()<sum) throw new NoMoneyException("用户的金额不够");
        user.setMoney(user.getMoney()-sum);
        int i = userMapper.updateById(user);
        return  true;
    }
}
