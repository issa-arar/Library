package com.assessment.library.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {


    @Pointcut("execution(* com.assessment.library.controller.*.*(..))")
    public void controllerMethods() {}

    @Before("controllerMethods()")
    public void logBefore(JoinPoint joinPoint) {
        log.info("Executing method: {}", joinPoint.getSignature().getName());
    }

    @AfterReturning(pointcut = "controllerMethods()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        log.info("Method {} executed successfully", joinPoint.getSignature().getName());
    }

    @AfterThrowing(pointcut = "controllerMethods()", throwing = "ex")
    public void logAfterException(JoinPoint joinPoint, Exception ex) {
        log.error("Exception in method {}: {}", joinPoint.getSignature().getName(), ex.getMessage());
    }
}

