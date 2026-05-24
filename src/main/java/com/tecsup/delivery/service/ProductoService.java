package com.tecsup.delivery.service;

import com.tecsup.delivery.entity.Categoria;
import com.tecsup.delivery.entity.Producto;
import com.tecsup.delivery.exception.NegocioException;
import com.tecsup.delivery.exception.RecursoNoEncontradoException;
import com.tecsup.delivery.repository.CategoriaRepository;
import com.tecsup.delivery.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;

    public ProductoService(ProductoRepository productoRepository, CategoriaRepository categoriaRepository) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    public List<Producto> listar() {
        return productoRepository.findAll();
    }

    public Producto obtener(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Producto no encontrado"));
    }

    public Producto crear(Producto producto) {
        producto.setCategoria(buscarCategoria(producto.getCategoria().getId()));
        return productoRepository.save(producto);
    }

    public Producto actualizar(Long id, Producto datos) {
        Producto producto = obtener(id);
        producto.setNombre(datos.getNombre());
        producto.setDescripcion(datos.getDescripcion());
        producto.setPrecio(datos.getPrecio());
        producto.setStock(datos.getStock());
        producto.setCategoria(buscarCategoria(datos.getCategoria().getId()));
        return productoRepository.save(producto);
    }

    public void eliminar(Long id) {
        productoRepository.delete(obtener(id));
    }

    private Categoria buscarCategoria(Long id) {
        if (id == null) {
            throw new NegocioException("La categoria es obligatoria");
        }
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Categoria no encontrada"));
    }
}
