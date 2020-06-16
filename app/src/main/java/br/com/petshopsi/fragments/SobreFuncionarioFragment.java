package br.com.petshopsi.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import br.com.petshopsi.AnimaisCadastradosActivity;
import br.com.petshopsi.LoginActivity;
import br.com.petshopsi.R;
import br.com.petshopsi.classes.ConfiguracaoFirebase;

/**
 * A simple {@link Fragment} subclass.
 */
public class SobreFuncionarioFragment extends Fragment {

    // USANDO REFERENCIA DO FirebaseAuth
    private FirebaseAuth autenticacao;
    private DatabaseReference referenciaFirebase;


    public SobreFuncionarioFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sobre_funcionario, container, false);

        MaterialButton btnSair = (MaterialButton)view.findViewById(R.id.btnSair);

        final TextView txtNome = (TextView)view.findViewById(R.id.txtNome);
        final TextView txtPerfil = (TextView)view.findViewById(R.id.txtPerfil);

        btnSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doLogout();
            }
        });



        return view;
    }

    public void doLogout(){
        referenciaFirebase = ConfiguracaoFirebase.getFirebase();
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.signOut();
        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivity(intent);
        // Encerra Fragment
        getActivity().finish();
    }
}
