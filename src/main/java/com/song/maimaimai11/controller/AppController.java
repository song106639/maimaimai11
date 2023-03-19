package com.song.maimaimai11.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.song.maimaimai11.bean.ProductInfo;
import com.song.maimaimai11.bean.ShopCar;
import com.song.maimaimai11.bean.TypeInfo;
import com.song.maimaimai11.bean.User;
import com.song.maimaimai11.bean.util.ShopCarUtil;
import com.song.maimaimai11.exception.NoCountException;
import com.song.maimaimai11.exception.NoMoneyException;
import com.song.maimaimai11.service.ProductInfoService;
import com.song.maimaimai11.service.ShopCarService;
import com.song.maimaimai11.service.TypeInfoService;
import com.song.maimaimai11.service.UserService;
import com.song.maimaimai11.vo.LayuiVO;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/app")
@ApiResponses({@ApiResponse(code = 200,message = "成功"),@ApiResponse(code = 510,message = "操作失败"),@ApiResponse(code = 500,message = "内部服务错误")})
@Api(tags = "小程序接收端口")
public class AppController {
    @Resource
    UserService userService;

    //得到用户信息
    @ApiOperation(value = "得到用户信息")
    @GetMapping("/getUser")
    public LayuiVO<User> getUser(@ApiParam(name = "id" ,value = "用户id") @RequestParam Integer id){
        User user = userService.getById(id);
        if(user==null) return new LayuiVO<User>(510,"用户信息为空");
        return new LayuiVO<User>(200,user);
    }

    @Resource
    ShopCarService shopCarService;
    //添加商品信息到购物车
    @ApiOperation(value = "添加商品到购物车",notes = "这里的count如果不传就默认是1；如果大于1，比如说3，就是3；如果想减少一个商品在购物车里面的数量，那么传入负数就可以了")
    @PostMapping("/addShopToCar")
    public LayuiVO<Object> addShopToCar( @RequestParam @ApiParam(value = "用户id",required = true) String userId
            ,@RequestParam @ApiParam(value = "商品id" ,required = true) int shopId,@RequestParam @ApiParam(value = "商品的数量") int count){
        if(count==0) count=1;
        //首先要判断商品这个购物车在不在
        LambdaQueryWrapper<ShopCar> shopCarLqw = new LambdaQueryWrapper<>();
        shopCarLqw.eq(ShopCar::getId,userId).eq(ShopCar::getPno,shopId);
        ShopCar currentCar  = shopCarService.getOne(shopCarLqw);
        //判断当前购物车里面的内容存不存在
        //如果不存在，那么，我们就要像这个购物车里面添加一个新的成员
        //存在就要在原先的基础上去加
        //如果压我减少在购物车里面当前商品的数量传入负数就就可以了

        //但是这里判断是不是小于0
        if(currentCar==null){
            boolean isSuccess = shopCarService.save(new ShopCar(userId, shopId,count));
            if(isSuccess) return new LayuiVO<Object>(200,"添加成功");
            return new LayuiVO<Object>(510,"操作失败");
        }
        currentCar.setCount(currentCar.getCount()+count);
        //如果小于0就直接删除
        if(count<=0) shopCarService.remove(shopCarLqw);
        shopCarService.update(currentCar,shopCarLqw);
        return new LayuiVO<Object>(200,"添加成功");
    }

    //批量清除购物车
    @ApiOperation(value = "批量清除购物车",notes = "传在json里面的数据如：[111,112]就会删除当前用户111，112的购物车里面的商品")
    @DeleteMapping("/removeSomeShop")
    public LayuiVO<Object> removeSomeShop(@RequestParam @ApiParam(value = "对应用户的id",required = true) int userId
            , @RequestBody @ApiParam(value = "删除当前用户所对应购物车里面的商品" ,example = "[111,112,113") List<Integer> shopIdList){
        LambdaQueryWrapper<ShopCar> shopCarLqw = new LambdaQueryWrapper<>();
        shopCarLqw.eq(ShopCar::getId,userId);
        for (Integer shopId : shopIdList) {
            shopCarLqw.eq(ShopCar::getPno,shopId);
            shopCarService.remove(shopCarLqw);
        }
        return new LayuiVO<>(200,"删除成功！");
    }

    //结算功能
    @PostMapping("/settlement")
    @ApiOperation(value = "结算功能",notes = "这里的结算功能同时会清空购物车")
    public LayuiVO settlement(@RequestParam @ApiParam(value = "用户id") int userId,@ApiParam(value = "删除对应的pno的列表") @RequestBody  List<ShopCar> shopCarList){
        boolean isSuccess  = false;
        try {
            isSuccess = shopCarService.batchSettle(userId, shopCarList);
        } catch (NoCountException e) {
           return new LayuiVO(510,"库存不足");
        } catch (NoMoneyException e) {
           return new LayuiVO(510,"您的钱不够");
        }
        if(isSuccess) return new LayuiVO(200,"结算成功");
        return new LayuiVO(510,"结算失败，未知原因");
    }


    @Autowired
    TypeInfoService typeInfoService;
    @Autowired
    ProductInfoService productInfoService;


    @ApiOperation(value = "多字段模糊查询" ,notes = "如果类型没有传就是查询所有的商品信息")
    @GetMapping("/dimSearch")
    //实现一些查询功能 -手办的模糊查询
    public LayuiVO<List<ProductInfo>> getProductDimSearch(@RequestParam @ApiParam("查询的内容")  String msg,@RequestParam(required = false) @ApiParam(value = "指定的类型名字" ,required = false) String type) {
        //限制类型，根据什么都可以进行模糊查询
        LambdaQueryWrapper<ProductInfo> productInfoLqw = new LambdaQueryWrapper<>();
        List<ProductInfo> productInfoList=null;
        if (type != null){
            LambdaQueryWrapper<TypeInfo> typeInfoLqw = new LambdaQueryWrapper<>();
            typeInfoLqw.eq(TypeInfo::getTname, type);
            TypeInfo one = typeInfoService.getOne(typeInfoLqw);
            if(one==null) return new LayuiVO<>(510,"当前类型不存在");
            //限制商品的类型
            productInfoLqw.eq(ProductInfo::getTno,one.getTno()).like(ProductInfo::getPname,msg).or().like(ProductInfo::getIntro,msg).or().like(ProductInfo::getDescr,msg);
        }
        productInfoLqw.like(ProductInfo::getPname,msg).or().like(ProductInfo::getIntro,msg).or().like(ProductInfo::getDescr,msg);
        List<ProductInfo> list = productInfoService.list(productInfoLqw);
        return new LayuiVO<List<ProductInfo>>(200,list);
    }

    //得到购物车的信息
    @GetMapping("/getShopCar")
    @ApiOperation(value = "得到购物车里面的商品的信息")
    public LayuiVO<List<ShopCarUtil>> getAllShopCars(@RequestParam int userId){
        LambdaQueryWrapper<ShopCar> shopCarLqw = new LambdaQueryWrapper<>();
        shopCarLqw.eq(ShopCar::getId,userId);
        List<ShopCar> list = shopCarService.list(shopCarLqw);
        ArrayList<ShopCarUtil> shopCarUtilArrayList = new ArrayList<>();
        for (ShopCar shopCar : list) {
            ProductInfo productInfo = productInfoService.getById(shopCar.getPno());
            shopCarUtilArrayList.add(new ShopCarUtil(productInfo,shopCar.getCount()));
        }
        return new LayuiVO<List<ShopCarUtil>>(200,shopCarUtilArrayList);
    }
}
