<?php
// Configuración de la conexión a la base de datos
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "itvpruebadev";

// Crear conexión
$conn = new mysqli($servername, $username, $password, $dbname);

$user = $_POST['correo'];
$pass = $_POST['contrasena'];
//$user = "jorgo@gmail.com";
//$pass = "123";


// Verificar si el usuario existe
$sql = "SELECT * FROM datos_usuario d WHERE Correo = '$user' AND Contraseña = '$pass'";

$result = $conn->query($sql);

if ($result->num_rows > 0) {
    // Obtener la fila de resultados
    $row = $result->fetch_assoc();
    
    // Imprimir el ID del usuario y un indicador de éxito
    echo $row["id"] . ",true";
    
} else {
    // Usuario no encontrado, puedes registrar aquí si es necesario
    echo "false,false";
}

// Cerrar la conexión
$conn->close();

?>