package com.roy.gulimall.member.dao;

import com.roy.gulimall.member.entity.MemberEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员
 * 
 * @author ruofan
 * @email roydrinkwaterli@gmail.com
 * @date 2023-06-17 23:07:13
 */
@Mapper
public interface MemberDao extends BaseMapper<MemberEntity> {
	
}
