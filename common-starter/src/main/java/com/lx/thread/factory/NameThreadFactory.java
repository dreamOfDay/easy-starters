package com.lx.thread.factory;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: jyu
 * @Date: 2021/12/22
 * @Description: 带名字的线程工厂,建议传入全限定类名
 **/
public class NameThreadFactory implements ThreadFactory {

    private final AtomicInteger id = new AtomicInteger(0);

    private String name;

    public NameThreadFactory(String name) {
        if (!name.endsWith(".")) {
            name += ".";
        }
        this.name = name;
    }

    @Override
    public Thread newThread(Runnable r) {
        String threadName = name + id.getAndIncrement();
        Thread thread = new Thread(r, threadName);
        thread.setDaemon(true);
        return thread;
    }
}
