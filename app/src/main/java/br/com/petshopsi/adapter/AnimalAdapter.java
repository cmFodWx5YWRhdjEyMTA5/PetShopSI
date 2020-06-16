package br.com.petshopsi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import br.com.petshopsi.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import br.com.petshopsi.classes.Animal;

public class AnimalAdapter  extends ArrayAdapter<Animal> {

    private ArrayList<Animal> animais;
    private Context context;

    public AnimalAdapter(Context c, ArrayList<Animal> objects) {
        super(c, 0, objects);
        this.animais = objects;
        this.context = c;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = null;

        // Verifica se a lista esta vazia
        if (animais != null) {
            // Inicializar objeto  para montagem da view
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            // Montar view a partir do xml
            view = inflater.inflate(R.layout.itens_animais, parent, false);

            // Recupera elemento para exibição
            //TextView nomeCampanha = (TextView) view.findViewById(R.id.tv_nome);
            TextView txtNomeAnimal = (TextView) view.findViewById(R.id.txtNomeAnimal);


            Animal animal = animais.get(position);
            txtNomeAnimal.setText(animal.getNomeAnimal());


        }

        return view;
    }

}
