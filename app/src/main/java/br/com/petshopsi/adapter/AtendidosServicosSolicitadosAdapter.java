package br.com.petshopsi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import br.com.petshopsi.R;
import br.com.petshopsi.classes.ServicosSolicitados;


public class AtendidosServicosSolicitadosAdapter extends ArrayAdapter<ServicosSolicitados> {

    private ArrayList<ServicosSolicitados> servicosSolicitados;
    private Context context;

    public AtendidosServicosSolicitadosAdapter(Context c, ArrayList<ServicosSolicitados> objects) {
        super(c, 0, objects);
        this.servicosSolicitados = objects;
        this.context = c;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = null;

        // Verifica se a lista esta vazia
        if (servicosSolicitados != null) {
            // Inicializar objeto  para montagem da view
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            // Montar view a partir do xml
            view = inflater.inflate(R.layout.itens_servicos_solicitados, parent, false);

            // Recupera elemento para exibição
            //TextView nomeCampanha = (TextView) view.findViewById(R.id.tv_nome);
            TextView tv_nome_servico_servicos_solicitados = (TextView) view.findViewById(R.id.tv_nome_servico_servicos_solicitados);
            TextView tv_nome_animal_servicos_solicitados = (TextView) view.findViewById(R.id.tv_nome_animal_servicos_solicitados);
            TextView tv_status_servicos_solicitados = (TextView) view.findViewById(R.id.tv_status_servicos_solicitados);
            TextView tv_data_servicos_solicitados = (TextView) view.findViewById(R.id.tv_data_servicos_solicitados);
            TextView tv_hora_servicos_solicitados = (TextView) view.findViewById(R.id.tv_hora_servicos_solicitados);


            ServicosSolicitados servicosSolicitado = servicosSolicitados.get(position);
            tv_nome_servico_servicos_solicitados.setText(servicosSolicitado.getNomeAnimal());
            tv_nome_animal_servicos_solicitados.setText(servicosSolicitado.getNomeServico());
            tv_status_servicos_solicitados.setText(servicosSolicitado.getStatus());
            tv_data_servicos_solicitados.setText(servicosSolicitado.getData());
            tv_hora_servicos_solicitados.setText(servicosSolicitado.getHora());


        }

        return view;
    }

}
