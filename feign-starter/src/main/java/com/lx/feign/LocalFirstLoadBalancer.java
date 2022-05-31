package com.lx.feign;

import com.alibaba.fastjson.JSON;
import com.lx.utils.Ipv4Utils;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @Author: jyu
 * @Date: 2022/1/24
 * @Description: 本地优先的负载均衡
 **/
public class LocalFirstLoadBalancer extends AbstractLoadBalancerRule {
    protected final Log logger = LogFactory.getLog(getClass());
    private static List<String> LOCAL_IPS = Ipv4Utils.getAllIps();

    private final IRule rule;


    public LocalFirstLoadBalancer() {
        this(null);
    }
    public LocalFirstLoadBalancer(IRule rule) {
        this.rule = rule == null ? new RetryRule(): rule;
    }


    @Override
    public Server choose(Object key) {
        //委托给choose(Object key,ILoadBalancer lb)
        return choose(key,getLoadBalancer());
    }

    public Server choose(Object key, ILoadBalancer lb) {

        List<Server> activeServers = lb.getAllServers();
        if(CollectionUtils.isEmpty(activeServers)){
            logger.info(JSON.toJSONString(key)+" : have no active server in this time");
            // 找不到同机设备
            return rule.choose(key);
        }
        // 同机优先
        Server chooseServer = activeServers.stream().filter(i->LOCAL_IPS.contains(i.getHost())).findFirst().orElse(null);
        // 找不到同机设备
        if(chooseServer == null){
            chooseServer = rule.choose(key);
        }
        logger.info("final choose server " + chooseServer);
        return chooseServer;
    }

    @Override
    public void setLoadBalancer(ILoadBalancer lb) {
        super.setLoadBalancer(lb);
        rule.setLoadBalancer(lb);
    }

    @Override
    public void initWithNiwsConfig(IClientConfig clientConfig) {}


}

