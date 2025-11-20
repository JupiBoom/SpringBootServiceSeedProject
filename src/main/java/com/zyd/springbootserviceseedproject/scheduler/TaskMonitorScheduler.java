package com.zyd.springbootserviceseedproject.scheduler;

import com.zyd.springbootserviceseedproject.manager.AlertManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 任务监控调度器
 * 定期检查任务执行指标，并触发相应的告警
 * @author zhaoyudong
 * @version 1.0
 * @description 任务监控调度器
 * @date 2025/9/30 10:00
 */
@Component
public class TaskMonitorScheduler {
    private static Logger logger = LoggerFactory.getLogger(TaskMonitorScheduler.class);

    @Autowired
    private AlertManager alertManager;

    /**
     * 每5分钟检查一次任务堆积情况
     */
    @Scheduled(cron = "0 0/5 * * * ?")
    public void checkTaskQueueSize() {
        logger.info("检查任务堆积情况");
        // TODO: 获取实际的任务队列大小
        int queueSize = 0;
        alertManager.checkTaskQueueSize(queueSize);
    }

    /**
     * 每小时检查一次任务失败率
     */
    @Scheduled(cron = "0 0 0/1 * * ?")
    public void checkTaskFailureRate() {
        logger.info("检查任务失败率");
        // TODO: 获取实际的任务成功数和失败数
        long successCount = 0;
        long failureCount = 0;
        alertManager.checkTaskFailureRate(successCount, failureCount);
    }
}
