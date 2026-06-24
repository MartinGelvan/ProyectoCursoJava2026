# ProyectoCursoJava2026
## ✨ Características Principales

1. **Catálogo de Productos:** Visualización en cuadrícula con imágenes, precios y estado del stock.
2. **Gestión de Administrador (CRUD):** * Panel oculto para crear, editar y eliminar productos.
   * Asignación dinámica de categorías desde la base de datos.
3. **Carrito de Compras Inteligente:**
   * Agrupación automática de productos repetidos.
   * Botones interactivos para sumar (`+`) o restar (`-`) unidades, devolviendo o descontando stock en tiempo real.
   * Opción para eliminar una línea completa de producto o vaciar todo el carrito.
   * Cálculo automático de subtotales por producto y total general.
4. **Protección de Datos:** Restricción de base de datos que evita eliminar un producto del catálogo si existe un carrito activo usándolo.

---

## 🛠️ Instalación y Configuración

### 1. Configuración de la Base de Datos (MySQL)
Crea una base de datos en tu servidor MySQL local:
```sql
CREATE DATABASE ecommercecursojava;



🔗 Endpoints Principales de la APILa API REST responde bajo el puerto 8080 y sigue los estándares RESTful

GET/productos Lista todos los productos disponibles
POST/productos Crea un nuevo producto (Admin)
PUT/productos/{id} Actualiza un producto existente
DELETE/productos/{id} Elimina un producto del catálogo
GET/categorias Lista todas las categorías
POST/carritos Inicializa un nuevo carrito vacío
POST/carritos/{c_id}/productos/{p_id} Agrega una unidad de producto al carrito
DELETE/carritos/{c_id}/productos/{p_id} Resta una unidad de producto del carrito
DELETE/carritos/{c_id}/productos/{p_id}/todos Elimina todas las unidades del producto
DELETE/carritos/{c_id}/vaciar Vacía el carrito por completo
