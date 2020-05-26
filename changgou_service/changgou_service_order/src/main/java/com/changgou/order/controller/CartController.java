package com.changgou.order.controller;

import com.changgou.entity.Result;
import com.changgou.entity.StatusCode;
import com.changgou.order.config.TokenDecode;
import com.changgou.order.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private TokenDecode tokenDecode;

    /**
     * 添加购物车
     *
     * @param skuId
     * @param num
     * @return
     */
    @GetMapping("/add")
    public Result add(@RequestParam("skuId") String skuId, @RequestParam("num") Integer num) {

        //动态获取当前人信息,暂时静态
        String username = tokenDecode.getUserInfo().get("username");
        cartService.addCart(skuId, num, username);

        return new Result(true, StatusCode.OK, "加入购物车成功");
    }

    @GetMapping("/list")
    public Map list() {
        //动态获取当前人信息,暂时静态
        String username = tokenDecode.getUserInfo().get("username");
        Map map = cartService.list(username);
        return map;
    }
}