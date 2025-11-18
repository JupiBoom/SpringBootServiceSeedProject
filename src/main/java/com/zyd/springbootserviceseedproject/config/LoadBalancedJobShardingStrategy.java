package com.zyd.springbootserviceseedproject.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 负载均衡任务分片策略
 * 确保任务能够均匀地分配到各个执行器节点上
 * @author zhaoyudong
 * @version 1.0
 * @description 负载均衡任务分片策略
 * @date 2025/9/30 10:00
 */
@Component
public class LoadBalancedJobShardingStrategy {
    private static Logger logger = LoggerFactory.getLogger(LoadBalancedJobShardingStrategy.class);

    // 执行器节点列表
    private static List<String> executorNodes = new ArrayList<>();

    // 任务分片计数器，用于轮询分配任务
    private static AtomicInteger shardCounter = new AtomicInteger(0);

    // 执行器负载统计
    private static Map<String, AtomicInteger> executorLoadMap = new ConcurrentHashMap<>();

    /**
     * 负载均衡分片策略
     * @param jobId 任务ID
     * @param shardTotal 总分片数
     * @param executorNodes 执行器节点列表
     * @return 分片结果
     */
    public static Map<String, List<Integer>> shard(int jobId, int shardTotal, List<String> executorNodes) {
        Map<String, List<Integer>> shardResult = new HashMap<>();
        if (executorNodes == null || executorNodes.isEmpty()) {
            return shardResult;
        }

        // 初始化执行器负载统计
        for (String executorNode : executorNodes) {
            executorLoadMap.putIfAbsent(executorNode, new AtomicInteger(0));
        }

        // 初始化执行器节点列表
        LoadBalancedJobShardingStrategy.executorNodes = executorNodes;

        // 轮询分配任务分片
        for (int i = 0; i < shardTotal; i++) {
            // 选择负载最低的执行器节点
            String selectedExecutor = selectLowestLoadExecutor();
            if (selectedExecutor == null) {
                selectedExecutor = executorNodes.get(i % executorNodes.size());
            }

            // 将分片分配给选中的执行器
            List<Integer> shards = shardResult.computeIfAbsent(selectedExecutor, k -> new ArrayList<>());
            shards.add(i);

            // 更新执行器负载
            executorLoadMap.get(selectedExecutor).incrementAndGet();
        }

        logger.info("负载均衡分片结果: {}", shardResult);
        return shardResult;
    }

    /**
     * 选择负载最低的执行器节点
     * @return 负载最低的执行器节点
     */
    private static String selectLowestLoadExecutor() {
        if (executorNodes.isEmpty()) {
            return null;
        }

        // 找到负载最低的执行器节点
        String selectedExecutor = null;
        int minLoad = Integer.MAX_VALUE;

        for (Map.Entry<String, AtomicInteger> entry : executorLoadMap.entrySet()) {
            int load = entry.getValue().get();
            if (load < minLoad) {
                minLoad = load;
                selectedExecutor = entry.getKey();
            }
        }

        return selectedExecutor;
    }

    /**
     * 重置执行器负载
     */
    public static void resetExecutorLoad() {
        executorLoadMap.clear();
    }

    /**
     * 更新执行器负载
     * @param executorNode 执行器节点
     * @param load 负载值
     */
    public static void updateExecutorLoad(String executorNode, int load) {
        executorLoadMap.put(executorNode, new AtomicInteger(load));
    }
}