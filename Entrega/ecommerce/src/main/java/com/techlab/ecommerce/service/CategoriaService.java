package com.techlab.ecommerce.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.techlab.ecommerce.exception.CategoriaNoEncontradaException;
import com.techlab.ecommerce.exception.CategoriaNombreInvalidoException;
import com.techlab.ecommerce.exception.ProductoNoEncontradoException;
import com.techlab.ecommerce.model.Categoria;
import com.techlab.ecommerce.model.Producto;
import com.techlab.ecommerce.repository.CategoriaRepository;

@Service
public class CategoriaService {
  
    private final CategoriaRepository categoriaRepository;


    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }


public Categoria guardar(Categoria categoria) {

  

    return categoriaRepository.save(categoria);
}

public List<Categoria> listarTodos() {
    return categoriaRepository.findAll();
}

public Categoria buscarPorId(Integer id) {
    return categoriaRepository.findById(id)
            .orElseThrow(() -> new CategoriaNoEncontradaException("Categoría con ID " + id + " no encontrada"));
}

public Categoria actualizar(Integer id, Categoria datos) {
  
    Categoria categoriaExistente = buscarPorId(id);
    categoriaExistente.setNombre(datos.getNombre());
    categoriaExistente.setDescripcion(datos.getDescripcion());

    
    return categoriaExistente;
}

public void eliminar(Integer id) {
    Categoria categoriaExistente = buscarPorId(id);
    categoriaRepository.delete(categoriaExistente);
}
}
