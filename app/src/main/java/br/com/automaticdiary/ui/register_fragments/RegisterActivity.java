package br.com.automaticdiary.ui.register_fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.material.textfield.TextInputEditText;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import br.com.automaticdiary.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterActivity#newInstance} factory method to
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


    Calendar startDate;
    Calendar finishDate;


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

                Double performance = (diffMilisseconds / (1000.0 * 60.0 * 60.0));

                Double score = performance * 20.0;

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


        return view;
    }
}