<?php
// Configuración de la conexión a la base de datos
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "itvpruebadev2";

// Crear conexión
$conn = new mysqli($servername, $username, $password, $dbname);


$id = $_POST['id'];

//$id = "1";
//$pass = "maria123";


// Verificar si el usuario existe
$sql = "SELECT * FROM datos_usuario d WHERE id = '$id'";

$result = $conn->query($sql);

if ($result->num_rows > 0) {
    // Usuario encontrado, enviar mensaje de que ya está registrado
    //echo "true";
    while ($row = $result->fetch_assoc()) {
    echo $row["id"].",". $row["Correo"].",true".",". $row["Nombre"].",". $row["Apellido"].",". $row["Telefono"];
    }
} else {
    // Usuario no encontrado, puedes registrar aquí si es necesario
    echo "false";
}

// Cerrar la conexión
$conn->close();

?>