document.getElementById("btnLogin").addEventListener("click", function(){
    //leer valores
    let email = document.getElementById("email").value;
    let password = document.getElementById("password").value;
    //llamar API con fetch
    fetch("http://localhost:8080/auth/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
            email: email,
            password: password
            })
    })
    .then(response => response.json())
    .then(data => {
        console.log(data) //aqui llega respuesta de la API
        if(data.token){
            localStorage.setItem("token", data.token)
            window.location.href = "productos.html"
        }
        else{
            alert("Credenciales incorrectas")
        }
    })
})