package com.educar.textinputlayout;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private TextInputLayout tilNombre;
    private Button aceptar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tilNombre=(TextInputLayout)findViewById(R.id.til_nombre);
        aceptar=(Button)findViewById(R.id.bt_aceptar);

        Toolbar toolbar =(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Se encarga del efecto de desplazamiento entre las tabs
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new MiFragmentPageAdapter(
                getSupportFragmentManager()));

        /* Layout  al que asociamos el viewPager, y que hara que aparezcan
           correctamente el contenido de cady titulo correspondiente */
        TabLayout tabLayout = (TabLayout) findViewById(R.id.appbartabs);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        //tabLayout.setTabMode(TabLayout.GRAVITY_CENTER);
        tabLayout.setupWithViewPager(viewPager);
        TabLayout.Tab tab = tabLayout.getTabAt(0);
       // tab.setIcon(R.drawable.selector_new); //añadir un icono


        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarNombre(tilNombre.getEditText().getText().toString());
                Intent intent = new Intent(MainActivity.this,MapActivity.class);
                startActivity(intent);
            }
        });

    }


    private boolean validarNombre(String nombre)
    {
        //expresion regular solo letras y espacios
        Pattern pattern = Pattern.compile("^[a-zA-Z ]+$");
        //expresion regular comprueba que el campo tiene algo
        Pattern pa = Pattern.compile("^[^ ]+$");
        if(!pa.matcher(nombre).matches())
        {
            //tilNombre.setError("Nombre invalido");
            tilNombre.setError("Rellene campo");
            return false;
        }
        else
        {
            tilNombre.setError(null);
        }
        return true;
    }
}
