package br.com.petshopsi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import br.com.petshopsi.fragments.HomeFragment;
import br.com.petshopsi.fragments.ServicosFragment;
import br.com.petshopsi.fragments.SobreFragment;

public class HomeClienteNavActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    BottomNavigationView bottom_navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_cliente_nav);

        // CASTs
        bottom_navigationView = (BottomNavigationView)findViewById(R.id.bottom_navigationView);

        // Definir item padrão selecionado
        bottom_navigationView.setOnNavigationItemSelectedListener(this);
        bottom_navigationView.setSelectedItemId(R.id.navigation_home);

    }

    // CRIANDO OBJETOS DOS JAVAs FRAGMENTS
    HomeFragment homeFragment = new HomeFragment();
    ServicosFragment servicosFragment = new ServicosFragment();
    SobreFragment sobreFragment = new SobreFragment();

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){

            case R.id.navigation_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.id_frameLayout, homeFragment).commit();
                return true;

            case R.id.navigation_servicos:
                getSupportFragmentManager().beginTransaction().replace(R.id.id_frameLayout, servicosFragment).commit();
                return true;

            case R.id.navigation_sobre:
                getSupportFragmentManager().beginTransaction().replace(R.id.id_frameLayout, sobreFragment).commit();
                return true;
        }
        return false;
    }
}
