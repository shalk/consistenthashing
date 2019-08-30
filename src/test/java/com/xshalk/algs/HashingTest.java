package com.xshalk.algs;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author shalk
 * @since 19-8-23
 */
public class HashingTest {

    @Test
    public void test() {
        // given
        List<Endpoint> nodes = new ArrayList<>();
        int N = 10;
        for (int i = 0; i < N; i++) {
            Endpoint node = new Endpoint("10.0.0." + i, "1000" + i);
            nodes.add(node);
        }
        ModHashing hashing = new ModHashing(nodes);

        //when
        HashMap<Endpoint, Integer> countMap = new HashMap<>();
        int M = 1000;
        for (int key = 0; key < M; key++) {
            Endpoint target = hashing.getEndPoint(String.valueOf(key));
            countMap.put(target, countMap.getOrDefault(target, 0) + 1);
        }

        //then
        for (Endpoint node : nodes) {
            int count = countMap.getOrDefault(node, 0);
            double rate = count * 1.0 / M;
            double expect = 1.0 / N;
            Assert.assertTrue(Math.abs(rate - expect) / expect < 0.05);
        }
    }

    /**
     * 测试hash平衡性
     * 50个节点，20_0000请求; 平均每个节点收到 4000 请求， 误差不超过10% 【3600 ～ 4400】
     */
    @Test
    public void testBalacne() {
        //given
        List<Endpoint> nodes = new ArrayList<>();
        int N = 50;
        for (int i = 0; i < N; i++) {
            Endpoint node = new Endpoint("10.0.0." + i, "" + i);
            nodes.add(node);
        }

        Hashing hashing = new ConsistentHashing(nodes, 4000);

        //when
        int M = 40_0000;
        HashMap<Endpoint, Integer> countMap = new HashMap<>();
        for (int key = 0; key < M; key++) {
            Endpoint target = hashing.getEndPoint(String.valueOf(key));
            countMap.put(target, countMap.getOrDefault(target, 0) + 1);
        }

        // then
        double max = -1.0;
        for (Endpoint node : nodes) {
            int count = countMap.getOrDefault(node, 0);
            double rate = count * 1.0 / M;
            double expect = 1.0 / N;
            double diff = Math.abs(rate - expect) / expect;
            max = Math.max(max, diff);
        }
        System.out.println("max = " + max);
        Assert.assertTrue(max < 0.1);
    }

    /**
     * 测试hash的 移动率
     */
    @Test
    public void testAdded() {
        //given
        List<Endpoint> nodes = new ArrayList<>();
        int N = 50;
        for (int i = 0; i < N; i++) {
            Endpoint node = new Endpoint("10.0.0." + i, "" + i);
            nodes.add(node);
        }

        Hashing hashing = new ConsistentHashing(nodes);

        //when
        int M = 20_0000;
        HashMap<Integer, Endpoint> keyMap = new HashMap<>();
        for (int key = 0; key < M; key++) {
            Endpoint target = hashing.getEndPoint(String.valueOf(key));
            keyMap.put(key, target);
        }

        // add node
        Endpoint newNode = new Endpoint("10.0.0." + N, "" + N);
        hashing.addNode(newNode);
        int changeCount = 0;
        for (int key = 0; key < M; key++) {
            Endpoint target = hashing.getEndPoint(String.valueOf(key));
            if (!target.equals(keyMap.get(key))) {
                changeCount++;
            }
        }

        // calc miss
        double expectChangeRate = 1.0 / (N + 1);
        double actualChangeRate = changeCount * 1.0 / M;
        double diff = Math.abs(actualChangeRate - expectChangeRate);
        // 误差低于3%
        System.out.println("changeCount = " + changeCount);
        System.out.println("diff = " + diff);
        Assert.assertTrue(diff < 0.03);

    }
}
