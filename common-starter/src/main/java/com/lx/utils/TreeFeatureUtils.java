package com.lx.utils;

import com.lx.dto.TreeFeature;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Author: yuj
 * @Date: 2021/11/16
 * @Describe: 一些公共的树类的方法，需要提前继承{@link TreeFeature}
 */
public class TreeFeatureUtils {

    /**
     * 构建树形结构
     *
     * @param list     待构建的list
     * @param rootCode 根节点
     * @return 组装好的树结构list
     */
    public static <T extends TreeFeature> List<T> buildTree(List<T> list, String rootCode) {
        // 递归组装树形结构
        return list.stream()
                .filter(e -> rootCode.equals(e.getParentCode()))
                .peek(e -> e.setChildList(getChildList(e, list)))
                .sorted(Comparator.comparing(T::getCurrentCode))
                .collect(Collectors.toList());
    }


    private static <T extends TreeFeature> List<T> getChildList(T t, List<T> filterList) {
        return filterList.stream().filter(e -> e.getParentCode().equals(t.getCurrentCode()))
                .peek(e -> e.setChildList(getChildList(e, filterList)))
                .sorted(Comparator.comparing(T::getCurrentCode))
                .collect(Collectors.toList());
    }

    /**
     * 获取所有节点的全路径
     *
     * @param fullList 整个List
     * @return
     */
    public static Map<String, List<String>> getAllFullPaths(List<? extends TreeFeature> fullList) {
        return getAllFullPaths(fullList, "0");
    }

    /**
     * 获取所有节点的全路径
     *
     * @param fullList 整个List
     * @param rootCode 根节点code，默认为0
     * @return
     */
    public static Map<String, List<String>> getAllFullPaths(List<? extends TreeFeature> fullList, String rootCode) {
        return getAllFullPaths(fullList, rootCode, true);
    }

    /**
     * 获取所有节点的全路径
     *
     * @param fullList  整个List
     * @param rootCode  根节点code
     * @param sortFlag  排序规则
     * @return
     */
    public static Map<String, List<String>> getAllFullPaths(List<? extends TreeFeature> fullList, String rootCode, boolean sortFlag) {
        Map<String, ? extends TreeFeature> fullTreeMap = fullList.stream().collect(Collectors.toMap(TreeFeature::getCurrentCode, Function.identity()));
        return fullList.stream().collect(Collectors.toMap(
                TreeFeature::getCurrentCode, node -> {
                    ArrayList<String> lineList = new ArrayList<>();
                    TreeFeature tf = node;
                    lineList.add(tf.getCurrentCode());
                    while (!tf.getParentCode().equals(rootCode)) {
                        lineList.add(tf.getParentCode());
                        tf = fullTreeMap.get(tf.getParentCode());
                    }
                    if (sortFlag) {
                        Collections.reverse(lineList);
                    }
                    return lineList;
                }
        ));
    }

    public static Map<String, List<String>> getFullPaths(List<? extends TreeFeature> fullList, List<String> codeList, String rootCode) {
        return getFullPaths(fullList, codeList, rootCode, true);
    }

    /**
     * 根据节点id获取全路径
     *
     * @param fullList  整个List
     * @param codeList  需要获取节点id的List
     * @param rootCode  根节点code
     * @param sortFlag  排序规则
     * @return key为节点id, value为路径的List, 返回结果根据key已去重
     */
    public static Map<String, List<String>> getFullPaths(List<? extends TreeFeature> fullList, List<String> codeList, String rootCode, boolean sortFlag) {
        if (CollectionUtils.isEmpty(fullList) || CollectionUtils.isEmpty(codeList)) {
            return new HashMap<>();
        }

        Map<String, List<String>> lineMap = getAllFullPaths(fullList, rootCode, sortFlag);

        return codeList.stream().collect(Collectors.toMap(Function.identity(), e -> lineMap.getOrDefault(e, new ArrayList<>()), (oldValue, newValue) -> newValue));
    }

    /**
     * 将子节点为空的属性变成null
     *
     * @param treeFeatureList 树形list
     */
    public static void excludeEmptyChildList(List<? extends TreeFeature> treeFeatureList) {
        excludeEmptyChildList(treeFeatureList, null);
    }

    /**
     * 将子节点为空的属性变成null
     *
     * @param treeFeatureList 树形list
     * @param valueList       设置子节点属性的值，默认为 null
     */
    public static void excludeEmptyChildList(List<? extends TreeFeature> treeFeatureList, List<? extends TreeFeature> valueList) {
        treeFeatureList.forEach(e -> {
            if (!CollectionUtils.isEmpty(e.getChildList())) {
                excludeEmptyChildList(e.getChildList());
            } else {
                e.setChildList(valueList);
            }
        });
    }
}
