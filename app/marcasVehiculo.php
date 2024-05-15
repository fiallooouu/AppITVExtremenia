<?php
// Configuración de la conexión a la base de datos
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "itvpruebadev";

// Crear conexión
$conn = new mysqli($servername, $username, $password, $dbname);

    $sql = "SELECT * FROM marcavehiculo mv ORDER BY mv.Nombre ASC";

    $result = $conn->query($sql);

    if ($result->num_rows > 0) {
        // Mostrar los resultados de la consulta
        while ($row = $result->fetch_assoc()) {
            echo $row["Nombre"] . ",".$row["id"] . ",";
        }
    } else {
        echo "Error al mostrar Marcas Vehiculo: " . $conn->error;
    }


// Cerrar la conexión
$conn->close();
