package com.wicak.plito.ui.materi.dua;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.wicak.plito.R;
import com.wicak.plito.ui.materi.Materi;
import com.wicak.plito.ui.materi.satu.MateriSatu;
import com.wicak.plito.ui.materi.satu.MateriSatuAdapter;
import com.wicak.plito.ui.materi.satu.MateriSatuViewModel;

import java.util.ArrayList;
import java.util.List;

public class MateriDua extends AppCompatActivity {
    private MateriDuaViewModel materiViewModel;
    private RecyclerView rv;
    private MateriDuaAdapter adapter;
    private List<MtrDua> lstMateri;

    public MateriDua newInstance(){
        return new MateriDua();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materi_dua);

        lstMateri = new ArrayList<>();
        adapter = new MateriDuaAdapter(this, lstMateri);
        rv = (RecyclerView) findViewById(R.id.rv_materiDua);
//        rv.setHasFixedSize(false);
        rv.setLayoutManager(new GridLayoutManager(this,2));

        MateriDuaAdapter adapter = new MateriDuaAdapter(this, lstMateri);

        rv.setAdapter(adapter);
        lstMateri.add(new MtrDua("Materi asdasd", "Categories 2", "Description", R.drawable.ic_home_black_24dp));
        lstMateri.add(new MtrDua("Materi sadaqwesa", "Categories 3", "Description", R.drawable.ic_home_black_24dp));
        lstMateri.add(new MtrDua("Materi agcaswq", "Categories 4", "Description", R.drawable.ic_home_black_24dp));
        lstMateri.add(new MtrDua("Materi awgsv", "Categories 4", "Description", R.drawable.ic_home_black_24dp));
        lstMateri.add(new MtrDua("Main Activity", "Categories 1", "Description", R.drawable.ic_home_black_24dp));
    }
}