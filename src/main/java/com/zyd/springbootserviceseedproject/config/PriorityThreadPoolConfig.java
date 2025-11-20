package com.zyd.springbootserviceseedproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 优先级线程池配置
 * 实现任务的优先级调度
 * @author zhaoyudong
 * @version 1.0
 * @description 优先级线程池配置
 * @date 2025/9/30 10:00
 */
@Configuration
public class PriorityThreadPoolConfig {

    /**
     * 自定义优先级线程池
     * @return 线程池执行器
     */
    @Bean("priorityTaskExecutor")
    public ThreadPoolTaskExecutor priorityTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor() {
            @Override
            protected java.util.concurrent.ExecutorService initializeExecutor(
                    java.util.concurrent.ThreadFactory threadFactory,
                    java.util.concurrent.RejectedExecutionHandler rejectedExecutionHandler) {
                // 使用PriorityBlockingQueue作为任务队列，支持任务优先级
                return new ThreadPoolExecutor(
                        getCorePoolSize(),
                        getMaxPoolSize(),
                        getKeepAliveSeconds(),
                        java.util.concurrent.TimeUnit.SECONDS,
                        new PriorityBlockingQueue<>(getQueueCapacity()),
                        threadFactory,
                        rejectedExecutionHandler);
            }
        };

        // 配置线程池参数
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(20);
        executor.setQueueCapacity(1000);
        executor.setThreadNamePrefix("priority-task-executor-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

        return executor;
    }
}
