package com.wicak.plito.ui.materi;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wicak.plito.R;

import java.util.ArrayList;
import java.util.List;

public class MateriFragment extends Fragment {

    private MateriViewModel materiViewModel;
    private RecyclerView rv;
    private MateriAdapter adapter;
    private List<Materi> lstMateri;

    public MateriFragment newInstance(){
        return new MateriFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState){super.onCreate(savedInstanceState);}

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        View root = inflater.inflate(R.layout.fragment_materi, container, false);

        lstMateri = new ArrayList<>();
        adapter = new MateriAdapter(this, lstMateri);
        rv = (RecyclerView) root.findViewById(R.id.rv_idmateri);
//        rv.setHasFixedSize(false);
        rv.setLayoutManager(new GridLayoutManager(getContext(),2));

        MateriAdapter adapter = new MateriAdapter(this, lstMateri);

        rv.setAdapter(adapter);
        lstMateri.add(new Materi("Materi Satu", "Categories 2", "Description", R.drawable.ic_notifications_black_24dp));
        lstMateri.add(new Materi("Materi Dua", "Categories 3", "Description", R.drawable.ic_dashboard_black_24dp));
        lstMateri.add(new Materi("Materi Tiga", "Categories 4", "Description", R.drawable.mood_bad));
        lstMateri.add(new Materi("Materi Empat", "Categories 4", "Description", R.drawable.mood_up));
        lstMateri.add(new Materi("Main Activity", "Categories 1", "Description", R.drawable.ic_home_black_24dp));


        return root;

    }
}
