package com.lx.dto;

import java.util.List;

/**
 * @Author: yuj
 * @Date: 2021/11/16
 * @Describe: 树类型数据结构封装抽象类，继承了此类的子类可以直接使用 {@link com.lx.utils.TreeFeatureUtils} 来调用公用方法
 */
public abstract class TreeFeature<T> {

    /**
     * 子节点list
     */
    private List<T> childList;

    /**
     * 获取当前节点的code
     *
     * @return
     */
    public abstract String getCurrentCode();

    /**
     * 获取父节点的code
     *
     * @return
     */
    public abstract String getParentCode();


    /**
     * 获取层级
     *
     * @return
     */
    public abstract String getCurrentLevel();

    public List<T> getChildList() {
        return childList;
    }

    public void setChildList(List<T> childList) {
        this.childList = childList;
    }


}
