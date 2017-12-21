package com.example.demo.ConfigSettings;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by 寇含尧 on 2017/10/30.
 */
@Configuration
public class ElasticSearchConfig {
    @Bean
    public TransportClient esClient() throws UnknownHostException {
        InetSocketTransportAddress node1 = new InetSocketTransportAddress(
                InetAddress.getByName("127.0.0.1"), 9300
        );//此处端口是es的tcp端口
        Settings setting = Settings.builder().put("cluster.name", "jiqun").build();
        TransportClient client = new PreBuiltTransportClient(setting);
        client.addTransportAddress(node1);//此处可以增加多个节点，及多个node
        return client;
    }
}
