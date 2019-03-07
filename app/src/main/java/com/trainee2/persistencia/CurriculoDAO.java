package com.trainee2.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.trainee2.dominio.Curriculo;
import com.trainee2.infra.BancoDados;
import com.trainee2.infra.SessaoEstagiario;

public class CurriculoDAO {
    private BancoDados bancoDados;
    private SessaoEstagiario sessaoEstagiario = SessaoEstagiario.getInstance();

    public CurriculoDAO(Context context){ bancoDados = new BancoDados(context);}

    public long add(Curriculo curriculo){
        long result;
        SQLiteDatabase db = bancoDados.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("curso", curriculo.getCurso());
        values.put("instituicao", curriculo.getInstituicao());
        values.put("area", curriculo.getArea());
        values.put("id_estagiario", SessaoEstagiario.getInstance().getEstagiario().getId());
        result = db.insert("curriculo", null, values);
        db.close();
        return result;
    }
}
