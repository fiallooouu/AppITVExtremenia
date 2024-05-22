package com.example.itvextremeo;

import static com.example.itvextremeo.Utils.hashPassword;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {


    EditText correo, contraseña;
    Button iniciarSesion, registrar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //EditTexts
        correo = findViewById(R.id.editTextCorreo);
        contraseña = findViewById(R.id.editTextPassword);

        //Botones
        iniciarSesion = findViewById(R.id.buttonIniciarSesion);
        registrar = findViewById(R.id.buttonRegistrar);

        correo.setText("maniesolis13@gmail.com");
        contraseña.setText("Secujorllo04");

        iniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comprobarUsuario();
            }
        });

        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Registrar.class);
                startActivity(intent);
                finish();
            }
        });
    }

    //Comprobamos los campos de inicio sesion
    private void comprobarUsuario() {
        String correo1 = correo.getText().toString().trim();
        String contraseña1 = contraseña.getText().toString().trim();

        if (!correo1.isEmpty() && !contraseña1.isEmpty()) {
                String contraCifra = hashPassword(contraseña1);
                new AgregarUsuarioTask().execute(correo1, contraCifra);
        } else {
            Toast.makeText(this, "Por favor, completa todos los campos.", Toast.LENGTH_SHORT).show();
        }
    }

    //Pasamos los datos de los campos al PHP
    private class AgregarUsuarioTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            HashMap<String, String> postDataParams = new HashMap<>();
            postDataParams.put("correo", params[0]);
            postDataParams.put("contrasena", params[1]);

            return ConexiónPHP.enviarPost(Utils.IPEQUIPO + "/iniciarSesion.php", postDataParams);
        }

        @Override
        protected void onPostExecute(String result) {
            String[] partes = result.trim().split(",");
            // Asignar cada parte a las variables correspondientes
            String ID = partes[0];
            String opc = partes[1];

            if (opc.equals("true")) {
                Intent intent = new Intent(MainActivity.this, Inicio.class);
                intent.putExtra("idUsu", ID);
                startActivity(intent);
                finish();
            } else if (opc.equals("false")) {
                Toast.makeText(MainActivity.this, "Credenciales erroneas", Toast.LENGTH_LONG).show();
            }
        }

    }

    //Ocultar teclado fuera cuando se pulsa fuera de las cajas de texto
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Utils.hideSoftKeyboard(this);
        return super.onTouchEvent(event);
    }
}