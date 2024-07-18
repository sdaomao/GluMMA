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
import information.Pressuredata;

public class PressureAdapter extends RecyclerView.Adapter<PressureAdapter.InformationViewHolder> {

    private List<Pressuredata> informationList;

    public PressureAdapter(List<Pressuredata> informationList) {
        this.informationList = informationList;
    }

    @NonNull
    @Override
    public InformationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pressure_item, parent, false);
        return new InformationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InformationViewHolder holder, int position) {
        Pressuredata information = informationList.get(position);
        holder.glucose.setText(information.getSystolic());
        holder.pressure.setText(information.getDiastolic());
        holder.time.setText(information.getTime());

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
            glucose = itemView.findViewById(R.id.text4);
            pressure = itemView.findViewById(R.id.text21);
            time = itemView.findViewById(R.id.text22);
            // Initialize other TextViews
        }
    }
}