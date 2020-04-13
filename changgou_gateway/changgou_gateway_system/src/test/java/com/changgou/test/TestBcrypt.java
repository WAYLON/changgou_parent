package com.changgou.test;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class TestBcrypt {

    public static void main(String[] args) {
        /**
         * 得到盐
         * 盐是一个随机生成的含有29个字符的字符串,并且会与密码一起合并进行最终的密文生成
         * 并且每一次生成的盐的值都是不同的
         */
        for (int i = 0; i < 10; i++) {
            String gensalt = BCrypt.gensalt();
            System.out.println("salt:" + gensalt);
            String saltPassword = BCrypt.hashpw("123456", gensalt);
            System.out.println("本次生成的密码:" + saltPassword);
            //校验密码
            boolean checkpw = BCrypt.checkpw("123456", saltPassword);
            System.out.println("密码校验结果:" + checkpw);
        }
    }
}