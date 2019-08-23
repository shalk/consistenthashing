package com.xshalk.algs;

import java.util.List;

/**
 * @author shalk
 * @since 19-8-23
 */
public interface Hashing {

    List<Endpoint> getNodes();

    void addNode(Endpoint node);

    void removeNode(Endpoint node);

    Endpoint getEndPoint(String key);


}
