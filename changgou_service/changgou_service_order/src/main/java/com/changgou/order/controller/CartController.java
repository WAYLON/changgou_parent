package com.changgou.order.controller;

import com.changgou.entity.Result;
import com.changgou.entity.StatusCode;
import com.changgou.order.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    /**
     * 添加购物车
     *
     * @param skuId
     * @param num
     * @return
     */
    @GetMapping("/add")
    public Result add(@RequestParam("skuId") String skuId, @RequestParam("num") Integer num) {

        //暂时静态,后续动态获取
        String username = "itcast";
        cartService.addCart(skuId, num, username);

        return new Result(true, StatusCode.OK, "加入购物车成功");
    }
}