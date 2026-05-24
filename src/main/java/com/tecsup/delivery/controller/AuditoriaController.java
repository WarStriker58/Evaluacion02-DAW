package com.tecsup.delivery.controller;

import com.tecsup.delivery.entity.AuditoriaLog;
import com.tecsup.delivery.repository.AuditoriaLogRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping({"/auditorias", "/auditorias/"})
public class AuditoriaController {

    private final AuditoriaLogRepository auditoriaLogRepository;

    public AuditoriaController(AuditoriaLogRepository auditoriaLogRepository) {
        this.auditoriaLogRepository = auditoriaLogRepository;
    }

    @GetMapping
    public List<AuditoriaLog> listar() {
        return auditoriaLogRepository.findAll();
    }
}
