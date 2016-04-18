package zekisanmobile.petsitter.view.owner;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zekisanmobile.petsitter.handler.SendRequestContactHandler;
import zekisanmobile.petsitter.PetSitterApp;
import zekisanmobile.petsitter.R;
import zekisanmobile.petsitter.model.AnimalModel;
import zekisanmobile.petsitter.model.OwnerModel;
import zekisanmobile.petsitter.model.SitterModel;
import zekisanmobile.petsitter.util.Formatter;
import zekisanmobile.petsitter.vo.Animal;
import zekisanmobile.petsitter.vo.Owner;
import zekisanmobile.petsitter.vo.Sitter;

public class NewContactActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener, DialogInterface.OnCancelListener {

    private Sitter sitter;
    private Owner owner;
    private List<Animal> animals;
    private int selectedAnimalsCount;

    @Bind(R.id.toolbar_new_contact)
    Toolbar toolbar;
    @Bind(R.id.tv_name)
    TextView tv_name;
    @Bind(R.id.tv_date_start)
    EditText tv_date_start;
    @Bind(R.id.tv_date_final)
    EditText tv_date_final;
    @Bind(R.id.tv_time_start)
    EditText tv_time_start;
    @Bind(R.id.tv_time_final)
    EditText tv_time_final;
    @Bind(R.id.tv_total_value)
    TextView tvTotalValue;

    private int year, month, day, hour, minute;
    private Calendar tDefault;
    private final int FLAG_START = 0;
    private final int FLAG_END = 1;
    private int flag;

    @Inject
    SitterModel sitterModel;

    @Inject
    OwnerModel ownerModel;

