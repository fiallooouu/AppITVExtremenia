<?php
// Configuración de la conexión a la base de datos
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "itvpruebadev2";

// Crear conexión
$conn = new mysqli($servername, $username, $password, $dbname);

// Recibir datos de la aplicación Android
$correo = $_POST['idUser'];
$matricula = $_POST['matricula'];

//$user = "Jorge";
//$password = "9827364";

//$sql = "SELECT * FROM datos_usuario WHERE Correo = '$correo'";
$sql = "SELECT * FROM vehiculo v WHERE v.Usuario_id = '$correo' AND v.Matricula LIKE '$matricula'";

//SELECT * FROM datos_usuario du INNER JOIN vehiculo v ON du.id LIKE v.Usuario_id WHERE du.Correo LIKE "admin" AND v.Matricula LIKE "ABC123";
$result = $conn->query($sql);

if ($result->num_rows > 0) {
    // Usuario encontrado, devolver un mensaje correspondiente
    $sql = "DELETE FROM vehiculo WHERE Usuario_id = '$correo' AND Matricula LIKE '$matricula'";

    if ($conn->query($sql) === TRUE) {
        echo "Vehiculo borrado correctamente";
    } else {
        echo "Error al borrar vehiculo: " . $conn->error;
    }
} else {
    // Insertar datos en la tabla
    //$sql = "INSERT INTO datos_usuario (Nombre, Apellido, Telefono, Correo, Password, Administrador) VALUES ('$name', '$ape','$tele', '$correo','$pass', '0')";
    echo "No existe la matricula";

}

// Cerrar la conexión
$conn->close();
