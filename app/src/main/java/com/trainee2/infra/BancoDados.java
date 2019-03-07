package com.trainee2.infra;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public  class BancoDados extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 25;
    private static final String DATABASE_NAME = "traineeapp";
    public BancoDados(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE usuario(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nome text NOT NULL, " +
                "cidade text NOT NULL);");

        db.execSQL("CREATE TABLE empregador(" +
                "id integer PRIMARY KEY AUTOINCREMENT," +
                "cnpj text NOT NULL,"+
                "email text NOT NULL," +
                "senha text NOT NULL," +
                "id_usuario integer NOT NULL);");

        db.execSQL("CREATE TABLE estagiario(" +
                "id integer PRIMARY KEY AUTOINCREMENT," +
                "cpf text NOT NULL," +
                "email text NOT NULL," +
                "senha text NOT NULL," +
                "id_usuario integer NOT NULL);");

        db.execSQL("CREATE TABLE curriculo(" +
                "id integer PRIMARY KEY AUTOINCREMENT,"+
                "curso text NOT NULL," +
                "instituicao text NOT NULL," +
                "area text NOT NULL, " +
                "id_estagiario integer NOT NULL);");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE usuario;");
        db.execSQL("DROP TABLE empregador");
        db.execSQL("DROP TABLE estagiario");
        db.execSQL("DROP TABLE curriculo");
        onCreate(db);
    }


    public SQLiteDatabase getBancoLeitura(Context context){
        SQLiteDatabase bancoDados = this.getReadableDatabase();
        return bancoDados;
    }

    public SQLiteDatabase getBancoEscrita(Context context) {
        SQLiteDatabase bancoDados = this.getWritableDatabase();
        return bancoDados;
    }
}
