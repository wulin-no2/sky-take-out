package com.sky.aspect;

import com.sky.annotation.AutoFill;
import com.sky.constant.AutoFillConstant;
import com.sky.context.BaseContext;
import com.sky.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * aspect class for autoFill
 */
@Aspect
@Component
@Slf4j
public class AutoFillAspect {
    /**
     * what classes and what methods should we intercept
     */
    @Pointcut("execution(* com.sky.mapper.*.*(..)) && @annotation(com.sky.annotation.AutoFill)")
    public void autoFillPointCut(){
    }

    /**
     * Pre-notification, field assignment in the notification
     */
    @Before("autoFillPointCut()")
    public void autoFill(JoinPoint joinPoint) throws Exception {
        log.info("start to autoFill..");
        //get database operation type
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();// get method name
        AutoFill autoFill = signature.getMethod().getAnnotation(AutoFill.class);// get annotation
        OperationType value = autoFill.value();// get operation type

        // get object
        Object[] args = joinPoint.getArgs(); // get parameters of the method
        if (args == null || args.length == 0){ // if can't get parameters, don't autoFill
            return;
        }
        Object entity = args[0]; // we put autofilled object as the first parameter
        // prepare data to assign
        LocalDateTime now = LocalDateTime.now();
        Long currentId = BaseContext.getCurrentId();

        // assign values according to the operation type
        if (value == OperationType.INSERT){
            // assign 4 values
            try {
                Method setCreateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_TIME, LocalDateTime.class);
                Method setCreateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_USER, Long.class);
                Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                Method setUpdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);

                // assign values with reflection
                setUpdateTime.invoke(entity,now);
                setCreateTime.invoke(entity,now);
                setCreateUser.invoke(entity,currentId);
                setUpdateUser.invoke(entity,currentId);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if (value == OperationType.UPDATE){
            // assign 2 values
            try {
                Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                Method setUpdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);

                // assign values with reflection
                setUpdateTime.invoke(entity,now);
                setUpdateUser.invoke(entity,currentId);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
