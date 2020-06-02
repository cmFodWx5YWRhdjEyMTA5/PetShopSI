package br.com.petshopsi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import br.com.petshopsi.classes.SolicitarServico;

public class SolicitarServicoActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener, DialogInterface.OnCancelListener{

    private Spinner spinner;
    private CheckBox checkBoxTransporte;
    private TextView tv_dataSolicitacao;
    private TextView tv_horaSolicitacao;
    private ImageButton ib_DataSolicitacao;
    private ListView listViewServicos;
    private Button btnSolicitarServico;
    private String transporteSelecionado;
    private String ServicoSelecionado;
    private EditText edtValorServico;

    ListView listView;
    FirebaseDatabase database;
    DatabaseReference ref;
    ArrayList<String> list;
    ArrayAdapter<String> adapter;
    Servico servico;
    private SolicitarServico solicitarServico;

    // USANDO REFERENCIA DO FIREBASE
    private DatabaseReference referenciaFirebase;
    // USANDO REFERENCIA DO FirebaseAuth
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitar_servico);

        // cast
        spinner = (Spinner) findViewById(R.id.spinner);
        checkBoxTransporte = (CheckBox) findViewById(R.id.checkTransporte);
        tv_dataSolicitacao = (TextView) findViewById(R.id.tv_dataSolicitacao);
        tv_horaSolicitacao = (TextView) findViewById(R.id.tv_horaSolicitacao);
        ib_DataSolicitacao = (ImageButton) findViewById(R.id.ib_DataSolicitacao);
        btnSolicitarServico = (Button) findViewById(R.id.btnSolicitarServico);
        edtValorServico = (EditText) findViewById(R.id.edtValorServico);




        listView = (ListView) findViewById(R.id.listView);

        database = FirebaseDatabase.getInstance();
        ref = database.getReference("Usuario");


        list = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this, R.layout.servico_info,R.id.servicoInfo, list);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Pega todos os filhos que estão dentro do nó pai Serviço
                for(DataSnapshot data: dataSnapshot.getChildren()){

                    servico = data.getValue(Servico.class);
                    //servico.toString();
                    // servico = ds.getValue(Servico.class);
                    list.add(servico.getNome().toString());
                }

                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //Pega o servico selecionado no ListView e salva em uma variável
