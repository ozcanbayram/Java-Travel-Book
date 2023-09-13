package com.example.javamaps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) { //menüyü ekleme metodu
        MenuInflater menuInflater = getMenuInflater(); //Menu bağlandı inflater kodunu layout ile aktiviteyi birbirine bağlayınca kullanırız.
        menuInflater.inflate(R.menu.travel_menu,menu); //menu yeri belirtildi ve bağlandı

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) { // menüden bir şey seçilirse ne olacak.
        if(item.getItemId() == R.id.add_place){ //add_place' ye mi tıklandı kontrolü.
            Intent intent = new Intent(MainActivity.this,MapsActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}