<?php
// Configuración de la conexión a la base de datos
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "itvpruebadev2";

// Crear conexión
$conn = new mysqli($servername, $username, $password, $dbname);
// Recibir datos de la aplicación Android
$id = $_POST['id'];

$sql = "SELECT * FROM cita WHERE id = '$id'";
$result = $conn->query($sql);

if ($result->num_rows > 0) {
        // Insertar datos en la tabla
        $sql = "DELETE FROM cita WHERE id = '$id'";

        if ($conn->query($sql) === TRUE) {
            echo "Datos borrados correctamente";
        } else {
            echo "ERROR eliminacion cita" . $conn->error;
        }
} else {

        // Usuario encontrado, devolver un mensaje correspondiente
        echo "No se encontro la cita con esos datos";

}
// Cerrar la conexión
$conn->close();
