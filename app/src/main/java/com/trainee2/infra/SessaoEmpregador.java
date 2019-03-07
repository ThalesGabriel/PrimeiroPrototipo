package com.trainee2.infra;

import com.trainee2.dominio.Empregador;

import java.util.HashMap;
import java.util.Map;

public class SessaoEmpregador {
    private final Map<String,Object> values = new HashMap<>();
    private static final SessaoEmpregador instance = new SessaoEmpregador();

    public static SessaoEmpregador getInstance() { return instance; }

    public Empregador getEmpregador() {
        return (Empregador) values.get("sessao.empregador");
    }

    public void setEmpregador(Empregador empregador) { setValue("sessao.empregador",empregador); }

    public Object getValues(String key) {
        return values.get(key);
    }

    public void setValue(String key, Object value) {
        values.put(key, value);
    }

    public void reset(){
        this.values.clear();
    }
}
