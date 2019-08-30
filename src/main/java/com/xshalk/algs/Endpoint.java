package com.xshalk.algs;

/**
 * @author shalk
 * @since 19-8-23
 */
public class Endpoint {

    private String ip;
    private String port;

    public Endpoint(String ip, String port) {
        this.ip = ip;
        this.port = port;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Endpoint endpoint = (Endpoint) o;

        if (ip != null ? !ip.equals(endpoint.ip) : endpoint.ip != null) return false;
        return port != null ? port.equals(endpoint.port) : endpoint.port == null;
    }

    @Override
    public int hashCode() {
        int result = ip != null ? ip.hashCode() : 0;
        result = 31 * result + (port != null ? port.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Endpoint{");
        sb.append("ip='").append(ip).append('\'');
        sb.append(", port='").append(port).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
