package br.com.petshopsi.helper;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenciasFuncionario {

    private Context contexto;
    private SharedPreferences preferences;
    private final String NOME_ARQUIVO = "funcionario.preferencias";
    private final int MODE = 0;
    private SharedPreferences.Editor editor;

    private final String CHAVE_IDENTIFICADOR = "identificadorUsuarioLogadoFuncionario";

    public PreferenciasFuncionario(Context contextoParametro){
        contexto = contextoParametro;
        preferences = contexto.getSharedPreferences(NOME_ARQUIVO, MODE);
        editor = preferences.edit();
    }

    public void salvarDadosFuncionrio(String identificadorUsuarioFuncionario){
        editor.putString(CHAVE_IDENTIFICADOR, identificadorUsuarioFuncionario);
        editor.commit();
    }

    public String getIdentificadorFuncionario(){
        return preferences.getString(CHAVE_IDENTIFICADOR, null);
    }

}
