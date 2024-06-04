package com.example.itvextremeo.controlador.ventanas;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.itvextremeo.R;
import com.example.itvextremeo.Utils;
import com.example.itvextremeo.controlador.bbdd.ConexiónPHP;
import com.example.itvextremeo.modelo.Cita;
import com.example.itvextremeo.modelo.Vehiculo;

import java.util.ArrayList;
import java.util.HashMap;

public class Inicio extends AppCompatActivity {

    private ArrayList<Vehiculo> datosVehiculo;
    private ArrayList<Cita> datosCitas;
    private ArrayList<String> fechasCitas;
    private String idActual, activa;
    private ListView misCitas, misVehiculos;
    private Button inicio, car, cita, perfil;
    private boolean salida;

    @SuppressLint("SuspiciousIndentation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        //Obtener ID de usuario actual
        Intent intent = getIntent();
        idActual = intent.getStringExtra("idUsu");

        //Inicializar componentes
        initComponent();
        //Escuchadores de las listas
        listenListas();
        //Menu botones inferior
        menuInferior();

        //Actualizar las citas del usuario
        while (true) {
            new misCitas().execute(idActual);
            if(!salida){
                break;
            }
        }
        //Cargamos los vehiculos en la lista
        new misVehiculos().execute(idActual);

        //Comprobar la fecha de la cita si es inferior a la actual eliminiarla
        new comprovacionFechasCitas().execute();

    }

