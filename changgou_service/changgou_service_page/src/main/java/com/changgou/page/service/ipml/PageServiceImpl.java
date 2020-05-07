package com.changgou.page.service.ipml;

import com.alibaba.fastjson.JSON;
import com.changgou.entity.Result;
import com.changgou.goods.feign.CategoryFeign;
import com.changgou.goods.feign.SkuFeign;
import com.changgou.goods.feign.SpuFeign;
import com.changgou.goods.pojo.Category;
import com.changgou.goods.pojo.Sku;
import com.changgou.goods.pojo.Spu;
import com.changgou.page.service.PageService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PageServiceImpl implements PageService {

    @Autowired
    private SpuFeign spuFeign;

    @Autowired
    private CategoryFeign categoryFeign;

    @Autowired
    private SkuFeign skuFeign;

    @Value("${pagepath}")
    private String pagepath;

    @Autowired
    private TemplateEngine templateEngine;

    @Override
    public void generateItemPage(String spuId) {
        //获取context对象,用于存放商品详情数据
        Context context = new Context();
        Map<String, Object> itemData = this.findItemData(spuId);
        context.setVariables(itemData);
        //获取商品详情页生成的指定位置
        File dir = new File(pagepath);
        //判断商品详情页文件夹是否存在,不存在则创建
        if (!dir.exists()) {
            dir.mkdirs();
        }
        //定义输出流,进行文件生成
        File file = new File(dir + "/" + spuId + ".html");
        Writer out = null;
        try {
            out = new PrintWriter(file);
            //生成文件
            /**
             * 1.模板名称
             * 2.context对象,包含了模板需要的数据
             * 3.输出流,指定文件输出位置
             */
            templateEngine.process("item", context, out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭流
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    //获取静态化页面数据
    private Map<String, Object> findItemData(String spuId) {

        Map<String, Object> resultMap = new HashMap<>();

        //获取spu信息
        Result<Spu> spuResult = spuFeign.findSpuById(spuId);
        Spu spu = spuResult.getData();
        resultMap.put("spu", spu);

        //获取图片信息
        if (spu != null) {
            if (!StringUtils.isEmpty(spu.getImages())) {
                resultMap.put("imageList", spu.getImages().split(","));
            }
        }
        //获取分类信息
        Category category1 = categoryFeign.findById(spu.getCategory1Id()).getData();
        resultMap.put("category1", category1);
        Category category2 = categoryFeign.findById(spu.getCategory2Id()).getData();
        resultMap.put("category2", category2);
        Category category3 = categoryFeign.findById(spu.getCategory3Id()).getData();
        resultMap.put("category3", category3);
        //获取sku集合信息
        List<Sku> skuList = skuFeign.findSkuListBySpuId(spuId);
        resultMap.put("skuList", skuList);

        //获取商品规格信息
        resultMap.put("specificationList", JSON.parseObject(spu.getSpecItems(), Map.class));

        return resultMap;
    }
}