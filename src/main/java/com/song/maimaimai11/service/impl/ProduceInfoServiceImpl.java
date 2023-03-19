package com.song.maimaimai11.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.song.maimaimai11.bean.ProductInfo;
import com.song.maimaimai11.mapper.ProductInfoMapper;
import com.song.maimaimai11.service.ProductInfoService;
import org.springframework.stereotype.Service;

@Service
public class ProduceInfoServiceImpl extends ServiceImpl<ProductInfoMapper, ProductInfo> implements ProductInfoService {
}
