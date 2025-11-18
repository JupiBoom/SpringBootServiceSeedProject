package com.zyd.springbootserviceseedproject.manager;

import com.zyd.springbootserviceseedproject.bean.PriorityTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 任务执行管理器
 * 管理任务的执行，包括任务的优先级调度、超时处理和幂等性检查
 * @author zhaoyudong
 * @version 1.0
 * @description 任务执行管理器
 * @date 2025/9/30 10:00
 */
@Component
public class TaskExecuteManager {
    private static Logger logger = LoggerFactory.getLogger(TaskExecuteManager.class);

    @Autowired
    private ThreadPoolTaskExecutor priorityTaskExecutor;

    /**
     * 执行优先级任务
     * @param taskId 任务ID
     * @param task 任务内容
     * @param priority 任务优先级（1-10，1最高）
     * @param timeout 超时时间（毫秒）
     * @return 执行结果
     */
    public boolean executePriorityTask(Long taskId, Runnable task, int priority, long timeout) {
        // 检查任务是否已经执行过（幂等性检查）
        if (isTaskAlreadyExecuted(taskId)) {
            logger.info("任务已经执行过，跳过执行: {}", taskId);
            return true;
        }

        // 创建优先级任务
        PriorityTask priorityTask = new PriorityTask(taskId, task, priority);

        // 执行任务并设置超时时间
        Future<Void> future = priorityTaskExecutor.submit(priorityTask);

        try {
            // 等待任务执行完成，设置超时时间
            future.get(timeout, TimeUnit.MILLISECONDS);
            logger.info("任务执行完成: {}", taskId);

            // 标记任务为已执行
            markTaskAsExecuted(taskId);
            return true;
        } catch (TimeoutException e) {
            // 任务执行超时
            logger.error("任务执行超时: {}", taskId, e);
            future.cancel(true);
            return false;
        } catch (Exception e) {
            // 任务执行失败
            logger.error("任务执行失败: {}", taskId, e);
            return false;
        }
    }

    /**
     * 检查任务是否已经执行过
     * @param taskId 任务ID
     * @return 是否已经执行过
     */
    private boolean isTaskAlreadyExecuted(Long taskId) {
        // TODO: 实现幂等性检查逻辑
        // 可以使用Redis或数据库来记录任务的执行状态
        return false;
    }

    /**
     * 标记任务为已执行
     * @param taskId 任务ID
     */
    private void markTaskAsExecuted(Long taskId) {
        // TODO: 实现标记任务为已执行的逻辑
        // 可以使用Redis或数据库来记录任务的执行状态
    }
}
