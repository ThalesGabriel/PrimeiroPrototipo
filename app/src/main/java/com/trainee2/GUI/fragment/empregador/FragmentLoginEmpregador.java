package com.trainee2.GUI.fragment.empregador;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.trainee2.GUI.empregador.TelaPrincipalEmpregador;
import com.trainee2.R;
import com.trainee2.dominio.Empregador;
import com.trainee2.dominio.Estagiario;
import com.trainee2.infra.SessaoEmpregador;
import com.trainee2.infra.SessaoEstagiario;
import com.trainee2.infra.Validacao;
import com.trainee2.negocio.LoginServices;

public class FragmentLoginEmpregador extends Fragment {
    private Validacao validacao = new Validacao();
    private Button LoginEmp;
    private EditText emailLoginempregador, senhaLoginEmpregador;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater Inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = Inflater.inflate(R.layout.activity_fragment_login_empregador, container, false);
        emailLoginempregador = (EditText)v.findViewById(R.id.emailLoginEMP);
        senhaLoginEmpregador = (EditText)v.findViewById(R.id.senhaLoginEMP);

        LoginEmp = (Button)v.findViewById(R.id.botLoginEMP);
        LoginEmp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logar();
            }
        });
        return v;
    }

    private void logar() {
        Empregador empregador;
        if (!this.verificarCampos()) {
            return;
        }else {
            LoginServices loginServices = new LoginServices(getContext());
            empregador = loginServices.logar(criarUsuario());
            if (empregador instanceof Empregador) {
                SessaoEmpregador.getInstance().setEmpregador(empregador);
                Toast.makeText(getContext(),"Usuário logado com sucesso.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(),"Email ou senha inválidos.", Toast.LENGTH_SHORT).show();
            }

        }

    }
    private boolean verificarCampos(){
        String email = this.emailLoginempregador.getText().toString().trim();
        String senha = this.senhaLoginEmpregador.getText().toString().trim();
        if (this.validacao.verificarCampoEmail(email)) {
            this.emailLoginempregador.setError("Email Inválido");
            return false;
        } else if (this.validacao.verificarCampoVazio(senha)) {
            this.senhaLoginEmpregador.setError("Senha Inválida");
            return false;
        } else {
            return true;
        }
    }

    private Empregador criarUsuario() {
        Empregador empregador = new Empregador();
        String email = this.emailLoginempregador.getText().toString().trim();
        String senha = this.senhaLoginEmpregador.getText().toString().trim();
        empregador.setEmail(email);
        empregador.setSenha(senha);
        return empregador;
    }
}
