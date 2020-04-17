package com.changgou.search.service;

public interface ESManagerService {

    /**
     * 创建索引库结构
     */
    public void createIndexAndMapping();

    /**
     * 导入全部数据到ES索引库
     */
    public void importAll();

    /**
     * 根据spuid导入数据到ES索引库
     *
     * @param spuId 商品id
     */
    public void importDataToESBySpuId(String spuId);

}