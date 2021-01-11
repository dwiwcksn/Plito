package com.wicak.plito.ui.materi.kunci;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.wicak.plito.R;
import com.wicak.plito.ui.materi.kunci.video.VideoKunciKombi;
import com.wicak.plito.ui.materi.kunci.video.VideoKunciPas;
import com.wicak.plito.ui.materi.kunci.video.VideoKunciRing;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class Pas extends Fragment {
    public Pas() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View root = inflater.inflate(R.layout.fragment_pas, container, false);

        ImageButton button = root.findViewById(R.id.button_vid);
        ImageButton button_ring = root.findViewById(R.id.button_vid_ring);
        ImageButton button_nipel = root.findViewById(R.id.button_vid_nipel);

        button_nipel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent selesai = new Intent(getActivity(), VideoKunciKombi.class);
                startActivity(selesai);
            }
        });

        button_ring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent selesai = new Intent(getActivity(), VideoKunciRing.class);
                startActivity(selesai);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pas.this.video();
            }
        });
        
        return root;
    }

    public void video() {
        Intent selesai = new Intent(getActivity(), VideoKunciPas.class);
        startActivity(selesai);
    }


}