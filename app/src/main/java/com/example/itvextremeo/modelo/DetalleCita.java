package com.example.itvextremeo.modelo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.itvextremeo.Inicio;
import com.example.itvextremeo.R;

public class DetalleCita extends AppCompatActivity {

    String idActual;
    TextView codigo,matricula,fechaHora,tipoInspe,tipoVehi,descrip;

    Button atras;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_cita);

        Intent intent = getIntent();
        idActual = intent.getStringExtra("idUsu");

        //EditText
        codigo = findViewById(R.id.txtDetaCitaCodigo);
        matricula = findViewById(R.id.txtDetaCitaMatri);
        fechaHora = findViewById(R.id.txtDetaCitaFechaHora);
        tipoInspe = findViewById(R.id.txtDetaCitaTipoInspe);
        tipoVehi = findViewById(R.id.txtDetaCitaTipoVehi);
        descrip = findViewById(R.id.txtDetaCitaDescrip);


        //Boton
        atras = findViewById(R.id.btnDetaCitaAtras);

        String fechaHoraa = intent.getStringExtra("fecha")+" / "+intent.getStringExtra("hora");

        codigo.setText(intent.getStringExtra("codigo"));
        matricula.setText(intent.getStringExtra("matricula"));
        fechaHora.setText(fechaHoraa);
        tipoInspe.setText(intent.getStringExtra("tipoInspe"));
        tipoVehi.setText(intent.getStringExtra("tipoVehi"));
        descrip.setText(intent.getStringExtra("descrip"));


        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetalleCita.this, Inicio.class);
                intent.putExtra("idUsu", idActual);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });
    }
}