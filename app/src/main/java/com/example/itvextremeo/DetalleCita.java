package com.example.itvextremeo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class DetalleCita extends AppCompatActivity {

    String idActual;
    TextView codigo,matricula,fechaHora,tipoInspe,tipoVehi,descrip;

    Button atras, eliminarCita;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_cita);

        Intent intent = getIntent();
        idActual = intent.getStringExtra("idUsu");

        //Inicializar componentes
        inirComponent(intent);
        //Escuchadores de botones
        escuchadores();
    }

    private void escuchadores() {
        //Boton para volver a Inicio
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

        //Boton eliminar Cita
        eliminarCita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(DetalleCita.this);
                builder.setTitle("Eliminar Cita");
                builder.setMessage("¿Estás seguro de que deseas eliminar la cita?");

                builder.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new borrarCita().execute(codigo.getText().toString());
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

    private void inirComponent(Intent intent) {
        //EditText
        codigo = findViewById(R.id.txtDetaCitaCodigo);
        matricula = findViewById(R.id.txtDetaCitaMatri);
        fechaHora = findViewById(R.id.txtDetaCitaFechaHora);
        tipoInspe = findViewById(R.id.txtDetaCitaTipoInspe);
        tipoVehi = findViewById(R.id.txtDetaCitaTipoVehi);
        descrip = findViewById(R.id.txtDetaCitaDescrip);


        //Boton
        atras = findViewById(R.id.btnDetaCitaAtras);
        eliminarCita = findViewById(R.id.btnDetaCitaEliminar);

        String fechaHoraa = intent.getStringExtra("fecha")+" / "+intent.getStringExtra("hora");

        codigo.setText(intent.getStringExtra("codigo"));
        matricula.setText(intent.getStringExtra("matricula"));
        fechaHora.setText(fechaHoraa);
        tipoInspe.setText(intent.getStringExtra("tipoInspe"));
        tipoVehi.setText(intent.getStringExtra("tipoVehi"));
        descrip.setText(intent.getStringExtra("descrip"));
    }

    //Clase que elimina la cita
    private class borrarCita extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            HashMap<String, String> postDataParams = new HashMap<>();
            postDataParams.put("id", params[0]);

            return ConexiónPHP.enviarPost(Utils.IPEQUIPO + "/borrarCita.php", postDataParams);
        }

        @Override
        protected void onPostExecute(String result) {
            if(result.equals("No se encontro la cita con esos datos")){
                Toast.makeText(DetalleCita.this, result, Toast.LENGTH_LONG).show();

            }else{
                Toast.makeText(DetalleCita.this, "Cita borrada correctamente", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(DetalleCita.this, Inicio.class);
                intent.putExtra("idUsu", idActual);
                startActivity(intent);
                overridePendingTransition(0, 0);

            }
        }
    }
}
