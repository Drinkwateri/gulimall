package com.roy.gulimall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.roy.common.utils.PageUtils;
import com.roy.gulimall.order.entity.MqMessageEntity;

import java.util.Map;

/**
 * 
 *
 * @author ruofan
 * @email roydrinkwaterli@gmail.com
 * @date 2023-06-17 23:13:38
 */
public interface MqMessageService extends IService<MqMessageEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

