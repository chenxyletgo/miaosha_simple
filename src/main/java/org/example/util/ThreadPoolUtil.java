package org.example.util;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by cld on 2024/4/1 16:54
 * 线程池工具类
 */
public class ThreadPoolUtil {

    private static final int SIZE_CORE_POOL = 5;
    private static final int SIZE_MAX_POOL = 10;
    private static final long ALIVE_TIME = 2000;

    private static BlockingQueue<Runnable> bQueue = new ArrayBlockingQueue<>(100);
    private static ThreadPoolExecutor pool = new ThreadPoolExecutor(SIZE_CORE_POOL, SIZE_MAX_POOL, ALIVE_TIME,
            TimeUnit.MILLISECONDS, bQueue, new ThreadPoolExecutor.CallerRunsPolicy());

    static {
        pool.prestartAllCoreThreads();
    }

    public static ThreadPoolExecutor getPool() {
        return pool;
    }

}
