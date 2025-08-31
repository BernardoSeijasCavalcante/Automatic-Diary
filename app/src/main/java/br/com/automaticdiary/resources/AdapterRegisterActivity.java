package br.com.automaticdiary.resources;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Calendar;
import java.util.List;

import br.com.automaticdiary.R;
import br.com.automaticdiary.entities.Activity;

public class AdapterRegisterActivity extends RecyclerView.Adapter<AdapterRegisterActivity.SimpleActivityViewHolder> {

    public static List<Activity> listActivity;

    public AdapterRegisterActivity(List<Activity> listActivity){this.listActivity = listActivity;}

    public class SimpleActivityViewHolder extends RecyclerView.ViewHolder{
        public TextView titleEditText, performanceEditText, startDateEditText,
            startTimeEditText, scoreEditText;
        public SimpleActivityViewHolder(@NonNull View itemView) {
            super(itemView);

            titleEditText = itemView.findViewById(R.id.titleEditText);
            performanceEditText = itemView.findViewById(R.id.performanceEditText);
            startDateEditText = itemView.findViewById(R.id.startDateEditText);
            startTimeEditText = itemView.findViewById(R.id.startTimeEditText);
            scoreEditText = itemView.findViewById(R.id.scoreEditText);

        }
    }

    @NonNull
    @Override
    public SimpleActivityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_registeractivity, parent, false);
        return new SimpleActivityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SimpleActivityViewHolder holder, int position) {
        Activity activity = listActivity.get(position);

        holder.titleEditText.setText(activity.getTitle());

        int hours = activity.getPerformance().intValue();
        Double minuteAux = activity.getPerformance() - (double) hours;
        int minute = (int)(minuteAux * 60.0);
        holder.performanceEditText.setText(hours + "h" + ((minute < 10) ? "0" : "") + minute);

        Calendar start = activity.getStart();

        holder.startDateEditText.setText(start.get(Calendar.DAY_OF_MONTH) + "/" + start.get(Calendar.MONTH) + "/" + start.get(Calendar.YEAR));
        holder.startTimeEditText.setText(start.get(Calendar.HOUR_OF_DAY) + ":" + start.get(Calendar.MINUTE));

        holder.scoreEditText.setText(activity.getScore().toString());
    }

    @Override
    public int getItemCount() {
        return listActivity.size();
    }

}
