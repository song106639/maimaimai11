package com.song.maimaimai11.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.song.maimaimai11.bean.ProductInfo;
import com.song.maimaimai11.bean.TypeInfo;
import com.song.maimaimai11.service.ProductInfoService;
import com.song.maimaimai11.service.TypeInfoService;
import com.song.maimaimai11.util.StringUtil;
import com.song.maimaimai11.vo.LayuiVO;
import com.song.maimaimai11.vo.ResultVO;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

@Api(tags = "商品类型控制接口")
@RestController
@ApiResponses({@ApiResponse(code = 200,message = "成功"),@ApiResponse(code = 510,message = "不存在"),@ApiResponse(code = 500,message = "内部服务错误")})
@RequestMapping("/type")
public class TypeInfoController {
    @Resource
    TypeInfoService typeInfoService;

    @ApiOperation(value = "增加商品种类信息" ,notes = "这里只需要添加商品的名字就好了")
    @PostMapping("/add")
    public ResultVO add( @RequestParam  @ApiParam(name = "tname",value = "商品种类名称",example = "水果",required = true)String  tname){
        boolean checkNull = StringUtil.checkNull(tname);
        if(checkNull) return new ResultVO(510,"数据验证失败");
        TypeInfo typeInfo = new TypeInfo(tname);
        typeInfo.setStatus(1);
        boolean result = typeInfoService.save(typeInfo);
        if(result){
            return new ResultVO(200,"成功");
        }
        return new ResultVO(500,"失败");
    }

    /**
     * 普通查询，这里没有做分页查询
     * @return
     */
    @GetMapping("/find")
    public ResultVO finds(){
        List<TypeInfo> list = typeInfoService.list();
        if(list != null && !list.isEmpty()){
            return new ResultVO(0,list);
        }
        return new ResultVO(600,"暂无数据");
    }

    @GetMapping("/findAll")
    public LayuiVO findAll(){
        List<TypeInfo> list = typeInfoService.list();
        if(list != null && !list.isEmpty()){
            return new LayuiVO(0,list);
        }
        return new LayuiVO(600,"暂无数据");
    }
    @Autowired
    ProductInfoService productInfoService;
    //分页查询
    @ApiOperation(value = "后台管理分页查询",notes = "没有参数查询所有，可以加条件来进行条件查询，pName代表名称，pInto代表介绍")
    @GetMapping("/page")
    private LayuiVO<Page<ProductInfo>> getPages(@RequestParam @ApiParam("当前页面") int currentPage,@RequestParam @ApiParam("页面大小") int pageSize
            ,@RequestParam(required = false) @ApiParam("商品名称") String pName,@RequestParam(required = false) @ApiParam("商品介绍") String pIntro){
        //根据名称和introduce来进行查询
        LambdaQueryWrapper<ProductInfo> productInfoLqw = new LambdaQueryWrapper<>();
        productInfoLqw.like(pName!=null, ProductInfo::getPname,pName)
                .like(pIntro!=null,ProductInfo::getIntro,pIntro);
        Page<ProductInfo> productInfoPage = new Page<>(currentPage, pageSize);
        Page<ProductInfo> page = productInfoService.page(productInfoPage, productInfoLqw);
        return new LayuiVO<Page<ProductInfo>>(200,page);
    }

    @Value("${file.upload.url}")
    private String uploadFile;
    //实现多文件上传
    @PostMapping("/addProduce")
    @ApiOperation("添加商品信息")
    public LayuiVO addProduct( ProductInfo productInfo,  MultipartFile  file) throws IOException {
        productInfo.setPicture("11122.png");
        boolean save = productInfoService.save(productInfo);
        System.out.println(productInfo.getPno());
        productInfo.setPicture(productInfo.getPno()+".jpg");
        productInfoService.updateById(productInfo);
        File dest = new File(uploadFile, productInfo.getPno() + ".jpg");
        if(file!=null) file.transferTo(dest);
        //存入数据
        productInfo.setPicture(productInfo.getPno()+".jpg");
        return new LayuiVO<>(200,"成功");
    }
}
