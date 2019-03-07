package com.trainee2.GUI.fragment.estagiario;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.trainee2.GUI.estagiario.TelaCadastroCurriculo;
import com.trainee2.GUI.estagiario.TelaPrincipalEstagiario;
import com.trainee2.R;
import com.trainee2.dominio.Estagiario;
import com.trainee2.dominio.Usuario;
import com.trainee2.infra.SessaoEstagiario;
import com.trainee2.infra.TraineeApp;
import com.trainee2.infra.Validacao;
import com.trainee2.negocio.LoginServices;

public class Fragment_a extends Fragment {
    private SessaoEstagiario sessaoEstagiario = SessaoEstagiario.getInstance();
    private TraineeApp traineeApp = new TraineeApp();
    private EditText senhaEstagiarioLogin, emailEstagiarioLogin;
    private Validacao validacao = new Validacao();
    private Context mContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater Inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = Inflater.inflate(R.layout.activity_fragment_a, container, false);
        mContext = v.getContext();
        emailEstagiarioLogin = (EditText)v.findViewById(R.id.emailLogin);
        senhaEstagiarioLogin = (EditText)v.findViewById(R.id.senhaLogin);
        Button botLogin = (Button)v.findViewById(R.id.botLogin);
        botLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logar();
            }
        });
        return v;
    }

    private void logar() {
        Estagiario estagiario;
        if (!this.verificarCampos()) {
            return;
        }else {
            LoginServices loginServices = new LoginServices(getContext());
            estagiario = loginServices.logar(criarUsuario(), getContext());
            if (estagiario instanceof Estagiario) {
                SessaoEstagiario.getInstance().setEstagiario(estagiario);
                Toast.makeText(getContext(),"Usuário logado com sucesso.", Toast.LENGTH_SHORT).show();
                Intent abreTelaPrincipal = new Intent(mContext, TelaPrincipalEstagiario.class);
                startActivity(abreTelaPrincipal);
            } else {
                Toast.makeText(getContext(),"Email ou senha inválidos.", Toast.LENGTH_SHORT).show();
            }

        }

    }


    private boolean verificarCampos(){
        String email = this.emailEstagiarioLogin.getText().toString().trim();
        String senha = this.senhaEstagiarioLogin.getText().toString().trim();
        if (this.validacao.verificarCampoEmail(email)) {
            this.emailEstagiarioLogin.setError("Email Inválido");
            return false;
        } else if (this.validacao.verificarCampoVazio(senha)) {
            this.senhaEstagiarioLogin.setError("Senha Inválida");
            return false;
        } else {
            return true;
        }
    }

    private Estagiario criarUsuario() {
        Estagiario estagiario = new Estagiario();
        String email = this.emailEstagiarioLogin.getText().toString().trim();
        String senha = this.senhaEstagiarioLogin.getText().toString().trim();
        estagiario.setEmail(email);
        estagiario.setSenha(senha);
        return estagiario;
    }
}
