package com.song.maimaimai11.controller;

import com.song.maimaimai11.bean.TypeInfo;
import com.song.maimaimai11.service.TypeInfoService;
import com.song.maimaimai11.util.StringUtil;
import com.song.maimaimai11.vo.LayuiVO;
import com.song.maimaimai11.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "商品类型控制接口")
@RestController
@RequestMapping("/type")
public class TypeInfoController {
    @Resource
    TypeInfoService typeInfoService;

    @ApiOperation(value = "增加商品种类信息" ,notes = "这里只需要添加商品的名字就好了")
    @PostMapping("/add")
    public ResultVO add(@ApiParam(name = "name",value = "商品种类名称" ,required = true) String name){
        boolean checkNull = StringUtil.checkNull(name);
        if(checkNull) return new ResultVO(510,"数据验证失败");
        boolean result = typeInfoService.save(new TypeInfo(name));
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
            return new ResultVO(200,list);
        }
        return new ResultVO(600,"暂无数据");
    }

    @GetMapping("/findAll")
    public LayuiVO findAll(){
        List<TypeInfo> list = typeInfoService.list();
        if(list != null && !list.isEmpty()){
            return new LayuiVO(200,list);
        }
        return new LayuiVO(600,"暂无数据");
    }
}
