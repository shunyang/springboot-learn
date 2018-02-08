package com.yangyang.springboot.es.common;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetSocketAddress;

public class ESHelper {
    public static TransportClient  makeEStransportClient(String clusterName,String[] esURLs){
        Settings settings = Settings.builder().put("cluster.name", clusterName).put("client.transport.sniff", true).build();
        TransportClient transportClient = new PreBuiltTransportClient(settings);
        for (String url : esURLs) {
            String[] hostAndPort = url.split(":");
            InetSocketAddress inetSocketAddress = new InetSocketAddress(hostAndPort[0], Integer.parseInt(hostAndPort[1]));
            transportClient.addTransportAddress(new InetSocketTransportAddress(inetSocketAddress));
        }
        return transportClient;
    }
}
