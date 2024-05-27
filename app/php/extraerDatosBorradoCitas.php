<?php
// Configuración de la conexión a la base de datos
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "itvpruebadev2";

// Crear conexión
$conn = new mysqli($servername, $username, $password, $dbname);

// Verificar si el usuario existe
$sql = "SELECT C.id FROM cita C WHERE C.Fecha < CURDATE();";

$result = $conn->query($sql);

if ($result->num_rows > 0) {
    // Mostrar los resultados de la consulta
    while ($row = $result->fetch_assoc()) {
        echo $row["id"].",";
    }
} else {
    // No hay resultados
    echo "ERROR al obtener datos id de cita";
}

// Cerrar la conexión
$conn->close();
?>
