package com.wicak.plito.ui.materi.satu;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wicak.plito.R;

import java.util.List;

public class MateriSatuAdapter extends RecyclerView.Adapter<MateriSatuViewModel> {

    //    private Context mContext;
    private MateriSatu context;
    private List<MtrSatu> mData;

    public MateriSatuAdapter(MateriSatu context, List<MtrSatu> mData) {
        this.context =  context;
        this.mData = mData;
    }


    @NonNull
    @Override
    public MateriSatuViewModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_materi, parent, false);
        return new MateriSatuViewModel(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MateriSatuViewModel holder, int position) {

        holder.materi_title.setText(mData.get(position).getTitle());
        holder.img_thumbnail.setImageResource(mData.get(position).getThumbnail());

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


}
