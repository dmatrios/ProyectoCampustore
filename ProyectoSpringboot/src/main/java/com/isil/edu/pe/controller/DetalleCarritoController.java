package com.isil.edu.pe.controller;

import com.isil.edu.pe.exceptions.ResourceNotFoundException;
import com.isil.edu.pe.model.DetalleCarrito;
import com.isil.edu.pe.repository.DetalleCarritoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/detallecarrito/controller")
public class DetalleCarritoController {

    @Autowired
    private DetalleCarritoRepository detalleRepo;

    // Obtener todos los detalles
    @GetMapping("/detalle")
    public List<DetalleCarrito> getAllDetalles() {
        return detalleRepo.findAll();
    }

    // Obtener detalle por ID
    @GetMapping("/detalle/{id}")
    public ResponseEntity<DetalleCarrito> getDetalleById(@PathVariable("id") Integer iddetalle)
            throws ResourceNotFoundException {
        DetalleCarrito detalle = detalleRepo.findById(iddetalle)
                .orElseThrow(() -> new ResourceNotFoundException("Detalle no encontrado con ID: " + iddetalle));
        return ResponseEntity.ok(detalle);
    }

    // Crear nuevo detalle
    @PostMapping("/detalle")
    public DetalleCarrito createDetalle(@Validated @RequestBody DetalleCarrito detalle) {
        return detalleRepo.save(detalle);
    }

    // Actualizar un detalle por ID
    @PutMapping("/detalle/{id}")
    public ResponseEntity<DetalleCarrito> updateDetalle(@PathVariable("id") Integer iddetalle,
                                                        @Validated @RequestBody DetalleCarrito detalleRequest)
            throws ResourceNotFoundException {
        DetalleCarrito detalle = detalleRepo.findById(iddetalle)
                .orElseThrow(() -> new ResourceNotFoundException("Detalle no encontrado con ID: " + iddetalle));

        detalle.setCantidad(detalleRequest.getCantidad());
        detalle.setPrecioUnitario(detalleRequest.getPrecioUnitario());
        detalle.setSubtotal(detalleRequest.getSubtotal());
        detalle.setFechaAgregada(detalleRequest.getFechaAgregada());
        detalle.setEstadoItem(detalleRequest.getEstadoItem());
        detalle.setCarrito(detalleRequest.getCarrito());
        detalle.setProducto(detalleRequest.getProducto());

        return ResponseEntity.ok(detalleRepo.save(detalle));
    }

    // Eliminar un detalle por ID
    @DeleteMapping("/detalle/{id}")
    public Map<String, Boolean> deleteDetalle(@PathVariable("id") Integer iddetalle)
            throws ResourceNotFoundException {
        DetalleCarrito detalle = detalleRepo.findById(iddetalle)
                .orElseThrow(() -> new ResourceNotFoundException("Detalle no encontrado con ID: " + iddetalle));
        detalleRepo.delete(detalle);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
