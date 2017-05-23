package com.yangyang.springboot.example.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by chenshunyang on 2017/5/23.
 */
@ConfigurationProperties("example.service")
public class ExampleServiceProperties {
    private String prefix;
    private String suffix;

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }
}
