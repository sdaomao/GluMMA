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
import information.WeightData;

public class WeightAdapter extends RecyclerView.Adapter<WeightAdapter.InformationViewHolder> {

    private List<WeightData> informationList;

    public WeightAdapter(List<WeightData> informationList) {
        this.informationList = informationList;
    }

    @NonNull
    @Override
    public InformationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.weight_item, parent, false);
        return new InformationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InformationViewHolder holder, int position) {
        WeightData information = informationList.get(position);
        holder.weight.setText(information.getWeight());
        holder.time.setText(information.getDateday());


        // Bind other fields
    }

    @Override
    public int getItemCount() {
        return informationList.size();
    }

    public static class InformationViewHolder extends RecyclerView.ViewHolder {

        TextView textTime, weight, textPeriod,pressure, time;

        public InformationViewHolder(@NonNull View itemView) {
            super(itemView);
            weight = itemView.findViewById(R.id.text4);
            time = itemView.findViewById(R.id.text22);
            // Initialize other TextViews
        }
    }
}