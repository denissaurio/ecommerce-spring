let token = localStorage.getItem("token");
if(!token){
    window.location.href = "index.html"
}
//llamar API con fetch
fetch("http://localhost:8080/productos", {
    method: "GET",
    headers: {
        "Authorization": "Bearer " + token
    }
})
.then(response => response.json())
.then(data => {
    data.content.forEach(producto => {
        document.getElementById("products_list").innerHTML += `
            <li>${producto.nombre}
            <br>Precio: $${producto.precio}
            <br>Descripción: ${producto.descripcion}
            <br>Categoría: ${producto.categoriaNombre}</li>
            <br><button data-id="${producto.id}">Agregar al carrito</button>
            <br>
        `
    })
    document.getElementById("products_list").addEventListener("click", function(event){
        if(event.target.tagName === "BUTTON"){
            let productoId = event.target.getAttribute("data-id")
            let carrito = JSON.parse(localStorage.getItem("carrito")) || []
            let itemExistente = carrito.find(item => item.productoId === productoId)
            if(itemExistente != null){
                itemExistente.cantidad += 1;
            }
            else{
                carrito.push({ productoId: productoId, cantidad: 1 })
            }
            localStorage.setItem("carrito", JSON.stringify(carrito))
        }
    })
})
