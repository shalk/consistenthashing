package com.xshalk.algs;

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
        for (int key = 0; key < 1000; key++) {
            Endpoint target = hashing.getEndPoint(String.valueOf(key));
            countMap.put(target, countMap.getOrDefault(target, 0) + 1);
        }

        //then
        countMap.forEach((endpoint, integer) -> System.out.println(endpoint + " -> " + integer));
    }

    @Test
    public void test1() {
        //given
        List<Endpoint> nodes = new ArrayList<>();
        int N = 10;
        for (int i = 0; i < N; i++) {
            Endpoint node = new Endpoint("10.0.0." + i, "1000" + i);
            nodes.add(node);
        }

        Hashing hashing = new ConsistentHashing(nodes);

        //when
        HashMap<Endpoint, Integer> countMap = new HashMap<>();
        for (int key = 0; key < 1000; key++) {
            Endpoint target = hashing.getEndPoint(String.valueOf(key));
            countMap.put(target, countMap.getOrDefault(target, 0) + 1);
        }

        //then
        countMap.forEach((endpoint, integer) -> System.out.println(endpoint + " -> " + integer));
    }
}
