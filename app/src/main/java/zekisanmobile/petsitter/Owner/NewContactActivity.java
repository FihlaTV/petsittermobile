package zekisanmobile.petsitter.Owner;

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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmResults;
import zekisanmobile.petsitter.Adapters.AnimalSpinnerAdapter;
import zekisanmobile.petsitter.DAO.AnimalDAO;
import zekisanmobile.petsitter.DAO.ContactDAO;
import zekisanmobile.petsitter.DAO.SitterDAO;
import zekisanmobile.petsitter.DAO.UserDAO;
import zekisanmobile.petsitter.Handlers.SendRequestContactHandler;
import zekisanmobile.petsitter.Model.Animal;
import zekisanmobile.petsitter.Model.Contact;
import zekisanmobile.petsitter.Model.Sitter;
import zekisanmobile.petsitter.Model.User;
import zekisanmobile.petsitter.R;

public class NewContactActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener, DialogInterface.OnCancelListener {

    private Sitter sitter;
    private User loggedUser;
    private RealmResults<Animal> animals;

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

    private int year, month, day, hour, minute;
    private boolean date_start_setted;
    private boolean time_start_setted;
    private Calendar tDefault;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_contact);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        sitter = (Sitter) intent.getSerializableExtra("sitter");
        tv_name.setText(sitter.getName());

        loggedUser = UserDAO.getLoggedUser(0);
        animals = AnimalDAO.getAnimalsFromSitter(sitter);

        configureToolbar();

        Spinner spAnimal = (Spinner) findViewById(R.id.sp_animal);
        spAnimal.setAdapter(new AnimalSpinnerAdapter(this, animals));
        View btRemoveAnimal = findViewById(R.id.bt_remove_animal);
        btRemoveAnimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callRemoveAnimal(v);
            }
        });

        //createPetForView(findViewById(R.id.bt_add_animal), animals);

        /*if (savedInstanceState != null){
            tv_date_start.setText(savedInstanceState.getString("date_start"));
            tv_date_final.setText(savedInstanceState.getString("date_final"));
            tv_time_start.setText(savedInstanceState.getString("time_start"));
            tv_time_final.setText(savedInstanceState.getString("time_final"));
        }*/
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
        // TOOLBAR
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_white_24dp);
    }

    private void requestContact(View view) {
        createContact(view);
        JSONObject jsonContact = new JSONObject();
        JSONArray jsonAnimals = new JSONArray();
        try {
            jsonContact.put("sitter_id", sitter.getApiId());
            jsonContact.put("date_start", tv_date_start.getText());
            jsonContact.put("date_final", tv_date_final.getText());
            jsonContact.put("time_start", tv_time_start.getText());
            jsonContact.put("time_final", tv_time_final.getText());

            List<Animal> selectedAnimals = getAnimalsFromView(view, animals);
            for (Animal a : selectedAnimals) {
                JSONObject jsonAnimal = new JSONObject();
                jsonAnimal.put("animal_id", a.getId());
                jsonAnimals.put(jsonAnimal);
            }

            jsonContact.put("animal_contacts", jsonAnimals);
            String[] params = {jsonContact.toString(), String.valueOf(loggedUser.getOwner().getApiId())};

            new SendRequestContactHandler().execute(params);
            Intent intent = new Intent(this, OwnerHomeActivity.class);
            startActivity(intent);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private Contact createContact(View view) {
        Sitter persitedSitter = SitterDAO.insertOrUpdateSitter(sitter.getApiId(), sitter.getName(),
                sitter.getAddress(), sitter.getPhoto(),
                sitter.getProfile_background(), sitter.getLatitude(), sitter.getLongitude(), sitter.getDistrict(),
                sitter.getValue_hour(), sitter.getValue_shift(), sitter.getValue_day(), sitter.getAbout_me());
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        Contact contact = realm.createObject(Contact.class);
        contact.setId(ContactDAO.getAllContacts().size() + 1);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        try {
            contact.setDate_start(formatter.parse(tv_date_start.getText().toString()));
            contact.setDate_final(formatter.parse(tv_date_final.getText().toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        contact.setTime_start(tv_time_start.getText().toString());
        contact.setTime_final(tv_time_final.getText().toString());
        contact.setOwner(loggedUser.getOwner());
        contact.setSitter(persitedSitter);
        contact.getAnimals().addAll(getAnimalsFromView(view, animals));
        realm.commitTransaction();
        return contact;
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

    @OnClick(R.id.bt_send)
    public void send(View view) {
        requestContact(view);
    }

    @OnClick({R.id.tv_date_start, R.id.tv_date_final})
    public void doScheduleDate() {
        scheduleJobDate();
    }

    @OnClick({R.id.tv_time_start, R.id.tv_time_final})
    public void doScheduleTime() {
        scheduleJobTime();
    }

    private void callRemoveAnimal(View view) {
        LinearLayout linearLayoutParent = (LinearLayout) view.getParent().getParent();

        if (linearLayoutParent.getChildCount() > 2) {
            linearLayoutParent.removeView((LinearLayout) view.getParent());
        }
    }

    private void createPetForView(View view, RealmResults<Animal> animals) {
        LayoutInflater inflater = this.getLayoutInflater();
        LinearLayout linearLayoutChild = (LinearLayout) inflater.inflate(R.layout.box_animal, null);

        Spinner spAnimal = (Spinner) linearLayoutChild.findViewById(R.id.sp_animal);
        spAnimal.setAdapter(new AnimalSpinnerAdapter(this, animals));

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
    }

    private List<Animal> getAnimalsFromView(View view, RealmResults<Animal> animals) {
        List<Animal> list = new ArrayList<Animal>();
        LinearLayout linearLayoutParent = (LinearLayout) view.getParent();
        LinearLayout linearLayoutChild = (LinearLayout) linearLayoutParent.getChildAt(9);

        for (int i = 0; i < linearLayoutChild.getChildCount(); i++) {
            if (linearLayoutChild.getChildAt(i) instanceof LinearLayout) {
                LinearLayout linearLayoutNestedChild = (LinearLayout) linearLayoutChild.getChildAt(i);
                Spinner spAnimal = (Spinner) linearLayoutNestedChild.getChildAt(0).findViewById(R.id.sp_animal);

                Animal animal = new Animal();
                animal.setId(animals.get(spAnimal.getSelectedItemPosition()).getId());
                animal.setName(animals.get(spAnimal.getSelectedItemPosition()).getName());
                list.add(animal);
            }
        }

        return list;
    }
}
