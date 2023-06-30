package com.roy.gulimall.product.vo;

import lombok.Data;

@Data
public class AttrRespVo extends AttrVo{
    //传递到前端的数据集合


    /**
     *          "catelogName": "手机/数码/手机", //所属分类名字
     * 			"groupName": "主体", //所属分组名字
     */

    private String catelogName;

    private String groupName;

    private Long[] catelogPath;

}
