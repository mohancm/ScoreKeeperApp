package com.mohan.scorekeeper;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ActionsAdapter extends RecyclerView.Adapter<ActionsAdapter.ViewHolder> {

    private Context context;
    private List<Holder> H;

    public ActionsAdapter(List<Holder> H, Context context) {
        this.H = H;
        this.context = context;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView act_name;
        ImageView act_image;
        ViewHolder(View view) {
            super(view);
            act_name = view.findViewById(R.id.act_t);
            act_image = view.findViewById(R.id.act_i);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.action_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Holder h = H.get(position);
        holder.act_name.setText(h.title);
        holder.act_image.setImageResource(h.img);
    }

    @Override
    public int getItemCount() {
        return H.size();
    }

    public static class Holder {
        String title;
        int img;

        public Holder(String title, int img) {
            this.title = title;
            this.img = img;
        }

    }
}
