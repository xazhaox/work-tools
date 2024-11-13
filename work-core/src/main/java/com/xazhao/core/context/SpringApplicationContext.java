package com.xazhao.core.context;

import cn.hutool.core.lang.TypeReference;
import com.xazhao.core.exception.ServiceErrorCode;
import com.xazhao.core.exception.ServiceException;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultSingletonBeanRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.ResolvableType;
import org.springframework.stereotype.Component;

import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Map;

/**
 * Spring(Spring boot)工具封装，包括：
 *
 * <ol>
 *     <li>Spring IOC容器中的bean对象获取</li>
 *     <li>注册和注销Bean</li>
 * </ol>
 *
 * @Description Created on 2024/11/07.
 * @Author Zhao.An
 */

@Component
public class SpringApplicationContext implements BeanFactoryPostProcessor, ApplicationContextAware {

    /**
     * "@PostConstruct"注解标记的类中，由于ApplicationContext还未加载，导致空指针<br>
     * 因此实现BeanFactoryPostProcessor注入ConfigurableListableBeanFactory实现bean的操作
     */
    private static ConfigurableListableBeanFactory beanFactory;

    /**
     * Spring应用上下文环境
     */
    private static ApplicationContext applicationContext;

    @SuppressWarnings("NullableProblems")
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        SpringApplicationContext.beanFactory = beanFactory;
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        SpringApplicationContext.applicationContext = applicationContext;
    }

    /**
     * 获取{@link ApplicationContext}
     *
     * @return {@link ApplicationContext}
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 获取{@link ListableBeanFactory}，可能为{@link ConfigurableListableBeanFactory} 或 {@link ApplicationContextAware}
     *
     * @return {@link ListableBeanFactory}
     */
    public static ListableBeanFactory getBeanFactory() {
        final ListableBeanFactory factory = null == beanFactory ? applicationContext : beanFactory;
        if (null == factory) {
            throw new ServiceException("No ConfigurableListableBeanFactory or ApplicationContext injected, maybe not in the Spring environment?", ServiceErrorCode.APPLICATION_CONTEXT_ERROR);
        }
        return factory;
    }

    /**
     * 获取{@link ConfigurableListableBeanFactory}
     *
     * @return {@link ConfigurableListableBeanFactory}
     * @throws ServiceException 当上下文非ConfigurableListableBeanFactory抛出异常
     */
    public static ConfigurableListableBeanFactory getConfigurableBeanFactory() throws ServiceException {
        final ConfigurableListableBeanFactory factory;
        if (null != beanFactory) {
            factory = beanFactory;
        } else if (applicationContext instanceof ConfigurableApplicationContext) {
            factory = ((ConfigurableApplicationContext) applicationContext).getBeanFactory();
        } else {
            throw new ServiceException("No ConfigurableListableBeanFactory from context!", ServiceErrorCode.APPLICATION_CONTEXT_ERROR);
        }
        return factory;
    }

    /**
     * 通过name获取 Bean
     *
     * @param <T>  Bean类型
     * @param name Bean名称
     * @return Bean
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) {
        return (T) getBeanFactory().getBean(name);
    }

    /**
     * 通过class获取Bean
     *
     * @param <T>   Bean类型
     * @param clazz Bean类
     * @return Bean对象
     */
    public static <T> T getBean(Class<T> clazz) {
        return getBeanFactory().getBean(clazz);
    }

    /**
     * 通过name，以及Clazz返回指定的Bean
     *
     * @param <T>   bean类型
     * @param name  Bean名称
     * @param clazz bean类型
     * @return Bean对象
     */
    public static <T> T getBean(String name, Class<T> clazz) {
        return getBeanFactory().getBean(name, clazz);
    }

    /**
     * 通过类型参考返回带泛型参数的Bean
     *
     * @param reference 类型参考，用于持有转换后的泛型类型
     * @param <T>       Bean类型
     * @return 带泛型参数的Bean
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(TypeReference<T> reference) {
        final ParameterizedType parameterizedType = (ParameterizedType) reference.getType();
        final Class<T> rawType = (Class<T>) parameterizedType.getRawType();
        final Class<?>[] genericTypes = Arrays.stream(parameterizedType.getActualTypeArguments()).map(type -> (Class<?>) type).toArray(Class[]::new);
        final String[] beanNames = getBeanFactory().getBeanNamesForType(ResolvableType.forClassWithGenerics(rawType, genericTypes));
        return getBean(beanNames[0], rawType);
    }

    /**
     * 获取指定类型对应的所有Bean，包括子类
     *
     * @param <T>  Bean类型
     * @param type 类、接口，null表示获取所有bean
     * @return 类型对应的bean，key是bean注册的name，value是Bean
     */
    public static <T> Map<String, T> getBeansOfType(Class<T> type) {
        return getBeanFactory().getBeansOfType(type);
    }

    /**
     * 获取指定类型对应的Bean名称，包括子类
     *
     * @param type 类、接口，null表示获取所有bean名称
     * @return bean名称
     */
    public static String[] getBeanNamesForType(Class<?> type) {
        return getBeanFactory().getBeanNamesForType(type);
    }

    /**
     * 获取配置文件配置项的值
     *
     * @param key 配置项key
     * @return 属性值
     */
    public static String getProperty(String key) {
        if (null == applicationContext) {
            return null;
        }
        return applicationContext.getEnvironment().getProperty(key);
    }

    /**
     * 获取配置文件配置项的值
     *
     * @param key          配置项key
     * @param defaultValue 默认值
     * @return 属性值
     */
    public static String getProperty(String key, String defaultValue) {
        if (null == applicationContext) {
            return null;
        }
        return applicationContext.getEnvironment().getProperty(key, defaultValue);
    }

    /**
     * 获取配置文件配置项的值
     *
     * @param <T>          属性值类型
     * @param key          配置项key
     * @param targetType   配置项类型
     * @param defaultValue 默认值
     * @return 属性值
     */
    public static <T> T getProperty(String key, Class<T> targetType, T defaultValue) {
        if (null == applicationContext) {
            return null;
        }
        return applicationContext.getEnvironment().getProperty(key, targetType, defaultValue);
    }

    /**
     * 获取应用程序名称
     *
     * @return 应用程序名称
     */
    public static String getApplicationName() {
        return getProperty("spring.application.name");
    }

    /**
     * 获取当前的环境配置，无配置返回null
     *
     * @return 当前的环境配置
     */
    public static String[] getActiveProfiles() {
        if (null == applicationContext) {
            return null;
        }
        return applicationContext.getEnvironment().getActiveProfiles();
    }

    /**
     * 获取当前的环境配置，当有多个环境配置时，只获取第一个<br>
     * 获取所有的环配置，调用{@link SpringApplicationContext#getActiveProfiles()}
     *
     * @return 当前的环境配置
     */
    public static String getActiveProfile() {
        final String[] activeProfiles = getActiveProfiles();
        return (null != activeProfiles && activeProfiles.length != 0) ? activeProfiles[0] : null;
    }

    /**
     * 动态向Spring注册Bean
     * <p>
     * 由{@link org.springframework.beans.factory.BeanFactory} 实现，通过工具开放API
     * <p>
     * 更新: shadow 2021-07-29 17:20:44 增加自动注入，修复注册bean无法反向注入的问题
     *
     * @param <T>      Bean类型
     * @param beanName 名称
     * @param bean     bean
     * @author shadow
     */
    public static <T> void registerBean(String beanName, T bean) {
        final ConfigurableListableBeanFactory factory = getConfigurableBeanFactory();
        factory.autowireBean(bean);
        factory.registerSingleton(beanName, bean);
    }

    /**
     * 注销bean
     * <p>
     * 将Spring中的bean注销，请谨慎使用
     *
     * @param beanName bean名称
     * @author shadow
     */
    public static void unregisterBean(String beanName) {
        final ConfigurableListableBeanFactory factory = getConfigurableBeanFactory();
        if (factory instanceof DefaultSingletonBeanRegistry) {
            DefaultSingletonBeanRegistry registry = (DefaultSingletonBeanRegistry) factory;
            registry.destroySingleton(beanName);
        } else {
            throw new ServiceException("Can not unregister bean, the factory is not a DefaultSingletonBeanRegistry!", ServiceErrorCode.APPLICATION_CONTEXT_ERROR);
        }
    }

    /**
     * 发布事件
     *
     * @param event 待发布的事件，事件必须是{@link ApplicationEvent}的子类
     */
    public static void publishEvent(ApplicationEvent event) {
        if (null != applicationContext) {
            applicationContext.publishEvent(event);
        }
    }

    /**
     * 发布事件
     * Spring 4.2+ 版本事件可以不再是{@link ApplicationEvent}的子类
     *
     * @param event 待发布的事件
     */
    public static void publishEvent(Object event) {
        if (null != applicationContext) {
            applicationContext.publishEvent(event);
        }
    }
}
