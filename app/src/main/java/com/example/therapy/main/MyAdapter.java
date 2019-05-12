package com.example.therapy.main;

import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.therapy.R;

import java.util.ArrayList;

class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private final ArrayList<Pair<Integer, String>> arrayList;
    private final GoToAvtivityable mainActivity;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public ImageView imageView;

        public MyViewHolder(LinearLayout v) {
            super(v);
            textView = v.findViewById(R.id.row_name);
            imageView = v.findViewById(R.id.row_image);
        }
    }

    public MyAdapter(ArrayList<Pair<Integer, String>> arrayList, GoToAvtivityable mainActivity) {
        this.arrayList = arrayList;
        this.mainActivity = mainActivity;
    }

    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.row_table, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, final int position) {
        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.goToWithId(position);
            }
        });
        myViewHolder.textView.setText(arrayList.get(position).second);
        myViewHolder.imageView.setImageResource(arrayList.get(position).first);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

}
