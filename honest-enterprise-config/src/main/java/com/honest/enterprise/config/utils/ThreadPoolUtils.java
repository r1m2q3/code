package com.honest.enterprise.config.utils;

import java.util.concurrent.*;

/**
 * Description:线程池工具类 保证全局使用一个相同的线程池，方便控制线程的创建和销毁
 * @date 2022-07-17 14:05
 * @author fanjie
 */
public class ThreadPoolUtils {
    private static final ExecutorService pool = new ThreadPoolExecutor(
            5,
            10,
            1000,
             TimeUnit.MILLISECONDS,
            new ArrayBlockingQueue<>(5000),
            Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.AbortPolicy()
    );

    /**
     * 获取线程池配置信息
     * @return
     */
    public static ExecutorService getPool(){
        return pool;
    }

    /**
     * 线程池提交
     * @param runnable
     */
    public static void submit(Runnable runnable){
        pool.submit(runnable);
    }
    /**
     * 线程池停止
     */
    public static void shutdown(){
        pool.shutdown();
    }
    /**
     * 线程池停止
     */
    public static void shutdownNow(){
        pool.shutdownNow();
    }

}
