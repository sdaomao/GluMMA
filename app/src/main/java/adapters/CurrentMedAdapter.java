package adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.glumma.R;

import java.util.List;

import information.MedicineData;

public class CurrentMedAdapter extends RecyclerView.Adapter<CurrentMedAdapter.InformationViewHolder> {

    private List<MedicineData> informationList;
    private OnDeleteClickListener onDeleteClickListener;

    public interface OnDeleteClickListener {
        void onDeleteClick(int position);
    }

    public CurrentMedAdapter(List<MedicineData> informationList, OnDeleteClickListener onDeleteClickListener) {
        this.informationList = informationList;
        this.onDeleteClickListener = onDeleteClickListener;
    }

    @NonNull
    @Override
    public InformationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.medicine_item, parent, false);
        return new InformationViewHolder(view, onDeleteClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull InformationViewHolder holder, int position) {
        MedicineData information = informationList.get(position);
        holder.medicine.setText(information.getMedicine());
        if (information.getType().equals("Insulin")) {
            holder.image.setImageDrawable(ContextCompat.getDrawable(holder.image.getContext(), R.drawable.baseline_vaccines_24));
        }

        if (information.getType().equals("Oral")) {
            holder.image.setImageDrawable(ContextCompat.getDrawable(holder.image.getContext(), R.drawable.capsule));
        }

        // Bind other fields
    }

    @Override
    public int getItemCount() {
        return informationList.size();
    }

    public static class InformationViewHolder extends RecyclerView.ViewHolder {

        TextView medicine;
        ImageButton delete;
        ImageView image;

        public InformationViewHolder(@NonNull View itemView, OnDeleteClickListener onDeleteClickListener) {
            super(itemView);
            medicine = itemView.findViewById(R.id.text13);
            delete = itemView.findViewById(R.id.imageButton12);
            image = itemView.findViewById(R.id.imageView14);

            delete.setOnClickListener(v -> {
                if (onDeleteClickListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        onDeleteClickListener.onDeleteClick(position);
                    }
                }
            });

            // Initialize other TextViews
        }
    }
}