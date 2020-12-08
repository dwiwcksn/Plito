package com.wicak.plito.ui.quiz;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wicak.plito.BottomActivity;
import com.wicak.plito.R;

public class HasilKuis extends AppCompatActivity {
    private long backPressedTime;
    private Toast backToast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasil_kuis);
        TextView hasil = findViewById(R.id.hasil);
        TextView nilai = findViewById(R.id.nilai);
        ImageView image = findViewById(R.id.imageView5);


        hasil.setText("Jawaban Benar : "+ MulaiKuis.benar+"\n Jawaban Salah : " +MulaiKuis.salah);
        nilai.setText(""+MulaiKuis.hasil);
        if (MulaiKuis.hasil >= 70){
            image.setImageResource(R.drawable.mood_up);
        }else {
            image.setImageResource(R.drawable.mood_bad);
        }
    }

    public void onBackPressed() {
        if(backPressedTime +2000 > System.currentTimeMillis()){
            finish();
            Intent backhome  = new Intent(getApplicationContext(), BottomActivity.class);
            startActivity(backhome);
        }else {
            backToast = Toast.makeText(getBaseContext(),"Press back again to Main Menu", Toast.LENGTH_SHORT);
            backToast.show();
        }
        backPressedTime =System.currentTimeMillis();

    }

    public void ulangi(View view){

        AlertDialog.Builder dial = new AlertDialog.Builder(this);
//            dial.setTitle("Yakin?");
        dial.setMessage("Ulangi Kuis?")
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        HasilKuis.this.finish();
                        Intent i = new Intent(HasilKuis.this.getApplicationContext(), MulaiKuis.class);
                        HasilKuis.this.startActivity(i);
                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        dial.create();
        dial.show();
    }
    public void kuis(View view){
        finish();
        Intent i = new Intent(getApplicationContext(), BottomActivity.class);
        startActivity(i);

    }
}


