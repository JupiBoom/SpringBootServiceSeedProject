package com.zyd.springbootserviceseedproject.handler;

import com.zyd.springbootserviceseedproject.config.TaskPrometheusConfig;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.context.XxlJobContext;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 任务执行监听器
 * 监听任务的执行状态，并记录任务执行指标到Prometheus
 * @author zhaoyudong
 * @version 1.0
 * @description 任务执行监听器
 * @date 2025/9/30 10:00
 */
@Component
@Aspect
public class TaskExecutionListener {
    private static Logger logger = LoggerFactory.getLogger(TaskExecutionListener.class);

    @Autowired
    private TaskPrometheusConfig prometheusConfig;

    /**
     * 定义切入点：所有xxl-job任务执行方法
     */
    @Pointcut("@annotation(com.xxl.job.core.handler.annotation.XxlJob)")
    public void xxlJobPointcut() {
    }

    /**
     * 任务执行前的通知
     * @param joinPoint 连接点
     */
    @Before("xxlJobPointcut()")
    public void beforeJob(JoinPoint joinPoint) {
        logger.info("任务执行前: {}", joinPoint);
    }

    /**
     * 任务执行后的通知
     * @param joinPoint 连接点
     * @param returnT 任务执行结果
     */
    @AfterReturning(pointcut = "xxlJobPointcut()", returning = "returnT")
    public void afterJob(JoinPoint joinPoint, ReturnT<String> returnT) {
        logger.info("任务执行后: {}, 执行结果: {}", joinPoint, returnT);

        // 获取任务执行上下文
        XxlJobContext jobContext = XxlJobContext.getXxlJobContext();
        if (jobContext != null) {
            // 记录任务执行时间（这里需要根据实际情况实现）
            // long duration = System.currentTimeMillis() - jobContext.getStartTime();
            // prometheusConfig.recordTaskExecutionTime(duration);
        }

        // 根据任务执行结果记录相应的指标
        if (ReturnT.SUCCESS_CODE == returnT.getCode()) {
            prometheusConfig.recordTaskSuccess();
        } else if (ReturnT.FAIL_CODE == returnT.getCode()) {
            prometheusConfig.recordTaskFailure();
        } else if ("timeout".equals(returnT.getMsg())) {
            prometheusConfig.recordTaskTimeout();
        }

        // 更新任务堆积数（这里只是示例，需要根据实际情况实现）
        prometheusConfig.updateTaskQueueSize(0);
    }
}
