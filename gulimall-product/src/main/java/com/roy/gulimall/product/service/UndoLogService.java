package com.roy.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.roy.common.utils.PageUtils;
import com.roy.gulimall.product.entity.UndoLogEntity;

import java.util.Map;

/**
 * 
 *
 * @author ruofan
 * @email roydrinkwaterli@gmail.com
 * @date 2023-06-17 19:17:18
 */
public interface UndoLogService extends IService<UndoLogEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

