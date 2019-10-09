package com.mybatis.plugin.dao;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import java.util.Properties;

/**
 * @ClassName: MyFristPlugin
 * @Description:
 * @Author: lin
 * @Date: 2019/10/8 8:45
 * History:
 * @<version> 1.0
 *
 * 完成插件签名的目标：就是告诉MyBatis当前插件用来拦截哪个对象的哪个方法
 *
 *@Intercepts 告诉MyBatis当前插件用来拦截哪个对象的哪个方法
 * 这里拦截的是 StatementHandler 对象，中的 设置 参数 parameterize 方法， args表示当前方法用的参数
 */
@Intercepts({@Signature(type = StatementHandler.class, method = "parameterize", args=java.sql.Statement.class)})
public class MyFristPlugin implements Interceptor {

    /**
     * intercept：拦截：
     * 		拦截目标对象的目标方法的执行；
     * // 如果此方法不调用那么，四大对象真正的方法才会被调用
     // 四大对象
     // Executor执行增删改查
     //•Executor(update, query, flushStatements, commit, rollback, getTransaction, close, isClosed)

     // ParameterHandler 参数设置
     //•ParameterHandler(getParameterObject, setParameters)

     // ResultSetHandler 结果处理
     //•ResultSetHandler(handleResultSets, handleOutputParameters)

     //StatementHandler sql预编译，批量处理 等
     //•StatementHandler(prepare, parameterize, batch, update, query)
     // 如果不调用就不能被执行

     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        // 执行目标方法
        System.out.println("MyPlugin.......intercept............:"+invocation.getMethod());

        //动态的改变一下sql运行的参数：以前2号员工，实际从数据库查询3号员工
        //目标对象
        Object target = invocation.getTarget();
        System.out.println("当前拦截到的对象："+target);
        //拿到：StatementHandler==>ParameterHandler===>parameterObject
        //拿到target的元数据
        MetaObject metaObject = SystemMetaObject.forObject(target);
        // 获取那个属性的值
        Object value = metaObject.getValue("parameterHandler.parameterObject");
        System.out.println("sql语句用的参数是："+ value);
        // 修改value的值
        // 所以调用方法查询的是2号员工，实际查询的是3号员工
        metaObject.setValue("parameterHandler.parameterObject",3);
        // sql语句用的参数是：2
        //实际查询 Employee [id=3, lastName=嘿嘿dg, email=567876@qq.com, gender=1, dId=5]

        // 如果此方法不调用那么，四大对象真正的方法才会被调用
        Object proceed = invocation.proceed();
        return proceed;
    }

    @Override
    public Object plugin(Object target) {
        //我们可以借助Plugin的wrap方法来使用当前Interceptor包装我们目标对象
        System.out.println("MyFirstPlugin...plugin:mybatis将要包装的对象"+target);
        Object wrap = Plugin.wrap(target, this);
        // 返回为当前target创建的动态代理
        return wrap;
    }

    /**
     * setProperties：将插件注册时的属性设置进来
     * @param properties
     */
    @Override
    public void setProperties(Properties properties) {
        System.out.println("插件配置的信息："+properties);
    }



















}
