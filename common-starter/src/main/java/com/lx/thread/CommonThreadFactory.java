package com.lx.thread;

import org.springframework.util.StringUtils;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: jyu
 * @Date: 2021/10/18
 * @Description: 线程构造工厂，可以生成带名字的线程工厂，更多扩展可以参照spring中的实现，例如 {@link org.springframework.scheduling.concurrent.CustomizableThreadFactory}
 **/
public final class CommonThreadFactory implements ThreadFactory {
    private static final AtomicInteger poolNumber = new AtomicInteger(1);
    private final ThreadGroup group;
    private final AtomicInteger threadNumber = new AtomicInteger(1);
    private final String namePrefix;
    private static final String defaultName = "CommonThreadPool-";

    public CommonThreadFactory() {
        this(defaultName);
    }

    public CommonThreadFactory(String name) {
        SecurityManager s = System.getSecurityManager();
        group = (s != null) ? s.getThreadGroup() :
                Thread.currentThread().getThreadGroup();
        name = (StringUtils.isEmpty(name)) ? defaultName : name;
        namePrefix = name +
                poolNumber.getAndIncrement() +
                "-thread-";
    }

    public Thread newThread(Runnable r) {
        Thread t = new Thread(group, r,
                namePrefix + threadNumber.getAndIncrement(),
                0);

        if (t.isDaemon())
            t.setDaemon(false);
        if (t.getPriority() != Thread.NORM_PRIORITY)
            t.setPriority(Thread.NORM_PRIORITY);
        return t;
    }
}
