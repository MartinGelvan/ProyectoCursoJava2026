package com.techlab.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techlab.ecommerce.model.Carrito;;

public interface CarritoRepository extends JpaRepository<Carrito,Integer>{
    
}