/*        listViewServicos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ServicoSelecionado = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/


        List<String> list = new ArrayList<String>();
        list.add("");
        list.add("Busca");
        list.add("Entrega");
        list.add("Busca e Entrega");


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        //Pega o item selecionado no spinner e salva em uma variável

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                transporteSelecionado = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //spinner ser apresentado desabilitado ao carregar a tela
        spinner.setEnabled(false);

        //Validar se o checkbox de transporte esta selecionado
        checkBoxTransporte.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (b) {
                    spinner.setEnabled(true);
                    //Toast.makeText(SolicitarServicoActivity.this, "Marcado", Toast.LENGTH_SHORT).show();
                } else {
                    spinner.setEnabled(false);
                    //Toast.makeText(SolicitarServicoActivity.this, "Não marcado", Toast.LENGTH_SHORT).show();
                }
            }
        });



        //ImageButtom para selecionar a data
        ib_DataSolicitacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chamarCalendario(v);
            }
        });

        //Enviar dados para o banco no momento que for clicado no botão Solicitar
        btnSolicitarServico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    String valorServico = edtValorServico.getText().toString();
                    String dataServico = tv_dataSolicitacao.getText().toString();
                    String horaServico = tv_horaSolicitacao.getText().toString();

                    solicitarServico = new SolicitarServico();
                    solicitarServico.setServicoSelecionado(ServicoSelecionado);
                    solicitarServico.setTransporteSelecionado(transporteSelecionado);
                    solicitarServico.setDataServico(dataServico);
                    solicitarServico.setHoraServico(horaServico);
                    solicitarServico.setValorServico(valorServico);
                    solicitarServico.setId(UUID.randomUUID().toString());
                    cadastrarSolicitacaoServico();


                }catch (Exception ex){
                    Toast.makeText(SolicitarServicoActivity.this, "Ocorreu um erro, tente novamente mais tarde!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    // Início de metódos para chamar o calendário

    private int year, month, day, hour, minute;
    public void chamarCalendario(View v){
        initDateTimeData();
        Calendar cDefault = Calendar.getInstance();
        cDefault.set(year, month, day);

        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
                this,
                cDefault.get(Calendar.YEAR),
                cDefault.get(Calendar.MONTH),
                cDefault.get(Calendar.DAY_OF_MONTH)
        );

        Calendar cMin = Calendar.getInstance();
        Calendar cMax = Calendar.getInstance();
        cMax.set( cMax.get(Calendar.YEAR), 11, 31 );
        datePickerDialog.setMinDate(cMin);
        datePickerDialog.setMaxDate(cMax);

        List<Calendar> daysList = new LinkedList<>();
        Calendar[] daysArray;
        Calendar cAux = Calendar.getInstance();

        while( cAux.getTimeInMillis() <= cMax.getTimeInMillis() ){
            if( cAux.get( Calendar.DAY_OF_WEEK ) != 1 && cAux.get( Calendar.DAY_OF_WEEK ) != 7 ){
                Calendar c = Calendar.getInstance();
                c.setTimeInMillis( cAux.getTimeInMillis() );

                daysList.add( c );
            }
            cAux.setTimeInMillis( cAux.getTimeInMillis() + ( 24 * 60 * 60 * 1000 ) );
        }
        daysArray = new Calendar[ daysList.size() ];
        for( int i = 0; i < daysArray.length; i++ ){
            daysArray[i] = daysList.get(i);
        }

        datePickerDialog.setSelectableDays( daysArray );
        datePickerDialog.setOnCancelListener(this);
        datePickerDialog.show( getFragmentManager(), "DatePickerDialog" );

    }

    private void initDateTimeData(){
        if( year == 0 ){
            Calendar c = Calendar.getInstance();
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);
            hour = c.get(Calendar.HOUR_OF_DAY);
            minute = c.get(Calendar.MINUTE);
        }
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        year = month = day = hour = minute = 0;
        tv_dataSolicitacao.setText("");
        tv_horaSolicitacao.setText("");

    }

    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int i, int i1, int i2) {

        Calendar tDefault = Calendar.getInstance();
        tDefault.set(year, month, day, hour, minute);

        year = i;
        month = i1;
        day = i2;

        TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(
                this,
                tDefault.get(Calendar.HOUR_OF_DAY),
                tDefault.get(Calendar.MINUTE),
                true
        );
        timePickerDialog.setOnCancelListener(this);
        timePickerDialog.show(getFragmentManager(), "timePickerDialog");
        timePickerDialog.setTitle("Horário Test Drive");

        timePickerDialog.setThemeDark(true);
    }

    @Override
    public void onTimeSet(RadialPickerLayout radialPickerLayout, int i, int i1) {
        if( i < 9 || i > 18 ){
            onDateSet(null, year, month, day);
            Toast.makeText(this, "Somente entre 9h e 18h", Toast.LENGTH_SHORT).show();
            return;
        }

        hour = i;
        minute = i1;

        tv_dataSolicitacao.setText( (day < 10 ? "0"+day : day)+"/"+
                (month+1 < 10 ? "0"+(month+1) : month+1)+"/"+
                year);

        tv_horaSolicitacao.setText((hour < 10 ? "0"+hour : hour)+"h"+
                (minute < 10 ? "0"+minute : minute));
    }
    //metódo para enviar a campanha para o firebase

    private void cadastrarSolicitacaoServico() {

        solicitarServico.salvar();
        feedbackCadastroSucesso();

    }

    public void feedbackCadastroSucesso(){
        AlertDialog.Builder builderFeedbackSucesso = new AlertDialog.Builder(SolicitarServicoActivity.this);
        builderFeedbackSucesso.setMessage("Cadastro de campanha realizado com sucesso!");
        builderFeedbackSucesso.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // FINALIZA ESSA ACTIVITY
                finish();

            }
        });
        builderFeedbackSucesso.setCancelable(false);
        AlertDialog alertFeedbackSucesso = builderFeedbackSucesso.create();
        alertFeedbackSucesso.show();
    }

}
