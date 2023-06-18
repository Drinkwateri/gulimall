package com.roy.gulimall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.roy.common.utils.PageUtils;
import com.roy.gulimall.ware.entity.WareSkuEntity;

import java.util.Map;

/**
 * 商品库存
 *
 * @author ruofan
 * @email roydrinkwaterli@gmail.com
 * @date 2023-06-17 23:16:24
 */
public interface WareSkuService extends IService<WareSkuEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

