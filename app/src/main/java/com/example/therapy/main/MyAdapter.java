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

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private List<Drug> contentList;
    private final goToActivityable mainActivity;

    public List<Drug> getContentList() {
        return contentList;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public ImageView imageView;

        public MyViewHolder(LinearLayout v) {
            super(v);
            textView = v.findViewById(R.id.row_name);
            imageView = v.findViewById(R.id.row_image);
        }
    }

    public MyAdapter(List<Drug> contentList, goToActivityable mainActivity) {
        this.contentList = contentList;
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
        myViewHolder.textView.setText(contentList.get(position).getDrugName());
        myViewHolder.imageView.setImageURI(Uri.parse(contentList.get(position).getDrugImagePath()));
    }

    @Override
    public int getItemCount() {
        return contentList.size();
    }

    public void removeItem(int position) {
        contentList.remove(position);
        mainActivity.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(Drug item, int position) {
        contentList.add(position, item);
        mainActivity.undo(position);
        notifyItemInserted(position);
    }
}
