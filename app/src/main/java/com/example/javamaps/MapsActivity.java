package com.example.javamaps;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.javamaps.databinding.ActivityMapsBinding;
import com.google.android.material.snackbar.Snackbar;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    ActivityResultLauncher<String> permissionLauncher; //(izin başlatıcısı)
    LocationManager locationManager; //Konum yöneticisi
    LocationListener locationListener; //Konum dinleyicisi

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        registerLauncher(); //
    }

    @Override
    public void onMapReady(GoogleMap googleMap)  {
        mMap = googleMap;

        // Telefonun konumunu anlayabilmek için bulabilmek için:
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {                                          //Konum dinleyicisi
            @Override
            public void onLocationChanged(@NonNull Location location) {                                         // Konum değişince çalışacak metot
                System.out.println("Location: " + location.toString());                                         //telefonun mevcut konumu logchatte yazdırılacaktır.
            }
            //eğer çalışmayan bir sürüm olursa hata alırsak boş bir şekilde onStatusChanged metotdunu çağırabiliriz.
        };


        if (ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            //Request Permission
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_FINE_LOCATION)){
                Snackbar.make(binding.getRoot(),"Permission needed for maps", Snackbar.LENGTH_INDEFINITE).setAction("Give Permission", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //request permission
                        permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
                    }
                }).show();
            } else {
                //request permission
                permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
            }
        }else {
            // Aşağıdaki kod satırında Konum değişikliklerini isteyebiliriz. Son güncellemeleri alabiliriz ama izin gereklidir.
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener); //Bu kodlar izin istenmeden çalışmaz. (0,0 normalde çok fazla enerji tüketir çünkü 0 saniye ve 0 yer değişikliğinde konumu güncelleme komutudur.)
        }





        // lat--> latitue  (enlem)
        // lon--> longitue (boylam)

        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-34, 151); //konum /başlangıçta gösterilen yerin enlem ve boylamı
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney")); // kırmızı işaretleme çubuğu ve başlığı
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        //Örnek oalrak kendi kodlarımızı yazalım: Açılış konumunu eifell tower olarak belirtelim:
        LatLng eiffel = new LatLng(48.858595232626534, 2.2947063683711746);
        mMap.addMarker(new MarkerOptions().position(eiffel).title("Eiffel Tower"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(eiffel,15));
    }

    public void registerLauncher(){
        permissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
            @Override
            public void onActivityResult(Boolean result) {
                if(result){
                    //Permission granted (izin verildi)
                    //(locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);) bu kod hata veriyor olabilir.Bunu garanti hale getirmek için if içieisinde bir kontrol daha yapılır.
                    if(ContextCompat.checkSelfPermission(MapsActivity.this,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){ //izin verildiyse : konumu aşağıdaki satırda iste.
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener); //Bu kodlar izin istenmeden çalışmaz. (0,0 normalde çok fazla enerji tüketir çünkü 0 saniye ve 0 yer değişikliğinde konumu güncelleme komutudur.)
                    }
                }else {//Perrmission Denied (İzin reddedildi)
                    Toast.makeText(MapsActivity.this, "İzin lazım", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}