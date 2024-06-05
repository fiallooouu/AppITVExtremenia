package com.example.itvextremeo.controlador.ventanas;

import static com.example.itvextremeo.Utils.IPEQUIPO;
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

import com.example.itvextremeo.R;
import com.example.itvextremeo.Utils;
import com.example.itvextremeo.controlador.bbdd.ConexiónPHP;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class Registrar extends AppCompatActivity {

    private EditText nombre, apellido, telefono, correo, contrasena, repetirContrasena;
    private Button registrarse, cancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        inicializarVistas();
        configurarEscuchadores();
    }

    private void inicializarVistas() {
        nombre = findViewById(R.id.editTextReNombre);
        apellido = findViewById(R.id.editTextReApe);
        telefono = findViewById(R.id.editTextReTele);
        correo = findViewById(R.id.editTextReCorreo);
        contrasena = findViewById(R.id.editTextReContraseña);
        repetirContrasena = findViewById(R.id.editTextReContraseñaRepe);
        registrarse = findViewById(R.id.buttonRegistrarse);
        cancelar = findViewById(R.id.buttonCancelar);
    }

    private void configurarEscuchadores() {
        registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    registrarUsuario();
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                } catch (InstantiationException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Registrar.this, MainActivity.class));
            }
        });
    }

    private void registrarUsuario() throws IllegalAccessException, InstantiationException {
        String nombreFormateado = Utils.toTitleCase(nombre.getText().toString());
        String apellidoFormateado = Utils.toTitleCase(apellido.getText().toString());
        String telefonoLimpiado = telefono.getText().toString().trim();
        String correoLimpiado = correo.getText().toString().trim();
        String contrasenaLimpiada = contrasena.getText().toString().trim();
        String repetirContrasenaLimpiada = repetirContrasena.getText().toString().trim();
        String fechaActual = obtenerFechaActual();

        if (camposValidos(nombreFormateado, apellidoFormateado, telefonoLimpiado, correoLimpiado, contrasenaLimpiada, repetirContrasenaLimpiada)) {
            if (contrasenaLimpiada.equals(repetirContrasenaLimpiada)) {
                String contrasenaCifrada = hashPassword(contrasenaLimpiada);
                new AgregarUsuarioTask().execute(nombreFormateado, apellidoFormateado, telefonoLimpiado, correoLimpiado, contrasenaCifrada, fechaActual);
            } else {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Por favor, rellena todos los campos correctamente", Toast.LENGTH_LONG).show();
        }
    }

    private boolean camposValidos(String nombre, String apellido, String telefono, String correo, String contrasena, String repetirContrasena) throws IllegalAccessException, InstantiationException {
        return !nombre.isEmpty() && !apellido.isEmpty() && Utils.validarTelefono(telefono) &&
                Utils.validarCorreo(correo) && Utils.validarPassword(contrasena) && !repetirContrasena.isEmpty();
    }

    private class AgregarUsuarioTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            HashMap<String, String> parametrosPost = new HashMap<>();
            parametrosPost.put("name", params[0]);
            parametrosPost.put("ape", params[1]);
            parametrosPost.put("tele", params[2]);
            parametrosPost.put("correo", params[3]);
            parametrosPost.put("pass", params[4]);
            parametrosPost.put("fecha", params[5]);

            return ConexiónPHP.enviarPost(IPEQUIPO + "/registrar.php", parametrosPost);
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(Registrar.this, result, Toast.LENGTH_LONG).show();
            if ("Datos insertados correctamente".equals(result)) {
                startActivity(new Intent(Registrar.this, MainActivity.class));
            }
        }
    }

    public static String obtenerFechaActual() {
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        return formato.format(new Date());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Utils.hideSoftKeyboard(this);
        return super.onTouchEvent(event);
    }
}
