package com.roy.gulimall.order.dao;

import com.roy.gulimall.order.entity.OrderItemEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单项信息
 * 
 * @author ruofan
 * @email roydrinkwaterli@gmail.com
 * @date 2023-06-17 23:13:38
 */
@Mapper
public interface OrderItemDao extends BaseMapper<OrderItemEntity> {
	
}
