package ru.mirea.netelev.mireaproject.tmtimes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.mirea.netelev.mireaproject.R;


public class TmTimesAdapter  extends RecyclerView.Adapter<TmTimesAdapter.TmTimesViewHolder>{
    private final List<TmTimes> times;

    public TmTimesAdapter(List<TmTimes> times){
        this.times = times;
    }

    @NonNull
    @Override
    public TmTimesAdapter.TmTimesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View cardView = LayoutInflater.from(parent.getContext()).inflate(R.layout.tmtimes_list, parent, false);

        return new TmTimesAdapter.TmTimesViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull TmTimesAdapter.TmTimesViewHolder holder, int position) {
        TmTimes time = times.get(position);
        holder.name.setText(time.name);
        holder.number.setText(time.number);
        holder.time.setText(time.time);
    }

    @Override
    public int getItemCount() {
        return times.size();
    }

    public static class TmTimesViewHolder extends RecyclerView.ViewHolder{
        public final TextView name;
        public final TextView number;
        public final TextView time;

        public TmTimesViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textTmTimesName);
            number = itemView.findViewById(R.id.textTmTimesNumber);
            time = itemView.findViewById(R.id.textTmTimesTime);
        }
    }
}
