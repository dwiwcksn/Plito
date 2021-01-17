package com.wicak.plito.ui.materi;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.wicak.plito.R;
import com.wicak.plito.ui.materi.extension.TabsExtension;
import com.wicak.plito.ui.materi.obeng.TabsObeng;
import com.wicak.plito.ui.materi.palu.TabsPalu;
import com.wicak.plito.ui.materi.tang.MateriEmpat;
import com.wicak.plito.ui.materi.kunci.TabsKunci;
import com.wicak.plito.ui.materi.obeng.MateriTiga;
import com.wicak.plito.ui.materi.tang.TabsTang;

public class MateriViewModel extends RecyclerView.ViewHolder {
    public TextView materi, materi_title;
    public ImageView img_thumbnail;
    public LinearLayout linearLayout;
    public CardView cardView;
    Context context;
    private MutableLiveData<String> mText;

    public MateriViewModel(View itemView) {
        super(itemView);
        context = itemView.getContext();

        materi_title = itemView.findViewById(R.id.cv_title);
        img_thumbnail = itemView.findViewById(R.id.cv_img);
//        linearLayout = itemView.findViewById(R.id.linear_card);
        cardView = itemView.findViewById(R.id.cv_click);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                switch (MateriViewModel.this.getAdapterPosition()) {
                    case 0:
                        intent = new Intent(context, TabsKunci.class);
                        break;
                    case 1:
                        intent = new Intent(context, TabsPalu.class);
                        break;
                    case 2:
                        intent = new Intent(context, TabsObeng.class);
                        break;
                    case 3:
                        intent = new Intent(context, TabsTang.class);
                        break;
                    case 4:
                        intent = new Intent(context, TabsExtension.class);
                        break;
                }
                context.startActivity(intent);
            }
        });
    }

}