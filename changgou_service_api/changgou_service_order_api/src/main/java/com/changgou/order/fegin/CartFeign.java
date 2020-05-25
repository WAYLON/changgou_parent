package com.changgou.order.fegin;

import com.changgou.entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "order")
public interface CartFeign {

    /**
     * 添加购物车
     *
     * @param skuId
     * @param num
     * @return
     */
    @GetMapping("/cart/add")
    public Result add(@RequestParam("skuId") String skuId, @RequestParam("num") Integer num);

    /***
     * 查询用户购物车列表
     * @return
     */
    @GetMapping(value = "/cart/list")
    public Map list();
}