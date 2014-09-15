package com.jtang.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;



public class MyInterceptor {
    

    public void doAccessCheck(JoinPoint jp) {
        //System.out.println(jp.getTarget().getClass().getName());
    	
        System.out.println("前置通知");
        return;
    }

    /* @AfterReturning("anyMethod()") */
    
    public void doAfter(JoinPoint jp) {
        System.out.println("后置通知");
    }

    
    public void after(JoinPoint jp) {
        System.out.println("最终通知");
    }

    
    public void doAfterThrow(JoinPoint jp,Throwable ex) {
        System.out.println("例外通知");
    }


    public Object doBasicProfiling(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("进入环绕通知");
        Object object = pjp.proceed();// 执行该方法
        System.out.println("退出方法");
        return object;
    }

    
    public Object doBasic(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("进入环绕通知...");
        Object object = pjp.proceed();// 执行该方法
        System.out.println("退出方法...");
        return object;
    }

}
