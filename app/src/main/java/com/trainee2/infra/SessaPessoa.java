package com.trainee2.infra;

import com.trainee2.dominio.Estagiario;

import java.util.HashMap;
import java.util.Map;

public class SessaoEstagiario {
    private final Map<String,Object> values = new HashMap<>();
    private static final SessaoEstagiario instance = new SessaoEstagiario();

    public static SessaoEstagiario getInstance() {
        return instance;
    }

    public Estagiario getEstagiario() {
        return (Estagiario) values.get("sessao.estagiario");
    }

    public void setEstagiario(Estagiario estagiario) {
        setValue("sessao.estagiario",estagiario);
    }

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