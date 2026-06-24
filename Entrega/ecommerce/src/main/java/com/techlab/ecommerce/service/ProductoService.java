package com.techlab.ecommerce.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.techlab.ecommerce.exception.ProductoNoEncontradoException;
import com.techlab.ecommerce.exception.StockInsuficianteException;
import com.techlab.ecommerce.model.Producto;
import com.techlab.ecommerce.repository.ProductoRepository;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

public ProductoService(ProductoRepository productoRepository) {
    this.productoRepository = productoRepository;
    
}


public Producto guardar(Producto producto) {


    
    return productoRepository.save(producto);
}

public List<Producto> listarTodos() {
    return productoRepository.findAll();
}

public Producto buscarPorId(Integer id) {
    return productoRepository.findById(id)
            .orElseThrow(() -> new ProductoNoEncontradoException("Producto con ID " + id + " no encontrado"));
}

public Producto actualizar(Integer id, Producto productoActualizado) {
    Producto productoExistente = buscarPorId(id);
   
    // Actualizamos los datos
    productoExistente.setNombre(productoActualizado.getNombre());
    productoExistente.setPrecio(productoActualizado.getPrecio());
    productoExistente.setStock(productoActualizado.getStock());
    productoExistente.setCategoria(productoActualizado.getCategoria());

    if(productoActualizado.getImagenUrl() != null){
        productoExistente.setImagenUrl(productoActualizado.getImagenUrl());
    }
 
    // ¡ESTA ES LA LÍNEA QUE FALTABA! Guardamos en la base de datos
    return productoRepository.save(productoExistente);
}
public void eliminar(Integer id) {

    Producto productoExistente = buscarPorId(id);
    productoRepository.delete(productoExistente);
}


public List<Producto> buscarPorCategoria(String categoria) {
    return productoRepository.buscarPorCategoria(categoria);
}
public List<Producto> buscarPorNombre(String nombre) {
    return productoRepository.findByNombreContaining(nombre);
}

}