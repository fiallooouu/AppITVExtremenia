package com.example.itvextremeo;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.itvextremeo.modelo.InfoVehiculo;
import com.example.itvextremeo.modelo.TipoVehiculo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Citas extends AppCompatActivity {
    String idActual, idTipoVehiculo;

    Spinner matricula, tipoInspeccion;
    private TextView fechatxt, fechatxtDelete, tipoVehiculo;
    private Button inicio, car, cita, perfil, btnfecha, btnfechaDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citas);

        Intent intent = getIntent();
        idActual = intent.getStringExtra("idUsu");

        //Spinner
        matricula = findViewById(R.id.spinner3Matricula);
        tipoInspeccion = findViewById(R.id.spinner2TipoInspec);

        //Botones
        inicio = findViewById(R.id.buttonInicio3);
        car = findViewById(R.id.buttonCar3);
        cita = findViewById(R.id.buttonCitas3);
        perfil = findViewById(R.id.buttonPefil3);
        btnfecha = findViewById(R.id.button5Fecha);
        btnfechaDelete = findViewById(R.id.button6FechaDelete);

        //TextView
        fechatxt = findViewById(R.id.editFechatxt);
        fechatxtDelete = findViewById(R.id.editFechatxt2);
        tipoVehiculo = findViewById(R.id.txtViewTipoVehiculo);


        new infoVehiculo().execute(idActual);

        matricula.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                InfoVehiculo vehiculoSeleccionado = (InfoVehiculo) matricula.getSelectedItem();
                int valorSeleccionado = vehiculoSeleccionado.getIdTipoVehiculo();
                //tipoVehiculo.setText(valorSeleccionado+"");
                String itemSeleccionado = String.valueOf(valorSeleccionado);
                new tipoVehiculoID().execute(itemSeleccionado);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnfecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = 1;
                fecha(i);
            }

        });
        btnfechaDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = 0;
                fecha(i);
            }
        });

        inicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Citas.this, Inicio.class);
                intent.putExtra("idUsu", idActual);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });

        car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Citas.this, Vehiculos.class);
                intent.putExtra("idUsu", idActual);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });

        cita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Citas.this, Citas.class);
                intent.putExtra("idUsu", idActual);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });

        perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Citas.this, Perfil.class);
                intent.putExtra("idUsu", idActual);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });
    }

    private class infoVehiculo extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            HashMap<String, String> postDataParams = new HashMap<>();
            postDataParams.put("idUser", params[0]);

            return ConexiónPHP.enviarPost("http://192.168.56.1/itvExtremenaPHP/infoVehiculo.php",postDataParams);
        }

        @Override
        protected void onPostExecute(String result) {
            String[] datosSpinner = result.split(",");
            List<InfoVehiculo> vehiculos = new ArrayList<>();

            // Convertir los datos obtenidos en objetos InfoVehiculo y agregarlos a la lista
            for (int i = 0; i < datosSpinner.length; i += 2) {
                String matricula = datosSpinner[i];
                int idTipoVehiculo = Integer.parseInt(datosSpinner[i + 1]);
                vehiculos.add(new InfoVehiculo(matricula, idTipoVehiculo));
            }
            // Crear un adaptador con los objetos InfoVehiculo
            ArrayAdapter<InfoVehiculo> adapter = new ArrayAdapter<>(Citas.this, android.R.layout.simple_spinner_dropdown_item, vehiculos);

            // Establecer el adaptador en el Spinner de matrículas
            matricula.setAdapter(adapter);



        }
    }
    private class tipoVehiculoID extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            HashMap<String, String> postDataParams = new HashMap<>();
            postDataParams.put("idUser", params[0]);

            return ConexiónPHP.enviarPost("http://192.168.56.1/itvExtremenaPHP/tipoVehiculoID.php",postDataParams);
        }

        @Override
        protected void onPostExecute(String result) {
            String[] datosSpinner = result.split(",");
            String tipo = datosSpinner[0];
            idTipoVehiculo = datosSpinner[1];
            tipoVehiculo.setText(tipo);
            //Toast.makeText(Citas.this,result,Toast.LENGTH_LONG).show();

        }
    }


    private void fecha(int i) {
        DatePickerDialog dialog = new DatePickerDialog(Citas.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month , int day) {
                switch (i){
                    case 1:
                        fechatxtDelete.setText("");
                        fechatxt.setText(String.valueOf(day)+"/"+String.valueOf(month+1)+"/"+String.valueOf(year));
                        break; // Agregar esta línea
                    case 0:
                        fechatxt.setText("");
                        fechatxtDelete.setText(String.valueOf(day)+"/"+String.valueOf(month+1)+"/"+String.valueOf(year));
                        break; // Agregar esta línea
                }
            }
        },
                2024,0,15);
        dialog.show();
    }


    //Ocultar teclado fuera cuando se pulsa fuera de las cajas de texto
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        KeyboardUtils.hideSoftKeyboard(this);
        return super.onTouchEvent(event);
    }


}