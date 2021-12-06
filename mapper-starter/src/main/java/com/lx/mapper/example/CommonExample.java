package com.lx.mapper.example;

import tk.mybatis.mapper.weekend.Fn;
import tk.mybatis.mapper.weekend.Weekend;
import tk.mybatis.mapper.weekend.WeekendCriteria;
import tk.mybatis.mapper.weekend.reflection.Reflections;

import java.util.Arrays;

/**
 * @Author: jyu
 * @Date: 2021/10/10
 * @Description: 对Weekend的再次封装，如果你熟悉Weekend用法，建议可以使用Weekend
 **/
public class CommonExample<T> extends Weekend<T> {
    private Weekend<T> weekend;
    private WeekendCriteria<T, Object> criteria;

    private CommonExample(Class<T> entityClass) {
        super(entityClass);
        weekend = this;
        where();
    }

    private CommonExample(Class<T> entityClass, boolean exists) {
        super(entityClass, exists);
        this.weekend = this;
        where();
    }

    private CommonExample(Class<T> entityClass, boolean exists, boolean notNull) {
        super(entityClass, exists, notNull);
        this.weekend = this;
        where();
    }

    //	factory start ------------------------------
    public static <A> CommonExample<A> of(Class<A> clazz, Boolean exists, boolean notNull) {
        return new CommonExample<A>(clazz, exists, notNull);
    }

    public static <A> CommonExample<A> of(Class<A> clazz, Boolean exists) {
        return new CommonExample<A>(clazz, exists, Boolean.FALSE);
    }

    public static <A> CommonExample<A> of(Class<A> clazz) {
        return new CommonExample<A>(clazz, Boolean.TRUE);
    }
//	factory end ------------------------------

    // 防止TlrobotExample被多线程共享
    private CommonExample<T> where() {
        if (this.criteria == null) {
            this.criteria = this.weekend.weekendCriteria();
        }
        return this;
    }

    public CommonExample<T> selectProperties(Fn<T, Object>...property){
        String[] properties = Arrays.asList(property).stream().map(Reflections::fnToFieldName).toArray(String[]::new);
        this.weekend.selectProperties(properties);
        return this;
    }

    public CommonExample<T> excludeProperties(Fn<T, Object> ...property){
        String[] properties = Arrays.asList(property).stream().map(Reflections::fnToFieldName).toArray(String[]::new);
        this.weekend.excludeProperties(properties);

        return this;
    }

    public OrderBy orderBy(Fn<T, Object> property) {
        return this.weekend.orderBy(Reflections.fnToFieldName(property));
    }

    public String fnToFieldName(Fn<T, Object> property) {
        return Reflections.fnToFieldName(property);
    }


    public CommonExample<T> andIsNull(Fn<T, Object> fn) {
        this.criteria.andIsNull(Reflections.fnToFieldName(fn));
        return this;
    }

    public CommonExample<T> andIsNotNull(Fn<T, Object> fn) {
        this.criteria.andIsNotNull(Reflections.fnToFieldName(fn));
        return this;
    }

    public CommonExample<T> andEqualTo(Fn<T, Object> fn, Object value) {
        this.criteria.andEqualTo(Reflections.fnToFieldName(fn), value);
        return this;
    }

    public CommonExample<T> andNotEqualTo(Fn<T, Object> fn, Object value) {
        this.criteria.andNotEqualTo(Reflections.fnToFieldName(fn), value);
        return this;
    }

    public CommonExample<T> andGreaterThan(Fn<T, Object> fn, Object value) {
        this.criteria.andGreaterThan(Reflections.fnToFieldName(fn), value);
        return this;
    }

    public CommonExample<T> andGreaterThanOrEqualTo(Fn<T, Object> fn, Object value) {
        this.criteria.andGreaterThanOrEqualTo(Reflections.fnToFieldName(fn), value);
        return this;
    }

    public CommonExample<T> andLessThan(Fn<T, Object> fn, Object value) {
        this.criteria.andLessThan(Reflections.fnToFieldName(fn), value);
        return this;
    }

