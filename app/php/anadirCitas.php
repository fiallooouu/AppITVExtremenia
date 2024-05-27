<?php
// Configuración de la conexión a la base de datos
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "itvpruebadev2";

$conn = new mysqli($servername, $username, $password, $dbname);

$fecha = $_POST['fecha'];
$hora = $_POST['hora'];
$idVehi = $_POST['idVehi'];
$tipoInspec = $_POST['tipoIns'];
$tipoVehi = $_POST['tipoVehi'];

$sql = "SELECT * FROM cita WHERE Fecha = '$fecha' AND Hora = '$hora'";
$result = $conn->query($sql);

if ($result->num_rows > 0) {
    // Usuario encontrado, devolver un mensaje correspondiente
    echo "Cita con esa fecha y hora ya existente";
} else {

    // Inserta la Cita
    $sql = "INSERT INTO cita (Fecha, Hora, id_Vehiculo, Tipo_Inspeccion_id, Tipo_Vehiculo_id) VALUES ('$fecha', '$hora', '$idVehi', '$tipoInspec', '$tipoVehi')";

    $sql2 = "INSERT INTO historial_inspecciones (Fecha, Hora, id_Vehiculo, Tipo_Inspeccion_id, Tipo_Vehiculo_id) VALUES ('$fecha', '$hora', '$idVehi', '$tipoInspec', '$tipoVehi')";


    if ($conn->query($sql) === TRUE) {
        $conn->query($sql2);
        echo "Datos insertados correctamente";
    } else {
        echo "Error al insertar datos: " . $conn->error;
    }
}
// Cerrar la conexión
$conn->close();
