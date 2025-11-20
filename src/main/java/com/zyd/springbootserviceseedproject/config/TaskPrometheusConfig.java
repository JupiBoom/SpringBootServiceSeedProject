package com.zyd.springbootserviceseedproject.config;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Prometheus监控配置
 * 实现任务执行指标的实时收集
 * @author zhaoyudong
 * @version 1.0
 * @description Prometheus监控配置
 * @date 2025/9/30 10:00
 */
@Configuration
public class PrometheusConfig {

    @Autowired
    private MeterRegistry meterRegistry;

    // 任务执行成功计数器
    private Counter taskSuccessCounter;

    // 任务执行失败计数器
    private Counter taskFailureCounter;

    // 任务执行超时计数器
    private Counter taskTimeoutCounter;

    // 任务平均执行时间计时器
    private Timer taskExecutionTimer;

    // 任务堆积数 gauge
    private AtomicInteger taskQueueSizeGauge;

    /**
     * 初始化监控指标
     */
    @PostConstruct
    public void initMetrics() {
        // 任务执行成功计数器
        taskSuccessCounter = Counter.builder("task_execution_success_total")
                .description("Total number of successful task executions")
                .register(meterRegistry);

        // 任务执行失败计数器
        taskFailureCounter = Counter.builder("task_execution_failure_total")
                .description("Total number of failed task executions")
                .register(meterRegistry);

        // 任务执行超时计数器
        taskTimeoutCounter = Counter.builder("task_execution_timeout_total")
                .description("Total number of timeout task executions")
                .register(meterRegistry);

        // 任务平均执行时间计时器
        taskExecutionTimer = Timer.builder("task_execution_duration_seconds")
                .description("Average duration of task executions")
                .register(meterRegistry);

        // 任务堆积数 gauge
        taskQueueSizeGauge = new AtomicInteger(0);
        Gauge.builder("task_queue_size", taskQueueSizeGauge::get)
                .description("Number of tasks in queue")
                .register(meterRegistry);
    }

    /**
     * 记录任务执行成功
     */
    public void recordTaskSuccess() {
        taskSuccessCounter.increment();
    }

    /**
     * 记录任务执行失败
     */
    public void recordTaskFailure() {
        taskFailureCounter.increment();
    }

    /**
     * 记录任务执行超时
     */
    public void recordTaskTimeout() {
        taskTimeoutCounter.increment();
    }

    /**
     * 记录任务执行时间
     * @param duration 执行时间（毫秒）
     */
    public void recordTaskExecutionTime(long duration) {
        taskExecutionTimer.record(duration, java.util.concurrent.TimeUnit.MILLISECONDS);
    }

    /**
     * 更新任务堆积数
     * @param queueSize 队列大小
     */
    public void updateTaskQueueSize(int queueSize) {
        taskQueueSizeGauge.set(queueSize);
    }
}
