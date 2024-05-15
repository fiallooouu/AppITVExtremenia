<?php
// Configuración de la conexión a la base de datos
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "itvpruebadev";

// Crear conexión
$conn = new mysqli($servername, $username, $password, $dbname);

$fecha = $_POST['fecha'];
$hora = $_POST['hora'];
$idUser = $_POST['isUse'];
$idVehi = $_POST['idVehi'];
$tipoInspec = $_POST['tipoIns'];
$tipoVehi = $_POST['tipoVehi'];
//$hora = "2024-04-30";

$sql = "SELECT * FROM cita WHERE Fecha = '$fecha' AND Hora = '$hora'";
$result = $conn->query($sql);

if ($result->num_rows > 0) {
    // Usuario encontrado, devolver un mensaje correspondiente
    echo "Cita con esa fecha y hora ya existente";
} else {

    // Inserta la Cita
    $sql = "INSERT INTO cita (Fecha, Hora, id_Usuario, id_Vehiculo, Tipo_Inspeccion_id, Tipo_Vehiculo_id) VALUES ('$fecha', '$hora','$idUser', '$idVehi','$tipoInspec', '$tipoVehi')";

    if ($conn->query($sql) === TRUE) {
        echo "Datos insertados correctamente";
    } else {
        echo "Error al insertar datos: " . $conn->error;
    }
}
// Cerrar la conexión
$conn->close();
