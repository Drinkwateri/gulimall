package com.roy.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.roy.common.utils.PageUtils;
import com.roy.gulimall.product.entity.SkuImagesEntity;

import java.util.Map;

/**
 * sku图片
 *
 * @author ruofan
 * @email roydrinkwaterli@gmail.com
 * @date 2023-06-17 19:17:18
 */
public interface SkuImagesService extends IService<SkuImagesEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

