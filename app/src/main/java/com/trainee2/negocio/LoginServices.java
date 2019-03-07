package com.trainee2.negocio;

import android.content.Context;

import com.trainee2.dominio.Curriculo;
import com.trainee2.dominio.Empregador;
import com.trainee2.dominio.Estagiario;
import com.trainee2.dominio.Usuario;
import com.trainee2.infra.TraineeApp;
import com.trainee2.persistencia.CurriculoDAO;
import com.trainee2.persistencia.EmpregadorDAO;
import com.trainee2.persistencia.EstagiarioDAO;
import com.trainee2.persistencia.UsuarioDAO;

public class LoginServices {
    private UsuarioDAO usuarioDAO;
    private EstagiarioDAO estagiarioDAO;
    private EmpregadorDAO empregadorDAO;
    private CurriculoDAO curriculoDAO;
    private TraineeApp trainee;

    public LoginServices(Context context) {
        usuarioDAO = new UsuarioDAO(context);
        estagiarioDAO = new EstagiarioDAO(context);
        empregadorDAO = new EmpregadorDAO(context);
        curriculoDAO = new CurriculoDAO(context);


    }

    public Estagiario logar(Estagiario estagiario, Context context) {
        Estagiario estagiarioLogin = this.estagiarioDAO.getEstagiarioByEmailAndSenha(estagiario.getEmail(), estagiario.getSenha(), trainee.getContext());
        if (estagiarioLogin != null) {
            Usuario usuario = this.usuarioDAO.getIdUsuario(estagiario.getId(), context);
            estagiarioLogin.setUsuario(usuario);
            return estagiarioLogin;
        }
        return null;
    }
    public Empregador logar(Empregador empregador) {
        Empregador empregadorLogin = this.empregadorDAO.getEmpregadorByEmailAndSenha(empregador.getEmail(), empregador.getSenha(), trainee.getContext());
        if (empregadorLogin != null) {
            return empregadorLogin;
        }
        return null;
    }

    public Estagiario cadastrarEstagiario(Estagiario estagiario, Context context) {
        long resultado;
        if (verificarEmailEstagiario(estagiario.getEmail(), context)) {
            return null;
        } else {
            resultado = this.cadastrarUsuario(estagiario.getUsuario());
            if (resultado != -1) {
                estagiario.getUsuario().setId(resultado);
                resultado = this.estagiarioDAO.add(estagiario);
                estagiario.setId(resultado);
                return estagiario;
            } else {
                return null;
            }


        }
    }

    public Empregador cadastrarEmpregador(Empregador empregador, Context context) {
        long resultado;
        if (verificarEmailEmpregador(empregador.getEmail(), context)) {
            return null;
        } else {
            resultado = this.cadastrarUsuario(empregador.getUsuario());
            if (resultado != -1) {
                empregador.getUsuario().setId(resultado);
                resultado = this.empregadorDAO.add(empregador);
                empregador.setId(resultado);
                return empregador;
            } else {
                return null;
            }


        }
    }

    private long cadastrarUsuario(Usuario usuario) {
        long result;
        result = this.usuarioDAO.add(usuario);
        return result;
    }


    private boolean verificarEmailEstagiario(String email, Context context) {
        Estagiario usuarioEmail = this.estagiarioDAO.getEstagiarioByEmail(email, context);
        if (usuarioEmail != null) {
            return true;
        }
        return false;
    }
    private boolean verificarEmailEmpregador(String email, Context context) {
        Empregador usuarioEmail = this.empregadorDAO.getEmpregadorByEmail(email, context);
        if (usuarioEmail != null) {
            return true;
        }
        return false;
    }

    public Curriculo cadastrarCurriculo(Curriculo curriculo) {
        long resultado;
        resultado = this.curriculoDAO.add(curriculo);

        curriculo.setId(resultado);
        return curriculo;
    }
}
