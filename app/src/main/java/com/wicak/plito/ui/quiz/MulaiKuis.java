package com.wicak.plito.ui.quiz;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.wicak.plito.BottomActivity;
import com.wicak.plito.R;

public class MulaiKuis extends AppCompatActivity {

    TextView pertanyaan, judul;
    RadioGroup rg;
    RadioButton PilA, PilB, PilC, PilD;
    ImageView imgKuis;
    ImageButton nxt;
    int nomor = 0;

    private long backPressedTime;

    public static int hasil, benar, salah;

    String[] nomor_urut = new String[]{
            "1", "2","3","4","5","6","7","8","9","10"

    };
    String[] pertanyaan_kuis = new String[]{
            "Yang tidak termasuk dalam komponen dasar dalam sebuah ER-Diagram adalah ",
            "Atribut adalah ",
            "Dalam ER-Diagram, Atribut yang didapatkan dari pengolahan nilai atau atribut lain disebut ",
            "Atribut key dapat dibedakan menjadi tiga, yaitu",
            "Primary key dalam tabel berikut adalah",
            "Relasi adalah",
            "Gambar berikut merupakan derajat relasi ",
            "Gambar dibawah ini merupakan contoh dari relasi",
            "Dalam kegiatan pembelajaran disekolah, bagaimanakah bentuk kardinalitas dari relasi antara entitas Siswa dengan entitas Mapel",
            "Setiap anggota entitas X dapat berhubungan dengan lebih dari satu anggota entitas Y namun tidak sebaliknya merupakan definisi dari"

    };

    String[] pilihan_jawaban = new String[]{
            "Atribut","Entitas","File","Relasi",
            "Object yang mewakili sesuatu yang nyata","Batasan kombinasi suatu entitas","Hubungan antara dua atau beberapa entitas","Karakteristik khusus yang dimiliki suatu entitas",
            "Single Valued Attribute","Derived Attribute","Mandatory Attribute","Composite Attribute",
            "Primary key, Super key, Candidate key","Foreign key, Primary key, Super key","Super key, Candidate key, Foreign key","Primary key, Candidate key, Foreign key",
            "NIS","Nama","Kelas","Alamat",
            "Object yang mewakili sesuatu yang nyata","Batasan kombinasi suatu entitas","Hubungan antara dua atau beberapa entitas","Karakteristik khusus yang dimiliki suatu entitas",
            "Unary Degree","Binary Degree","Ternary Degree","Multi Degree",
            "Unary degree dengan kardinalitas one to many","Binary degree dengan kardinalitas many to many","Ternary degree dengan kardinalitas one to many","Ternary degree dengan kardinalitas many to many",
            "One to One","One to Many","Many to One","Many to Many",
            "One to One","One to Many","Many to One","Many to Many"
    };

    String[] jawaban_benar = new String[]{
            "File",
            "Karakteristik khusus yang dimiliki suatu entitas",
            "Derived Attribute",
            "Primary key, Super key, Candidate key",
            "NIS",
            "Hubungan antara dua atau beberapa entitas",
            "Binary Degree",
            "Atribut",
            "Many to Many",
            "One to Many"
    };

    private static final Integer[] img ={
            R.drawable.rb_a_checked,
            R.drawable.rb_a_checked,
            R.drawable.rb_a_checked,
            R.drawable.rb_a_checked,
            R.drawable.rb_a_checked,
            R.drawable.rb_a_checked,
            R.drawable.rb_a_checked,
            R.drawable.rb_a_checked,
            R.drawable.rb_a_checked,
            R.drawable.rb_a_checked
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mulai_kuis);
        judul = findViewById(R.id.jud);
        pertanyaan = findViewById(R.id.pertanyaan);
        nxt = findViewById(R.id.nxtbutton);
        nxt.setVisibility(View.GONE);
        rg = findViewById(R.id.radio_group);
        PilA = findViewById(R.id.pilihanA);
        PilB = findViewById(R.id.pilihanB);
        PilC = findViewById(R.id.pilihanC);
        PilD = findViewById(R.id.pilihanD);
        imgKuis = findViewById(R.id.imageView2);

        judul.setText(nomor_urut[nomor]+"/10");
        pertanyaan.setText(pertanyaan_kuis[nomor]);
        PilA.setText(pilihan_jawaban[0]);
        PilB.setText(pilihan_jawaban[1]);
        PilC.setText(pilihan_jawaban[2]);
        PilD.setText(pilihan_jawaban[3]);


        rg.check(0);
        benar = 0;
        salah = 0;
        nxt.setVisibility(View.GONE);
        nxt.animate().translationY(nxt.getHeight());



    }

    @SuppressLint("NonConstantResourceId")
    public void onRBClicked (View view){
        boolean isSelected = ((RadioButton)view).isChecked();
        switch (view.getId()){
            case R.id.pilihanA:
            case R.id.pilihanD:
            case R.id.pilihanB:
            case R.id.pilihanC:
                if (isSelected) {
                    nxt.setVisibility(View.VISIBLE);
                    nxt.animate().translationY(0);
                }else{
                    nxt.setVisibility(View.GONE);
                    nxt.animate().translationY(nxt.getHeight());
                }
                break;
        }
    }
    public void onBackPressed() {
        if(backPressedTime +2000 > System.currentTimeMillis()){
            finish();
            Intent backhome  = new Intent(getApplicationContext(), BottomActivity.class);
            startActivity(backhome);
        }else {
            Toast backToast = Toast.makeText(getBaseContext(), "Press back again to Main Menu", Toast.LENGTH_SHORT);
            backToast.show();
        }
        backPressedTime =System.currentTimeMillis();

    }

    @SuppressLint("SetTextI18n")
    public void next(View view){

        if (PilA.isChecked() || PilB.isChecked() || PilC.isChecked() || PilD.isChecked()) {
            RadioButton jawaban_user = findViewById(rg.getCheckedRadioButtonId());
            String ambil_jawaban_user = jawaban_user.getText().toString();
            rg.check(0);
            nxt.setVisibility(View.GONE);
            nxt.animate().translationY(nxt.getHeight());
            if (ambil_jawaban_user.equalsIgnoreCase(jawaban_benar[nomor]))
            {
                AlertDialog.Builder dial = new AlertDialog.Builder(this);
                dial.setMessage("Jawaban Kamu Benar")
                        .setPositiveButton("Next", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                benar++;
                            }
                        });
                dial.create();
                dial.show();

            }
            else{
                AlertDialog.Builder dial = new AlertDialog.Builder(this);
                dial.setMessage("Jawaban Kamu Salah")
                        .setPositiveButton("Next", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                salah++;
                            }
                        });
                dial.create();
                dial.show();

            }
            nomor++;

            if (nomor < pertanyaan_kuis.length) {
                judul.setText(nomor_urut[nomor] +"/10" );
                pertanyaan.setText(pertanyaan_kuis[nomor]);
                imgKuis.setImageResource(img[nomor]);
                PilA.setText(pilihan_jawaban[nomor * 4]);
                PilB.setText(pilihan_jawaban[(nomor * 4) + 1]);
                PilC.setText(pilihan_jawaban[(nomor * 4) + 2]);
                PilD.setText(pilihan_jawaban[(nomor * 4) + 3]);
            } else {
                hasil = benar * 10;
                Intent selesai = new Intent(getApplicationContext(), HasilKuis.class);
                startActivity(selesai);
            }
        }
        else {
            Toast.makeText(this, "Pilih Terlebih dahulu", Toast.LENGTH_SHORT).show();
        }
    }

}