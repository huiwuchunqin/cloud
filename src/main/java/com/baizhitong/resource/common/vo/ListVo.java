package com.baizhitong.resource.common.vo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Vo的list实现！！！
 * 
 * @author xuaihua
 */
public class ListVo<E> extends BaseVo implements List<E> {

    private List<E> innerList = new ArrayList<E>();

    /**
     * 将普通的list转换为listVo
     * 
     * @param list
     * @param code
     * @param msg
     * @return
     */
    public static <E> ListVo<E> asListVo(List<E> list, String bizCode, String bizMsg) {
        ListVo<E> listVo = new ListVo<E>();
        listVo.innerList = list;
        listVo.bizCode = bizCode;
        listVo.bizMsg = bizMsg;
        return listVo;
    }

    /**
     * 将list转换为普通的listVo,bizCode 为成功
     * 
     * @param list
     * @param bizCode
     * @return
     */
    public static <E> ListVo<E> asListVo(List<E> list) {
        return asListVo(list, null, null);
    }

    /**
     * 将list转换为普通的listVo
     * 
     * @param list
     * @param bizCode
     * @return
     */
    public static <E> ListVo<E> asListVo(List<E> list, String bizCode) {
        return asListVo(list, bizCode, null);
    }

    @Override
    public int size() {
        return innerList.size();
    }

    @Override
    public boolean isEmpty() {
        return innerList.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return innerList.contains(o);
    }

    @Override
    public Iterator<E> iterator() {
        return innerList.iterator();
    }

    @Override
    public Object[] toArray() {
        return innerList.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return innerList.toArray(a);
    }

    @Override
    public boolean add(E e) {
        return innerList.add(e);
    }

    @Override
    public boolean remove(Object o) {
        return innerList.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return innerList.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return innerList.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        return innerList.addAll(index, c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return innerList.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return innerList.retainAll(c);
    }

    @Override
    public void clear() {
        innerList.clear();
    }

    @Override
    public E get(int index) {
        return innerList.get(index);
    }

    @Override
    public E set(int index, E element) {
        return innerList.set(index, element);
    }

    @Override
    public void add(int index, E element) {
        innerList.add(index, element);
    }

    @Override
    public E remove(int index) {
        return innerList.remove(index);
    }

    @Override
    public int indexOf(Object o) {
        return innerList.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return innerList.lastIndexOf(o);
    }

    @Override
    public ListIterator<E> listIterator() {
        return innerList.listIterator();
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return innerList.listIterator(index);
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return innerList.subList(fromIndex, toIndex);
    }

}
