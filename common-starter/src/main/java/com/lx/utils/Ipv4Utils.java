package com.lx.utils;

import org.springframework.util.AntPathMatcher;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @Author: yuj
 * @Date: 2021/10/14
 * @Describe:
 */
public class Ipv4Utils {

    public static final AntPathMatcher MATCHER = new AntPathMatcher(".");

    /**
     * 对IP分组
     * @param baseIp 192.168.0.1
     * @param ipList
     */
    public static Map<Ipv4Group, List<String>> groupIp(String baseIp , List<String> ipList){
        Map<Ipv4Group,String> group = Ipv4Group.getRegexs(baseIp);
        Map<Ipv4Group,List<String>> result = new HashMap<>();
        result.put(Ipv4Group.LEVEL_0, ipList);
        List<String> lv4 = ipList.stream().filter(i->i.equals(baseIp)).collect(Collectors.toList());
        result.put(Ipv4Group.LEVEL_4, lv4);

        for(Map.Entry<Ipv4Group,String> entry : group.entrySet()) {
            if(entry.getKey() == Ipv4Group.LEVEL_0 || entry.getKey() == Ipv4Group.LEVEL_4) {
                continue;
            }
            final String regex = entry.getValue();
            List<String> list=ipList.stream().filter(i->MATCHER.match(regex, i)).collect(Collectors.toList());
            result.put(entry.getKey(), list);
        }

        return result;
    }



    public static enum Ipv4Group{

        /**
         * *.*.*.*无匹配。比如234.234.234。234与192.123.123.123是匹配的。
         */
        LEVEL_0(0),
        /**
         * XXX.*.*.*一级网段匹配。比如192.234.234。234与192.123.123.123是匹配的。
         */
        LEVEL_1(1),
        /**
         * XXX.XXX.*.*二级网段匹配。比如192.234.234。234与192.234.123.123是匹配的。
         */
        LEVEL_2(2),
        /**
         * XXX.XXX.XXX.*三级网段匹配。比如192.234.234。234与192.234.234.123是匹配的。
         */
        LEVEL_3(3),
        /**
         * XXX.XXX.XXX.XXX同地址匹配。比如192.234.234。234与192.234.234.234是匹配的。
         */
        LEVEL_4(4),;

        public final int order;

        private Ipv4Group(int order) {
            this.order = order;
        }

        /**
         * LEVEL_0的表达式
         */
        public static final String LEVEL_0_REGEX = "*.*.*.*";

        public static final String IP_REGEX = "\\.";

        /**
         * ( ([0-9]（0到9） | [1-9][0-9]（10到99） | 1[0-9]{2}（100到199） | 2[0-4][0-9]（200-249） | 25[0-5]（250-255）) \\.){3} [0-9] |[1-9][0-9]|[1-9][0-9]{2}|2[0-4][0-9]|25[0-5]
         */
        public static final Pattern IPV4_PATTERN = Pattern.compile("(([0-9]|([1-9][0-9])|(1[0-9]{2})|(2[0-4][0-9])|(25[0-5]))\\.){3}([0-9]|([1-9][0-9])|(1[0-9]{2})|(2[0-4][0-9])|(25[0-5]))");

        public static final boolean isIpv4(String ip) {
            return ip != null && IPV4_PATTERN.matcher(ip).matches();
        }

        public static Map<Ipv4Group, String> getRegexs(String ip){
            if(!isIpv4(ip)) {
                throw new IllegalArgumentException(ip + " is not a ipv4 ip. eg: 172.23.156.123");
            }
            Map<Ipv4Group,String> result = new LinkedHashMap<Ipv4Group, String>();

            String[] ipParts= ip.split(IP_REGEX);
            result.put(LEVEL_4, ip);
            result.put(LEVEL_3, ipParts[0]+"."+ipParts[1]+"."+ipParts[2]+".*");
            result.put(LEVEL_2, ipParts[0]+"."+ipParts[1]+".*.*");
            result.put(LEVEL_1, ipParts[0]+".*.*.*");
            result.put(LEVEL_0, LEVEL_0_REGEX);

            return result;
        }
    }
}
