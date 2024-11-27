package com.xazhao.core.config;

import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.wrapper.ObjectWrapper;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;

import java.util.Map;

/**
 * 重写Map的包装器工厂，返回已经实现的MapKeyLowerWrapper
 *
 * @Description Created on 2024/11/20.
 * @Author Zhao.An
 */

public class MapWrapperFactory implements ObjectWrapperFactory {

    @Override
    public boolean hasWrapperFor(Object object) {

        return object != null && object instanceof Map;
    }

    @Override
    public ObjectWrapper getWrapperFor(MetaObject metaObject, Object object) {

        return new CustomMapWrapper(metaObject, (Map) object);
    }
}
