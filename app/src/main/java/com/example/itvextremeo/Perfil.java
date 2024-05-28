package com.example.itvextremeo;

import static com.example.itvextremeo.Utils.hashPassword;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import java.util.HashMap;

public class Perfil extends AppCompatActivity {
    String idActual;
    private Switch modificarDatos ,cambiarContraseña;
    private EditText nombre, apellido, telefono, correo, contraseña, repetirContraseña;
    private Button inicio, car, cita, perfil, btnCambiarContraseña, actualizar, cerrarSesion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        //Obtener ID de usuario actual
        Intent intent = getIntent();
        idActual = intent.getStringExtra("idUsu");

        //Cargar datos de Usuario
        new cargarPerfil().execute(idActual);

        initComponent();
        menuInferior();
        escuchadoresBTN();
        escuchadoresSwitch();
        escuchadorCerrarSesion();

    }

    private void escuchadorCerrarSesion() {
        cerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Perfil.this);
                builder.setTitle("Cerrar sesión");
                builder.setMessage("¿Estás seguro de que deseas cerrar sesión?");

                builder.setPositiveButton("Cerrar Sesión", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Perfil.this, MainActivity.class);
                        startActivity(intent);
                        finish();                    }
                });

                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }
    private void escuchadoresSwitch() {
        //Switch
        modificarDatos.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                nombre.setEnabled(isChecked);
                apellido.setEnabled(isChecked);
                telefono.setEnabled(isChecked);
                correo.setEnabled(isChecked);
                if (isChecked) {
                    cambiarContraseña.setChecked(false);
                    actualizar.setVisibility(View.VISIBLE);
                } else {
                    actualizar.setVisibility(View.GONE);
                }
            }
        });

        //Switch
        cambiarContraseña.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    modificarDatos.setChecked(false);
                    contraseña.setVisibility(View.VISIBLE);
                    repetirContraseña.setVisibility(View.VISIBLE);
                    btnCambiarContraseña.setVisibility(View.VISIBLE);
                } else {
                    contraseña.setVisibility(View.GONE);
                    contraseña.setText("");
                    repetirContraseña.setVisibility(View.GONE);
                    repetirContraseña.setText("");
                    btnCambiarContraseña.setVisibility(View.GONE);
                }
            }
        });
    }
    private void escuchadoresBTN() {
        //Botones de actualizacion
        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = nombre.getText().toString().trim();
                String apel = Utils.toTitleCase(apellido.getText().toString().trim());
                String tele = telefono.getText().toString().trim();
                String corr = correo.getText().toString().trim();

                if(!name.isEmpty() && !apel.isEmpty() && !tele.isEmpty() && !corr.isEmpty()) {
                    try {
                        if(Utils.validarTelefono(tele)) {
                            if(Utils.validarCorreo(corr)) {
                                new modificarDatosPerfil().execute(idActual, name, apel, tele, corr);
                                new cargarPerfil().execute(idActual);
                            }else{
                                Toast.makeText(Perfil.this, "Formato de correo incorrecto", Toast.LENGTH_LONG).show();
                            }
                        }else{
                            Toast.makeText(Perfil.this, "Formato de telefono icorrecto", Toast.LENGTH_LONG).show();
                        }
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    } catch (InstantiationException e) {
                        throw new RuntimeException(e);
                    }
                }else{
                    Toast.makeText(Perfil.this, "Campos vacios", Toast.LENGTH_LONG).show();
                }
            }
        });
        btnCambiarContraseña.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String contrase = contraseña.getText().toString().trim();
                String repeContra = repetirContraseña.getText().toString().trim();
                if(repeContra.equals(contrase)){
                    if(Utils.validarPassword(contrase)) {
                        String contraCifra = hashPassword(contrase);
                        new modificarContraseña().execute(idActual, contraCifra);
                        contraseña.setText("");
                        repetirContraseña.setText("");
                    }else{
                        Toast.makeText(Perfil.this, "Formato de contraseñas erroneos", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(Perfil.this, "Contraseñas no coinciden", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    private void menuInferior() {
        //Botones de navegacion
        inicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Perfil.this, Inicio.class);
                intent.putExtra("idUsu", idActual);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });

        car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Perfil.this, Vehiculos.class);
                intent.putExtra("idUsu", idActual);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });

        cita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Perfil.this, Citas.class);
                intent.putExtra("idUsu", idActual);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });

        perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Perfil.this, Perfil.class);
                intent.putExtra("idUsu", idActual);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });
    }
    private void initComponent() {
        //Botones
        inicio = findViewById(R.id.buttonInicio4);
        car = findViewById(R.id.buttonCar4);
        cita = findViewById(R.id.buttonCitas4);
        perfil = findViewById(R.id.buttonPefil4);
        btnCambiarContraseña = findViewById(R.id.buttonCambiarContraseña);
        actualizar = findViewById(R.id.button5Actualizar);
        cerrarSesion = findViewById(R.id.btnCerrarSesion);
        //Switch
        modificarDatos = findViewById(R.id.switchModDatos);
        cambiarContraseña = findViewById(R.id.switchModContra);
        //EditText
        nombre = findViewById(R.id.editTextPerfilNombre);
        apellido = findViewById(R.id.editTextPerfilApe);
        telefono = findViewById(R.id.editTextPerfilTelef);
        correo = findViewById(R.id.editTextPerfilCorreo);
        contraseña = findViewById(R.id.editTextTextNuevaPassword);
        repetirContraseña = findViewById(R.id.editTextTextRepetirNuevaPassword4);
    }
    private class modificarContraseña extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            HashMap<String, String> postDataParams = new HashMap<>();
            postDataParams.put("id", params[0]);
            postDataParams.put("pass", params[1]);

            return ConexiónPHP.enviarPost(Utils.IPEQUIPO+"/cambiarContrasena.php", postDataParams);
        }
        protected void onPostExecute(String result) {
            Toast.makeText(Perfil.this, result, Toast.LENGTH_LONG).show();

        }
    }
    private class modificarDatosPerfil extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            HashMap<String, String> postDataParams = new HashMap<>();
            postDataParams.put("id", params[0]);
            postDataParams.put("name", params[1]);
            postDataParams.put("ape", params[2]);
            postDataParams.put("tele", params[3]);
            postDataParams.put("corre", params[4]);

            return ConexiónPHP.enviarPost(Utils.IPEQUIPO+"/modificarPerfil.php", postDataParams);
        }
        protected void onPostExecute(String result) {
            Toast.makeText(Perfil.this, result, Toast.LENGTH_LONG).show();


        }
    }
    private class cargarPerfil extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            HashMap<String, String> postDataParams = new HashMap<>();
            postDataParams.put("id", params[0]);

            return ConexiónPHP.enviarPost(Utils.IPEQUIPO+"/perfil.php", postDataParams);
        }

        @Override
        protected void onPostExecute(String result) {
            String[] partes = result.split(",");
            // Asignar cada parte a las variables correspondientes
            String corr = partes[1];
            String name = partes[3];
            String ape = partes[4];
            String tele = partes[5];

            nombre.setText(name);
            apellido.setText(ape);
            telefono.setText(tele);
            correo.setText(corr);

        }
    }

    //Ocultar teclado fuera cuando se pulsa fuera de las cajas de texto
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Utils.hideSoftKeyboard(this);
        return super.onTouchEvent(event);
    }
}