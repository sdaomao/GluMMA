package adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.glumma.R;

import java.util.List;

import information.FoodData;
import information.Information;
import information.Pressuredata;
import information.WeightData;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.InformationViewHolder> {

    private List<FoodData> informationList;

    public FoodAdapter(List<FoodData> informationList) {
        this.informationList = informationList;
    }

    @NonNull
    @Override
    public InformationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fooditem, parent, false);
        return new InformationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InformationViewHolder holder, int position) {
        FoodData information = informationList.get(position);
        holder.Food.setText(information.getFood());
        holder.time.setText(information.getDate());
        holder.textTime.setText(information.getTime());


        // Bind other fields
    }

    @Override
    public int getItemCount() {
        return informationList.size();
    }

    public static class InformationViewHolder extends RecyclerView.ViewHolder {

        TextView textTime, weight, Food,pressure, time;

        public InformationViewHolder(@NonNull View itemView) {
            super(itemView);
            Food = itemView.findViewById(R.id.text4);
            time = itemView.findViewById(R.id.text22);
            textTime = itemView.findViewById(R.id.text12);
            // Initialize other TextViews
        }
    }
}