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
import android.widget.Toast;

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
    private ArrayAdapter adapterSpinner;

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

                performance = SystemAD.getPerformance(startDate, finishDate);

                score = SystemAD.getScore(performance);

                performanceEditText.setText(SystemAD.getPerformanceText(startDate, finishDate));
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

        adapterSpinner = new ArrayAdapter<>(
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

        startDate = Calendar.getInstance();
        finishDate = Calendar.getInstance();

        int year = startDate.get(Calendar.YEAR);
        int month = startDate.get(Calendar.MONTH);
        int dayOfMonth = startDate.get(Calendar.DAY_OF_MONTH);

        int hour = startDate.get(Calendar.HOUR_OF_DAY);
        int minute = startDate.get(Calendar.MINUTE);


        resetFields();

        startDateEditText.setOnClickListener(v -> {

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    view.getContext(),
                    (viewOn,yearOn,monthOn,dayOfMonthOn) ->{
                        startDate.set(yearOn, monthOn, dayOfMonthOn);
                        startDateEditText.setText(SystemAD.getDate(dayOfMonthOn, monthOn, yearOn));
                    },year,month,dayOfMonth
            );
            datePickerDialog.show();
        });

        startTimeEditText.setOnClickListener(v -> {

            TimePickerDialog timePickerDialog = new TimePickerDialog(
                    view.getContext(),
                    (viewOn,hourOn,minuteOn) ->{
                        startDate.set(startDate.get(Calendar.YEAR), startDate.get(Calendar.MONTH), startDate.get(Calendar.DAY_OF_MONTH),hourOn,minuteOn);
                        startTimeEditText.setText(SystemAD.getTime(hourOn, minuteOn));
                    },hour,minute,true
            );

            timePickerDialog.show();
        });

        finishDateEditText.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    view.getContext(),
                    (viewOn,yearOn,monthOn,dayOfMonthOn) ->{
                        finishDate.set(yearOn, monthOn, dayOfMonthOn);
                        finishDateEditText.setText(SystemAD.getDate(dayOfMonthOn, monthOn, yearOn));
                    },year,month,dayOfMonth
            );
            datePickerDialog.show();
        });

        finishTimeEditText.setOnClickListener(v -> {

            TimePickerDialog timePickerDialog = new TimePickerDialog(
                    view.getContext(),
                    (viewOn, hourOn, minuteOn) -> {
                        finishDate.set(finishDate.get(Calendar.YEAR), finishDate.get(Calendar.MONTH), finishDate.get(Calendar.DAY_OF_MONTH),hourOn,minuteOn);
                        finishTimeEditText.setText(SystemAD.getTime(hourOn, minuteOn));
                    }, hour, minute, true
            );

            timePickerDialog.show();
        });

        startDateEditText.addTextChangedListener(dateTimeEditTextOnChange);
        startTimeEditText.addTextChangedListener(dateTimeEditTextOnChange);
        finishDateEditText.addTextChangedListener(dateTimeEditTextOnChange);
        finishTimeEditText.addTextChangedListener(dateTimeEditTextOnChange);

        registerButton.setOnClickListener(v ->{
            registerOrUpdateActivity();
        });

        lastDeleteButton.setOnClickListener(v -> {
            deleteLastActivity();
        });

        resetButton.setOnClickListener(v ->{
            resetFields();
        });

        registerActivityRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                View itemView = rv.findChildViewUnder(e.getX(),e.getY());

                if(itemView != null){
                    int position = rv.getChildAdapterPosition(itemView);
                    Activity activity = AdapterRegisterActivity.listActivity.get(position);
                    fillBlanksByActivity(activity);
                    return true;
                }
                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

        return view;
    }

    public void registerOrUpdateActivity(){
        saveAttributes();

        if(SystemAD.selectedActivity != null) { //Atualiza Atividade
            Activity activity = SystemAD.toActivity(SystemAD.selectedActivity.getId(), title, performance, startDate, finishDate, score, description, category);
            DB.updateActivity(activity, this);
            SystemAD.selectedActivity = null;
            SystemAD.lastRegisteredActivity = activity;
        }else{ //Registra Atividade
            Activity activity = SystemAD.toActivity(null, title, performance, startDate, finishDate, score, description, category);
            SystemAD.lastRegisteredActivity = DB.registerActivity(activity, this);
        }
        refreshRegisterActivityRecyclerView();
        resetFields();
    }

    public void deleteLastActivity(){
        Activity activity = DB.deleteLastActivity(this);
        fillBlanksByActivity(activity);
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

    public void fillBlanksByActivity(Activity activity){
        SystemAD.selectedActivity = activity;

        titleTextInput.setText(activity.getTitle());
        startDateEditText.setText(SystemAD.getDate(activity.getStart()));
        startTimeEditText.setText(SystemAD.getTime(activity.getStart()));
        finishDateEditText.setText(SystemAD.getDate(activity.getFinish()));
        finishTimeEditText.setText(SystemAD.getTime(activity.getFinish()));
        descriptionTextInput.setText(activity.getDescription());
        categorySpinner.setSelection(adapterSpinner.getPosition(activity.getCategory()));

        //Para evitar a alteração pelo evento de mudança dos campos, é necessário que a alteração destes fique por último
        performanceEditText.setText(SystemAD.getPerformanceText(activity.getPerformance()));
        scoreEditText.setText(activity.getScore().toString());

        Toast.makeText(this.getContext(),"Registro de Atividade carregado com sucesso!",Toast.LENGTH_LONG).show();
    }

    public void saveAttributes(){ //Salva os valores dos campos nas variáveis de instanciação de activity reservadas ao fragment
        title = titleTextInput.getText().toString();
        description = descriptionTextInput.getText().toString();
        category = categorySpinner.getSelectedItem().toString();

        String [] filterStartDate = startDateEditText.getText().toString().split("/");
        String[] filterStartTime = startTimeEditText.getText().toString().split(":");
        startDate.set(Integer.parseInt(filterStartDate[2]),
                Integer.parseInt(filterStartDate[1]),
                Integer.parseInt(filterStartDate[0]),
                Integer.parseInt(filterStartTime[0]),
                Integer.parseInt(filterStartTime[1]));

        String [] filterFinishDate = finishDateEditText.getText().toString().split("/");
        String[] filterFinishTime = finishTimeEditText.getText().toString().split(":");
        finishDate.set(Integer.parseInt(filterFinishDate[2]),
                Integer.parseInt(filterFinishDate[1]),
                Integer.parseInt(filterFinishDate[0]),
                Integer.parseInt(filterFinishTime[0]),
                Integer.parseInt(filterFinishTime[1]));
        performance = SystemAD.getPerformance(performanceEditText.getText().toString());
        score = Double.parseDouble(scoreEditText.getText().toString());
    }

    public void resetFields(){
        titleTextInput.setText("");
        //-----Momento Atual da Execução do Fragment----------

        startDate = Calendar.getInstance();
        finishDate = Calendar.getInstance();

        int year = startDate.get(Calendar.YEAR);
        int month = startDate.get(Calendar.MONTH);
        int dayOfMonth = startDate.get(Calendar.DAY_OF_MONTH);

        int hour = startDate.get(Calendar.HOUR_OF_DAY);
        int minute = startDate.get(Calendar.MINUTE);

        //-----------------------------------------------------

        startDateEditText.setText(SystemAD.getDate(dayOfMonth, month, year));
        startTimeEditText.setText(SystemAD.getTime(hour, minute));
        finishDateEditText.setText(SystemAD.getDate(dayOfMonth, month, year));
        finishTimeEditText.setText(SystemAD.getTime(hour, minute));
        performanceEditText.setText("");
        scoreEditText.setText("");
        descriptionTextInput.setText("");
        categorySpinner.setSelection(0);

        SystemAD.selectedActivity = null;
    }

}