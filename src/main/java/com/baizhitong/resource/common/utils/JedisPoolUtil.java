package com.baizhitong.resource.common.utils;

import org.apache.commons.pool.impl.GenericObjectPool.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baizhitong.utils.PropertieUtils;
import com.baizhitong.utils.StringUtils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedisPool;

/**
 * 缓存连接池 JedisPoolUtil TODO
 * 
 * @author creator gaow 2017年2月16日 上午9:31:12
 * @author updater
 *
 * @version 1.0.0
 */
public class JedisPoolUtil {
    private final static Logger    log      = LoggerFactory.getLogger(JedisPoolUtil.class);
    public static PropertieUtils   property = new PropertieUtils("settings.properties");
    public static ShardedJedisPool sharedJedisPool;

    public static JedisPool        jedisPool;
    public static String           host;
    public static Integer          port;
    public static Integer          timeout;
    public static String           password;

    static {
        Config config = new Config();
        config.maxWait = 10000;
        config.maxIdle = 100;
        config.maxActive = 5000;
        config.testOnBorrow = true;
        config.testWhileIdle = false;
        host = property.getProperty("redis.host");
        port = property.getInteger("redis.port");
        timeout = property.getInteger("redis.maxWaitSecond");
        password = property.getString("password");
        if (null == jedisPool) {
            if (StringUtils.isNotEmpty(password)) {
                jedisPool = new JedisPool(config, host, port, 10000, password);
            } else {
                jedisPool = new JedisPool(config, host, port, 10000);
            }
        }
    }

    public JedisPoolUtil() {

    }

    /**
     * 获取连接 ()<br>
     * 
     * @return
     */
    protected Jedis getClient() {
        if (jedisPool != null) {
            Jedis resource = jedisPool.getResource();
            return resource;
        } else {
            return null;
        }
    }

    /**
     * 释放资源 ()<br>
     * 
     * @param jedis
     */
    protected void returnResource(final Jedis jedis) {
        if (jedis != null) {
            jedisPool.returnResource(jedis);
        }
    }

}
