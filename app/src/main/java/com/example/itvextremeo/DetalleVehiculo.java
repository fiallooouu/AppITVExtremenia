package com.example.itvextremeo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class DetalleVehiculo extends AppCompatActivity {


    String idActual;
    TextView matricula, marca, modelo, año, tipoVehiculo, titular;
    Button atras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_vehiculo);

        Intent intent = getIntent();
        idActual = intent.getStringExtra("idUsu");

        //EditText
        matricula = findViewById(R.id.txtDetaMatri);
        marca = findViewById(R.id.txtDetaMarca);
        modelo = findViewById(R.id.txtDetaModel);
        año = findViewById(R.id.txtDetaAno);
        tipoVehiculo = findViewById(R.id.txtDetaTipoVe);
        titular = findViewById(R.id.txtDetaTitu);

        //Boton
        atras = findViewById(R.id.btnDetaAtras);

        matricula.setText(intent.getStringExtra("matricula"));
        marca.setText(intent.getStringExtra("marca"));
        modelo.setText(intent.getStringExtra("modelo"));
        año.setText(intent.getStringExtra("ano"));
        tipoVehiculo.setText(intent.getStringExtra("tipoVehi"));
        titular.setText(intent.getStringExtra("titular"));


        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetalleVehiculo.this, Inicio.class);
                intent.putExtra("idUsu", idActual);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }

        });
    }
}