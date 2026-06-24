package com.techlab.ecommerce.model;

import org.hibernate.annotations.ManyToAny;

import jakarta.persistence.*;
import jakarta.validation.constraints.Negative;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "producto")
public class Producto {
    // 1. Cambiamos los primitivos por sus Wrappers
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "El nombre del producto no puede estar vacío") 
    @NotNull(message = "El nombre del producto no puede ser nulo")     
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @NotNull(message = "El precio del producto no puede ser nulo")
    @Positive(message = "El precio del producto debe ser positivo")
    @Column(name = "precio", nullable = false)
    private Double precio;   


    @NotNull(message = "El stock del producto no puede ser nulo")
    @Positive(message = "El stock del producto debe ser positivo")
    @Column(name = "stock",nullable = false)
    private Integer stock;  
    
    @Column(name = "imagen_url", columnDefinition = "TEXT")
private String imagenUrl;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    
}