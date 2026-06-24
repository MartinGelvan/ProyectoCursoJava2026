const API_URL = "http://localhost:8080";
let carritoIdActual = null;

// --- INICIALIZACIÓN ---
document.addEventListener("DOMContentLoaded", async () => {
  await inicializarCarrito();
  cargarCategorias();
  cargarProductos();

  // Configurar evento del formulario
  document
    .getElementById("producto-form")
    .addEventListener("submit", guardarProducto);
});

// --- FUNCIONES DEL CARRITO ---
async function inicializarCarrito() {
  try {
    const response = await fetch(`${API_URL}/carritos`, { method: "POST" });
    const carrito = await response.json();
    carritoIdActual = carrito.id;
    console.log("Carrito creado con ID:", carritoIdActual);
  } catch (error) {
    console.error("Error al inicializar el carrito:", error);
  }
}

async function agregarAlCarrito(productoId) {
  if (!carritoIdActual) return alert("Error: No hay carrito activo.");

  try {
    const response = await fetch(
      `${API_URL}/carritos/${carritoIdActual}/productos/${productoId}`,
      { method: "POST" },
    );

    if (response.ok) {
      const carritoActualizado = await response.json();
      actualizarUICarrito(carritoActualizado);
      cargarProductos(); // Recarga el catálogo para actualizar el stock visualmente
    } else {
      alert(
        "No se pudo agregar el producto. ¡Posiblemente no quede stock disponible!",
      );
    }
  } catch (error) {
    console.error("Error al agregar al carrito:", error);
  }
}

async function restarDelCarrito(productoId) {
  if (!carritoIdActual) return;

  try {
    const response = await fetch(
      `${API_URL}/carritos/${carritoIdActual}/productos/${productoId}`,
      { method: "DELETE" },
    );

    if (response.ok) {
      const carritoActualizado = await response.json();
      actualizarUICarrito(carritoActualizado);
      cargarProductos(); // Recupera el stock visualmente en el catálogo
    }
  } catch (error) {
    console.error("Error al restar producto del carrito:", error);
  }
}

async function eliminarProductoCompletoDelCarrito(productoId) {
  if (!carritoIdActual) return;
  if (
    !confirm("¿Deseas remover todas las unidades de este producto del carrito?")
  )
    return;

  try {
    const response = await fetch(
      `${API_URL}/carritos/${carritoIdActual}/productos/${productoId}/todos`,
      { method: "DELETE" },
    );

    if (response.ok) {
      const carritoActualizado = await response.json();
      actualizarUICarrito(carritoActualizado);
      cargarProductos();
    }
  } catch (error) {
    console.error("Error al eliminar producto completo:", error);
  }
}

async function vaciarCarrito() {
  if (!carritoIdActual) return;
  if (!confirm("¿Seguro que quieres vaciar todo el carrito?")) return;
  try {
    const response = await fetch(
      `${API_URL}/carritos/${carritoIdActual}/vaciar`,
      { method: "DELETE" },
    );
    if (response.ok) {
      const carritoActualizado = await response.json();
      actualizarUICarrito(carritoActualizado);
      cargarProductos();
    }
  } catch (error) {
    console.error("Error al vaciar carrito:", error);
  }
}

