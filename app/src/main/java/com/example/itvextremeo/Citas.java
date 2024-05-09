package com.example.itvextremeo;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Layout;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.itvextremeo.modelo.InfoVehiculo;
import com.example.itvextremeo.modelo.TipoInspeccion;
import com.example.itvextremeo.modelo.TipoVehiculo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Citas extends AppCompatActivity {
    private String idActual, idTipoVehiculo, idIspeccion, idVehiculoSeleccionado;
    private Switch switchCita;
    private Spinner matricula, tipoInspeccion, horas;
    private TextView fechatxt, fechatxtDelete, tipoVehiculo, precio, cabecera;
    private Button inicio, car, cita, perfil, btnfecha, btnfechaDelete, btnCitaDelete, horaAct, btnPedirCita;
    private ArrayList<String> horasDisponibles;
    private View txtLayout, btnLayout, prinLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citas);

        Intent intent = getIntent();
        idActual = intent.getStringExtra("idUsu");

        //Layout
        txtLayout = findViewById(R.id.layoutTXTBorrado);
        btnLayout = findViewById(R.id.layoutBTNBorrado);
        prinLayout = findViewById(R.id.layoutPrinciBorrado);

        //Switch
        switchCita = findViewById(R.id.switchCitas);

        //Spinner
        matricula = findViewById(R.id.spinner3Matricula);
        tipoInspeccion = findViewById(R.id.spinner2TipoInspec);
        horas = findViewById(R.id.spinnerHora);

        //Botones
        inicio = findViewById(R.id.buttonInicio3);
        car = findViewById(R.id.buttonCar3);
        cita = findViewById(R.id.buttonCitas3);
        perfil = findViewById(R.id.buttonPefil3);
        btnfecha = findViewById(R.id.button5Fecha);
        btnfechaDelete = findViewById(R.id.button6FechaDelete);
        btnCitaDelete = findViewById(R.id.button2BorrarCita);
        horaAct = findViewById(R.id.button2Horas);
        btnPedirCita = findViewById(R.id.buttonPedirCita);

        //TextView
        fechatxt = findViewById(R.id.editFechatxt);
        fechatxtDelete = findViewById(R.id.editFechatxt2);
        tipoVehiculo = findViewById(R.id.txtViewTipoVehiculo);
        precio = findViewById(R.id.editTextText5);
        cabecera = findViewById(R.id.textView124);


        new infoVehiculo().execute(idActual);
        new tipoInspeccion().execute();

        switchCita.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    txtLayout.setVisibility(View.VISIBLE);
                    btnLayout.setVisibility(View.VISIBLE);
                    prinLayout.setVisibility(View.GONE);
                    cabecera.setText("Borrar Cita");

                } else {
                    txtLayout.setVisibility(View.GONE);
                    btnLayout.setVisibility(View.GONE);
                    prinLayout.setVisibility(View.VISIBLE);
                    cabecera.setText("Pedir Cita");
                }
            }
        });

        btnPedirCita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!matricula.getSelectedItem().toString().isEmpty() && !fechatxt.getText().toString().isEmpty() && !horas.getSelectedItem().toString().isEmpty() && !tipoInspeccion.getSelectedItem().toString().isEmpty()) {
                    InfoVehiculo vehiculoSeleccionado = (InfoVehiculo) matricula.getSelectedItem();
                    idVehiculoSeleccionado = vehiculoSeleccionado.getMatricula();

                    String fech = fechatxt.getText().toString().trim();
                    String hor = horas.getSelectedItem().toString().trim();
                    String idUs = idActual;
                    String idVeh = idVehiculoSeleccionado;
                    String tipoInsp = idIspeccion;
                    String tipoVehi = idTipoVehiculo;

                    //Toast.makeText(Citas.this,fech+" "+hor+" "+idUs+" "+idVeh+" "+tipoInsp+" "+tipoVehi,Toast.LENGTH_LONG).show();
                    new pedirCita().execute(fech, hor, idUs, idVeh, tipoInsp, tipoVehi);

                    Intent intent = new Intent(Citas.this, Citas.class);
                    intent.putExtra("idUsu", idActual);
                    startActivity(intent);
                    overridePendingTransition(0, 0);

                } else {
                    Toast.makeText(Citas.this, "Rellena todos los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });

        horaAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!fechatxt.getText().toString().isEmpty()) {
                    new extraerHoras().execute();
                } else {
                    Toast.makeText(Citas.this, "Introduce antes una fecha", Toast.LENGTH_LONG).show();
                }

            }
        });

        btnCitaDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

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

        tipoInspeccion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TipoInspeccion tipoinspeccion = (TipoInspeccion) tipoInspeccion.getSelectedItem();
                int valorSeleccionado = tipoinspeccion.getCosto();
                idIspeccion = String.valueOf(tipoinspeccion.getId());
                //tipoVehiculo.setText(valorSeleccionado+"");
                String itemSeleccionado = String.valueOf(valorSeleccionado);


                precio.setText(itemSeleccionado + " €");
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

    //Extraemos la matricula y el tipo de vehiculo
    private class infoVehiculo extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            HashMap<String, String> postDataParams = new HashMap<>();
            postDataParams.put("idUser", params[0]);

            return ConexiónPHP.enviarPost(Utils.IPEQUIPO + "/infoVehiculo.php", postDataParams);
        }

        @Override
        protected void onPostExecute(String result) {
            String[] datosSpinner = result.split(",");
            List<InfoVehiculo> vehiculos = new ArrayList<>();

            vehiculos.add(new InfoVehiculo("", 1));
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
            postDataParams.put("idTipo", params[0]);

            return ConexiónPHP.enviarPost(Utils.IPEQUIPO + "/tipoVehiculoID.php", postDataParams);
        }

        @Override
        protected void onPostExecute(String result) {
            String[] datosSpinner = result.split(",");
            String tipo = datosSpinner[0];
            idTipoVehiculo = datosSpinner[1];
            if (!matricula.getSelectedItem().toString().isEmpty()) {
                tipoVehiculo.setText(tipo);
                //Toast.makeText(Citas.this,result,Toast.LENGTH_LONG).show();
            } else {
                tipoVehiculo.setText("");
            }
        }
    }

    private class tipoInspeccion extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {


            return ConexiónPHP.enviarPost(Utils.IPEQUIPO + "/tipoInspeccion.php");
        }

        @Override
        protected void onPostExecute(String result) {
            String[] datosSpinner = result.split(",");
            List<TipoInspeccion> tiposInspeccion = new ArrayList<>();

            tiposInspeccion.add(new TipoInspeccion("", 0, 1));
            // Convertir los datos obtenidos en objetos TipoInspeccion y agregarlos a la lista
            for (int i = 0; i < datosSpinner.length; i += 3) {
                String nombre = datosSpinner[i];
                int costo = Integer.parseInt(datosSpinner[i + 1]);
                int id = Integer.parseInt(datosSpinner[i + 2]);
                tiposInspeccion.add(new TipoInspeccion(nombre, costo, id));
            }
            // Crear un adaptador con los objetos TipoInspeccion
            ArrayAdapter<TipoInspeccion> adapter = new ArrayAdapter<>(Citas.this, android.R.layout.simple_spinner_dropdown_item, tiposInspeccion);

            // Establecer el adaptador en el Spinner de tipos de inspección
            tipoInspeccion.setAdapter(adapter);

        }
    }

    private class extraerHoras extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {


            String fecha = fechatxt.getText().toString().trim();


            //String fecha = fechatxt.getText().toString().trim();
            //new extraerHoras().execute(fecha);

            HashMap<String, String> postDataParams = new HashMap<>();
            postDataParams.put("hora", fecha);

            return ConexiónPHP.enviarPost(Utils.IPEQUIPO + "/obtenerHora.php", postDataParams);
        }

        @Override
        protected void onPostExecute(String result) {
            String[] horasArray = {"09:00:00", "09:15:00", "09:30:00", "09:45:00", "10:00:00", "10:15:00", "10:30:00", "10:45:00",
                    "11:00:00", "11:15:00", "11:30:00", "11:45:00", "12:00:00", "12:15:00", "12:30:00", "12:45:00",
                    "13:00:00", "13:15:00", "13:30:00", "13:45:00", "14:00:00", "14:15:00", "14:30:00", "14:45:00",
                    "16:00:00", "16:15:00", "16:30:00", "16:45:00", "17:00:00", "17:15:00", "17:30:00", "17:45:00",
                    "18:00:00", "18:15:00", "18:30:00", "18:45:00", "19:00:00", "19:15:00", "19:30:00", "19:45:00", "20:00:00"};
            String[] datosSpinner = result.split(",");
            horasDisponibles = new ArrayList<>();

            for (String hora : horasArray) {
                boolean encontrada = false;
                for (String dato : datosSpinner) {
                    if (hora.equals(dato)) {
                        encontrada = true;
                        break;
                    }
                }
                if (!encontrada) {
                    horasDisponibles.add(hora);
                }
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(Citas.this, android.R.layout.simple_spinner_item, horasDisponibles);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            horas.setAdapter(adapter);

            // Ahora el ArrayList horasDisponibles contiene las horas que no están en datosSpinner
        }

    }

    private class pedirCita extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            HashMap<String, String> postDataParams = new HashMap<>();
            postDataParams.put("fecha", params[0]);
            postDataParams.put("hora", params[1]);
            postDataParams.put("isUse", params[2]);
            postDataParams.put("idVehi", params[3]);
            postDataParams.put("tipoIns", params[4]);
            postDataParams.put("tipoVehi", params[5]);

            return ConexiónPHP.enviarPost(Utils.IPEQUIPO + "/anadirCitas.php", postDataParams);
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(Citas.this, result, Toast.LENGTH_LONG).show();
        }
    }


    private void fecha(int i) {
        DatePickerDialog dialog = new DatePickerDialog(Citas.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                switch (i) {
                    case 1:
                        fechatxtDelete.setText("");
                        fechatxt.setText(String.format("%04d-%02d-%02d", year, month + 1, day));
                        break;
                    case 0:
                        fechatxt.setText("");
                        fechatxtDelete.setText(String.format("%04d-%02d-%02d", year, month + 1, day));
                        break;
                }
            }
        },
                2024, 0, 15);
        dialog.show();
    }


    //Ocultar teclado fuera cuando se pulsa fuera de las cajas de texto
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        KeyboardUtils.hideSoftKeyboard(this);
        return super.onTouchEvent(event);
    }


}