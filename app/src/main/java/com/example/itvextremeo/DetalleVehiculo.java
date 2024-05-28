package com.example.itvextremeo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class DetalleVehiculo extends AppCompatActivity {

    private String idActual;
    private TextView matricula, marca, modelo, año, tipoVehiculo, titular;
    private Button atras, eliminarVehiculo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_vehiculo);

        //Obtener ID de usuario actual
        Intent intent = getIntent();
        idActual = intent.getStringExtra("idUsu");

        //Inicializar componentes
        initComponent(intent);
        //Escuchadores de botones
        escuchadoresBTN();

    }

    private void escuchadoresBTN() {
        //Boton para volver a Inicio
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

        //Boton de eliminar vehiculo
        eliminarVehiculo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DetalleVehiculo.this);
                builder.setTitle("Eliminar Vehículo");
                builder.setMessage("¿Estás seguro de que deseas eliminar el vehiculo?");

                builder.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String matriEli = matricula.getText().toString().trim();
                        new borrarVehiculo().execute(idActual,matriEli);
                    }
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

    private void initComponent(Intent intent) {
        //EditText
        matricula = findViewById(R.id.txtDetaMatri);
        marca = findViewById(R.id.txtDetaMarca);
        modelo = findViewById(R.id.txtDetaModel);
        año = findViewById(R.id.txtDetaAno);
        tipoVehiculo = findViewById(R.id.txtDetaTipoVe);
        titular = findViewById(R.id.txtDetaTitu);

        //Boton
        atras = findViewById(R.id.btnDetaAtras);
        eliminarVehiculo = findViewById(R.id.btnDVEliminarVehi);

        matricula.setText(intent.getStringExtra("matricula"));
        marca.setText(intent.getStringExtra("marca"));
        modelo.setText(intent.getStringExtra("modelo"));
        año.setText(intent.getStringExtra("ano"));
        tipoVehiculo.setText(intent.getStringExtra("tipoVehi"));
        titular.setText(intent.getStringExtra("titular"));
    }

    //Clase que se encarga de eliminar el vehiculo
    private class borrarVehiculo extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            HashMap<String, String> postDataParams = new HashMap<>();
            postDataParams.put("idUser", params[0]);
            postDataParams.put("matricula", params[1]);


            return ConexiónPHP.enviarPost("http://192.168.56.1/itvExtremenaPHP/eliminarVehiculo.php",postDataParams);
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(DetalleVehiculo.this, result, Toast.LENGTH_LONG).show();

            Intent intent = new Intent(DetalleVehiculo.this, Inicio.class);
            intent.putExtra("idUsu", idActual);
            startActivity(intent);
            overridePendingTransition(0, 0);
            finish();

        }
    }
}