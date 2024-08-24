package adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.glumma.R;

import java.util.List;

import information.MedicineData;
import information.TimeData;

public class TimerAdapter extends RecyclerView.Adapter<TimerAdapter.InformationViewHolder> {

    private List<TimeData> informationList;
    private OnDeleteClickListener onDeleteClickListener;
    private  EnableListener onEnableListener;

    public interface OnDeleteClickListener {
        void onDeleteClick(int position);
    }

    public interface EnableListener {
        void onEnableClick(int position);
    }

    public TimerAdapter(List<TimeData> informationList, OnDeleteClickListener onDeleteClickListener, EnableListener onEnableListener) {
        this.informationList = informationList;
        this.onDeleteClickListener = onDeleteClickListener;
        this.onEnableListener = onEnableListener;
    }

    @NonNull
    @Override
    public InformationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.time_item, parent, false);
        return new InformationViewHolder(view, onDeleteClickListener , onEnableListener);
    }

    @Override
    public void onBindViewHolder(@NonNull InformationViewHolder holder, int position) {
        TimeData information = informationList.get(position);
        holder.time.setText(information.getTime());
        holder.label.setText(information.getLabel());
        holder.repeat.setText(information.getRepeatDays());
        holder.enable.setChecked(information.isEnable());

        // Bind other fields
    }

    @Override
    public int getItemCount() {
        return informationList.size();
    }

    public static class InformationViewHolder extends RecyclerView.ViewHolder {

        TextView time;
        TextView label;
        TextView repeat;
        SwitchCompat enable;
        ImageButton delete;

        public InformationViewHolder(@NonNull View itemView, OnDeleteClickListener onDeleteClickListener, EnableListener onEnableListener) {
            super(itemView);
            time = itemView.findViewById(R.id.text13);
            delete = itemView.findViewById(R.id.imageButton12);
            label = itemView.findViewById(R.id.text24);
            enable = itemView.findViewById(R.id.switch1);
            repeat = itemView.findViewById(R.id.text25);


            delete.setOnClickListener(v -> {
                if (onDeleteClickListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        onDeleteClickListener.onDeleteClick(position);
                    }
                }
            });

            enable.setOnClickListener( v -> {
              if (onEnableListener != null) {
                  int position = getAdapterPosition();
                  if (position != RecyclerView.NO_POSITION) {
                      onEnableListener.onEnableClick(position);
                  }
              }
            });

            // Initialize other TextViews
        }
    }
}