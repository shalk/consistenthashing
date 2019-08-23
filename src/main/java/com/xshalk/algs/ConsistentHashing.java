package com.xshalk.algs;

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

    public ConsistentHashing(List<Endpoint> nodes) {
        this.nodes = nodes;
        initRing();
    }

    private void initRing() {
        // node1 => 2^32-1
        for (Endpoint node : nodes) {
            //TODO3 virtual node
            long key = hash(node);
            System.out.print("key = " + key);
            System.out.println(",node = " + node);
            ring.put(key, node);
        }
    }

    private void clearRing() {
        ring.clear();
    }

    private long hash(Endpoint node) {
        //TODO1
        return node.hashCode() & 0x7fff_ffff;
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
        nodes.add(node);
        clearRing();
        initRing();
    }

    private long hash1(String key) {
        //TODO2
        return key.hashCode() & 0x7fff_ffff;
    }

    @Override
    public Endpoint getEndPoint(String key) {
        Map.Entry<Long, Endpoint> entry = ring.ceilingEntry(hash1(key));
        return entry != null ? entry.getValue() : ring.firstEntry().getValue();
    }
}
