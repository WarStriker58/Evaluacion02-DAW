package com.tecsup.delivery.service;

import com.tecsup.delivery.entity.Categoria;
import com.tecsup.delivery.exception.RecursoNoEncontradoException;
import com.tecsup.delivery.repository.CategoriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public List<Categoria> listar() {
        return categoriaRepository.findAll();
    }

    public Categoria obtener(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Categoria no encontrada"));
    }

    public Categoria crear(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    public Categoria actualizar(Long id, Categoria datos) {
        Categoria categoria = obtener(id);
        categoria.setNombre(datos.getNombre());
        return categoriaRepository.save(categoria);
    }

    public void eliminar(Long id) {
        categoriaRepository.delete(obtener(id));
    }
}
