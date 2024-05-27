<?php
// Configuración de la conexión a la base de datos
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "itvpruebadev2";

// Crear conexión
$conn = new mysqli($servername, $username, $password, $dbname);

// Verificar la conexión
if ($conn->connect_error) {
    die("Conexión fallida: " . $conn->connect_error);
}else{
    echo "Conectada" . "<br>";
}

// Verificar si el usuario existe
$sql = "SELECT * FROM datos_usuario";

$result = $conn->query($sql);

if ($result->num_rows > 0) {
    // Mostrar los resultados de la consulta
    while ($row = $result->fetch_assoc()) {
        echo "ID: " . $row["id"] . " - Usuario: " . $row["Apellido"] . " - Contraseña: " . $row["Apellido"]  . "<br>";
    }
} else {
    // No hay resultados
    echo "No hay usuarios en la base de datos.";
}

// Cerrar la conexión
$conn->close();
?>