// Interfaz del carrito con botones de gestión de cantidad (+ / - / eliminar)
function actualizarUICarrito(carrito) {
  const contenedor = document.getElementById("cart-items");
  const contadorBadge = document.getElementById("cart-count");

  // El badge de arriba sigue mostrando el total absoluto de ítems
  contadorBadge.innerText = carrito.productos.length;
  contenedor.innerHTML = "";

  if (carrito.productos.length === 0) {
    contenedor.innerHTML =
      '<p class="text-gray-500 text-center mt-10">El carrito está vacío.</p>';
    return;
  }

  // AGRUPACIÓN: Mapeamos la lista plana a objetos con propiedad 'cantidad'
  const productosAgrupados = {};
  carrito.productos.forEach((prod) => {
    if (!productosAgrupados[prod.id]) {
      productosAgrupados[prod.id] = { ...prod, cantidad: 0 };
    }
    productosAgrupados[prod.id].cantidad++;
  });

  // Renderizado estético de cada producto agrupado
  Object.values(productosAgrupados).forEach((prod) => {
    const totalPorProducto = (prod.precio * prod.cantidad).toFixed(2);

    contenedor.innerHTML += `
      <div class="flex flex-col bg-white p-3 mb-3 rounded-xl shadow-sm border border-gray-100 transition-all hover:border-gray-200">
          <div class="flex items-center gap-3">
              <img src="${prod.imagenUrl || "https://via.placeholder.com/50"}" alt="${prod.nombre}" class="w-12 h-12 object-cover rounded-lg">
              <div class="flex-grow">
                  <h4 class="font-bold text-sm text-gray-800 line-clamp-1">${prod.nombre}</h4>
                  <p class="text-blue-600 font-extrabold text-sm">$${totalPorProducto}</p>
              </div>
              <button onclick="eliminarProductoCompletoDelCarrito(${prod.id})" class="text-gray-400 hover:text-red-500 transition p-1">
                  <i class="fas fa-trash-alt text-xs"></i>
              </button>
          </div>
          
          <div class="flex items-center justify-between mt-3 pt-2 border-t border-gray-50">
              <span class="text-xs font-medium text-gray-400">Precio u.: $${prod.precio}</span>
              <div class="flex items-center bg-gray-100 rounded-lg p-1 gap-2">
                  <button onclick="restarDelCarrito(${prod.id})" 
                      class="bg-white hover:bg-gray-200 text-gray-700 font-extrabold w-6 h-6 rounded-md flex items-center justify-center text-xs transition shadow-xs">-</button>
                  
                  <span class="text-xs font-bold text-gray-800 px-1">${prod.cantidad}</span>
                  
                  <button onclick="agregarAlCarrito(${prod.id})" 
                      class="bg-white hover:bg-gray-200 text-gray-700 font-extrabold w-6 h-6 rounded-md flex items-center justify-center text-xs transition shadow-xs">+</button>
              </div>
          </div>
      </div>
    `;
  });
}

// --- FUNCIONES DE PRODUCTOS (CRUD) ---
async function cargarProductos() {
  try {
    const response = await fetch(`${API_URL}/productos`);
    const productos = await response.json();
    renderizarProductos(productos);
  } catch (error) {
    console.error("Error cargando productos:", error);
  }
}

function renderizarProductos(productos) {
  const contenedor = document.getElementById("productos-container");
  contenedor.innerHTML = "";

  productos.forEach((prod) => {
    contenedor.innerHTML += `
      <div class="bg-white rounded-xl shadow-md hover:shadow-xl transition-shadow duration-300 overflow-hidden flex flex-col">
          <div class="h-48 overflow-hidden relative group">
              <img src="${prod.imagenUrl || "https://via.placeholder.com/300x200?text=Sin+Imagen"}" alt="${prod.nombre}" class="w-full h-full object-cover transition-transform duration-500 group-hover:scale-110">
              <div class="absolute top-2 right-2 flex gap-2">
                  <button onclick="editarProducto(${prod.id})" class="bg-yellow-400 text-white p-2 rounded-full shadow hover:bg-yellow-500 transition"><i class="fas fa-pen text-xs"></i></button>
                  <button onclick="eliminarProducto(${prod.id})" class="bg-red-500 text-white p-2 rounded-full shadow hover:bg-red-600 transition"><i class="fas fa-trash text-xs"></i></button>
              </div>
          </div>
          <div class="p-5 flex flex-col flex-grow">
              <h3 class="text-lg font-bold text-gray-800 mb-1">${prod.nombre}</h3>
              <p class="text-sm text-gray-500 mb-4">Stock disponible: <span class="font-bold ${prod.stock > 0 ? "text-green-600" : "text-red-600"}">${prod.stock}</span></p>
              <div class="mt-auto flex items-center justify-between">
                  <span class="text-2xl font-extrabold text-blue-700">$${prod.precio}</span>
                  <button onclick="agregarAlCarrito(${prod.id})" ${prod.stock === 0 ? "disabled" : ""} 
                      class="bg-blue-600 hover:bg-blue-700 disabled:bg-gray-400 text-white px-4 py-2 rounded-lg font-semibold shadow transition">
                      <i class="fas ${prod.stock === 0 ? "fa-ban" : "fa-cart-plus"}"></i>
                  </button>
              </div>
          </div>
      </div>
    `;
  });
}

