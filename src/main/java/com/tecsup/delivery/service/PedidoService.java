package com.tecsup.delivery.service;

import com.tecsup.delivery.dto.DetallePedidoRequest;
import com.tecsup.delivery.dto.PedidoRequest;
import com.tecsup.delivery.entity.Cliente;
import com.tecsup.delivery.entity.DetallePedido;
import com.tecsup.delivery.entity.Pedido;
import com.tecsup.delivery.entity.Producto;
import com.tecsup.delivery.exception.NegocioException;
import com.tecsup.delivery.exception.RecursoNoEncontradoException;
import com.tecsup.delivery.repository.ClienteRepository;
import com.tecsup.delivery.repository.PedidoRepository;
import com.tecsup.delivery.repository.ProductoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final ProductoRepository productoRepository;

    public PedidoService(PedidoRepository pedidoRepository, ClienteRepository clienteRepository,
                         ProductoRepository productoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.clienteRepository = clienteRepository;
        this.productoRepository = productoRepository;
    }

    public List<Pedido> listar() {
        return pedidoRepository.findAll();
    }

    public Pedido obtener(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Pedido no encontrado"));
    }

    @Transactional
    public Pedido crear(PedidoRequest request) {
        Cliente cliente = clienteRepository.findById(request.getClienteId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Cliente no encontrado"));

        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setFecha(LocalDate.now());

        double total = 0.0;
        for (DetallePedidoRequest item : request.getDetalles()) {
            Producto producto = productoRepository.findById(item.getProductoId())
                    .orElseThrow(() -> new RecursoNoEncontradoException("Producto no encontrado"));

            if (producto.getStock() < item.getCantidad()) {
                throw new NegocioException("Stock insuficiente para el producto " + producto.getNombre());
            }

            double subtotal = producto.getPrecio() * item.getCantidad();
            producto.setStock(producto.getStock() - item.getCantidad());
            productoRepository.save(producto);

            DetallePedido detalle = new DetallePedido();
            detalle.setProducto(producto);
            detalle.setCantidad(item.getCantidad());
            detalle.setSubtotal(subtotal);
            pedido.agregarDetalle(detalle);
            total += subtotal;
        }

        pedido.setTotal(total);
        return pedidoRepository.save(pedido);
    }

    public void eliminar(Long id) {
        pedidoRepository.delete(obtener(id));
    }
}
