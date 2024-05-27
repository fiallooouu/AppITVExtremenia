<?php
// Configuración de la conexión a la base de datos
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "itvpruebadev2";

// Crear conexión
$conn = new mysqli($servername, $username, $password, $dbname);
// Recibir datos de la aplicación Android
$name = $_POST['name'];
$ape = $_POST['ape'];
$tele = $_POST['tele'];
$correo = $_POST['correo'];
$pass = $_POST['pass'];
$fecha = $_POST['fecha'];

//$user = "Jorge";
//$password = "9827364";

$sql = "SELECT * FROM datos_usuario WHERE Correo = '$correo'";
$result = $conn->query($sql);

if ($result->num_rows > 0) {
    // Usuario encontrado, devolver un mensaje correspondiente
    echo "Usuario ya existe";
} else {
    // Insertar datos en la tabla
    $sql = "INSERT INTO datos_usuario (Nombre, Apellido, Telefono, Correo, Contraseña, Administrador, FechaAlta) VALUES ('$name', '$ape','$tele', '$correo','$pass', '0', '$fecha')";

    if ($conn->query($sql) === TRUE) {
        echo "Datos insertados correctamente";
    } else {
        echo "Error al insertar datos: " . $conn->error;
    }
}

// Cerrar la conexión
$conn->close();
