<?php
// Configuración de la conexión a la base de datos
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "itvpruebadev";

// Crear conexión
$conn = new mysqli($servername, $username, $password, $dbname);

if(isset($_POST['id'], $_POST['pass'])) {

$id = $_POST['id'];
$contra = $_POST['pass'];


// Verificar si el usuario existe
$sql = "UPDATE datos_usuario SET Contraseña = '$contra' WHERE id = '$id'";


    // Ejecutar la consulta SQL
    if ($conn->query($sql) === TRUE) {
        echo "Datos actualizados correctamente";
    } else {
        echo "Error al actualizar los datos: " . $conn->error;
    }

} else {
    echo "Faltan parámetros en la solicitud";
}

// Cerrar la conexión
$conn->close();

?>