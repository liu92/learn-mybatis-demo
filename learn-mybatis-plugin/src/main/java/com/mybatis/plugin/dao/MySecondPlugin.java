package com.mybatis.plugin.dao;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.*;

import java.util.Properties;

/**
 * 多个插件实现
 * @ClassName: MySecondPlugin
 * @Description:
 * @Author: lin
 * @Date: 2019/10/8 9:31
 * History:
 * @<version> 1.0
 */
@Intercepts({@Signature(type = StatementHandler.class, method = "parameterize", args=java.sql.Statement.class)})
public class MySecondPlugin implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        System.out.println("MySecondPlugin.......intercept............:"+invocation.getMethod());
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        //我们可以借助Plugin的wrap方法来使用当前Interceptor包装我们目标对象
        System.out.println("MySecondPlugin...plugin:mybatis将要包装的对象"+target);
        Object wrap = Plugin.wrap(target, this);
        // 返回为当前target创建的动态代理
        return wrap;
    }

    @Override
    public void setProperties(Properties properties) {
        System.out.println("MySecondPlugin插件配置的信息："+properties);
    }































}
