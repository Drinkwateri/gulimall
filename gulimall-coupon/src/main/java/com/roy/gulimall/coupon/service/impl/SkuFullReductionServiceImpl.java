package com.roy.gulimall.coupon.service.impl;

import com.roy.common.to.MemberPrice;
import com.roy.common.to.SkuReductionTo;
import com.roy.gulimall.coupon.entity.MemberPriceEntity;
import com.roy.gulimall.coupon.entity.SkuLadderEntity;
import com.roy.gulimall.coupon.service.MemberPriceService;
import com.roy.gulimall.coupon.service.SkuLadderService;
import com.roy.gulimall.coupon.service.SpuBoundsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.roy.common.utils.PageUtils;
import com.roy.common.utils.Query;

import com.roy.gulimall.coupon.dao.SkuFullReductionDao;
import com.roy.gulimall.coupon.entity.SkuFullReductionEntity;
import com.roy.gulimall.coupon.service.SkuFullReductionService;


@Service("skuFullReductionService")
public class SkuFullReductionServiceImpl extends ServiceImpl<SkuFullReductionDao, SkuFullReductionEntity> implements SkuFullReductionService {
    @Autowired
    SkuLadderService skuLadderService;

    @Autowired
    SpuBoundsService spuBoundsService;

    @Autowired
    MemberPriceService memberPriceService;


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SkuFullReductionEntity> page = this.page(
                new Query<SkuFullReductionEntity>().getPage(params),
                new QueryWrapper<SkuFullReductionEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveSkuReduction(SkuReductionTo skuReductionTo) {
        // 1.保存阶梯信息 sms_sku_ladder
        //  / /  / sms_spu_bounds\
        SkuLadderEntity skuLadderEntity = new SkuLadderEntity();
        skuLadderEntity.setSkuId(skuReductionTo.getSkuId());
        skuLadderEntity.setFullCount(skuReductionTo.getFullCount());
        skuLadderEntity.setDiscount(skuReductionTo.getDiscount());
        skuLadderEntity.setAddOther(skuReductionTo.getCountStatus());
        if(skuReductionTo.getFullCount() > 0){
            skuLadderService.save(skuLadderEntity);
        }

        // 2. 保存满减信息 sms_sku_full_reduction
        SkuFullReductionEntity skuFullReductionEntity = new SkuFullReductionEntity();
        BeanUtils.copyProperties(skuReductionTo, skuFullReductionEntity);
        if(skuFullReductionEntity.getFullPrice().compareTo(new BigDecimal(0)) == 1){
            this.save(skuFullReductionEntity);
        }

        // 3. 保存会员价格 sms_member_price
        List<MemberPrice> memberPrice = skuReductionTo.getMemberPrice();
        List<MemberPriceEntity> memberPriceEntities = memberPrice.stream().map(price -> {
            MemberPriceEntity memberPriceEntity = new MemberPriceEntity();
            memberPriceEntity.setSkuId(skuReductionTo.getSkuId());
            memberPriceEntity.setMemberLevelId(price.getId());
            memberPriceEntity.setMemberLevelName(price.getName());
            memberPriceEntity.setMemberPrice(price.getPrice());
            memberPriceEntity.setAddOther(1); //默认叠加其他优惠
            return memberPriceEntity;
        }).filter(item -> {
            return item.getMemberPrice().compareTo(new BigDecimal(0)) == 1;
        }).collect(Collectors.toList());
        memberPriceService.saveBatch(memberPriceEntities);
    }

}