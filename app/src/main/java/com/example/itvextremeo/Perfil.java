package com.example.itvextremeo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

public class Perfil extends AppCompatActivity {
    private Switch modificarDatos ,cambiarContraseña;
    private EditText nombre, apellido, telefono, correo, contraseña, repetirContraseña;
    private Button inicio, car, cita, perfil, btnCambiarContraseña, actualizar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        //Botones
        inicio = findViewById(R.id.buttonInicio4);
        car = findViewById(R.id.buttonCar4);
        cita = findViewById(R.id.buttonCitas4);
        perfil = findViewById(R.id.buttonPefil4);
        btnCambiarContraseña = findViewById(R.id.buttonCambiarContraseña);
        actualizar = findViewById(R.id.button5Actualizar);
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
        nombre.setText("Jorge");
        apellido.setText("Gómez Fiallo");
        telefono.setText("656745693");
        correo.setText("jorgollo04@gmail.com");

        modificarDatos.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                nombre.setEnabled(isChecked);
                apellido.setEnabled(isChecked);
                telefono.setEnabled(isChecked);
                correo.setEnabled(isChecked);
                if(isChecked){
                    actualizar.setVisibility(View.VISIBLE);
                }else{
                    actualizar.setVisibility(View.GONE);
                }
            }
        });

        cambiarContraseña.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
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


        inicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Perfil.this, Inicio.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });

        car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Perfil.this, Vehiculos.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });

        cita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Perfil.this, Citas.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });

        perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Perfil.this, Perfil.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });
    }
    //Ocultar teclado fuera cuando se pulsa fuera de las cajas de texto
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        KeyboardUtils.hideSoftKeyboard(this);
        return super.onTouchEvent(event);
    }
}