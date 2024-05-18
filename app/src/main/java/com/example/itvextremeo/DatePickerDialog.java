package com.example.itvextremeo;
import android.content.Context;
import android.widget.DatePicker;

import java.util.Calendar;

public class DatePickerDialog extends android.app.DatePickerDialog {

    public DatePickerDialog(Context context, OnDateSetListener listener, int year, int month, int dayOfMonth) {
        super(context, listener, year, month, dayOfMonth);
        // Establecer la fecha mínima a la fecha actual
        DatePicker datePicker = getDatePicker();
        Calendar calendar = Calendar.getInstance();
        datePicker.setMinDate(calendar.getTimeInMillis());
    }

    @Override
    public void onDateChanged(DatePicker view, int year, int month, int day) {
        super.onDateChanged(view, year, month, day);

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY) {
            // Deshabilitar sábados y domingos
            view.updateDate(year, month, day == calendar.getActualMaximum(Calendar.DAY_OF_MONTH) ? day - 1 : day + 1);
        }
    }
}

