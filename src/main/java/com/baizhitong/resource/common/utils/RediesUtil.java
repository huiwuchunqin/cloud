package com.baizhitong.resource.common.utils;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;

public class RediesUtil extends JedisPoolUtil {

    protected final static ExecutorService executorService  = Executors.newCachedThreadPool();
    protected final static long            TIMEOUT_MILL_SEC = 1000;                                     // 5000ms
    protected final static long            limit            = 50;                                       // 50ms
    private static final Logger            log              = LoggerFactory.getLogger(RediesUtil.class);
    private static RediesUtil              rediesUtil;

    private RediesUtil() {
        super();
    }

    synchronized public static RediesUtil getInstance() {
        return rediesUtil == null ? new RediesUtil() : rediesUtil;
    }

    /**
     * get
     * 
     * @param key
     * @return
     */
    public String get(final String key) {
        final Jedis jedis = this.getClient();
        try {
            Future<String> future = executorService.submit(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    long tick = System.currentTimeMillis();

                    String res = jedis.get(key);

                    tick = System.currentTimeMillis() - tick;
                    if (tick > limit) {
                        log.error(key, tick, "RediesUtil get");
                    }

                    return res;
                }
            });
            return future.get(TIMEOUT_MILL_SEC, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            log.error("get error!", e);
            this.returnResource(jedis);
            return null;
        } finally {
            this.returnResource(jedis);
        }

    }

    /**
     * del
     * 
     * @param key
     * @return
     */
    public Long del(final String key) {
        final Jedis jedis = this.getClient();
        try {
            Future<Long> future = executorService.submit(new Callable<Long>() {
                @Override
                public Long call() throws Exception {
                    long tick = System.currentTimeMillis();
                    long result = jedis.del(key);
                    tick = System.currentTimeMillis() - tick;
                    if (tick > limit) {
                        log.error(key, tick, "RediesUtil del");
                        ;
                    }
                    return result;
                }
            });
            return future.get(TIMEOUT_MILL_SEC, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            log.error("del error!", e);
            this.returnResource(jedis);
            return 0L;
        } finally {
            this.returnResource(jedis);
        }
    }

    /**
     * set
     * 
     * @param key
     * @param value
     * @param seconds
     * @return
     */
    public boolean set(final String key, final String value, final int seconds) {
        final Jedis jedis = this.getClient();
        try {
            Future<Boolean> future = executorService.submit(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    long tick = System.currentTimeMillis();

                    String ret = jedis.set(key, value);
                    if (seconds > 0) {
                        jedis.expire(key, seconds);
                    }

                    tick = System.currentTimeMillis() - tick;
                    if (tick > limit) {
                        log.error(key, tick, "RediesUtil set");
                        ;
                    }

                    return ret.equalsIgnoreCase("OK");
                }
            });
            return future.get(TIMEOUT_MILL_SEC, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            log.error("set error!", e);
            this.returnResource(jedis);
            return false;
        } finally {
            this.returnResource(jedis);
        }

    }

    public boolean set(String key, String value) {
        return set(key, value, 0);
    }

}
