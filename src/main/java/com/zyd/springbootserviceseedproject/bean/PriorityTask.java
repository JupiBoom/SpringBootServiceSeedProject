package com.zyd.springbootserviceseedproject.bean;

import java.util.concurrent.Callable;

/**
 * 优先级任务类
 * 实现Comparable接口，用于支持任务的优先级排序
 * @author zhaoyudong
 * @version 1.0
 * @description 优先级任务类
 * @date 2025/9/30 10:00
 */
public class PriorityTask implements Callable<Void>, Comparable<PriorityTask> {

    // 任务ID
    private Long taskId;

    // 任务内容
    private Runnable task;

    // 任务优先级（1-10，1最高）
    private int priority;

    // 任务创建时间
    private long createTime;

    public PriorityTask(Long taskId, Runnable task, int priority) {
        this.taskId = taskId;
        this.task = task;
        this.priority = priority;
        this.createTime = System.currentTimeMillis();
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Runnable getTask() {
        return task;
    }

    public void setTask(Runnable task) {
        this.task = task;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    @Override
    public Void call() throws Exception {
        task.run();
        return null;
    }

    /**
     * 比较任务优先级
     * 优先级高的任务排在前面
     * 优先级相同的任务，创建时间早的排在前面
     * @param other 另一个任务
     * @return 比较结果
     */
    @Override
    public int compareTo(PriorityTask other) {
        // 优先级高的任务排在前面
        if (this.priority < other.priority) {
            return -1;
        } else if (this.priority > other.priority) {
            return 1;
        } else {
            // 优先级相同的任务，创建时间早的排在前面
            return Long.compare(this.createTime, other.createTime);
        }
    }
}
