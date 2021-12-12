package com.example.github.common;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ZhangSanSpeak implements Speak{
    @Override
    public void speak() {
        log.info("我老婆打我！");
    }
}
