package com.xazhao.core.config;

import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.wrapper.MapWrapper;

import java.util.Map;

/**
 * 重写Map的包装器将Map的key全部转换为小写
 *
 * @Description Created on 2024/11/20.
 * @Author Zhao.An
 */

public class CustomMapWrapper extends MapWrapper {

    public CustomMapWrapper(MetaObject metaObject, Map<String, Object> map) {
        super(metaObject, map);
    }

    @Override
    public String findProperty(String name, boolean useCamelCaseMapping) {
        // 返回字段转小写
        return name == null ? "" : name.toLowerCase();
    }
}
