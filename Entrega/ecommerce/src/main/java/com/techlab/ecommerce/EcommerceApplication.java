package com.techlab.ecommerce;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.techlab.ecommerce.model.Categoria;
import com.techlab.ecommerce.model.Producto;
import com.techlab.ecommerce.repository.ProductoRepository;
import com.techlab.ecommerce.service.CategoriaService;
import com.techlab.ecommerce.service.ProductoService;




@SpringBootApplication
public class EcommerceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommerceApplication.class, args);
	}



@Bean
CommandLineRunner cargarDatos(CategoriaService categoriaService, ProductoService productoService) {
    return args -> {
        // 1. Guardar primero
		//  las categorías
	
        if(categoriaService.listarTodos().isEmpty()) {
            Categoria ropa = categoriaService.guardar(new Categoria(null, "Ropa", "Indumentaria"));
            Categoria tecnologia = categoriaService.guardar(new Categoria(null, "Tecnologia", "Tecnologia"));
            Categoria joyeria = categoriaService.guardar(new Categoria(null, "Joyeria", "Joyeria"));


            productoService.guardar(new Producto(null, "Camisa", 19000.00,50, "https://suayalocales.com.ar/wp-content/uploads/2024/07/CamisaLisa-ML.jpg",ropa));
             productoService.guardar(new Producto(null, "Celular", 399000.00,10, "https://riiing.com.ar/wp-content/uploads/2024/12/Img1_A16-5G_Blue-Black-2400x2400-1-1024x1024.jpg",tecnologia));
productoService.guardar(new Producto(null, "Anillo", 39000.00,4, "https://cdn-media.glamira.com/media/product/newgeneration/view/1/sku/RV3G/diamond/diamond-Brillant_AAA/alloycolour/red_white.jpg",joyeria));

        }
        
     

    

    };
}

}
