package com.techlab.ecommerce.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techlab.ecommerce.model.Carrito;
import com.techlab.ecommerce.service.CarritoService;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/carritos")
public class CarritoController {

    private final CarritoService service;

    public CarritoController(CarritoService service){
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Carrito>> listarTodos(){
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Carrito> obtenerCarrito(@PathVariable Integer id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<Carrito> crear() {
      
        
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crear());
    }
    
    @PostMapping("/{carritoId}/productos/{productoId}")
    public ResponseEntity<Carrito> agregarProducto(@PathVariable Integer carritoId, @PathVariable Integer productoId) {
        return ResponseEntity.ok(service.agregarProducto(carritoId, productoId));
    }

    @DeleteMapping("/{id}/vaciar")
    public ResponseEntity<Carrito> vaciar(@PathVariable Integer id){
        return ResponseEntity.ok(service.vaciar(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id){
        service.eliminar(id);
        return ResponseEntity.ok().build();
    }
    
    

@DeleteMapping("/{carritoId}/productos/{productoId}")
public ResponseEntity<Carrito> restarProducto(@PathVariable Integer carritoId, @PathVariable Integer productoId) {
    return ResponseEntity.ok(service.restarProducto(carritoId, productoId));
}

@DeleteMapping("/{carritoId}/productos/{productoId}/todos")
public ResponseEntity<Carrito> eliminarProductoCompleto(@PathVariable Integer carritoId, @PathVariable Integer productoId) {
    return ResponseEntity.ok(service.eliminarProductoCompleto(carritoId, productoId));
}
    
}
