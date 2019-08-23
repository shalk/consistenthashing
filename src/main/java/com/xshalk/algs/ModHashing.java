package com.xshalk.algs;

import java.util.Collections;
import java.util.List;

/**
 * @author shalk
 * @since 19-8-23
 */
public class ModHashing implements Hashing {

    private List<Endpoint> nodes;

    public ModHashing(List<Endpoint> nodes) {
        this.nodes = nodes;
    }

    public List<Endpoint> getNodes() {
        return Collections.unmodifiableList(nodes);
    }

    public void addNode(Endpoint node) {
        nodes.add(node);
    }

    public void removeNode(Endpoint node) {
        nodes.remove(node);
    }

    public Endpoint getEndPoint(String key) {
        int N = nodes.size();
        int index = key.hashCode() % N;
        return nodes.get(index);
    }
}
