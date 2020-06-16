package br.com.petshopsi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import br.com.petshopsi.fragments.HomeClienteFragment;
import br.com.petshopsi.fragments.ServicosClienteFragment;
import br.com.petshopsi.fragments.SobreClienteFragment;

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
    HomeClienteFragment homeClienteFragment = new HomeClienteFragment();
    ServicosClienteFragment servicosClienteFragment = new ServicosClienteFragment();
    SobreClienteFragment sobreClienteFragment = new SobreClienteFragment();

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){

            case R.id.navigation_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.id_frameLayout, homeClienteFragment).commit();
                return true;

            case R.id.navigation_servicos:
                getSupportFragmentManager().beginTransaction().replace(R.id.id_frameLayout, servicosClienteFragment).commit();
                return true;

            case R.id.navigation_sobre:
                getSupportFragmentManager().beginTransaction().replace(R.id.id_frameLayout, sobreClienteFragment).commit();
                return true;
        }
        return false;
    }
}
