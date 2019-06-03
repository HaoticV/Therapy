package com.example.therapy.main;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.therapy.R;
import com.example.therapy.database.Drug;

import java.util.List;

class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private final List<Drug> arrayList;
    private final goToActivityable mainActivity;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public ImageView imageView;

        public MyViewHolder(LinearLayout v) {
            super(v);
            textView = v.findViewById(R.id.row_name);
            imageView = v.findViewById(R.id.row_image);
        }
    }

    public MyAdapter(List<Drug> arrayList, goToActivityable mainActivity) {
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
        myViewHolder.textView.setText(arrayList.get(position).getDrugName());
        myViewHolder.imageView.setImageURI(Uri.parse(arrayList.get(position).getDrugImagePath()));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

}
