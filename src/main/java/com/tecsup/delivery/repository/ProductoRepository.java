package com.tecsup.delivery.repository;

import com.tecsup.delivery.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
}
