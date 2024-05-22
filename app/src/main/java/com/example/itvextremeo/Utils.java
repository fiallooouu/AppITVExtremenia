package com.example.itvextremeo;

import android.app.Activity;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;

public class Utils {

    //Variable para el acceso a la BBDD
    public static final String IPEQUIPO = "http://192.168.1.170/itvExtremenaPHP";

    //Comprobaciones de campos
    public static final String TELEFONOREGEX = "\\d{9}";
    public static final String CORREOREGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,}$";
    public static final String PASSWORDREGEX = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";
    public static final String MATRICULAREGEX = "(?i)^(\\d{4}?[ -]*[A-Z]{3}|[A-Z]{1,2}[ -]*\\d{4}?[ -]*[A-Z]{1,2})$";


    public static boolean validarTelefono(String telefono) throws IllegalAccessException, InstantiationException {
        if (!telefono.matches(TELEFONOREGEX)) {
            return false;
        }
        return true;
    }
    public static boolean validarCorreo(String correo) {
        if (!correo.matches(CORREOREGEX)) {
            return false;
        }
        return true;
    }

    public static boolean validarPassword(String pass) {
        if (!pass.matches(PASSWORDREGEX)) {
            return false;
        }
        return true;
    }
    public static boolean validarMatricula(String matricula) {
        if (!matricula.matches(MATRICULAREGEX)) {
            return false;
        }
        return true;
    }


    //Metodo que me devuelve el año actual
    public static int getAnoActual() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR);
    }
    public static boolean isAnoValido(String anoString) {
        try {
            int ano = Integer.parseInt(anoString);
            int anoActual = getAnoActual();
            return ano >= 1900 && ano <= anoActual;
        } catch (NumberFormatException e) {
            return false;
        }
    }



    //Metodo para el cifrado de las contraseñas
    public static String hashPassword(String password) {
        try {
            // Usamos SHA-512 para el hash
            MessageDigest digest = MessageDigest.getInstance("SHA-512");
            byte[] hash = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();

            // Convertir el hash a formato hexadecimal
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    //Metodo que te pone una frase en minuscula a que el inicio de la frase se ponga en Mayuscula
    public static String toTitleCase(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        StringBuilder titleCase = new StringBuilder();
        boolean nextTitleCase = true;

        for (char c : input.toCharArray()) {
            if (Character.isSpaceChar(c)) {
                nextTitleCase = true;
            } else if (nextTitleCase) {
                c = Character.toTitleCase(c);
                nextTitleCase = false;
            } else {
                c = Character.toLowerCase(c);
            }
            titleCase.append(c);
        }

        return titleCase.toString();
    }

    //Metodo para recojer el teclado cuando pulsas fuera de una caja de texto
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager.isAcceptingText()) {
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
    }


}
