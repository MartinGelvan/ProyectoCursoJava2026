package com.techlab.ecommerce.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.techlab.ecommerce.exception.CarritoNoEncontradoException;
import com.techlab.ecommerce.exception.StockInsuficianteException;
import com.techlab.ecommerce.model.Carrito;
import com.techlab.ecommerce.model.Producto;
import com.techlab.ecommerce.repository.CarritoRepository;

@Service
public class CarritoService {

    private final CarritoRepository carritoRepository;
    private final ProductoService productoService;

    public CarritoService(CarritoRepository carritoRepository, ProductoService productoService){
        this.carritoRepository = carritoRepository;
        this.productoService = productoService;
    }

    public Carrito crear(){
        return carritoRepository.save(new Carrito());
    }

    public Carrito obtenerPorId(Integer id){
        return carritoRepository.findById(id)
        .orElseThrow(()-> new CarritoNoEncontradoException("No se encontro un carrito con id" + id));
    }

    public List<Carrito> listarTodos(){
        return carritoRepository.findAll();
    }

    public Carrito agregarProducto(Integer carritoId, Integer productoId){
        Carrito carrito = obtenerPorId(carritoId);
        Producto producto = productoService.buscarPorId(productoId);

        if(producto.getStock() <= 0){
            throw new StockInsuficianteException("El producto \"" + producto.getNombre() + "\" no tiene stock disponible.");
            
        }

        producto.setStock(producto.getStock() - 1);
        productoService.guardar(producto);
        carrito.getProductos().add(producto);
        return carritoRepository.save(carrito);
    }

    public Carrito vaciar(Integer id){
        Carrito carrito = obtenerPorId(id);
        carrito.getProductos().clear();
        return carritoRepository.save(carrito);
    }

    public void eliminar(Integer id){
        Carrito carrito = obtenerPorId(id);
        carritoRepository.delete(carrito);
    }

  

public Carrito restarProducto(Integer carritoId, Integer productoId) {
    Carrito carrito = obtenerPorId(carritoId);
    Producto producto = productoService.buscarPorId(productoId);


    Producto aRemover = carrito.getProductos().stream()
            .filter(p -> p.getId().equals(productoId))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("El producto no está en el carrito"));

    carrito.getProductos().remove(aRemover);
    producto.setStock(producto.getStock() + 1); 
    productoService.guardar(producto);

    return carritoRepository.save(carrito);
}

public Carrito eliminarProductoCompleto(Integer carritoId, Integer productoId) {
    Carrito carrito = obtenerPorId(carritoId);
    Producto producto = productoService.buscarPorId(productoId);

    
    long cantidadCargada = carrito.getProductos().stream()
            .filter(p -> p.getId().equals(productoId))
            .count();

    if (cantidadCargada > 0) {
        
        carrito.getProductos().removeIf(p -> p.getId().equals(productoId));
        producto.setStock(producto.getStock() + (int) cantidadCargada); // Devolvemos todo el stock
        productoService.guardar(producto);
    }

    return carritoRepository.save(carrito);
}
    
}
