package com.moe.common.core.utils;


import cn.hutool.core.util.ObjectUtil;
import com.moe.common.core.exception.ServiceException;

/**
 * 断言某些对象或值是否符合规定，否则抛出异常
 *
 * @author：zhandepei
 * @date：2024/9/2
 */
public class Assert {

    /**
     * 判断一个布尔表达式是否为true，如果不是，则抛出一个自定义的业务异常
     *
     * @param expression 布尔表达式
     * @param message    异常消息
     */
    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            throw new ServiceException(message);
        }
    }

    /**
     * 判断一个布尔表达式是否为true，如果不是，则抛出一个根据模板生成的业务异常
     *
     * @param expression 布尔表达式
     * @param template   异常消息模板
     * @param args       模板参数
     */
    public static void isTrue(boolean expression, String template, Object... args) {
        if (!expression) {
            throw new ServiceException(template, args);
        }
    }

    /**
     * 判断一个布尔表达式是否为false，如果不是，则抛出一个自定义的业务异常
     *
     * @param expression 布尔表达式
     * @param message    异常消息
     */
    public static void isFalse(boolean expression, String message) {
        if (expression) {
            throw new ServiceException(message);
        }
    }

    /**
     * 判断一个布尔表达式是否为false，如果不是，则抛出一个根据模板生成的业务异常
     *
     * @param expression 布尔表达式
     * @param template   异常消息模板
     * @param args       模板参数
     */
    public static void isFalse(boolean expression, String template, Object... args) {
        if (expression) {
            throw new ServiceException(template, args);
        }
    }


    /**
     * 判断一个字符序列是否为空，如果是，则抛出一个自定义的业务异常
     *
     * @param object  字符序列
     * @param message 异常消息
     */
    public static void notBlank(CharSequence object, String message) {
        if (ObjectUtil.isEmpty(object)) {
            throw new ServiceException(message);
        }
    }

    /**
     * 判断一个字符序列是否为空，如果是，则抛出一个根据模板生成的业务异常
     *
     * @param object   字符序列
     * @param template 异常消息模板
     * @param args     模板参数
     */
    public static void notBlank(CharSequence object, String template, Object... args) {
        if (ObjectUtil.isEmpty(object)) {
            throw new ServiceException(template, args);
        }
    }

    /**
     * 判断一个字符序列是否不为空，如果为空，则抛出一个自定义的业务异常
     *
     * @param object  字符序列
     * @param message 异常消息
     */
    public static void isBlank(CharSequence object, String message) {
        if (ObjectUtil.isNotEmpty(object)) {
            throw new ServiceException(message);
        }
    }

    /**
     * 判断一个字符序列是否不为空，如果为空，则抛出一个根据模板生成的业务异常
     *
     * @param object   字符序列
     * @param template 异常消息模板
     * @param args     模板参数
     */
    public static void isBlank(CharSequence object, String template, Object... args) {
        if (ObjectUtil.isNotEmpty(object)) {
            throw new ServiceException(template, args);
        }
    }

    /**
     * 判断一个对象是否为null，如果不是，则抛出一个自定义的业务异常
     *
     * @param object  对象
     * @param message 异常消息
     */
    public static void notNull(Object object, String message) {
        if (ObjectUtil.isNull(object)) {
            throw new ServiceException(message);
        }
    }

    /**
     * 判断一个对象是否为null，如果不是，则抛出一个根据模板生成的业务异常
     *
     * @param object   对象
     * @param template 异常消息模板
     * @param args     模板参数
     */
    public static void notNull(Object object, String template, Object... args) {
        if (ObjectUtil.isNull(object)) {
            throw new ServiceException(template, args);
        }
    }

    /**
     * 判断一个对象是否不为null，如果是，则抛出一个自定义的业务异常
     *
     * @param object  对象
     * @param message 异常消息
     */
    public static void isNull(Object object, String message) {
        if (ObjectUtil.isNotNull(object)) {
            throw new ServiceException(message);
        }
    }

    /**
     * 判断一个对象是否不为null，如果是，则抛出一个根据模板生成的业务异常
     *
     * @param object   对象
     * @param template 异常消息模板
     * @param args     模板参数
     */
    public static void isNull(Object object, String template, Object... args) {
        if (object != null) {
            throw new ServiceException(template, args);
        }
    }

    /**
     * 判断一个对象是否为空，如果是，则抛出一个自定义的业务异常
     *
     * @param object  对象
     * @param message 异常消息
     */
    public static void notEmpty(Object object, String message) {
        if (ObjectUtil.isEmpty(object)) {
            throw new ServiceException(message);
        }
    }

    /**
     * 判断一个对象是否为空，如果是，则抛出一个根据模板生成的业务异常
     *
     * @param object   对象
     * @param template 异常消息模板
     * @param args     模板参数
     */
    public static void notEmpty(Object object, String template, Object... args) {
        if (ObjectUtil.isEmpty(object)) {
            throw new ServiceException(template, args);
        }
    }

    /**
     * 判断一个对象是否不为空，如果为空，则抛出一个自定义的业务异常
     *
     * @param object  对象
     * @param message 异常消息
     */
    public static void isEmpty(Object object, String message) {
        if (ObjectUtil.isNotEmpty(object)) {
            throw new ServiceException(message);
        }
    }

    /**
     * 判断一个对象是否不为空，如果为空，则抛出一个根据模板生成的业务异常
     *
     * @param object   对象
     * @param template 异常消息模板
     * @param args     模板参数
     */
    public static void isEmpty(Object object, String template, Object... args) {
        if (ObjectUtil.isNotEmpty(object)) {
            throw new ServiceException(template, args);
        }
    }

    /**
     * 判断一个对象数组是否为空，如果是，则抛出一个自定义的业务异常
     *
     * @param object  对象数组
     * @param message 异常消息
     */
    public static void notEmpty(Object[] object, String message) {
        if (ObjectUtil.isEmpty(object)) {
            throw new ServiceException(message);
        }
    }

    /**
     * 判断一个对象数组是否为空，如果是，则抛出一个根据模板生成的业务异常
     *
     * @param object   对象数组
     * @param template 异常消息模板
     * @param args     模板参数
     */
    public static void notEmpty(Object[] object, String template, Object... args) {
        if (ObjectUtil.isEmpty(object)) {
            throw new ServiceException(template, args);
        }
    }

    /**
     * 判断一个对象数组是否不为空，如果为空，则抛出一个自定义的业务异常
     *
     * @param object  对象数组
     * @param message 异常消息
     */
    public static void isEmpty(Object[] object, String message) {
        if (ObjectUtil.isNotEmpty(object)) {
            throw new ServiceException(message);
        }
    }

    /**
     * 判断一个对象数组是否不为空，如果为空，则抛出一个根据模板生成的业务异常
     *
     * @param object   对象数组
     * @param template 异常消息模板
     * @param args     模板参数
     */
    public static void isEmpty(Object[] object, String template, Object... args) {
        if (ObjectUtil.isNotEmpty(object)) {
            throw new ServiceException(template, args);
        }
    }

}
