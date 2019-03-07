package com.trainee2.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.trainee2.dominio.Empregador;
import com.trainee2.dominio.Estagiario;
import com.trainee2.infra.BancoDados;

public class EmpregadorDAO {
    private BancoDados bancodados;

    public EmpregadorDAO(Context context){ bancodados = new BancoDados(context);}

    public long add(Empregador empregador){
        long result;
        SQLiteDatabase db = bancodados.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("cnpj", empregador.getCnpj());
        values.put("email", empregador.getEmail());
        values.put("senha", empregador.getSenha());
        values.put("id_usuario", empregador.getUsuario().getId());
        result = db.insert("empregador", null, values);
        db.close();
        return result;
    }

    private Empregador load(String query, String[] args, Context context) {
        SQLiteDatabase leitorBanco = bancodados.getReadableDatabase();
        Cursor cursor = leitorBanco.rawQuery(query, args);
        Empregador empregador = null;
        if (cursor.moveToNext()) {
            empregador = criarEmpregador(cursor);
        }
        cursor.close();
        leitorBanco.close();
        return empregador;
    }

    public Empregador getEmpregadorByEmail(String email, Context context) {
        String query = "SELECT * FROM empregador " +
                "WHERE email = ?";
        String[] args = {email};
        return this.load(query, args, context);
    }

    public Empregador getEmpregadorByEmailAndSenha(String email, String senha, Context context){
        String query = "SELECT * FROM empregador " +
                "WHERE email = ? AND senha = ?";
        String[] args = {email, senha};
        return this.load(query, args, context);
    }

    private Empregador criarEmpregador(Cursor cursor) {
        int indexId = cursor.getColumnIndex("id");
        int id = cursor.getInt(indexId);
        int indexCnpj = cursor.getColumnIndex("cnpj");
        String cnpj = cursor.getString(indexCnpj);
        int indexEmail = cursor.getColumnIndex("email");
        String email = cursor.getString(indexEmail);
        int indexSenha = cursor.getColumnIndex("senha");
        String senha = cursor.getString(indexSenha);
        Empregador empregador = new Empregador();
        empregador.setId(id);
        empregador.setCnpj(cnpj);
        empregador.setEmail(email);
        empregador.setSenha(senha);
        return empregador;
    }


}
