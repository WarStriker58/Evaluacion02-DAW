package com.tecsup.delivery.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

public class PedidoRequest {

    @NotNull(message = "El cliente es obligatorio")
    private Long clienteId;

    @Valid
    @NotEmpty(message = "El pedido debe contener al menos un producto")
    private List<DetallePedidoRequest> detalles = new ArrayList<>();

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public List<DetallePedidoRequest> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetallePedidoRequest> detalles) {
        this.detalles = detalles;
    }
}
