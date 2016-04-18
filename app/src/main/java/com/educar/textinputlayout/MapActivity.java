package com.educar.textinputlayout;

import android.Manifest;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends FragmentActivity implements GoogleMap.OnMapClickListener,OnMapReadyCallback {

    private SupportMapFragment map;

    private static final int PERMISO_ACCESS_FINE_LOCATION = 0;
    private LocationManager locManager;
    private LocationListener locListener;
    private double latitud;
    private double longitud;
    private GoogleMap mapa;
    private boolean modoAutomatico;
    private FloatingActionButton fabAuto;
    private FloatingActionButton fabManual;
    private AlertDialog  alert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        map = (SupportMapFragment)getSupportFragmentManager()
                .findFragmentById(R.id.map);
        fabAuto=(FloatingActionButton)findViewById(R.id.fab_auto);
        fabManual=(FloatingActionButton)findViewById(R.id.fab_manual);
        map.getMapAsync(this);

        fabManual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Se presionó el FAB", Snackbar.LENGTH_LONG).show();
            }

        });




    }

    @Override
    public void onMapClick(LatLng latLng) {
        mapa.addMarker(new MarkerOptions().position(latLng).
                icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
        mapa.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        Log.d("TAGLONGITUD", String.valueOf(latLng.longitude));
        Log.d("TAGLATITUD", String.valueOf(latLng.latitude));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapa = googleMap;
        mapa.setMyLocationEnabled(true);
       /* mapa.addMarker(new MarkerOptions()
                .position(new LatLng(latitud, longitud))
                .title("Marker"))*/;

           /*Se comprueba la versión del dispositivo en que se lanza la aplicación.Si la versión del dispositivo es mayor o igual a la 6.0 en clave*/
/*
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //Comprobamos si tenemos los permisos de localización exigidos, de no tenerlos se solicitan al usuario
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MapActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISO_ACCESS_FINE_LOCATION);
            } else {
                //En caso de que la version sea mayor o igual a la 6.0 pero si que tengamos listos los permisos, continuamos.
                rastreoGPS();
            }
        } else {
            //Si la version es menor a la 6.0 directamete se continua invocando al metodo rastreoGPS().
            rastreoGPS();
        }*/

        mapa.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mapa.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(latitud, longitud)));
        rastreoGPS();
        Log.d("TAGLONGITUD", String.valueOf(longitud));
        Log.d("TAGLATITUD",String.valueOf(latitud));
        mapa.setOnMapClickListener(this);
    }

    private void rastreoGPS()
    {
        Log.d("TAGRASTREO","EYYYYY");
        //Si la versión del dispositivo es mayor o igual a la 6.0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //Y si no tiene los permisos necesarios
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                //Se solicitan los permisos
                ActivityCompat.requestPermissions(MapActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISO_ACCESS_FINE_LOCATION);
            }
        }


        /*Se asigna a la clase LocationManager el servicio a partir del nombre.*/
        locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


        /*Se declara y asigna a la clase Location la última posición conocida proporcionada por el proveedor.*/
        Location loc = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        // Comprobamos si está disponible el proveedor GPS.
        if (!locManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
        {
            alertaNoGps();
        }
        //Se llama al método que muestra la posición
         mostrarPosicion(loc);
        //Se define la interfaz LocationListener, que deberá implementarse con los siguientes métodos.
        locListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                mostrarPosicion(location);

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }




        };

        /*Por último se llama al método encargado establecer la localización actualizada, recibiendo como parámetros de entrada
         - el nombre del proveedor,
         - el intervalo de tiempo entre cada actualización en milisegundos,
         - distancia en metros entre localizaciones actualizadas,
         - y la variable de tipo LocationListener
        que actualizará la localización en caso de producirse nuevos cambios.*/

        locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locListener);

    }

    private void mostrarPosicion(Location loc)
    {
        if(loc != null)
        {
            latitud = loc.getLatitude();
            longitud = loc.getLongitude();
            Log.d("TAGLONGITUD",String.valueOf(longitud));
            Log.d("TAGLATITUD",String.valueOf(latitud));
            mapa.addMarker(new MarkerOptions()
                    .position(new LatLng(latitud, longitud))
                    .title("Marker"));
            mapa.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(latitud,longitud)));
        }
    }


    /**
     * Método que será llamado cuando el usuario acepte el permiso requeridos.
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            /*Identificador para realizar la tarea localización*/
            case PERMISO_ACCESS_FINE_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Permiso aceptado, se actua en consecuencia.
                    rastreoGPS();
                   // Snackbar.make(vista, "Permiso establecido", Snackbar.LENGTH_LONG).show();
                } else {
                    //Permiso denegado..
                    //Snackbar.make(vista, "Permiso denegado, no se podrá actualizar la posicion del GPS", Snackbar.LENGTH_LONG).show();
                    return;
                }


        }
    }

    @Override
    public void onPause()
    {
        super.onPause();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //Y si no tiene los permisos necesarios
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            else
            {
                locManager.removeUpdates(locListener);
            }
        }
        else
        {
            locManager.removeUpdates(locListener);
        }
    }

   /* @Override
    public void onResume()
    {
        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //Y si no tiene los permisos necesarios
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            else
            {
                locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locListener);
            }
        }
        else
        {
            locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locListener);
        }

    }*/

    public void alertaNoGps()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("El GPS esta desactivado,Deseas activarlo?")
                .setCancelable(false)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        alert = builder.create();
        alert.show();
    }

}
