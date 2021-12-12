package com.example.github.jdkstatic;

import com.example.github.common.Speak;
import com.example.github.common.ZhangSanSpeak;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ZhangSanLawyerSpeak implements Speak {

    private ZhangSanSpeak zhangSanSpeak = new ZhangSanSpeak();

    @Override
    public void speak() {
        log.info("引用法律条文！");
        zhangSanSpeak.speak();
        log.info("打人是不对的！");
    }
}
