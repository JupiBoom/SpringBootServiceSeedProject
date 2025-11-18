package com.zyd.springbootserviceseedproject.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

/**
 * 告警管理器
 * 根据任务执行指标设置性能阈值自动告警
 * @author zhaoyudong
 * @version 1.0
 * @description 告警管理器
 * @date 2025/9/30 10:00
 */
@Component
public class AlertManager {
    private static Logger logger = LoggerFactory.getLogger(AlertManager.class);

    @Autowired
    private JavaMailSender mailSender;

    // 告警邮件接收人
    private static final String ALERT_EMAIL_RECIPIENTS = "your-alert-email@qq.com";

    // 任务堆积阈值
    private static final int TASK_QUEUE_SIZE_THRESHOLD = 1000;

    // 任务失败率阈值
    private static final double TASK_FAILURE_RATE_THRESHOLD = 0.1;

    /**
     * 检查任务堆积情况
     * @param queueSize 任务队列大小
     */
    public void checkTaskQueueSize(int queueSize) {
        if (queueSize > TASK_QUEUE_SIZE_THRESHOLD) {
            String subject = "任务调度平台告警：任务堆积超过阈值";
            String content = String.format("当前任务堆积数：%d，超过阈值：%d，请及时处理！", queueSize, TASK_QUEUE_SIZE_THRESHOLD);
            sendAlertEmail(subject, content);
        }
    }

    /**
     * 检查任务失败率
     * @param successCount 成功数
     * @param failureCount 失败数
     */
    public void checkTaskFailureRate(long successCount, long failureCount) {
        long totalCount = successCount + failureCount;
        if (totalCount == 0) {
            return;
        }

        double failureRate = (double) failureCount / totalCount;
        if (failureRate > TASK_FAILURE_RATE_THRESHOLD) {
            String subject = "任务调度平台告警：任务失败率超过阈值";
            String content = String.format("当前任务失败率：%.2f%%，超过阈值：%.2f%%，请及时处理！", failureRate * 100, TASK_FAILURE_RATE_THRESHOLD * 100);
            sendAlertEmail(subject, content);
        }
    }

    /**
     * 发送告警邮件
     * @param subject 邮件主题
     * @param content 邮件内容
     */
    private void sendAlertEmail(String subject, String content) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom("your-email@qq.com");
            helper.setTo(ALERT_EMAIL_RECIPIENTS.split(","));
            helper.setSubject(subject);
            helper.setText(content, true);
            mailSender.send(mimeMessage);
            logger.info("告警邮件发送成功：{}", subject);
        } catch (MessagingException e) {
            logger.error("告警邮件发送失败：{}", subject, e);
        }
    }
}
