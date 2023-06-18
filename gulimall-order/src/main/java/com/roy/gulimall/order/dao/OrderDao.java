package com.roy.gulimall.order.dao;

import com.roy.gulimall.order.entity.OrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单
 * 
 * @author ruofan
 * @email roydrinkwaterli@gmail.com
 * @date 2023-06-17 23:13:38
 */
@Mapper
public interface OrderDao extends BaseMapper<OrderEntity> {
	
}
