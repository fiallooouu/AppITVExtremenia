<?php
// Configuración de la conexión a la base de datos
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "itvpruebadev";

// Crear conexión
$conn = new mysqli($servername, $username, $password, $dbname);

$user = $_POST['user'];

// Verificar si el usuario existe
$sql = "SELECT v.Matricula, mv.Nombre AS Marca, v.Modelo, v.Año, CONCAT(du.Nombre,' ', du.Apellido) AS 'Nombre y Apellidos', tv.Nombre AS 'Tipo Vehiculo' FROM vehiculo v INNER JOIN datos_usuario du ON v.Usuario_id = du.id INNER JOIN marcavehiculo mv ON v.Marca_id = mv.id INNER JOIN tipo_vehiculo tv ON v.Tipo_Vehiculo_id = tv.id WHERE du.id = '$user'";

$result = $conn->query($sql);

if ($result->num_rows > 0) {
    // Mostrar los resultados de la consulta
    while ($row = $result->fetch_assoc()) {
        echo $row["Matricula"].",".$row["Marca"].",".$row["Modelo"].",".$row["Año"].",".$row["Nombre y Apellidos"].",".$row["Tipo Vehiculo"].",";
    }
} else {
    // No hay resultados
    echo "false";
}

// Cerrar la conexión
$conn->close();

?>