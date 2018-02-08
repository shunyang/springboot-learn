package com.yangyang.springboot.actuator.endpoint;

import org.springframework.boot.actuate.endpoint.AbstractEndpoint;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author chenshunyang
 * @create 2018-02-08 13:16
 **/
@ConfigurationProperties(prefix="endpoints.servertime")
public class ServerTimeEndpoint extends AbstractEndpoint<Map<String,Object>>{


    /**
     * 构造方法 ServerTimeEndpoint，两个参数分别表示端点 ID 和是否端点默认是敏感的。我这边设置端点 ID 是 servertime，它默认不是敏感的。
     */
    public ServerTimeEndpoint() {
        super("servertime",true);
    }

    /**
     * 我们需要通过重写 invoke 方法，返回我们要监控的内容。
     * 这里我定义了一个 Map，它将返回两个参数，一个是标准的包含时区的当前时间格式，一个是当前时间的时间戳格式
     * @return
     */
    @Override
    public Map<String, Object> invoke() {
        Map<String,Object> result = new HashMap<>();
        Date date = new Date();
        result.put("server_time", date.toString());
        result.put("ms_format", System.currentTimeMillis());
        return result;
    }
}
