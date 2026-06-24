package com.techlab.ecommerce.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techlab.ecommerce.exception.CategoriaNoEncontradaException;
import com.techlab.ecommerce.model.Categoria;
import com.techlab.ecommerce.service.CategoriaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    
    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }
/* 

    @GetMapping
    public List<Categoria> listarTodos() {
        return categoriaService.listarTodos();
    }
*/
    @GetMapping
    public ResponseEntity<List<Categoria>> listarTodos() {
        List<Categoria> categorias = categoriaService.listarTodos();
        return ResponseEntity.ok(categorias);
    }
/* 
@GetMapping("/{id}")
    public Categoria obtenerPorId(@PathVariable int id) {
        return productoService.buscarPorId(id);
    }
*/
    @GetMapping("/{id}")
    public ResponseEntity<Categoria> obtenerPorId(@PathVariable int id) {
        
      return ResponseEntity.ok(categoriaService.buscarPorId(id));
    }
/* 
   @PostMapping("")
    public Producto crearProducto(@RequestBody Producto producto) {
        return productoService.guardar(producto);
    }
*/
    @PostMapping("")
    public ResponseEntity<Categoria> crearCategoria(@Valid @RequestBody Categoria categoria) {
        Categoria nuevaCategoria = categoriaService.guardar(categoria);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaCategoria);
    }
/* 
    @PutMapping("/{id}")
    public Producto actualizarProducto(@PathVariable int id, @RequestBody Producto producto) {
        return productoService.actualizar(id, producto);
    }
*/
     @PutMapping("/{id}")
    public ResponseEntity<Categoria> actualizarProducto(@PathVariable int id, @Valid @RequestBody Categoria categoria) {

      return ResponseEntity.ok(categoriaService.actualizar(id, categoria));
        
    
    }
/* 
    @DeleteMapping("/{id}")
    public void eliminarProducto(@PathVariable int id) {
        productoService.eliminar(id);
    }
*/
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCategoria(@PathVariable int id) {
        
             categoriaService.eliminar(id);
             return ResponseEntity.ok().build();
       
       
    }


}

