package com.lx.feign;

import com.alibaba.fastjson.JSON;
import com.lx.utils.Ipv4Utils;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @Author: jyu
 * @Date: 2021/10/10
 * @Description: 优先使用同段的服务
 **/
public class LoadBalancerRule extends AbstractLoadBalancerRule {

    private final static Logger log = LoggerFactory.getLogger(LoadBalancerRule.class);

    private static final AntPathMatcher MATCHER = new AntPathMatcher(".");

    private static String LOCAL_IP;

    private Map<Ipv4Utils.Ipv4Group, String> group;

    private IRule rule;

    static {
        try {
            LOCAL_IP = InetAddress.getLocalHost().getHostAddress();
        }catch (UnknownHostException e) {
            LOCAL_IP = null;
        }

    }

    private boolean isLocal;

    //本地服务
    private static final String[] LOCAL_IP_PREFIX = {"192.168","127"};

    public LoadBalancerRule() {
        this(null);
    }
    public LoadBalancerRule(IRule rule) {
        if(rule == null) {
            rule = new RetryRule();
        }
        if(LOCAL_IP == null) {
            this.isLocal = false;
        }else {
            this.isLocal = Arrays.asList(LOCAL_IP_PREFIX).stream().anyMatch(i -> LOCAL_IP.startsWith(i));
        }
        this.rule = rule;
        this.group = Ipv4Utils.Ipv4Group.getRegexs(LOCAL_IP);
    }


    @Override
    public Server choose(Object key) {
        //委托给choose(Object key,ILoadBalancer lb)
        return choose(key,getLoadBalancer());
    }

    public Server choose(Object key, ILoadBalancer lb) {

        //非本地服务
        if(!isLocal) {
            return rule.choose(key);
        }

        List<Server> activeServers = lb.getReachableServers();
        if(CollectionUtils.isEmpty(activeServers)){
            log.info(JSON.toJSONString(key)+" : have no active server in this time");
            return null;
        }

        Server chooseServer = activeServers.stream().filter(i->i.getHost().equals(LOCAL_IP)).findFirst().orElse(null);
        //降级
        if(chooseServer == null) {
            chooseServer = activeServers.stream().filter(i->MATCHER.match(group.get(Ipv4Utils.Ipv4Group.LEVEL_3), i.getHost())).findAny().orElse(null);
        }
        //再降级		
        if(chooseServer == null) {
            chooseServer = activeServers.stream().filter(i->MATCHER.match(group.get(Ipv4Utils.Ipv4Group.LEVEL_2), i.getHost())).findAny().orElse(null);
        }

        if(chooseServer == null) {
            chooseServer = activeServers.stream().findAny().orElse(null);
        }

        return chooseServer;
    }

    @Override
    public void initWithNiwsConfig(IClientConfig clientConfig) {}
}
