let token = localStorage.getItem("token");
if(!token){
    window.location.href = "index.html"
}
let carrito = JSON.parse(localStorage.getItem("carrito")) || []
if(carrito.length === 0){
    document.getElementById("carrito_items").innerHTML = "<li>Carrito vacío</li>"
} else{
    fetch("http://localhost:8080/carrito", {
        method: "POST",
        headers: {
            "Authorization": "Bearer " + token,
            "Content-Type": "application/json"
        },
        body: JSON.stringify(carrito)
    })
    .then(response => response.json())
    .then(data => {
        data.items.forEach(item => {
            document.getElementById("carrito_items").innerHTML += `
                <li>${item.nombre}
                <br>Descripcion: ${item.descripcion}
                <br>Cantidad: ${item.cantidad}
                <br>Precio unitario: $${item.precio}</li>
            `
        })
        document.getElementById("total").innerHTML = `Total: $${data.total}`
    })
}