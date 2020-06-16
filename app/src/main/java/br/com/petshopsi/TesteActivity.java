package br.com.petshopsi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class TesteActivity extends AppCompatActivity {

    EditText edtNome, edtTel;
    Button btnEnviar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teste);
        setTitle("Teste");
        edtNome = (EditText)findViewById(R.id.edtNome);
        edtTel = (EditText)findViewById(R.id.edtTel);
        btnEnviar = (Button) findViewById(R.id.btnEnviar);

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = edtNome.getText().toString().trim();
                String telefone = edtTel.getText().toString().trim();
                //String msgConfirmacao = "Envio realizado com sucesso!";
                enviaSMS("+" + telefone, nome);
            }
        });

    }

    // Envio SMS
    private boolean enviaSMS(String telefone, String nome){
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(telefone, null, nome, null, null);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
