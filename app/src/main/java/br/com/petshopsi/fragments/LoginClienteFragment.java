package br.com.petshopsi.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import br.com.petshopsi.CadastraFuncionario;
import br.com.petshopsi.CadastrarClienteActivity;
import br.com.petshopsi.CarregarPerfilClienteActivity;
import br.com.petshopsi.LoginClienteActivity;
import br.com.petshopsi.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginClienteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginClienteFragment extends Fragment {

    ProgressBar progressBar;
    // AUTENTICACAO
    private FirebaseAuth mAuth;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    //Pegar o usuario corrente
    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
    //Referência do banco
    private DatabaseReference databaseRef;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LoginClienteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginClienteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginClienteFragment newInstance(String param1, String param2) {
        LoginClienteFragment fragment = new LoginClienteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login_cliente, container, false);



         /*// VERIFICA USUARIO LOGADO
        verificarUsuarioLogado();*/

        final EditText mEmail  = (EditText)view.findViewById(R.id.Email);
        final EditText mPassword = (EditText)view.findViewById(R.id.password);
        progressBar = (ProgressBar)view.findViewById(R.id.progressBar);
        Button mLoginBtn = (Button)view.findViewById(R.id.loginBtn);
        TextView mCreateBtn = (TextView)view.findViewById(R.id.createText);
        TextView forgotTextLink = (TextView)view.findViewById(R.id.forgotPassword);
        TextView criarConta = (TextView)view.findViewById(R.id.criarConta);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                    String email = mEmail.getText().toString().trim();
                    String password = mPassword.getText().toString().trim();

                    if(TextUtils.isEmpty(email)){
                        mEmail.setError("Email is Required.");
                        return;
                    }

                    if(TextUtils.isEmpty(password)){
                        mPassword.setError("Password is Required.");
                        return;
                    }

                    if(password.length() < 6){
                        mPassword.setError("Password Must be >= 6 Characters");
                        return;
                    }

                    progressBar.setVisibility(View.VISIBLE);

                    // authenticate the user
                    EfetuarLogin(mEmail.getText().toString(), mPassword.getText().toString());
                }catch (Exception ex){
                    Toast.makeText(getActivity(), "Ocorreu um erro ao efetuar o login. Tente novamente mais tarde.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        forgotTextLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final EditText resetMail = new EditText(v.getContext());
                final AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
                passwordResetDialog.setTitle("Reset Password ?");
                passwordResetDialog.setMessage("Enter Your Email To Received Reset Link.");
                passwordResetDialog.setView(resetMail);

                passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // extract the email and send reset link
                        String mail = resetMail.getText().toString();
                        mAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getActivity(), "Reset Link Sent To Your Email.", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getActivity(), "Error ! Reset Link is Not Sent" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });

                passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // close the dialog
                    }
                });

                passwordResetDialog.create().show();

            }
        });

        criarConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(LoginActivity.this, CadastrarClienteActivity.class);
                // TESTE CRIAR SERVICO
                Intent intent = new Intent(getActivity(), CadastrarClienteActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        return view;
    }

    // METODO onStart
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            UsuarioLogado();
            //mAuth.signOut();
        }
    }

    public void UsuarioLogado() {
        Intent intentCarregarCliente = new Intent(getContext(), CarregarPerfilClienteActivity.class);
        startActivity(intentCarregarCliente);
        getActivity().finish();
    }


    public void EfetuarLogin(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){

                            UsuarioLogado();

                        }else {
                            Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }
}