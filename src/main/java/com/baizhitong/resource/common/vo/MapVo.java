package com.baizhitong.resource.common.vo;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MapVo<K, V> extends BaseVo implements Map<K, V>, Cloneable {

    private Map<K, V> innerMap = new HashMap<K, V>();

    /**
     * 将普通的list转换为listVo
     * 
     * @param list
     * @param code
     * @param msg
     * @return
     */
    public static <K, V> MapVo<K, V> asMapVo(Map<K, V> map, String bizCode, String bizMsg) {
        MapVo<K, V> mapVo = new MapVo<K, V>();
        mapVo.innerMap = map;
        mapVo.bizCode = bizCode;
        mapVo.bizMsg = bizMsg;
        return mapVo;
    }

    /**
     * 将list转换为普通的listVo,bizCode 为成功
     * 
     * @param list
     * @param bizCode
     * @return
     */
    public static <K, V> MapVo<K, V> asMapVo(Map<K, V> map) {
        return asMapVo(map, null, null);
    }

    /**
     * 将list转换为普通的listVo
     * 
     * @param list
     * @param bizCode
     * @return
     */
    public static <K, V> MapVo<K, V> asMapVo(Map<K, V> map, String bizCode) {
        return asMapVo(map, bizCode, null);
    }

    @Override
    public int size() {
        return innerMap.size();
    }

    @Override
    public boolean isEmpty() {
        return innerMap.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return innerMap.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return innerMap.containsValue(value);
    }

    @Override
    public V get(Object key) {
        return innerMap.get(key);
    }

    @Override
    public V put(K key, V value) {
        return innerMap.put(key, value);
    }

    @Override
    public V remove(Object key) {
        return innerMap.remove(key);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        innerMap.putAll(m);
    }

    @Override
    public void clear() {
        innerMap.clear();
    }

    @Override
    public Set<K> keySet() {
        return innerMap.keySet();
    }

    @Override
    public Collection<V> values() {
        return innerMap.values();
    }

    @Override
    public Set<java.util.Map.Entry<K, V>> entrySet() {
        return innerMap.entrySet();
    }

}
