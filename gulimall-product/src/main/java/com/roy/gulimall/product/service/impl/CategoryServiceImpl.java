package com.roy.gulimall.product.service.impl;

import com.roy.gulimall.product.service.CategoryBrandRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.roy.common.utils.PageUtils;
import com.roy.common.utils.Query;

import com.roy.gulimall.product.dao.CategoryDao;
import com.roy.gulimall.product.entity.CategoryEntity;
import com.roy.gulimall.product.service.CategoryService;
import org.springframework.transaction.annotation.Transactional;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {
//    @Autowired
//    CategoryDao categoryDao;

    @Autowired
    CategoryBrandRelationService categoryBrandRelationService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 以树的形式返回所有的菜单信息
     * @return
     */
    @Override
    public List<CategoryEntity> listWithTree() {
        // 1.查处所有分类
        List<CategoryEntity> entities = baseMapper.selectList(null);

        // 2.组装成父子的树形结构
            // 2.1 找到一级分类
        List<CategoryEntity> level1Menus = entities.stream().filter((categoryEntity) -> {
            return categoryEntity.getCatLevel() == 1;
        }).map((menu) -> {
            menu.setChildren(getChildrenMenu(menu, entities));
            return menu;
        }).sorted((menu1, menu2) ->{
            return (menu1.getSort() == null ? 0 : menu1.getSort()) - (menu2.getSort() == null ? 0 : menu2.getSort());
        }).collect(Collectors.toList());

        return level1Menus;
    }

    /**
     * 递归查找每个菜单的子菜单
     * @return
     */
    private List<CategoryEntity> getChildrenMenu(CategoryEntity root, List<CategoryEntity> all){

        List<CategoryEntity> childrenMenu = all.stream().filter(categoryEntity -> {
            return categoryEntity.getParentCid() == root.getCatId();
        }).map(categoryEntity -> {
            // 1.找到子菜单，递归
            categoryEntity.setChildren(getChildrenMenu(categoryEntity, all));
            return categoryEntity;
        }).sorted((menu1, menu2) ->{
            // 2.菜单排序
            return (menu1.getSort() == null ? 0 : menu1.getSort()) - (menu2.getSort() == null ? 0 : menu2.getSort());
        }).collect(Collectors.toList());

        return childrenMenu;
    }

    @Override
    public void removeMenuByIds(List<Long> list) {
        // TODO 1. 检查当前删除的菜单 是否被别的地方引用。如果引用了就不能被删除

        //逻辑删除（直接删除数据库的项目 -> 物理删除）
        baseMapper.deleteBatchIds(list);
    }

    //[2,25,255]
    @Override
    public Long[] findCatelogPath(Long catelogId) {
        List<Long> paths = new ArrayList<>();

        findParentPath(catelogId, paths);

        Collections.reverse(paths);

        return paths.toArray(new Long[paths.size()]);
    }


    private void findParentPath(Long catelogId, List<Long> paths){
        //1. 收集当前节点id
        paths.add(catelogId);
        CategoryEntity categoryEntity = this.getById(catelogId);
        if(categoryEntity.getParentCid() != 0){
            //存在父节点就继续递归查询
            findParentPath(categoryEntity.getParentCid(), paths);
        }
    }

    /**
     * 级联更新所有关联的数据
     * @param category
     */
    @Transactional
    @Override
    public void updateCascade(CategoryEntity category) {
        //先更新分类数据本身
        this.updateById(category);

        //然后更新关联表中数据 catelog_name
        categoryBrandRelationService.updateCategory(category.getCatId(), category.getName());
    }


}