package br.com.petshopsi.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import br.com.petshopsi.CadastrarServicoActivity;
import br.com.petshopsi.R;
import br.com.petshopsi.SolicitarServicoActivity;
import br.com.petshopsi.classes.CadastrarServico;

public class ServicosFuncionarioFragment extends Fragment {

    Button btn_cadastrar_servico;



    public ServicosFuncionarioFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_servicos_funcionario, container, false);

        // CAST
        btn_cadastrar_servico = (Button) view.findViewById(R.id.btn_cadastrar_servico);

        btn_cadastrar_servico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CadastrarServicoActivity.class);
                startActivity(intent);
            }
        });



        return view;
    }




}
