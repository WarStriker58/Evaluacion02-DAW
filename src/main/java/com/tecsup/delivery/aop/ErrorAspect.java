package com.tecsup.delivery.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ErrorAspect {

    private static final Logger logger = LoggerFactory.getLogger(ErrorAspect.class);

    @AfterThrowing(pointcut = "execution(* com.tecsup.delivery.service..*(..))", throwing = "ex")
    public void capturarError(JoinPoint joinPoint, Throwable ex) {
        logger.error("Error en {}: {}", joinPoint.getSignature().toShortString(), ex.getMessage());
    }
}
