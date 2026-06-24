package com.techlab.ecommerce.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techlab.ecommerce.exception.ProductoNoEncontradoException;
import com.techlab.ecommerce.model.Producto;
import com.techlab.ecommerce.service.ProductoService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/productos")
@CrossOrigin(origins = "http://localhost:5500")
public class ProductoController {
    
    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }
/* 

    @GetMapping
    public List<Producto> listarTodos() {
        return productoService.listarTodos();
    }
*/
    @GetMapping
    public ResponseEntity<List<Producto>> listarTodos() {
        List<Producto> productos = productoService.listarTodos();
        return ResponseEntity.ok(productos);
    }
/* 
@GetMapping("/{id}")
    public Producto obtenerPorId(@PathVariable int id) {
        return productoService.buscarPorId(id);
    }
*/
    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerPorId(@PathVariable int id) {
        
       return ResponseEntity.ok(productoService.buscarPorId(id));
    }
/* 
   @PostMapping("")
    public Producto crearProducto(@RequestBody Producto producto) {
        return productoService.guardar(producto);
    }
*/
    @PostMapping("")
    public ResponseEntity<Producto> crearProducto(@Valid @RequestBody Producto producto) {
        Producto nuevoProducto = productoService.guardar(producto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoProducto);
    }
/* 
    @PutMapping("/{id}")
    public Producto actualizarProducto(@PathVariable int id, @RequestBody Producto producto) {
        return productoService.actualizar(id, producto);
    }
*/
     @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizarProducto(@PathVariable int id,@Valid @RequestBody Producto producto) {

       return ResponseEntity.ok(productoService.actualizar(id, producto));
        
    
    }
/* 
    @DeleteMapping("/{id}")
    public void eliminarProducto(@PathVariable int id) {
        productoService.eliminar(id);
    }
*/
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable int id) {
        
             productoService.eliminar(id);
             return ResponseEntity.ok().build();
      
       
    }

    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<Producto>> obtenerPorCategoria(@PathVariable String categoria) {
        List<Producto> productos = productoService.buscarPorCategoria(categoria);
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<List<Producto>> obtenerPorNombre(@PathVariable String nombre) {
        List<Producto> productos = productoService.buscarPorNombre(nombre);
        return ResponseEntity.ok(productos);
    }

}