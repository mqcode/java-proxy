package com.example.github.jdkstatic;

import com.example.github.common.Interview;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HeadHunterProxy implements Interview {

    private Interview interview;

    public HeadHunterProxy(Interview speak) {
        this.interview = speak;
    }

    @Override
    public void interview() {
        log.info("你是谁？来干什么？");
        //反射
        interview.interview();
        log.info("那我们开始吧！");
    }
}
