package com.song.maimaimai11.controller;

import com.song.maimaimai11.bean.TypeInfo;
import com.song.maimaimai11.service.TypeInfoService;
import com.song.maimaimai11.util.StringUtil;
import com.song.maimaimai11.vo.LayuiVO;
import com.song.maimaimai11.vo.ResultVO;
import io.swagger.annotations.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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
}
