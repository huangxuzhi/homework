package com.lagou.edu.pojo;

import com.lagou.edu.factory.BeanFactory;
import com.lagou.edu.utils.TransactionManager;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author huangxz
 * @date ${date}
 * @description
 */
public class TransactionProxy implements InvocationHandler {

    private Object o;

    private List<String> methodNamesToProxy;

    TransactionManager transactionManager = (TransactionManager) BeanFactory.getBean("transactionManager");

    public TransactionProxy(List<String> methodNamesToProxy, Object o) {
        this.methodNamesToProxy = methodNamesToProxy;
        this.o  = o;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = null;
        if (methodNamesToProxy.contains(method.getName())) {
            try {
                // 开启事务(关闭事务的自动提交)
                transactionManager.beginTransaction();

                result = method.invoke(o, args);

                // 提交事务

                transactionManager.commit();
            } catch (Exception e) {
                e.printStackTrace();
                // 回滚事务
                transactionManager.rollback();

                // 抛出异常便于上层servlet捕获
                throw e;

            }

            return result;
        } else {
            return method.invoke(o, args);
        }
    }
}
