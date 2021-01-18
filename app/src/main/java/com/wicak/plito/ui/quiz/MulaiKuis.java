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
            "1","2","3","4","5","6","7","8","9","10"

    };
    String[] pertanyaan_kuis = new String[]{
            "Hand Tools adalah ",
            "Kunci yang penggunannya tidak boleh untuk mengendorkan atau merapatkan baut/mur adalah ",
            "Saat pelepasan baut selang rem kunci yang digunakan adalah",
            "Fungsi dari gambar alat dibawah ini adalah",
            "Alat yang digunakan untuk mengukur celah suatu benda kerja adalah",
            "Fungsi alat pada gambar dibawah ini adalah",
            "Tang yang berfungsi untuk melepas pengunci dalam piston adalah",
            "Saat proses membuka sekrup yang memiliki kekencangan lebih sebaiknya menggunakan",
            "Tang pada gambar digunakan saat",
            "Saat proses pembukaan baut tidak ada kunci dengan ukuran yang sesuai, maka alat yang dapat digunakan adalah"

    };

    String[] pilihan_jawaban = new String[]{
            "Alat yang penggunaanya membutuhkan bantuan alat tenaga","Alat yang bisa dipakai mekanik","Alat yang penggunaanya menggunakan tangan dari tenaga manusia","Alat yang bida dipegang oleh tangan",
            "Kunci Pas","Kunci Allen","Kunci Ring","Kunci Nipel",
            "Kunci Ring","Kunci socket","Kunci Inggris","Kunci Nipel",
            "Mengukur celah benda","Mengukur tinggi baut","Mengidentifikasi jenis ulir","Mengidentifikasi lebar baut",
            "Thread Gauge","Feeler Gauge","Allen Key","Pin Punch",
            "Melepas pin penahan komponen","Pembuka awal pin penahan komponen","Penanda pada komponen","Pengunci pin penahan komponen",
            "Slip Joint Pliers","Internal Circlip Pliers","External Circlip Pliers","Vice Grip Pliers",
            "Philips Screwdriver","Slotte Screwdriver","Combination Screwdriver","Impact Screwdriver",
            "Saat pekerjaan di ruangan sempit","Pekerjaan memotong kabel/kawat","Mencengkeram plat","Membentuk batang besi",
            "Interlocking Joint Plier","Combination Plier","Shifing Spanner","Flare Nut Spanner"
    };

    String[] jawaban_benar = new String[]{
            "Alat yang penggunaanya menggunakan tangan dari tenaga manusia",
            "Kunci Pas",
            "Kunci Nipel",
            "Mengidentifikasi jenis ulir",
            "Feeler Gauge",
            "Melepas pin penahan komponen",
            "Internal Circlip Pliers",
            "Impact Screwdriver",
            "Saat pekerjaan di ruangan sempit",
            "Shifing Spanner"
    };

    private static final Integer[] img ={
            R.drawable.rb_a_checked,
            R.drawable.rb_a_checked,
            R.drawable.rb_a_checked,
            R.drawable.addthread,
            R.drawable.rb_a_checked,
            R.drawable.addpinpunch,
            R.drawable.rb_a_checked,
            R.drawable.rb_a_checked,
            R.drawable.tangcucut,
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
                benar++;
                AlertDialog.Builder dial = new AlertDialog.Builder(this);
                dial.setMessage("Jawaban Kamu Benar")
                        .setPositiveButton("Next", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                dial.create();
                dial.show();

            }
            else{
                salah++;
                AlertDialog.Builder dial = new AlertDialog.Builder(this);
                dial.setMessage("Jawaban Kamu Salah")
                        .setPositiveButton("Next", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

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