    public CommonExample<T> andLessThanOrEqualTo(Fn<T, Object> fn, Object value) {
        this.criteria.andLessThanOrEqualTo(Reflections.fnToFieldName(fn), value);
        return this;
    }

    public CommonExample<T> andIn(Fn<T, Object> fn, Iterable values) {
        this.criteria.andIn(Reflections.fnToFieldName(fn), values);
        return this;
    }

    public CommonExample<T> andNotIn(Fn<T, Object> fn, Iterable values) {
        this.criteria.andNotIn(Reflections.fnToFieldName(fn), values);
        return this;
    }

    public CommonExample<T> andBetween(Fn<T, Object> fn, Object value1, Object value2) {
        this.criteria.andBetween(Reflections.fnToFieldName(fn), value1, value2);
        return this;
    }

    public CommonExample<T> andNotBetween(Fn<T, Object> fn, Object value1, Object value2) {
        this.criteria.andNotBetween(Reflections.fnToFieldName(fn), value1, value2);
        return this;
    }

    public CommonExample<T> andLike(Fn<T, Object> fn, String value) {
        this.criteria.andLike(Reflections.fnToFieldName(fn), value);
        return this;
    }

    public CommonExample<T> andNotLike(Fn<T, Object> fn, String value) {
        this.criteria.andNotLike(Reflections.fnToFieldName(fn), value);
        return this;
    }

    public CommonExample<T> orIsNull(Fn<T, Object> fn) {
        this.criteria.orIsNull(Reflections.fnToFieldName(fn));
        return this;
    }

    public CommonExample<T> orIsNotNull(Fn<T, Object> fn) {
        this.criteria.orIsNotNull(Reflections.fnToFieldName(fn));
        return this;
    }

    public CommonExample<T> orEqualTo(Fn<T, Object> fn, Object value) {
        this.criteria.orEqualTo(Reflections.fnToFieldName(fn), value);
        return this;
    }

    public CommonExample<T> orNotEqualTo(Fn<T, Object> fn, Object value) {
        this.criteria.orNotEqualTo(Reflections.fnToFieldName(fn), value);
        return this;
    }

    public CommonExample<T> orGreaterThan(Fn<T, Object> fn, Object value) {
        this.criteria.orGreaterThan(Reflections.fnToFieldName(fn), value);
        return this;
    }

    public CommonExample<T> orGreaterThanOrEqualTo(Fn<T, Object> fn, Object value) {
        this.criteria.orGreaterThanOrEqualTo(Reflections.fnToFieldName(fn), value);
        return this;
    }

    public CommonExample<T> orLessThan(Fn<T, Object> fn, Object value) {
        this.criteria.orLessThan(Reflections.fnToFieldName(fn), value);
        return this;
    }

    public CommonExample<T> orLessThanOrEqualTo(Fn<T, Object> fn, Object value) {
        this.criteria.orLessThanOrEqualTo(Reflections.fnToFieldName(fn), value);
        return this;
    }

    public CommonExample<T> orIn(Fn<T, Object> fn, Iterable values) {
        this.criteria.orIn(Reflections.fnToFieldName(fn), values);
        return this;
    }

    public CommonExample<T> orNotIn(Fn<T, Object> fn, Iterable values) {
        this.criteria.orNotIn(Reflections.fnToFieldName(fn), values);
        return this;
    }

    public CommonExample<T> orBetween(Fn<T, Object> fn, Object value1, Object value2) {
        this.criteria.orBetween(Reflections.fnToFieldName(fn), value1, value2);
        return this;
    }

    public CommonExample<T> orNotBetween(Fn<T, Object> fn, Object value1, Object value2) {
        this.criteria.orNotBetween(Reflections.fnToFieldName(fn), value1, value2);
        return this;
    }

    public CommonExample<T> orLike(Fn<T, Object> fn, String value) {
        this.criteria.orLike(Reflections.fnToFieldName(fn), value);
        return this;
    }

    public CommonExample<T> orNotLike(Fn<T, Object> fn, String value) {
        this.criteria.orNotLike(Reflections.fnToFieldName(fn), value);
        return this;
    }

}
