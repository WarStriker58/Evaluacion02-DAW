package com.tecsup.delivery.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Before("execution(* com.tecsup.delivery.service..*(..))")
    public void inicio(JoinPoint joinPoint) {
        logger.info("Inicio de ejecucion: {}", joinPoint.getSignature().toShortString());
    }

    @AfterReturning("execution(* com.tecsup.delivery.service..*(..))")
    public void fin(JoinPoint joinPoint) {
        logger.info("Finalizacion de ejecucion: {}", joinPoint.getSignature().toShortString());
    }
}