async function eliminarProducto(id) {
  if (
    !confirm("¿Estás seguro de que deseas eliminar este producto del catálogo?")
  )
    return;

  try {
    const response = await fetch(`${API_URL}/productos/${id}`, {
      method: "DELETE",
    });

    if (response.ok) {
      cargarProductos();
      alert("Producto eliminado correctamente.");
    } else {
      // Aquí atrapamos el error cuando la base de datos bloquea la eliminación
      alert(
        "❌ No se puede eliminar el producto. Es probable que esté dentro de un carrito de compras activo. Vacía el carrito primero.",
      );
    }
  } catch (error) {
    console.error("Error al eliminar:", error);
    alert("Error de conexión con el servidor.");
  }
}

async function guardarProducto(event) {
  event.preventDefault();

  const id = document.getElementById("prod-id").value;
  const categoriaSeleccionada = document.getElementById("prod-categoria").value;

  if (!categoriaSeleccionada) {
    return alert("Por favor, seleccione una categoría válida.");
  }

  const producto = {
    nombre: document.getElementById("prod-nombre").value,
    precio: parseFloat(document.getElementById("prod-precio").value),
    stock: parseInt(document.getElementById("prod-stock").value),
    imagenUrl: document.getElementById("prod-imagen").value,
    categoria: { id: parseInt(categoriaSeleccionada) }, // <--- AHORA ES DINÁMICO
  };

  const method = id ? "PUT" : "POST";
  const url = id ? `${API_URL}/productos/${id}` : `${API_URL}/productos`;

  try {
    const response = await fetch(url, {
      method: method,
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(producto),
    });

    if (response.ok) {
      limpiarFormulario();
      toggleAdmin(); // Cierra el panel al guardar con éxito
      cargarProductos();
      alert(`Producto ${id ? "actualizado" : "creado"} con éxito.`);
    } else {
      alert("Error al guardar el producto. Revisa los datos.");
    }
  } catch (error) {
    console.error("Error:", error);
  }
}

function editarProducto(id) {
  toggleAdmin(true);
  fetch(`${API_URL}/productos/${id}`)
    .then((res) => res.json())
    .then((prod) => {
      document.getElementById("prod-id").value = prod.id;
      document.getElementById("prod-nombre").value = prod.nombre;
      document.getElementById("prod-precio").value = prod.precio;
      document.getElementById("prod-stock").value = prod.stock;
      document.getElementById("prod-imagen").value = prod.imagenUrl || "";

      // Seleccionamos la categoría correspondiente en el dropdown
      if (prod.categoria && prod.categoria.id) {
        document.getElementById("prod-categoria").value = prod.categoria.id;
      }
    })
    .catch((error) =>
      console.error("Error al cargar producto para edición:", error),
    );
}
function limpiarFormulario() {
  document.getElementById("producto-form").reset();
  document.getElementById("prod-id").value = "";
}

function toggleCart() {
  const sidebar = document.getElementById("cart-sidebar");
  const overlay = document.getElementById("overlay");
  sidebar.classList.toggle("translate-x-full");
  overlay.classList.toggle("hidden");
}

function toggleAdmin(forceOpen = false) {
  const adminSection = document.getElementById("admin-section");
  if (forceOpen) {
    adminSection.classList.remove("hidden");
  } else {
    adminSection.classList.toggle("hidden");
    if (adminSection.classList.contains("hidden")) limpiarFormulario();
  }
}
// --- FUNCIONES DE CATEGORÍAS ---
async function cargarCategorias() {
  try {
    const response = await fetch(`${API_URL}/categorias`);
    const categorias = await response.json();
    const selectCategoria = document.getElementById("prod-categoria");

    // Limpiamos y agregamos la opción por defecto
    selectCategoria.innerHTML =
      '<option value="" disabled selected>Seleccione una categoría</option>';

    categorias.forEach((cat) => {
      selectCategoria.innerHTML += `<option value="${cat.id}">${cat.nombre}</option>`;
    });
  } catch (error) {
    console.error("Error cargando categorías:", error);
    document.getElementById("prod-categoria").innerHTML =
      '<option value="" disabled>Error de conexión</option>';
  }
}
