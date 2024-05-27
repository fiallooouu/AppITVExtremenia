<?php
// Configuración de la conexión a la base de datos
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "itvpruebadev2";

// Crear conexión
$conn = new mysqli($servername, $username, $password, $dbname);

// Recibir datos de la aplicación Android
$matricula = $_POST['matricula'];
$marca = $_POST['marca'];
$modelo = $_POST['modelo'];
$año = $_POST['ano'];
$correo = $_POST['idUser'];
$tipoVehiculo = $_POST['idTipoVehi'];

//$sql = "SELECT * FROM vehiculo v WHERE v.Usuario_id = '$correo' AND v.Matricula LIKE '$matricula';";
$sql = "SELECT * FROM vehiculo v WHERE v.Matricula LIKE '$matricula';";


//SELECT * FROM datos_usuario du INNER JOIN vehiculo v ON du.id LIKE v.Usuario_id WHERE du.Correo LIKE "admin" AND v.Matricula LIKE "ABC123";
$result = $conn->query($sql);

if ($result->num_rows > 0) {
    // Usuario encontrado, devolver un mensaje correspondiente
    echo "Vehiculo ya existe";
} else {
    // Insertar datos en la tabla
    //$sql = "INSERT INTO datos_usuario (Nombre, Apellido, Telefono, Correo, Password, Administrador) VALUES ('$name', '$ape','$tele', '$correo','$pass', '0')";
    $sql = "INSERT INTO vehiculo (Matricula, Marca_id, Modelo, Año, Usuario_id, Tipo_Vehiculo_id) VALUES ('$matricula', '$marca','$modelo', '$año','$correo', '$tipoVehiculo')";

    if ($conn->query($sql) === TRUE) {
        echo "Datos insertados correctamente";
    } else {
        echo "Error al insertar datos: " . $conn->error;
    }
}

// Cerrar la conexión
$conn->close();
