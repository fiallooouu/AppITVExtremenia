<?php
// Configuración de la conexión a la base de datos
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "itvpruebadev";

// Crear conexión
$conn = new mysqli($servername, $username, $password, $dbname);
// Recibir datos de la aplicación Android
$matr = $_POST['matricu'];
$fech = $_POST['fecha'];
$hora = $_POST['hora'];

$sql = "SELECT * FROM cita WHERE id_Vehiculo = '$matr' AND Fecha = '$fech' AND Hora = '$hora'";
$result = $conn->query($sql);

if ($result->num_rows > 0) {
        // Insertar datos en la tabla
        $sql = "DELETE FROM cita WHERE id_Vehiculo = '$matr' AND Fecha = '$fech' AND Hora = '$hora'";

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
