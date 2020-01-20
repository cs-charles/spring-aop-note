package com.orange.springaoptest.aop;

import com.orange.springaoptest.annotation.IsLogin;
import com.orange.springaoptest.model.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.lang.reflect.Modifier;

/**
 * @author xieyong
 * @date 2020/1/19
 * @Description:
 */
@Aspect
@Component
@Slf4j
public class Aop {

    //execution写法
    /*@Pointcut(value = "execution(* com.orange.springaoptest.service..*.*(..))")
    public void pointcut(){

    }*/

    //within写法
    @Pointcut(value = "within(com.orange.springaoptest.service..*)")
    public void pointcut(){

    }



    @Before(value = "pointcut() && @annotation(isLogin) && args(userId)",argNames = "isLogin,userId")
    public void before(JoinPoint joinPoint, IsLogin isLogin,Long userId){
        log.info("【execution pointcut】 before方法进入:");
        log.info("方法注解："+isLogin.toString());
        log.info("方法入参:userId="+userId);
        printLog(joinPoint);
        log.info("【execution pointcut】 before方法退出:");
    }

    @Around("pointcut() && args(userId)")
    public Object aroundMethod(ProceedingJoinPoint pjd,Long userId){
        //前置通知
        log.info("【execution pointcut】 aroundMethod方法进入:");
        if(userId == null){
            //前置通知
            log.info("执行目标方法异常后...");
            throw new NullPointerException();
        }
        Object result = null;
        try {
            //执行目标方法
            //result = pjd.proeed();
            //用新的参数值执行目标方法 before方法在proceed方法前执行，如果proceed方法不执行，则before方法也不会执行
            result = pjd.proceed(new Object[]{2L});
            log.info("执行目标方法完后...");
        } catch (Throwable e) {
            //异常通知
            log.info("执行目标方法异常后...");
            throw new NullPointerException();
        }
        //后置通知
        log.info("【execution pointcut】 aroundMethod方法退出:");
        return result;
    }


    @AfterReturning(value = "pointcut()",returning = "result")
    public void afterReturning(JoinPoint joinPoint, UserDTO result){
        log.info("【execution pointcut】 afterReturning方法进入:");
        log.info(result.toString());
        result.setUserName("诸葛亮");
        log.info("【execution pointcut】 afterReturning方法退出:");
    }

    @AfterThrowing(value = "pointcut()",throwing = "e")
    public void afterThrowing(JoinPoint joinPoint,NullPointerException e){
        log.info("【execution pointcut】 afterThrowing方法进入:");
        log.info(e.getMessage());
        log.info("【execution pointcut】 afterThrowing方法退出:");
    }

    @After(value = "pointcut()")
    public void after(JoinPoint joinPoint){
        log.info("【execution pointcut】 after方法进入:");
        printLog(joinPoint);
        log.info("【execution pointcut】 after方法退出:");
    }



    private void printLog(JoinPoint joinPoint){
        log.info("目标方法名为:" + joinPoint.getSignature().getName());
        log.info("目标方法所属类的简单类名:" +        joinPoint.getSignature().getDeclaringType().getSimpleName());
        log.info("目标方法所属类的类名:" + joinPoint.getSignature().getDeclaringTypeName());
        log.info("目标方法声明类型:" + Modifier.toString(joinPoint.getSignature().getModifiers()));
        //获取传入目标方法的参数
        Object[] args = joinPoint.getArgs();
        for (int i = 0; i < args.length; i++) {
            log.info("第" + (i+1) + "个参数为:" + args[i]);
        }
        log.info("被代理的对象:" + joinPoint.getTarget());
        log.info("代理对象自己:" + joinPoint.getThis());
    }

}
