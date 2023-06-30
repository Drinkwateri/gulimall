
package com.roy.gulimall.product.service.impl;

import com.roy.gulimall.product.entity.AttrEntity;
import com.roy.gulimall.product.service.AttrService;
import com.roy.gulimall.product.vo.AttrGroupWithAttrsVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.roy.common.utils.PageUtils;
import com.roy.common.utils.Query;

import com.roy.gulimall.product.dao.AttrGroupDao;
import com.roy.gulimall.product.entity.AttrGroupEntity;
import com.roy.gulimall.product.service.AttrGroupService;
import org.springframework.util.StringUtils;


@Service("attrGroupService")
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupDao, AttrGroupEntity> implements AttrGroupService {
    @Autowired
    AttrService attrService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrGroupEntity> page = this.page(
                new Query<AttrGroupEntity>().getPage(params),
                new QueryWrapper<AttrGroupEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params, Long categlogId) {
        String key = (String)params.get("key");
        // select * from attr_group where catelog_id = ? and (attr_group_id = key or attr_group_name like %key%)

        //QueryWrapper的范型类型 -> 查哪张表就写哪个表对应的实体类型
        QueryWrapper<AttrGroupEntity> wrapper = new QueryWrapper<AttrGroupEntity>();
        if(!StringUtils.isEmpty(key)){
            //如果能查到，就继续构造QueryWrapper
            wrapper.and((obj) -> {
                obj.eq("attr_group_id", key).or().like("attr_group_name", key);
            });
        }

        //如果三级分类Id catelogId为0，就查询所有的数据
        if(categlogId == 0){
            //查询所有
            IPage<AttrGroupEntity> page = this.page(new Query<AttrGroupEntity>().getPage(params), new QueryWrapper<AttrGroupEntity>());
            return new PageUtils(page);
        }else{

            wrapper.eq("catelog_id", categlogId);
            IPage<AttrGroupEntity> page = this.page(new Query<AttrGroupEntity>().getPage(params), wrapper);
            return new PageUtils(page);
        }
    }

    /**
     * 根据分类Id查出所有的分组以及这些组里面的属性
     * @param catelogId
     * @return
     */
    @Override
    public List<AttrGroupWithAttrsVo> getAttrGroupWithAttrsByCatelogId(Long catelogId) {

        // 1. 根据分类Id 查询该分类Id对应的所有分组信息(AttrGroupEntity)
        List<AttrGroupEntity> attrGroupEntities = this.list(new QueryWrapper<AttrGroupEntity>().eq("catelog_id", catelogId));

        // 2. 查出每个属性分组的所有属性
        List<AttrGroupWithAttrsVo> collect = attrGroupEntities.stream().map(item ->{
            AttrGroupWithAttrsVo attrGroupWithAttrsVo = new AttrGroupWithAttrsVo();
            //将所有查到的对应分组信息 复制到新建的AttrGroupWithAttrsVo中
            BeanUtils.copyProperties(item, attrGroupWithAttrsVo);

            //根据分组Id 查找所有拥有该分组Id的AttrEntity
            List<AttrEntity> attrEntities = attrService.getRelationAttr(attrGroupWithAttrsVo.getAttrGroupId());
            // 将查到的对应AttrEntity直接赋值给Vo
            attrGroupWithAttrsVo.setAttrs(attrEntities);
            return attrGroupWithAttrsVo;
        }).collect(Collectors.toList());
        return collect;
    }
}