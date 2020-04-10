package com.changgou.goods.pojo;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * pref实体类
 *
 * @author 黑马架构师2.5
 */
@Table(name = "tb_pref")
@Data
public class Pref implements Serializable {

    @Id
    private Integer id;//ID
    private Integer cateId;//分类ID
    private Integer buyMoney;//消费金额
    private Integer preMoney;//优惠金额
    private java.util.Date startTime;//活动开始日期
    private java.util.Date endTime;//活动截至日期
    private String type;//类型
    private String state;//状态

}
