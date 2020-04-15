package com.changgou.goods.pojo;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "tb_category_brand")
@Data
public class CategoryBrand implements Serializable {

    @Id
    private Integer categoryId;

    @Id
    private Integer brandId;

}