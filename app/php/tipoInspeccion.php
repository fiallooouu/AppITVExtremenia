<?php
// Configuración de la conexión a la base de datos
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "itvpruebadev";

// Crear conexión
$conn = new mysqli($servername, $username, $password, $dbname);

// Verificar si el usuario existe
$sql = "SELECT * FROM tipo_inspeccion ti";
//$sql = "SELECT * FROM tipo_inspeccion ti WHERE ti.tipoVehiculo = '2'";

$result = $conn->query($sql);

if ($result->num_rows > 0) {
    // Mostrar los resultados de la consulta
    while ($row = $result->fetch_assoc()) {
        echo $row["Nombre"].",".$row["Precio"].",".$row["id"].",";
    }
} else {
    // No hay resultados
    echo "No hay tipo de vehiculos en la base de datos.";
}

// Cerrar la conexión
$conn->close();
?>
