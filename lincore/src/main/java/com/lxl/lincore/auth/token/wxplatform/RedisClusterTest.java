package com.lxl.lincore.auth.token.wxplatform;

import org.junit.Test;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;

/**
 * @author linxili
 * @Description: redis测试连接
 * @Date: 2018/11/29 10:13
 */
public class RedisClusterTest {

    // redis集群测试
    @Test
    public void testJedisCluster() throws Exception {
        HostAndPort hostAndPort = new HostAndPort("47.107.88.216", 10010);
        HostAndPort hostAndPort1 = new HostAndPort("47.107.88.216", 10011);
        HostAndPort hostAndPort2 = new HostAndPort("47.107.88.216", 10012);
        HostAndPort hostAndPort3 = new HostAndPort("47.107.88.216", 10013);
        HostAndPort hostAndPort4 = new HostAndPort("47.107.88.216", 10014);
        HostAndPort hostAndPort5 = new HostAndPort("47.107.88.216", 10015);
        Set<HostAndPort> hostAndPortSet = new HashSet<>();
        hostAndPortSet.add(hostAndPort);
        hostAndPortSet.add(hostAndPort1);
        hostAndPortSet.add(hostAndPort2);
        hostAndPortSet.add(hostAndPort3);
        hostAndPortSet.add(hostAndPort4);
        hostAndPortSet.add(hostAndPort5);
        JedisCluster jedis = new JedisCluster(hostAndPortSet);
        jedis.set("xixicluster", "10011");
        System.out.println(jedis.get("xixicluster"));
        jedis.close();

    }

    // redis单机测试
    @Test
    public void testJedisSingle() throws Exception {
        Jedis jedis = new Jedis("47.107.88.216", 10011);
        jedis.set("xixi", "10010");
        System.out.println(jedis.get("xixi"));
        jedis.close();

    }

}
