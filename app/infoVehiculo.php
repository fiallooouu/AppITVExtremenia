<?php
// Configuración de la conexión a la base de datos
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "itvpruebadev";

// Crear conexión
$conn = new mysqli($servername, $username, $password, $dbname);

$idUser = $_POST['idUser'];
//$pass = $_POST['contrasena'];

// Verificar si el usuario existe
$sql = "SELECT v.Matricula, v.Tipo_Vehiculo_id FROM vehiculo v WHERE v.Usuario_id = '$idUser'";

$result = $conn->query($sql);

if ($result->num_rows > 0) {
    // Mostrar los resultados de la consulta
    while ($row = $result->fetch_assoc()) {
        echo $row["Matricula"].",".$row["Tipo_Vehiculo_id"].",";
    }
} else {
    // No hay resultados
    echo "No hay tipo de vehiculos en la base de datos.";
}

// Cerrar la conexión
$conn->close();
?>
