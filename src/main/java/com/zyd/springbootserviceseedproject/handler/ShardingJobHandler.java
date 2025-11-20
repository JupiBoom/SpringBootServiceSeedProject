package com.zyd.springbootserviceseedproject.handler;

import com.zyd.springbootserviceseedproject.config.LoadBalancedJobShardingStrategy;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 分片任务处理器
 * 处理任务的分片执行，并确保任务分片均匀
 * @author zhaoyudong
 * @version 1.0
 * @description 分片任务处理器
 * @date 2025/9/30 10:00
 */
@Component
public class ShardingJobHandler {
    private static Logger logger = LoggerFactory.getLogger(ShardingJobHandler.class);

    /**
     * 分片任务示例
     * @return 执行结果
     */
    @XxlJob("shardingJobHandler")
    public ReturnT<String> shardingJobHandler() {
        // 获取分片参数
        int shardIndex = com.xxl.job.core.context.XxlJobContext.getXxlJobContext().getShardIndex();
        int shardTotal = com.xxl.job.core.context.XxlJobContext.getXxlJobContext().getShardTotal();

        logger.info("分片任务执行: index={}, total={}", shardIndex, shardTotal);

        // TODO: 实现具体的分片任务逻辑
        // 例如：处理数据库中的数据，根据分片参数分批次处理

        return ReturnT.SUCCESS;
    }

    /**
     * 自定义负载均衡分片策略示例
     * @return 执行结果
     */
    @XxlJob("loadBalancedShardingJobHandler")
    public ReturnT<String> loadBalancedShardingJobHandler() {
        // 获取任务ID
        long jobId = com.xxl.job.core.context.XxlJobContext.getXxlJobContext().getJobId();
        int shardTotal = 10;

        // TODO: 获取执行器节点列表
        List<String> executorNodes = new ArrayList<>();

        logger.info("自定义负载均衡分片任务执行: jobId={}, executorNodes={}, shardTotal={}", jobId, executorNodes, shardTotal);

        // 使用自定义的负载均衡分片策略
        Map<String, List<Integer>> shardResult = LoadBalancedJobShardingStrategy.shard((int) jobId, shardTotal, executorNodes);

        // TODO: 实现具体的分片任务逻辑
        // 例如：根据分片结果处理相应的数据

        return ReturnT.SUCCESS;
    }
}
