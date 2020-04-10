package com.changgou.goods.pojo;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * para实体类
 *
 * @author 黑马架构师2.5
 */
@Table(name = "tb_para")
@Data
public class Para implements Serializable {

    @Id
    private Integer id;//id
    private String name;//名称
    private String options;//选项
    private Integer seq;//排序
    private Integer templateId;//模板ID

}
