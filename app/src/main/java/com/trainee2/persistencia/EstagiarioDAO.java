package com.trainee2.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.trainee2.dominio.Empregador;
import com.trainee2.dominio.Estagiario;
import com.trainee2.dominio.Usuario;
import com.trainee2.infra.BancoDados;

public class EstagiarioDAO {
    private UsuarioDAO usuarioDAO;
    private BancoDados bancoDados;


    public EstagiarioDAO(Context context) {
        bancoDados = new BancoDados(context);
        usuarioDAO = new UsuarioDAO(context);
    }

    public long add(Estagiario estagiario){
        long result;
        SQLiteDatabase db = bancoDados.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("cpf",estagiario.getCpf());
        values.put("email", estagiario.getEmail());
        values.put("senha", estagiario.getSenha());
        values.put("id_usuario", estagiario.getUsuario().getId());
        result = db.insert("estagiario", null, values);
        db.close();
        return result;
    }


    private Estagiario load(String query, String[] args, Context context) {
        SQLiteDatabase leitorBanco = bancoDados.getReadableDatabase();
        Cursor cursor = leitorBanco.rawQuery(query, args);
        Estagiario estagiario = null;
        if (cursor.moveToNext()) {
            estagiario = criarEstagiario(cursor);
        }
        cursor.close();
        leitorBanco.close();
        return estagiario;
    }


    public Estagiario getEstagiarioByEmail(String email, Context context) {
        String query = "SELECT * FROM estagiario " +
                "WHERE email = ?";
        String[] args = {email};
        return this.load(query, args, context);
    }

    public Estagiario getEstagiarioByEmailAndSenha(String email, String senha, Context context){
        String query = "SELECT * FROM estagiario " +
                "WHERE email = ? AND senha = ?";
        String[] args = {email, senha};
        return this.load(query, args, context);
    }

    private Estagiario criarEstagiario(Cursor cursor) {
    int indexId = cursor.getColumnIndex("id");
    int id = cursor.getInt(indexId);
    int indexCpf = cursor.getColumnIndex("cpf");
    String cpf = cursor.getString(indexCpf);
    int indexEmail = cursor.getColumnIndex("email");
    String email = cursor.getString(indexEmail);
    int indexSenha = cursor.getColumnIndex("senha");
    String senha = cursor.getString(indexSenha);
    Estagiario estagiario = new Estagiario();
    estagiario.setId(id);
    estagiario.setCpf(cpf);
    estagiario.setEmail(email);
    estagiario.setSenha(senha);
    return estagiario;
    }




}
