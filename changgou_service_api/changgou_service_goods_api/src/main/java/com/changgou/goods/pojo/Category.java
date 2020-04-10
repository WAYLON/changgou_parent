package com.changgou.goods.pojo;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * category实体类
 *
 * @author 黑马架构师2.5
 */
@Table(name = "tb_category")
@Data
public class Category implements Serializable {

    @Id
    private Integer id;//分类ID
    private String name;//分类名称
    private Integer goodsNum;//商品数量
    private String isShow;//是否显示
    private String isMenu;//是否导航
    private Integer seq;//排序
    private Integer parentId;//上级ID
    private Integer templateId;//模板ID

}
