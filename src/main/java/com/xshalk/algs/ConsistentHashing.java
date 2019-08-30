package com.xshalk.algs;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author shalk
 * @since 19-8-23
 */
public class ConsistentHashing implements Hashing {

    private TreeMap<Long, Endpoint> ring = new TreeMap<>();
    private List<Endpoint> nodes;
    private int virtualCount;

    public ConsistentHashing(List<Endpoint> nodes) {
        this.nodes = nodes;
        this.virtualCount = 200;
        initRing();
    }

    public ConsistentHashing(List<Endpoint> nodes, int virtualCount) {
        this.nodes = nodes;
        this.virtualCount = virtualCount;
        initRing();
    }

    private void initRing() {
        // node1 => 2^32-1
        for (Endpoint node : nodes) {
            for (int i = 0; i < virtualCount; i++) {
                String uniq_name = node.getIp() + ":" + node.getPort() + "_" + i;
                byte[] md5bytes = DigestUtils.md5(uniq_name);
                for (int j = 0; j < 4; j++) {
                    long key = (long) ((md5bytes[3 + j * 4] & 0xFF) << 24) |
                            (long) ((md5bytes[2 + j * 4] & 0xFF) << 16) |
                            (long) ((md5bytes[1 + j * 4] & 0xFF) << 8) |
                            (long) ((md5bytes[j * 4] & 0xFF));
                    ring.put(key, node);
                }
            }
        }
    }

    private void clearRing() {
        ring.clear();
    }

    /**
     * 给虚拟节点做hash
     *
     * @param uniq_name
     * @return
     */
    private long hash(String uniq_name) {
        byte[] md5bytes = DigestUtils.md5(uniq_name);
        return (long) ((md5bytes[3] & 0xFF) << 24) |
                (long) ((md5bytes[2] & 0xFF) << 16) |
                (long) ((md5bytes[1] & 0xFF) << 8) |
                (long) ((md5bytes[0] & 0xFF));
    }


    @Override
    public List<Endpoint> getNodes() {
        return Collections.unmodifiableList(nodes);
    }

    @Override
    public void addNode(Endpoint node) {
        nodes.add(node);
        clearRing();
        initRing();
    }

    @Override
    public void removeNode(Endpoint node) {
        nodes.remove(node);
        clearRing();
        initRing();
    }

    /**
     * 选取时的key的hash
     *
     * @param key
     * @return
     */
    private long hash1(String key) {
        return hash(key);
    }


    //    private long murmur3(String key) {
//
//    }
    @Override
    public Endpoint getEndPoint(String key) {
        Map.Entry<Long, Endpoint> entry = ring.ceilingEntry(hash1(key));
        return entry != null ? entry.getValue() : ring.firstEntry().getValue();
    }

    public static void main(String[] args) {
        System.out.println(DigestUtils.md5("ads").length);
    }
}
