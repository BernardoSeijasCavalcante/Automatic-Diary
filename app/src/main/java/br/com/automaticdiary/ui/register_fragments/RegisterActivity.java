package br.com.automaticdiary.ui.register_fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;

import org.w3c.dom.Text;

import java.sql.Array;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import br.com.automaticdiary.R;
import br.com.automaticdiary.entities.Activity;
import br.com.automaticdiary.resources.AdapterRegisterActivity;
import br.com.automaticdiary.resources.DB;
import br.com.automaticdiary.resources.SystemAD;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterActivity #newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterActivity extends Fragment {

    private TextInputEditText titleTextInput;
    private EditText startDateEditText;
    private EditText startTimeEditText;
    private EditText performanceEditText;
    private EditText scoreEditText;
    private EditText finishDateEditText;
    private EditText finishTimeEditText;
    private TextInputEditText descriptionTextInput;
    private Spinner categorySpinner;
    private AppCompatButton registerButton;
    private AppCompatButton lastDeleteButton;
    private AppCompatButton resetButton;

    private RecyclerView registerActivityRecyclerView;

    private String title;
    private Double performance;
    private Calendar startDate;
    private Calendar finishDate;
    private Double score;
    private String description;
    private String category;

    private TextWatcher dateTimeEditTextOnChange;

    public RegisterActivity() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dateTimeEditTextOnChange = new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                long diffMilisseconds = finishDate.getTimeInMillis() - startDate.getTimeInMillis();

                performance = (diffMilisseconds / (1000.0 * 60.0 * 60.0));

                score = performance * 20.0;

                long performanceHours = TimeUnit.MILLISECONDS.toHours(diffMilisseconds);
                long performanceMinutes = TimeUnit.MILLISECONDS.toMinutes(diffMilisseconds) - TimeUnit.HOURS.toMinutes(performanceHours);

                String performanceText = performanceHours + "h" + ((performanceMinutes < 10) ? "0" : "") + performanceMinutes;

                performanceEditText.setText(performanceText);
                scoreEditText.setText(String.format("%.2f",score));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register_activity, container, false);

        titleTextInput = view.findViewById(R.id.titleTextInput);
        startDateEditText = view.findViewById(R.id.startDateEditText);
        startTimeEditText = view.findViewById(R.id.startTimeEditText);
        performanceEditText = view.findViewById(R.id.performanceEditText);
        scoreEditText = view.findViewById(R.id.scoreEditText);
        finishDateEditText = view.findViewById(R.id.finishDateEditText);
        finishTimeEditText = view.findViewById(R.id.finishTimeEditText);
        descriptionTextInput = view.findViewById(R.id.descriptionTextInput);
        categorySpinner = view.findViewById(R.id.categorySpinner);

        registerButton = view.findViewById(R.id.registerButton);
        lastDeleteButton = view.findViewById(R.id.lastDeleteButton);
        resetButton = view.findViewById(R.id.resetButton);

        registerActivityRecyclerView = view.findViewById(R.id.registerActivityRecyclerView);

        ArrayAdapter adapterSpinner = new ArrayAdapter<>(
                this.getContext(),
                R.layout.registeractivityspinner_item,
                getResources().getStringArray(R.array.ActivityCategory)
        );

        adapterSpinner.setDropDownViewResource(R.layout.registeractivityspinnerdropdown_item);
        categorySpinner.setAdapter(adapterSpinner);

        RecyclerView.LayoutManager layoutManger = new LinearLayoutManager(getActivity().getApplicationContext());
        registerActivityRecyclerView.setLayoutManager(layoutManger);
        registerActivityRecyclerView.setHasFixedSize(true);
        registerActivityRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity().getApplicationContext(), LinearLayout.VERTICAL));

        refreshRegisterActivityRecyclerView();

        Calendar now = Calendar.getInstance();

        //-----Momento Atual da Execução do Fragment----------

        int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH);
        int dayOfMonth = now.get(Calendar.DAY_OF_MONTH);

        int hour = now.get(Calendar.HOUR_OF_DAY);
        int minute = now.get(Calendar.MINUTE);

        //-----------------------------------------------------

        startDate = Calendar.getInstance();
        finishDate = Calendar.getInstance();

        startDateEditText.setText(dayOfMonth + "/" + ((month < 10) ? "0" : "") + (month + 1) + "/" + year);
        startTimeEditText.setText(hour + ":" + ((minute < 10) ? "0" : "") + minute);

        finishDateEditText.setText(dayOfMonth + "/" + ((month < 10) ? "0" : "") + (month + 1) + "/" + year);
        finishTimeEditText.setText(hour + ":" + ((minute < 10) ? "0" : "") + minute);

        startDateEditText.setOnClickListener(v -> {

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    view.getContext(),
                    (viewOn,yearOn,monthOn,dayOfMonthOn) ->{
                        startDate.set(yearOn, monthOn, dayOfMonthOn);
                        startDateEditText.setText(dayOfMonthOn + "/" + ((monthOn < 10) ? "0" : "") + (monthOn + 1) + "/" + yearOn);
                    },year,month,dayOfMonth
            );
            datePickerDialog.show();
        });

        startTimeEditText.setOnClickListener(v -> {

            TimePickerDialog timePickerDialog = new TimePickerDialog(
                    view.getContext(),
                    (viewOn,hourOn,minuteOn) ->{
                        startDate.set(startDate.get(Calendar.YEAR), startDate.get(Calendar.MONTH), startDate.get(Calendar.DAY_OF_MONTH),hourOn,minuteOn);
                        startTimeEditText.setText(hourOn + ":" + ((minuteOn < 10) ? "0" : "") + minuteOn);
                    },hour,minute,true
            );

            timePickerDialog.show();
        });

        finishDateEditText.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    view.getContext(),
                    (viewOn,yearOn,monthOn,dayOfMonthOn) ->{
                        finishDate.set(yearOn, monthOn, dayOfMonthOn);
                        finishDateEditText.setText(dayOfMonthOn + "/" + ((monthOn < 10) ? "0" : "") + (monthOn + 1) + "/" + yearOn);
                    },year,month,dayOfMonth
            );
            datePickerDialog.show();
        });

        finishTimeEditText.setOnClickListener(v -> {

            TimePickerDialog timePickerDialog = new TimePickerDialog(
                    view.getContext(),
                    (viewOn, hourOn, minuteOn) -> {
                        finishDate.set(finishDate.get(Calendar.YEAR), finishDate.get(Calendar.MONTH), finishDate.get(Calendar.DAY_OF_MONTH),hourOn,minuteOn);
                        finishTimeEditText.setText(hourOn + ":" + ((minuteOn < 10) ? "0" : "") + minuteOn);
                    }, hour, minute, true
            );

            timePickerDialog.show();
        });

        startDateEditText.addTextChangedListener(dateTimeEditTextOnChange);
        startTimeEditText.addTextChangedListener(dateTimeEditTextOnChange);
        finishDateEditText.addTextChangedListener(dateTimeEditTextOnChange);
        finishTimeEditText.addTextChangedListener(dateTimeEditTextOnChange);

        registerButton.setOnClickListener(v ->{
            registerActivity();
        });


        return view;
    }

    public void registerActivity(){
        title = titleTextInput.getText().toString();
        description = descriptionTextInput.getText().toString();
        category = categorySpinner.getSelectedItem().toString();

        Activity activity = SystemAD.toActivity(null, title, performance, startDate, finishDate, score, description, category);
        DB.registerActivity(activity, this);
        refreshRegisterActivityRecyclerView();
    }

    public void refreshRegisterActivityRecyclerView(){
        List<Activity> listActivity = DB.findAllRegisterActivity(this);

        while(listActivity.size() > 20){
            listActivity.remove(20);
        }

        AdapterRegisterActivity adapter = new AdapterRegisterActivity(listActivity);
        registerActivityRecyclerView.setAdapter(adapter);
    }
}