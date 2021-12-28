package com.lx.thread;

import com.lx.thread.factory.NameThreadFactory;
import com.lx.utils.ThreadUtils;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @Author: yuj
 * @Date: 2021/12/28
 * @Describe: simple common executor,more expansion can reference to ExecutorFactory in nacos
 */
public class CommonExecutor {

    private static final ScheduledExecutorService Common_SCHEDULED_EXECUTOR =
            Executors.newScheduledThreadPool(ThreadUtils.getSuitableThreadCount(), new NameThreadFactory("com.lx.thread.CommonExecutor"));


    public static void scheduleCommonTask(Runnable command, long initialDelay, long delay, TimeUnit unit) {
        Common_SCHEDULED_EXECUTOR.scheduleWithFixedDelay(command, initialDelay, delay, unit);
    }

}
