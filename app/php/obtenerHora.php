<?php
// Configuración de la conexión a la base de datos
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "itvpruebadev";

// Crear conexión
$conn = new mysqli($servername, $username, $password, $dbname);

$hora = $_POST['hora'];
//$hora = "2024-04-30";

// Verificar si el usuario existe
$sql = "SELECT * FROM cita c WHERE c.Fecha LIKE '$hora'";

$result = $conn->query($sql);

if ($result->num_rows > 0) {
    // Mostrar los resultados de la consulta
    while ($row = $result->fetch_assoc()) {
        echo $row["Hora"].",";
    }
} else {
    // No hay resultados
    echo "No hay horas en la base de datos con esa fecha.";
}

// Cerrar la conexión
$conn->close();
?>