    private void menuInferior() {
        inicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Inicio.this, Inicio.class);
                intent.putExtra("idUsu", idActual);
                startActivity(intent);
                overridePendingTransition(0, 0);
                salida = false;
                finish();
            }
        });

        car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Inicio.this, Vehiculos.class);
                intent.putExtra("idUsu", idActual);
                startActivity(intent);
                overridePendingTransition(0, 0);
                salida = false;
                finish();
            }
        });

        cita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Inicio.this, Citas.class);
                intent.putExtra("idUsu", idActual);
                startActivity(intent);
                overridePendingTransition(0, 0);
                salida = false;
                finish();
            }
        });

        perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Inicio.this, Perfil.class);
                intent.putExtra("idUsu", idActual);
                startActivity(intent);
                overridePendingTransition(0, 0);
                salida = false;
                finish();
            }
        });
    }

    private void listenListas() {
        misVehiculos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Obtener el vehículo seleccionado
                Vehiculo vehiculoSeleccionado = datosVehiculo.get(position);
                // Crear un Intent para abrir la actividad DetalleVehiculoActivity
                Intent intent = new Intent(Inicio.this, DetalleVehiculo.class);
                // Pasar la información del vehículo seleccionado a la actividad DetalleVehiculoActivity
                intent.putExtra("matricula", vehiculoSeleccionado.getMatricula());
                intent.putExtra("marca", vehiculoSeleccionado.getMarca());
                intent.putExtra("modelo", vehiculoSeleccionado.getModelo());
                intent.putExtra("ano", vehiculoSeleccionado.getAño());
                intent.putExtra("tipoVehi", vehiculoSeleccionado.getTipoVehiculo());
                intent.putExtra("titular", vehiculoSeleccionado.getUsuario());
                intent.putExtra("idUsu", idActual);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });
        misCitas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cita citaSeleccionada = datosCitas.get(position);
                Intent intent = new Intent(Inicio.this, DetalleCita.class);
                intent.putExtra("codigo", citaSeleccionada.getCodigo());
                intent.putExtra("matricula", citaSeleccionada.getMatricula());
                intent.putExtra("fecha", citaSeleccionada.getFecha());
                intent.putExtra("hora", citaSeleccionada.getHora());
                intent.putExtra("tipoInspe", citaSeleccionada.getTipoInspec());
                intent.putExtra("tipoVehi", citaSeleccionada.getTipoVehiculo());
                intent.putExtra("descrip", citaSeleccionada.getDescripccion());
                intent.putExtra("idUsu", idActual);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });
    }

    private void initComponent() {
        //Botones
        inicio = findViewById(R.id.buttonInicio);
        car = findViewById(R.id.buttonCar);
        cita = findViewById(R.id.buttonCitas);
        perfil = findViewById(R.id.buttonPefil);
        //Listas
        misCitas = findViewById(R.id.listCitas);
        misVehiculos = findViewById(R.id.listVehiculos);
    }

    private class misCitas extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            HashMap<String, String> postDataParams = new HashMap<>();
            postDataParams.put("user", params[0]);

            return ConexiónPHP.enviarPost(Utils.IPEQUIPO + "/datosCitas.php", postDataParams);
        }

        @Override
        protected void onPostExecute(String result) {
            if (!result.equals("false")) {
                String[] datosSpinner = result.split("_");
                datosCitas = new ArrayList<>();

                for (int i = 0; i < datosSpinner.length; i += 8) {
                    String codigoCita = datosSpinner[i];
                    String matricula = datosSpinner[i + 1];
                    String fecha = datosSpinner[i + 2];
                    String hora = datosSpinner[i + 3];
                    String tipoInspeccion = datosSpinner[i + 4];
                    String tipoVehi = datosSpinner[i + 5];
                    String descripccion = datosSpinner[i + 6];
                    activa = datosSpinner[i + 7];

                    datosCitas.add(new Cita(codigoCita, matricula, fecha, hora, tipoInspeccion, tipoVehi, descripccion, activa));
                }

                ArrayAdapter<Cita> adapter = new ArrayAdapter<Cita>(Inicio.this, android.R.layout.simple_list_item_1, datosCitas) {
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        Cita cita = datosCitas.get(position);

                        // Crear una vista para mostrar el vehículo
                        LayoutInflater inflater = LayoutInflater.from(getContext());
                        View view = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);

                        // Obtener referencias a los TextView dentro de la vista
                        TextView textView = view.findViewById(android.R.id.text1);

                        // Establecer el texto en el TextView
                        textView.setText((position + 1) + "- " + cita.getMatricula() + " · " + cita.getFecha() + " " + cita.getHora());

                        textView.setTextColor(Color.WHITE);
                        textView.setTextSize(14);
                        textView.setTypeface(textView.getTypeface(), Typeface.BOLD);

                        return view;
                    }
                };

                misCitas.setAdapter(adapter);
            } else {
                Toast.makeText(Inicio.this, "No tiene citas en su perfil", Toast.LENGTH_SHORT).show();
            }
        }

    }
    private class misVehiculos extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            HashMap<String, String> postDataParams = new HashMap<>();
            postDataParams.put("user", params[0]);

            return ConexiónPHP.enviarPost(Utils.IPEQUIPO + "/datosVehiculo.php", postDataParams);
        }

        @Override
        protected void onPostExecute(String result) {
            if(!result.equals("false")) {
                String[] datosSpinner = result.split(",");
                datosVehiculo = new ArrayList<>();

                // Convertir los datos obtenidos en objetos TipoInspeccion y agregarlos a la lista
                for (int i = 0; i < datosSpinner.length; i += 6) {
                    String matricula = datosSpinner[i];
                    String marca = datosSpinner[i + 1];
                    String modelo = datosSpinner[i + 2];
                    String ano = datosSpinner[i + 3];
                    String titular = datosSpinner[i + 4];
                    String tipoVehi = datosSpinner[i + 5];
                    datosVehiculo.add(new Vehiculo(matricula, marca, modelo, ano, titular, tipoVehi));
                }
                // Crear un adaptador con los objetos TipoInspeccion
                ArrayAdapter<Vehiculo> adapter = new ArrayAdapter<Vehiculo>(Inicio.this, android.R.layout.simple_list_item_1, datosVehiculo) {
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        // Obtener el vehículo en la posición actual
                        Vehiculo vehiculo = datosVehiculo.get(position);

                        // Crear una vista para mostrar el vehículo
                        LayoutInflater inflater = LayoutInflater.from(getContext());
                        View view = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);

                        // Obtener referencias a los TextView dentro de la vista
                        TextView textView = view.findViewById(android.R.id.text1);

                        // Establecer el texto en el TextView
                        textView.setText((position + 1) + "- " + vehiculo.getMatricula() + " · " + vehiculo.getMarca() + " " + vehiculo.getModelo());

                        textView.setTextColor(Color.WHITE);
                        textView.setTextSize(14);
                        textView.setTypeface(textView.getTypeface(), Typeface.BOLD);

                        return view;
                    }
                };

                misVehiculos.setAdapter(adapter);
            }else{
                Toast.makeText(Inicio.this,"No tiene vehiculos en su perfil",Toast.LENGTH_SHORT).show();
            }

        }
    }
    private class comprovacionFechasCitas extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            return ConexiónPHP.enviarPost(Utils.IPEQUIPO + "/extraerDatosBorradoCitas.php");
        }

        @Override
        protected void onPostExecute(String result) {
                String[] datosSpinner = result.split(",");
                fechasCitas = new ArrayList<>();

                for (int i = 0; i < datosSpinner.length;i++){
                    new borrarCita().execute(datosSpinner[i]);
                }

        }

        private class borrarCita extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> postDataParams = new HashMap<>();
                postDataParams.put("id", params[0]);

                return ConexiónPHP.enviarPost(Utils.IPEQUIPO + "/borrarCitaCaducada.php", postDataParams);
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
