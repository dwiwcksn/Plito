package com.wicak.plito.ui.materi;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wicak.plito.R;

import java.util.List;

public class MateriAdapter extends RecyclerView.Adapter<MateriViewModel> {

    //    private Context mContext;
    private MateriFragment context;
    private List<Materi> mData;

    public MateriAdapter(MateriFragment context, List<Materi> mData) {
        this.context =  context;
        this.mData = mData;
    }


    @NonNull
    @Override
    public MateriViewModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_materi, parent, false);
        return new MateriViewModel(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MateriViewModel holder, int position) {

        holder.materi_title.setText(mData.get(position).getTitle());
        holder.img_thumbnail.setImageResource(mData.get(position).getThumbnail());

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


}
