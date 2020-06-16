package br.com.petshopsi;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import br.com.petshopsi.fragments.HomeClienteFragment;
import br.com.petshopsi.fragments.HomeFuncionarioFragment;
import br.com.petshopsi.fragments.PetsFuncionarioFragment;
import br.com.petshopsi.fragments.ServicosClienteFragment;
import br.com.petshopsi.fragments.ServicosFuncionarioFragment;
import br.com.petshopsi.fragments.SobreClienteFragment;
import br.com.petshopsi.fragments.SobreFuncionarioFragment;

public class HomeFuncionarioNavActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    BottomNavigationView bottom_navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_funcionario_nav);

        // CASTs
        bottom_navigationView = (BottomNavigationView)findViewById(R.id.bottom_navigationView);

        // Definir item padrão selecionado
        bottom_navigationView.setOnNavigationItemSelectedListener(this);
        bottom_navigationView.setSelectedItemId(R.id.navigation_home_funcionario);

    }

    // CRIANDO OBJETOS DOS JAVAs FRAGMENTS
    HomeFuncionarioFragment homeFuncionarioFragment = new HomeFuncionarioFragment();
    ServicosFuncionarioFragment servicosFuncionarioFragment = new ServicosFuncionarioFragment();
    PetsFuncionarioFragment petsFuncionarioFragment = new PetsFuncionarioFragment();
    SobreFuncionarioFragment sobreFuncionarioFragment = new SobreFuncionarioFragment();

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){

            case R.id.navigation_home_funcionario:
                getSupportFragmentManager().beginTransaction().replace(R.id.id_frameLayout, homeFuncionarioFragment).commit();
                return true;

            case R.id.navigation_servicos_funcionario:
                getSupportFragmentManager().beginTransaction().replace(R.id.id_frameLayout, servicosFuncionarioFragment).commit();
                return true;

            case R.id.navigation_pets_funcionario:
                getSupportFragmentManager().beginTransaction().replace(R.id.id_frameLayout, petsFuncionarioFragment).commit();
                return true;

            case R.id.navigation_sobre_funcionario:
                getSupportFragmentManager().beginTransaction().replace(R.id.id_frameLayout, sobreFuncionarioFragment).commit();
                return true;
        }
        return false;
    }
}
