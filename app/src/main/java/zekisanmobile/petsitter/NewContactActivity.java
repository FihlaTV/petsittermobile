package zekisanmobile.petsitter;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import zekisanmobile.petsitter.Handlers.ContactHandler;
import zekisanmobile.petsitter.Model.Sitter;

public class NewContactActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener, DialogInterface.OnCancelListener {

    private Toolbar toolbar;
    private Sitter sitter;
    private EditText tv_date_start;
    private EditText tv_date_final;
    private EditText tv_time_start;
    private EditText tv_time_final;
    private Button bt_send;
    private int year, month, day, hour, minute;
    private boolean date_start_setted;
    private boolean time_start_setted;
    private Calendar tDefault;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_contact);

        Intent intent = getIntent();
        sitter = (Sitter) intent.getSerializableExtra("sitter");

        configureToolbar();
        configureViews();
    }

    private void configureToolbar() {
        // TOOLBAR
        toolbar = (Toolbar) findViewById(R.id.toolbar_new_contact);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_white_24dp);
    }

    private void configureViews() {
        TextView tv_name = (TextView) findViewById(R.id.tv_name);
        tv_name.setText(sitter.getName());

        tv_date_start = (EditText) findViewById(R.id.tv_date_start);
        tv_date_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scheduleJobDate(view);
            }
        });

        tv_date_final = (EditText) findViewById(R.id.tv_date_final);
        tv_date_final.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scheduleJobDate(view);
            }
        });

        tv_date_start = (EditText) findViewById(R.id.tv_date_start);
        tv_date_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scheduleJobDate(view);
            }
        });

        tv_date_final = (EditText) findViewById(R.id.tv_date_final);
        tv_date_final.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scheduleJobDate(view);
            }
        });

        tv_time_start = (EditText) findViewById(R.id.tv_time_start);
        tv_time_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scheduleJobTime(view);
            }
        });

        tv_time_final = (EditText) findViewById(R.id.tv_time_final);
        tv_time_final.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scheduleJobTime(view);
            }
        });

        bt_send = (Button) findViewById(R.id.bt_send);
        bt_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestContact();
            }
        });
    }

    private void requestContact() {
        new ContactHandler().execute();
        Intent intent = new Intent(this, OwnerHomeActivity.class);
        startActivity(intent);
    }

    private void scheduleJobDate(View view) {
        initDateData();
        Calendar calendarDefault = Calendar.getInstance();
        calendarDefault.set(year, month, day);
        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
                this,
                calendarDefault.get(Calendar.YEAR),
                calendarDefault.get(Calendar.MONTH),
                calendarDefault.get(Calendar.DAY_OF_MONTH)
        );

        Calendar cMin = Calendar.getInstance();
        Calendar cMax = Calendar.getInstance();

        cMax.set(cMax.get(Calendar.YEAR), 11, 31);
        datePickerDialog.setMinDate(cMin);
        datePickerDialog.setMaxDate(cMax);

        List<Calendar> daysList = new LinkedList<>();
        Calendar[] daysArray;
        Calendar cAux = Calendar.getInstance();
        while (cAux.getTimeInMillis() <= cMax.getTimeInMillis()) {
            if (cAux.get(Calendar.DAY_OF_WEEK) != 1 && cAux.get(Calendar.DAY_OF_WEEK) != 7) {
                Calendar c = Calendar.getInstance();
                c.setTimeInMillis(cAux.getTimeInMillis());

                daysList.add(c);
            }
            cAux.setTimeInMillis(cAux.getTimeInMillis() + (24 * 60 * 60 * 1000));
        }

        daysArray = new Calendar[daysList.size()];
        for (int i = 0; i < daysArray.length; i++) {
            daysArray[i] = daysList.get(i);
        }

        datePickerDialog.setSelectableDays(daysArray);
        datePickerDialog.setOnCancelListener(this);
        datePickerDialog.show(getFragmentManager(), "DatePickerDialog");
    }

    private void scheduleJobTime(View view) {
        tDefault = Calendar.getInstance();
        initTimeData();
        TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(
                this,
                tDefault.get(Calendar.HOUR_OF_DAY),
                tDefault.get(Calendar.MINUTE),
                true
        );
        timePickerDialog.setOnCancelListener(this);
        timePickerDialog.show(getFragmentManager(), "TimePickerDialog");
    }

    private void initDateData() {
        if (year == 0) {
            Calendar c = Calendar.getInstance();
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);
        }
    }

    private void initTimeData() {
        if (hour == 0) {
            Calendar c = Calendar.getInstance();
            hour = c.get(Calendar.HOUR_OF_DAY);
            minute = c.get(Calendar.MINUTE);
        }
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        year = month = day = hour = minute = 0;
        tv_date_start.setText("");
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        tDefault = Calendar.getInstance();
        tDefault.set(this.year, month, day, hour, minute);

        this.year = year;
        month = monthOfYear;
        day = dayOfMonth;

        if (!date_start_setted) {
            tv_date_start.setText(setDateOnTextView());
        } else {
            tv_date_final.setText(setDateOnTextView());
        }
        date_start_setted = true;
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
        this.hour = hourOfDay;
        this.minute = minute;

        if (!time_start_setted) {
            tv_time_start.setText(setTimeOnTextView());
        } else {
            tv_time_final.setText(setTimeOnTextView());
        }
        time_start_setted = true;
    }

    private String setDateOnTextView() {
        return (day < 10 ? "0" + day : day) + "/" +
                ((month + 1) < 10 ? "0" + (month + 1) : (month + 1)) + "/" + year;
    }

    private String setTimeOnTextView() {
        return (hour < 10 ? "0" + hour : hour) + "h" +
                (this.minute < 10 ? "0" + this.minute : this.minute);
    }
}
