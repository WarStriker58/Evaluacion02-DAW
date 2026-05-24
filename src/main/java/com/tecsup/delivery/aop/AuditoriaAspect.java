package com.tecsup.delivery.aop;

import com.tecsup.delivery.entity.AuditoriaLog;
import com.tecsup.delivery.repository.AuditoriaLogRepository;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Aspect
@Component
public class AuditoriaAspect {

    private final AuditoriaLogRepository auditoriaLogRepository;

    public AuditoriaAspect(AuditoriaLogRepository auditoriaLogRepository) {
        this.auditoriaLogRepository = auditoriaLogRepository;
    }

    @AfterReturning("execution(* com.tecsup.delivery.service..*.crear(..)) || " +
            "execution(* com.tecsup.delivery.service..*.actualizar(..)) || " +
            "execution(* com.tecsup.delivery.service..*.eliminar(..))")
    public void registrar(JoinPoint joinPoint) {
        AuditoriaLog log = new AuditoriaLog();
        log.setAccion(joinPoint.getSignature().getName().toUpperCase());
        log.setMetodo(joinPoint.getSignature().toShortString());
        log.setFecha(LocalDateTime.now());
        log.setUsuario(usuarioActual());
        auditoriaLogRepository.save(log);
    }

    private String usuarioActual() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return "anonimo";
        }
        return authentication.getName();
    }
}