    @Inject
    AnimalModel animalModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_contact);

        ((PetSitterApp) getApplication()).getAppComponent().inject(this);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        long sitter_id = intent.getLongExtra("sitter_id", 0);
        sitter = sitterModel.find(sitter_id);
        tv_name.setText(sitter.getName());

        owner = ownerModel.getLoggedOwnerUser();
        animals = sitterModel.getAnimals(sitter);

        configureToolbar();

        Spinner spAnimal = (Spinner) findViewById(R.id.sp_animal);
        ArrayAdapter<Animal> animalArrayAdapter = new ArrayAdapter<Animal>(
                this, android.R.layout.simple_spinner_item, animals
        );
        spAnimal.setAdapter(animalArrayAdapter);
        View btRemoveAnimal = findViewById(R.id.bt_remove_animal);
        btRemoveAnimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callRemoveAnimal(v);
            }
        });
    }

    public void callAddAnimal(View view) {
        createPetForView(view, animals);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("date_start", tv_date_start.getText().toString());
        outState.putString("date_final", tv_date_final.getText().toString());
        outState.putString("time_start", tv_time_start.getText().toString());
        outState.putString("time_final", tv_time_final.getText().toString());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void configureToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_white_24dp);
    }

    private void requestContact(View view) {
        JSONObject jsonContact = new JSONObject();
        JSONArray jsonAnimals = new JSONArray();
        try {
            jsonContact.put("sitter_id", sitter.getId());
            jsonContact.put("date_start", Formatter.formattedDateForAPI(tv_date_start.getText().toString()));
            jsonContact.put("date_final", Formatter.formattedDateForAPI(tv_date_final.getText().toString()));
            jsonContact.put("time_start", tv_time_start.getText());
            jsonContact.put("time_final", tv_time_final.getText());
            jsonContact.put("total_value", tvTotalValue.getText().toString()
                    .replace("R$", "").replace(",", "."));

            List<Animal> selectedAnimals = getAnimalsFromView(view, animals);
            for (Animal a : selectedAnimals) {
                JSONObject jsonAnimal = new JSONObject();
                Animal animalFromDB = animalModel.findByName(a.getName());
                jsonAnimal.put("animal_id", animalFromDB.getId());
                jsonAnimals.put(jsonAnimal);
            }

            jsonContact.put("animal_contacts", jsonAnimals);
            String[] params = {jsonContact.toString(), String.valueOf(owner.getApiId())};

            new SendRequestContactHandler((PetSitterApp) getApplication()).execute(params);
            Intent intent = new Intent(this, OwnerHomeActivity.class);
            startActivity(intent);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void scheduleJobDate() {
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

    private void scheduleJobTime() {
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

        switch (flag){
            case FLAG_START:
                tv_date_start.setText(setDateOnTextView());
                break;
            case FLAG_END:
                tv_date_final.setText(setDateOnTextView());
                break;
        }
        calculateTotalValue();
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
        this.hour = hourOfDay;
        this.minute = minute;

        switch (flag){
            case FLAG_START:
                tv_time_start.setText(setTimeOnTextView());
                break;
            case FLAG_END:
                tv_time_final.setText(setTimeOnTextView());
                break;
        }
        calculateTotalValue();
    }

    private void calculateTotalValue() {
        updateSelectedAnimalsCount();
        if (canCalculateTotalValue() && selectedAnimalsCount > 0) {
            SimpleDateFormat formatterDate = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat formatterTime = new SimpleDateFormat("hh:mm");
            try {
                Date dateStart = formatterDate.parse(tv_date_start.getText().toString());
                Date dateFinal = formatterDate.parse(tv_date_final.getText().toString());
                long days = ((dateFinal.getTime() - dateStart.getTime()) / (24 * 60 * 60 * 1000)) + 1;

                Date convertedTimeStart = formatterTime.parse(tv_time_start.getText().toString().replace("h", ":"));
                Date convertedTimeFinal = formatterTime.parse(tv_time_final.getText().toString().replace("h", ":"));
                long minutes = (convertedTimeFinal.getTime() - (convertedTimeStart.getTime())) / 60000;

                double minuteValue = sitter.getValue_hour() / 60;
                double totalMinutesValue = minutes * minuteValue;
                double totalValue = totalMinutesValue * days;
                tvTotalValue.setText(NumberFormat.getCurrencyInstance().format(totalValue * selectedAnimalsCount));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean canCalculateTotalValue(){
        if(tv_date_start.getText() != null && tv_date_final.getText() != null
                && tv_time_start.getText() != null && tv_time_final.getText() != null) return true;
        return false;
    }

    private String setDateOnTextView() {
        return (day < 10 ? "0" + day : day) + "/" +
                ((month + 1) < 10 ? "0" + (month + 1) : (month + 1)) + "/" + year;
    }

    private String setTimeOnTextView() {
        return (hour < 10 ? "0" + hour : hour) + "h" +
                (this.minute < 10 ? "0" + this.minute : this.minute);
    }

    @OnClick(R.id.bt_send)
    public void send(View view) {
        requestContact(view);
    }

    @OnClick(R.id.tv_date_start)
    public void doScheduleDateStart() {
        flag = FLAG_START;
        scheduleJobDate();
    }

    @OnClick(R.id.tv_date_final)
    public void doScheduleDateFinal() {
        flag = FLAG_END;
        scheduleJobDate();
    }

    @OnClick(R.id.tv_time_start)
    public void doScheduleTimeStart() {
        flag = FLAG_START;
        scheduleJobTime();
    }

    @OnClick(R.id.tv_time_final)
    public void doScheduleTimeFinal() {
        flag = FLAG_END;
        scheduleJobTime();
    }

    private void callRemoveAnimal(View view) {
        LinearLayout linearLayoutParent = (LinearLayout) view.getParent().getParent();

        if (linearLayoutParent.getChildCount() > 2) {
            linearLayoutParent.removeView((LinearLayout) view.getParent());
            calculateTotalValue();
        }
    }

    private void createPetForView(View view, List<Animal> animals) {
        LayoutInflater inflater = this.getLayoutInflater();
        LinearLayout linearLayoutChild = (LinearLayout) inflater.inflate(R.layout.box_animal, null);

        Spinner spAnimal = (Spinner) linearLayoutChild.findViewById(R.id.sp_animal);
        ArrayAdapter<Animal> animalArrayAdapter = new ArrayAdapter<Animal>(
                this, android.R.layout.simple_spinner_item, animals
        );
        spAnimal.setAdapter(animalArrayAdapter);

        View btRemoveAnimal = linearLayoutChild.findViewById(R.id.bt_remove_animal);
        btRemoveAnimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callRemoveAnimal(view);
            }
        });

        float scale = getResources().getDisplayMetrics().density;
        int margin = (int) (5 * scale + 0.5f);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(margin, margin, margin, margin);
        linearLayoutChild.setLayoutParams(layoutParams);

        LinearLayout linearLayoutParent = (LinearLayout) view.getParent();
        linearLayoutParent.addView(linearLayoutChild, linearLayoutParent.getChildCount() - 2);

        calculateTotalValue();
    }

    private void updateSelectedAnimalsCount(){
        View btAddAnimal = findViewById(R.id.bt_add_animal);
        LinearLayout linearLayoutParent = (LinearLayout) btAddAnimal.getParent().getParent();
        LinearLayout linearLayoutChild = (LinearLayout) linearLayoutParent.getChildAt(9);
        selectedAnimalsCount = linearLayoutChild.getChildCount() - 1;
    }

    private List<Animal> getAnimalsFromView(View view, List<Animal> animals) {
        List<Animal> list = new ArrayList<Animal>();
        LinearLayout linearLayoutParent = (LinearLayout) view.getParent();
        LinearLayout linearLayoutChild = (LinearLayout) linearLayoutParent.getChildAt(9);

        for (int i = 0; i < linearLayoutChild.getChildCount(); i++) {
            if (linearLayoutChild.getChildAt(i) instanceof LinearLayout) {
                LinearLayout linearLayoutNestedChild = (LinearLayout) linearLayoutChild.getChildAt(i);
                Spinner spAnimal = (Spinner) linearLayoutNestedChild.getChildAt(0).findViewById(R.id.sp_animal);

                Animal animal = new Animal();
                animal.setName(animals.get(spAnimal.getSelectedItemPosition()).getName());
                list.add(animal);
            }
        }

        return list;
    }
}
