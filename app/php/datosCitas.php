<?php
// Configuración de la conexión a la base de datos
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "itvpruebadev2";

// Crear conexión
$conn = new mysqli($servername, $username, $password, $dbname);

$user = $_POST['user'];
//$user = 4;



// Verificar si el usuario existe
$sql = "SELECT c.id AS 'Codigo',c.id_Vehiculo AS 'Matricula',c.Fecha AS 'Fecha',c.Hora AS 'Hora',ti.Nombre AS 'TipoInspeccion',tv.Nombre AS 'TipoVehiculo',ir.Descripcion AS 'Descripcion',c.Activa AS 'Activa' FROM datos_usuario du 
INNER JOIN vehiculo v ON v.Usuario_id = du.id
INNER JOIN cita c ON c.id_Vehiculo = v.Matricula
INNER JOIN tipo_vehiculo tv ON tv.id = v.Tipo_Vehiculo_id
INNER JOIN tipo_inspeccion ti ON ti.id = c.Tipo_Inspeccion_id
INNER JOIN item_revision ir ON ir.tipo_inspeccion = ti.id
WHERE du.id = '$user'";

$result = $conn->query($sql);

if ($result->num_rows > 0) {
    // Mostrar los resultados de la consulta
    while ($row = $result->fetch_assoc()) {
        echo $row["Codigo"]."_".$row["Matricula"]."_".$row["Fecha"]."_".$row["Hora"]."_".$row["TipoInspeccion"]."_".$row["TipoVehiculo"]."_".$row["Descripcion"]."_".$row["Activa"]."_";
    }
} else {
    // No hay resultados
    echo "false";
}

// Cerrar la conexión
$conn->close();

?>