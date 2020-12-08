package com.wicak.plito.ui.materi;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.wicak.plito.R;
import com.wicak.plito.ui.materi.dua.MateriDua;
import com.wicak.plito.ui.materi.empat.MateriEmpat;
import com.wicak.plito.ui.materi.lima.MateriLima;
import com.wicak.plito.ui.materi.satu.MateriSatu;
import com.wicak.plito.ui.materi.tiga.MateriTiga;

public class MateriViewModel extends RecyclerView.ViewHolder {
    public TextView materi, materi_title;
    public ImageView img_thumbnail;
    public CardView cardView;
    Context context;
    private MutableLiveData<String> mText;

    public MateriViewModel(View itemView) {
        super(itemView);
        context = itemView.getContext();

        materi_title = itemView.findViewById(R.id.cv_title);
        img_thumbnail = itemView.findViewById(R.id.cv_img);
        cardView = itemView.findViewById(R.id.cv_click);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                switch (MateriViewModel.this.getAdapterPosition()) {
                    case 0:
                        intent = new Intent(context, MateriSatu.class);
                        break;
                    case 1:
                        intent = new Intent(context, MateriDua.class);
                        break;
                    case 2:
                        intent = new Intent(context, MateriTiga.class);
                        break;
                    case 3:
                        intent = new Intent(context, MateriEmpat.class);
                        break;
                    case 4:
                        intent = new Intent(context, MateriLima.class);
                        break;
                }
                context.startActivity(intent);
            }
        });
    }

}