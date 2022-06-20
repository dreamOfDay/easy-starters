package com.lx.thread;

import com.lx.thread.factory.NameThreadFactory;
import com.lx.utils.ThreadUtils;

import java.util.concurrent.*;
import java.util.function.Function;

/**
 * @Author: yuj
 * @Date: 2021/12/28
 * @Describe: simple common executor,more expansion can reference to ExecutorFactory in nacos
 */
public class CommonExecutor {

    private static final ScheduledExecutorService COMMON_SCHEDULED_EXECUTOR =
            Executors.newScheduledThreadPool(ThreadUtils.getSuitableThreadCount(), new NameThreadFactory("com.lx.thread.CommonExecutor"));


    public static void scheduleCommonTask(Runnable command, long delay, TimeUnit unit) {
        COMMON_SCHEDULED_EXECUTOR.schedule(command, delay, unit);
    }

    public static void delayScheduleCommonTask(Runnable command, long initialDelay, long delay, TimeUnit unit) {
        COMMON_SCHEDULED_EXECUTOR.scheduleWithFixedDelay(command, initialDelay, delay, unit);
    }

    /**
     * 串行调用两个task,不建议使用此方法，改成带线程池的方法
     *
     * @param firstCommand    第一个task
     * @param secondCommand   第二个task
     * @param rollbackCommand 回滚task
     */
    @Deprecated
    public static void scheduleSyncTask(Runnable firstCommand, Runnable secondCommand, Runnable rollbackCommand) {
        scheduleSyncTask(firstCommand, secondCommand, rollbackCommand, COMMON_SCHEDULED_EXECUTOR);
    }

    /**
     * 串行调用两个task
     *
     * @param firstCommand    第一个task
     * @param secondCommand   第二个task
     * @param rollbackCommand 回滚task
     * @param executor        线程池
     */
    public static void scheduleSyncTask(Runnable firstCommand, Runnable secondCommand, Runnable rollbackCommand, Executor executor) {
        Function<Throwable, Boolean> rollbackFun = throwable -> {
            scheduleCommonTask(rollbackCommand, 5, TimeUnit.SECONDS);
            return false;
        };
        scheduleSyncTask(firstCommand, secondCommand, rollbackFun, executor);
    }

    /**
     * 串行调用两个task
     *
     * @param firstCommand  第一个task
     * @param secondCommand 第二个task
     * @param rollbackFun   回滚函数
     * @param executor      对应线程池
     */
    public static void scheduleSyncTask(Runnable firstCommand, Runnable secondCommand, Function<Throwable, Boolean> rollbackFun, Executor executor) {
        sycnTask(firstCommand, rollbackFun, executor)
                .thenAccept(flag -> {
                    if (flag) {
                        sycnTask(secondCommand, rollbackFun, executor);
                    }
                });
    }

    /**
     * 执行一个task，失败则执行对应的回滚的task
     *
     * @param runnable     需要执行的task
     * @param rollbackFunc 回滚的函数
     * @return
     */
    private static CompletableFuture<Boolean> sycnTask(Runnable runnable, Function<Throwable, Boolean> rollbackFunc, Executor executor) {
        return CompletableFuture.supplyAsync(() -> {
                    runnable.run();
                    return true;
                }, executor
        ).exceptionally(rollbackFunc);
    }


}
