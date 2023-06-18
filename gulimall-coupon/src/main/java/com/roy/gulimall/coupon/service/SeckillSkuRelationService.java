package com.roy.gulimall.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.roy.common.utils.PageUtils;
import com.roy.gulimall.coupon.entity.SeckillSkuRelationEntity;

import java.util.Map;

/**
 * 秒杀活动商品关联
 *
 * @author ruofan
 * @email roydrinkwaterli@gmail.com
 * @date 2023-06-17 22:56:28
 */
public interface SeckillSkuRelationService extends IService<SeckillSkuRelationEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

