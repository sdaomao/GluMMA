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
import information.Pressuredata;
import information.WeightData;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.InformationViewHolder> {

    private List<ExerciseData> informationList;

    public ExerciseAdapter(List<ExerciseData> informationList) {
        this.informationList = informationList;
    }

    @NonNull
    @Override
    public InformationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercise, parent, false);
        return new InformationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InformationViewHolder holder, int position) {
        ExerciseData information = informationList.get(position);
        holder.exercise.setText(information.getExercise());
        holder.time.setText(information.getDate());


        // Bind other fields
    }

    @Override
    public int getItemCount() {
        return informationList.size();
    }

    public static class InformationViewHolder extends RecyclerView.ViewHolder {

        TextView textTime, exercise, textPeriod,pressure, time;

        ImageView image;

        public InformationViewHolder(@NonNull View itemView) {
            super(itemView);
            exercise = itemView.findViewById(R.id.text4);
            time = itemView.findViewById(R.id.text22);
            image = itemView.findViewById(R.id.imageView14);
            image.setColorFilter(ContextCompat.getColor(itemView.getContext(), R.color.primaryColor));
            // Initialize other TextViews
        }
    }
}