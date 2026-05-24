package com.tecsup.delivery.service;

import com.tecsup.delivery.entity.Cliente;
import com.tecsup.delivery.exception.NegocioException;
import com.tecsup.delivery.exception.RecursoNoEncontradoException;
import com.tecsup.delivery.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public List<Cliente> listar() {
        return clienteRepository.findAll();
    }

    public Cliente obtener(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Cliente no encontrado"));
    }

    public Cliente crear(Cliente cliente) {
        if (clienteRepository.existsByCorreo(cliente.getCorreo())) {
            throw new NegocioException("El correo ya esta registrado");
        }
        return clienteRepository.save(cliente);
    }

    public Cliente actualizar(Long id, Cliente datos) {
        Cliente cliente = obtener(id);
        if (clienteRepository.existsByCorreoAndIdNot(datos.getCorreo(), id)) {
            throw new NegocioException("El correo ya esta registrado");
        }
        cliente.setNombres(datos.getNombres());
        cliente.setApellidos(datos.getApellidos());
        cliente.setCorreo(datos.getCorreo());
        cliente.setTelefono(datos.getTelefono());
        cliente.setDireccion(datos.getDireccion());
        return clienteRepository.save(cliente);
    }

    public void eliminar(Long id) {
        clienteRepository.delete(obtener(id));
    }
}
