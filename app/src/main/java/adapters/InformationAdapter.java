package adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.glumma.R;

import java.util.List;

import information.Information;

public class InformationAdapter extends RecyclerView.Adapter<InformationAdapter.InformationViewHolder> {

    private List<Information> informationList;

    public InformationAdapter(List<Information> informationList) {
        this.informationList = informationList;
    }

    @NonNull
    @Override
    public InformationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trackiitem, parent, false);
        return new InformationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InformationViewHolder holder, int position) {
        Information information = informationList.get(position);
        holder.textTime.setText(information.getTime());
        holder.glucose.setText(information.getGlucose());
        holder.textPeriod.setText(information.getPeriod());
        holder.time.setText(information.getTimer());
        // Bind other fields
    }

    @Override
    public int getItemCount() {
        return informationList.size();
    }

    public static class InformationViewHolder extends RecyclerView.ViewHolder {

        TextView textTime, glucose, textPeriod,pressure, time;

        public InformationViewHolder(@NonNull View itemView) {
            super(itemView);
            textTime = itemView.findViewById(R.id.text22);
            textPeriod = itemView.findViewById(R.id.text13);
            glucose = itemView.findViewById(R.id.text4);
            time = itemView.findViewById(R.id.text12);
            // Initialize other TextViews
        }
    }
}