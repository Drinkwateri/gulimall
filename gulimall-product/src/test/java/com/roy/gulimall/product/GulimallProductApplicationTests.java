package com.roy.gulimall.product;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.roy.gulimall.product.entity.BrandEntity;
import com.roy.gulimall.product.service.BrandService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
class GulimallProductApplicationTests {

    @Autowired
    BrandService brandService;

    @Autowired
    OSSClient ossClient;

    @Test
    public void testUpload() throws FileNotFoundException {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
//        String endpoint = "oss-us-east-1.aliyuncs.com";
//        // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建。
//        String accessKeyId = "LTAI5tCUcAaS6QzDSnDyj3io";
//        String accessKeySecret = "gnIszcs5ORicMrhwJYn6ZWYB8ZawdA";
//
//        System.out.println("test");
//
//        // 创建OSSClient实例。
//        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 上传文件流。
        InputStream inputStream = new FileInputStream("/Users/roy/Downloads/IMG_44460F0E7297-1.jpeg");
        ossClient.putObject("gulimall-ruofan", "test1.jpg", inputStream);

        // 关闭OSSClient。
        ossClient.shutdown();
        System.out.println("上传成功.");
    }



    @Test
    void contextLoads() {

        BrandEntity brandEntity = new BrandEntity();
//        brandEntity.setBrandId(9L);
//        brandEntity.setDescript("roy");
////        brandEntity.setName("华为");
////        brandService.save(brandEntity);
////        System.out.println("保存成功");
//
//        brandService.updateById(brandEntity);

        List<BrandEntity> list = brandService.list(new QueryWrapper<BrandEntity>().eq("brand_id", 12L));
        list.forEach((item) ->{
            System.out.println(item);
        });
    }

}
