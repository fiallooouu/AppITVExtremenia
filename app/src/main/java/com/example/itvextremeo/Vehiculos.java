package com.example.itvextremeo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.itvextremeo.modelo.MarcasVehiculo;
import com.example.itvextremeo.modelo.TipoVehiculo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class Vehiculos extends AppCompatActivity {
    String idActual;
    EditText matricula, modelo, año, matriculaEli;

    Spinner tipoVehiculo, marca;
    private Button inicio, car, cita, perfil, añadir, eliminar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehiculos);
        Intent intent = getIntent();
        idActual = intent.getStringExtra("idUsu");

        //EditText
        matricula = findViewById(R.id.editTextText8);
        modelo = findViewById(R.id.editTextText10);
        año = findViewById(R.id.editTextVehiAno);
        matriculaEli = findViewById(R.id.editTextText11);
        //Spinner
        tipoVehiculo = findViewById(R.id.spinnerTipoVehiculo);
        marca = findViewById(R.id.spinnerMarca);

        //Botones
        inicio = findViewById(R.id.buttonInicio2);
        car = findViewById(R.id.buttonCar2);
        cita = findViewById(R.id.buttonCitas2);
        perfil = findViewById(R.id.buttonPefil2);
        añadir = findViewById(R.id.button3);
        eliminar = findViewById(R.id.button4);


        //String[]datosSpinner = {"Hola","Adios"};
        //ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item, datosSpinner);
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        new tipoVehiculo().execute();
        new marcasVehiculo().execute();

        // Asignar adaptador al Spinner
        //tipoVehiculo.setAdapter(adapter);

        añadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TipoVehiculo vehiculoSeleccionado = (TipoVehiculo) tipoVehiculo.getSelectedItem();
                int valorSeleccionado = vehiculoSeleccionado.getValor();

                MarcasVehiculo marcaSeleccionada = (MarcasVehiculo) marca.getSelectedItem();
                int idSelecMarca = marcaSeleccionada.getId();

                //Toast.makeText(Vehiculos.this, valorSeleccionado, Toast.LENGTH_LONG).show();
                //Toast.makeText(Vehiculos.this, String.valueOf(valorSeleccionado), Toast.LENGTH_LONG).show();

                String matri = matricula.getText().toString().toUpperCase().trim();
                String marc = String.valueOf(idSelecMarca);
                String model = modelo.getText().toString();
                String an = año.getText().toString().trim();
                String itemSeleccionado = String.valueOf(valorSeleccionado);


                if(!matri.isEmpty() && !marc.equals("0") && !model.isEmpty() && !an.isEmpty() && !itemSeleccionado.isEmpty()){
                    new anadirVehiculo().execute(matri,marc,model,an,idActual,itemSeleccionado);
                    matricula.setText("");
                    marca.setSelection(0);
                    modelo.setText("");
                    año.setText("");
                }else{
                    Toast.makeText(Vehiculos.this, "Rellena todos los campos", Toast.LENGTH_LONG).show();
                }


            }
        });
        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String matriEli = matriculaEli.getText().toString().toUpperCase().trim();

                if(!matriEli.isEmpty()){
                    new borrarVehiculo().execute(idActual,matriEli);
                    matriculaEli.setText("");
                }else {
                    Toast.makeText(Vehiculos.this, "Rellena el campo", Toast.LENGTH_LONG).show();
                }

            }
        });

        inicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Vehiculos.this, Inicio.class);
                intent.putExtra("idUsu", idActual);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });

        car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Vehiculos.this, Vehiculos.class);
                intent.putExtra("idUsu", idActual);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });

        cita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Vehiculos.this, Citas.class);
                intent.putExtra("idUsu", idActual);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });

        perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Vehiculos.this, Perfil.class);
                intent.putExtra("idUsu", idActual);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });
    }

    private class tipoVehiculo extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            return ConexiónPHP.enviarPost(Utils.IPEQUIPO+"/tipoVehiculo.php");
        }

        @Override
        protected void onPostExecute(String result) {
            String[] datosSpinner = result.split(",");
            List<TipoVehiculo> vehiculos = new ArrayList<>();

            // Convertir los datos obtenidos en objetos TipoVehiculo y agregarlos a la lista
            for (int i = 0; i < datosSpinner.length; i += 2) {
                String nombre = datosSpinner[i];
                int valor = Integer.parseInt(datosSpinner[i + 1]);
                vehiculos.add(new TipoVehiculo(nombre, valor));
            }
            // Crear un adaptador con los objetos Vehiculo
            ArrayAdapter<TipoVehiculo> adapter = new ArrayAdapter<>(Vehiculos.this, android.R.layout.simple_spinner_dropdown_item, vehiculos);

            // Establecer el adaptador en el Spinner
            tipoVehiculo.setAdapter(adapter);
        }
    }

    private class anadirVehiculo extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            HashMap<String, String> postDataParams = new HashMap<>();
            postDataParams.put("matricula", params[0]);
            postDataParams.put("marca", params[1]);
            postDataParams.put("modelo", params[2]);
            postDataParams.put("ano", params[3]);
            postDataParams.put("idUser", params[4]);
            postDataParams.put("idTipoVehi", params[5]);


            return ConexiónPHP.enviarPost("http://192.168.56.1/itvExtremenaPHP/añadirVehiculo.php",postDataParams);
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(Vehiculos.this, result, Toast.LENGTH_LONG).show();

        }
    }

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
            Toast.makeText(Vehiculos.this, result, Toast.LENGTH_LONG).show();

        }
    }
    private class marcasVehiculo extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            return ConexiónPHP.enviarPost(Utils.IPEQUIPO+"/marcasVehiculo.php");
        }

        @Override
        protected void onPostExecute(String result) {
            String[] datosSpinner = result.split(",");
            List<MarcasVehiculo> vehiculos = new ArrayList<>();

            // Convertir los datos obtenidos en objetos TipoVehiculo y agregarlos a la lista
            vehiculos.add(new MarcasVehiculo(0,""));
            for (int i = 0; i < datosSpinner.length - 1; i += 2) {
                String nombre = datosSpinner[i].trim(); // Aseguramos que no hay espacios adicionales
                int valor;
                try {
                    valor = Integer.parseInt(datosSpinner[i + 1].trim());
                } catch (NumberFormatException e) {
                    // Manejar la excepción si no se puede convertir a entero
                    e.printStackTrace();
                    continue; // Saltar este par de datos y continuar con el siguiente
                }
                vehiculos.add(new MarcasVehiculo(valor, nombre));
            }

            // Crear un adaptador con los objetos Vehiculo
            ArrayAdapter<MarcasVehiculo> adapter = new ArrayAdapter<>(Vehiculos.this, android.R.layout.simple_spinner_dropdown_item, vehiculos);

            // Establecer el adaptador en el Spinner
            marca.setAdapter(adapter);
        }
    }


    //Ocultar teclado fuera cuando se pulsa fuera de las cajas de texto
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        KeyboardUtils.hideSoftKeyboard(this);
        return super.onTouchEvent(event);
    }
}