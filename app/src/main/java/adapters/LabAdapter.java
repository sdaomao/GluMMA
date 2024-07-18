package adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.glumma.R;

import java.util.List;

import information.ExerciseData;
import information.Information;
import information.LabData;
import information.Pressuredata;
import information.WeightData;

public class LabAdapter extends RecyclerView.Adapter<LabAdapter.InformationViewHolder> {

    private List<LabData> informationList;

    public LabAdapter(List<LabData> informationList) {
        this.informationList = informationList;
    }

    @NonNull
    @Override
    public InformationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.labitem, parent, false);
        return new InformationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InformationViewHolder holder, int position) {
        LabData information = informationList.get(position);
        holder.labtext.setText(information.getLab());
        holder.time.setText(information.getDate());


        // Bind other fields
    }

    @Override
    public int getItemCount() {
        return informationList.size();
    }

    public static class InformationViewHolder extends RecyclerView.ViewHolder {

        TextView textTime, labtext, textPeriod,pressure, time;

        ImageView image;

        public InformationViewHolder(@NonNull View itemView) {
            super(itemView);
            labtext = itemView.findViewById(R.id.text4);
            time = itemView.findViewById(R.id.text22);
            image = itemView.findViewById(R.id.imageView14);
            image.setColorFilter(ContextCompat.getColor(itemView.getContext(), R.color.primaryColor));
            // Initialize other TextViews
        }
    }
}