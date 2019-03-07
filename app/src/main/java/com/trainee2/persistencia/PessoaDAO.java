package com.trainee2.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.trainee2.dominio.Estagiario;
import com.trainee2.dominio.Usuario;
import com.trainee2.infra.BancoDados;
import com.trainee2.infra.TraineeApp;

public class UsuarioDAO {
    private BancoDados bancodados;
    private TraineeApp traineeApp = new TraineeApp();

    public UsuarioDAO(Context context) {
        bancodados = new BancoDados(context);
    }

    public long add(Usuario usuario1) {
        long result;
        SQLiteDatabase db = bancodados.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nome", usuario1.getNome());
        values.put("cidade", usuario1.getCidade());
        result = db.insert("usuario", null, values);
        db.close();
        return result;
    }

    private Usuario criarUsuario(Cursor cursor) {
        int indexId = cursor.getColumnIndex("id");
        long id = cursor.getLong(indexId);
        int indexNome = cursor.getColumnIndex("nome");
        String nome = cursor.getString(indexNome);
        int indexCidade = cursor.getColumnIndex("cidade");
        String cidade = cursor.getString(indexCidade);
        Usuario usuario = new Usuario();
        usuario.setId(id);
        usuario.setNome(nome);
        usuario.setCidade(cidade);
        return usuario;
    }

    private Usuario load(String query, String[] args, Context context) {
        SQLiteDatabase leitorBanco = bancodados.getBancoLeitura(context);
        Cursor cursor = leitorBanco.rawQuery(query, args);
        Usuario usuario = null;
        if (cursor.moveToNext()) {
            usuario = criarUsuario(cursor);
        }
        cursor.close();
        leitorBanco.close();
        return usuario;
    }

    public Usuario getIdUsuario(long id, Context context) {
        String query = "SELECT * FROM estagiario " +
                "WHERE id_usuario = ?";
        String[] args = {String.valueOf(id)};
        return this.load(query, args, context);
    }


}

