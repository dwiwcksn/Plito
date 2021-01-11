package com.wicak.plito.ui.materi.kunci;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.wicak.plito.R;

import java.util.ArrayList;
import java.util.List;

public class MateriSatu extends AppCompatActivity {

    private MateriSatuViewModel materiViewModel;
    private RecyclerView rv;
    private MateriSatuAdapter adapter;
    private List<MtrSatu> lstMateri;

    public MateriSatu newInstance(){
        return new MateriSatu();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materi_satu);

        lstMateri = new ArrayList<>();
        adapter = new MateriSatuAdapter(this, lstMateri);
        rv = (RecyclerView) findViewById(R.id.rv_materiSatu);
//        rv.setHasFixedSize(false);
        rv.setLayoutManager(new GridLayoutManager(this,1));

        MateriSatuAdapter adapter = new MateriSatuAdapter(this, lstMateri);

        rv.setAdapter(adapter);
        lstMateri.add(new MtrSatu("Kunci", "Categories 2", "Description", R.drawable.mood_bad));
        lstMateri.add(new MtrSatu("Socket", "Categories 3", "Description", R.drawable.mood_up));
        lstMateri.add(new MtrSatu("", "Categories 4", "Description", R.drawable.ic_notifications_black_24dp));
        lstMateri.add(new MtrSatu("Materi awgsv", "Categories 4", "Description", R.drawable.ic_notifications_black_24dp));

    }
}