package com.song.maimaimai11.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.song.maimaimai11.bean.User;
import com.song.maimaimai11.mapper.UserMapper;
import com.song.maimaimai11.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
