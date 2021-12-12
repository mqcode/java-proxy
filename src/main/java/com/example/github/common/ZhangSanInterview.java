package com.example.github.common;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ZhangSanInterview implements Interview {
    /**
     * 张三面试,未实现接口
     */
    @Override
    public void interview() {
        log.info("我是张三,我来面试！");
    }
}
