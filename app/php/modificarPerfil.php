<?php
// Configuración de la conexión a la base de datos
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "itvpruebadev";

// Crear conexión
$conn = new mysqli($servername, $username, $password, $dbname);

if(isset($_POST['id'], $_POST['name'], $_POST['ape'], $_POST['tele'], $_POST['corre'])) {

$id = $_POST['id'];
$namee = $_POST['name'];
$apel = $_POST['ape'];
$telef = $_POST['tele'];
$correo = $_POST['corre'];


// Verificar si el usuario existe
//$sql = "UPDATE datos_usuario SET Nombre = 'Jorge', Apellido = 'Gómez' , Telefono = 658997744 , Correo = 'jorgo@gmail.com' WHERE id = 10 ";
$sql = "UPDATE datos_usuario SET Nombre = '$namee', Apellido = '$apel' , Telefono = '$telef' , Correo = '$correo' WHERE id = '$id'";


    // Ejecutar la consulta SQL
    if ($conn->query($sql) === TRUE) {
        echo "Datos actualizados correctamente";
    } else {
        echo "Error al actualizar los datos: " . $conn->error;
    }

} else {
    echo "Faltan parámetros en la solicitud";
}

// Cerrar la conexión
$conn->close();

?>