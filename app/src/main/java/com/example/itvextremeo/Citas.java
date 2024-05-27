package com.example.itvextremeo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.itvextremeo.modelo.InfoVehiculo;
import com.example.itvextremeo.modelo.TipoInspeccion;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class Citas extends AppCompatActivity {
    private String idActual, idTipoVehiculo, idIspeccion, idVehiculoSeleccionado;
    private Spinner matricula, tipoInspeccion, horas;
    private TextView fechatxt, tipoVehiculo, precio;
    private Button inicio, car, cita, perfil, btnfecha, horaAct, btnPedirCita;
    private ArrayList<String> horasDisponibles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citas);

        //Obtener ID de usuario actual
        Intent intent = getIntent();
        idActual = intent.getStringExtra("idUsu");

        //Inicializamos los componentes
        initComponent();

        //Menu botones inferior
        menuInferior();

        //Escuchadores de eventos
        escuchadores();

        //Cargamos en el Spinner las matriculas y el tipo vehiculo correspondiente que tiene el usuario
        new infoVehiculo().execute(idActual);
        //Cargamos el Spinner con los tipos de inspeccion disponibles
        new tipoInspeccion().execute();

    }

    private void escuchadores() {
        //Boton para pedir la cita
        btnPedirCita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Comprobamos si el Spinner de matricula se ha cargado de la BBDD
                if(!(matricula.getSelectedItemPosition() == -1)) {
                    //Comprobamos si estan todos los campos rellenos
                    if (!matricula.getSelectedItem().toString().isEmpty() && !fechatxt.getText().toString().isEmpty() && !horas.getSelectedItem().toString().isEmpty() && !tipoInspeccion.getSelectedItem().toString().isEmpty()) {
                        InfoVehiculo vehiculoSeleccionado = (InfoVehiculo) matricula.getSelectedItem();
                        idVehiculoSeleccionado = vehiculoSeleccionado.getMatricula();

                        String fech = fechatxt.getText().toString().trim();
                        String hor = horas.getSelectedItem().toString().trim();
                        String idVeh = idVehiculoSeleccionado;
                        String tipoInsp = idIspeccion;
                        String tipoVehi = idTipoVehiculo;

                        new pedirCita().execute(fech, hor, idVeh, tipoInsp, tipoVehi);

                        //Recargamos la vista para que se vuelvan los campos vacios
                        Intent intent = new Intent(Citas.this, Citas.class);
                        intent.putExtra("idUsu", idActual);
                        startActivity(intent);
                        overridePendingTransition(0, 0);

                    } else {
                        Toast.makeText(Citas.this, "Rellena todos los campos", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(Citas.this,"Necesitas selecionar una matricula, pero no tienes vehiculos",Toast.LENGTH_LONG).show();
                }
            }
        });

        //Boton para activar las horas disponibles
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

        //Spinner que carga las matriculas de los vehiculos del usuario
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

        //Spinner que carga los tipos de inspecciones disponibles
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

        //Boton que carga las fechas disponibles
        btnfecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fecha();
            }

        });
    }

    private void menuInferior() {
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

    private void initComponent() {
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
        horaAct = findViewById(R.id.button2Horas);
        btnPedirCita = findViewById(R.id.buttonPedirCita);
        //TextView
        fechatxt = findViewById(R.id.editFechatxt);
        tipoVehiculo = findViewById(R.id.txtViewTipoVehiculo);
        precio = findViewById(R.id.editTextText5);
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
            if(!result.equals("No hay tipo de vehiculos en la base de datos.")) {
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

            }else{
                Toast.makeText(Citas.this,"No tiene vehiculos para pedir cita",Toast.LENGTH_SHORT).show();

            }

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
            HashMap<String, String> postDataParams = new HashMap<>();
            postDataParams.put("hora", fecha);

            return ConexiónPHP.enviarPost(Utils.IPEQUIPO + "/obtenerHora.php", postDataParams);
        }

        @Override
        protected void onPostExecute(String result) {
            String fechaSeleccionada = fechatxt.getText().toString().trim();
            String[] horasArray = {"09:00:00", "09:15:00", "09:30:00", "09:45:00", "10:00:00", "10:15:00", "10:30:00", "10:45:00",
                    "11:00:00", "11:15:00", "11:30:00", "11:45:00", "12:00:00", "12:15:00", "12:30:00", "12:45:00",
                    "13:00:00", "13:15:00", "13:30:00", "13:45:00", "14:00:00", "14:15:00", "14:30:00", "14:45:00",
                    "16:00:00", "16:15:00", "16:30:00", "16:45:00", "17:00:00", "17:15:00", "17:30:00", "17:45:00",
                    "18:00:00", "18:15:00", "18:30:00", "18:45:00", "19:00:00", "19:15:00", "19:30:00", "19:45:00", "20:00:00"};
            String[] datosSpinner = result.split(",");
            horasDisponibles = new ArrayList<>();

            // Obtener la fecha y hora actuales
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
            String currentDate = dateFormat.format(calendar.getTime());
            String currentTime = timeFormat.format(calendar.getTime());

            // Comprobar si la fecha seleccionada es igual a la fecha actual
            if (fechaSeleccionada.equals(currentDate)) {
                for (String hora : horasArray) {
                    // Solo considerar las horas posteriores a la hora actual
                    if (hora.compareTo(currentTime) > 0) {
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
                }
            } else {
                //Comprobar que la hora no esta cogida en esa fecha seleccionada
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
            postDataParams.put("idVehi", params[2]);
            postDataParams.put("tipoIns", params[3]);
            postDataParams.put("tipoVehi", params[4]);
            return ConexiónPHP.enviarPost(Utils.IPEQUIPO + "/anadirCitas.php", postDataParams);
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(Citas.this, result, Toast.LENGTH_LONG).show();
        }
    }


    //Metodo que se encarga de mostrar las fechas disponibles cuando pulsas el boton fecha
    private void fecha() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(Citas.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                Calendar selectedDate = Calendar.getInstance();
                selectedDate.set(year, month, day);

                int dayOfWeek = selectedDate.get(Calendar.DAY_OF_WEEK);
                if (dayOfWeek != Calendar.SATURDAY && dayOfWeek != Calendar.SUNDAY) {
                    fechatxt.setText(String.format("%04d-%02d-%02d", year, month + 1, day));

                } else {
                    Toast.makeText(Citas.this, "No puedes seleccionar sábados o domingos.", Toast.LENGTH_LONG).show();
                }
            }
        }, year, month, day);
        dialog.show();
    }




    //Ocultar teclado fuera cuando se pulsa fuera de las cajas de texto
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Utils.hideSoftKeyboard(this);
        return super.onTouchEvent(event);
    }


}