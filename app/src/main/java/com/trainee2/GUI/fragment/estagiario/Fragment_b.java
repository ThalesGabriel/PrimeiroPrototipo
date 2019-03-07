package com.trainee2.GUI.fragment.estagiario;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.trainee2.GUI.estagiario.TelaCadastroCurriculo;
import com.trainee2.R;
import com.trainee2.dominio.Estagiario;
import com.trainee2.dominio.Usuario;
import com.trainee2.infra.BancoDados;
import com.trainee2.infra.SessaoEstagiario;
import com.trainee2.infra.TraineeApp;
import com.trainee2.negocio.EstagiarioServices;
import com.trainee2.negocio.LoginServices;

import java.util.ArrayList;

public class Fragment_b extends Fragment {
    private SessaoEstagiario sessaoEstagiario = SessaoEstagiario.getInstance();
    private TraineeApp traineeApp = new TraineeApp();
    EditText nomeCadastroEstagiario, emailCadastroEstagiario, cpfCadastroEstagirario, senhaCadastroEstagiario,
            repeteSenhaCadastroEstagiario, cidadeCadastroEstagiario;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater Inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = Inflater.inflate(R.layout.activity_fragment_b, container, false);

        nomeCadastroEstagiario = (EditText)v.findViewById(R.id.nomeCadastroEstagiario);
        emailCadastroEstagiario = (EditText)v.findViewById(R.id.emailCadastroEstagiario);
        cpfCadastroEstagirario = (EditText)v.findViewById(R.id.cpfCadastroEstagirario);
        senhaCadastroEstagiario = (EditText)v.findViewById(R.id.senhaCadastroEstagiario);
        repeteSenhaCadastroEstagiario = (EditText)v.findViewById(R.id.repeteSenhaCadastroEstagiario);
        cidadeCadastroEstagiario = (EditText)v.findViewById(R.id.cidadeCadastroEstagiario);

        Button botCadastroPessoal = (Button)v.findViewById(R.id.botCadastroPessoal);
        botCadastroPessoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cadastrar();
            }
        });
        return v;
    }

    public void Cadastrar(){;
        Estagiario daSessao;
        if (!this.validaCampos()) {
            return;
        }
        long result;
        BancoDados mDataBase = new BancoDados(getContext());
        LoginServices loginServices = new LoginServices(getContext());
        daSessao = loginServices.cadastrarEstagiario(criarEstagiario(), traineeApp.getBaseContext());
        if (daSessao == null) {
            Toast.makeText(getContext(),"Já existe uma conta com este e-mail.",Toast.LENGTH_SHORT).show();
            return;
        }else {
            result = daSessao.getId();
        }
        mDataBase.close();
        if(result == -1){
            Toast.makeText(getContext(), "Informações não inseridas.", Toast.LENGTH_SHORT).show();
        }else{
            SessaoEstagiario.getInstance().setEstagiario(daSessao);
            Toast.makeText(getContext(),"Informações inseridas.",Toast.LENGTH_SHORT).show();
            Intent abreTelaCadastroCurriculo = new Intent(getContext(), TelaCadastroCurriculo.class);
            startActivity(abreTelaCadastroCurriculo);
        }


    }

    private Estagiario criarEstagiario() {
        String cpf = cpfCadastroEstagirario.getText().toString().trim();
        String email = emailCadastroEstagiario.getText().toString().trim();
        String senha = senhaCadastroEstagiario.getText().toString().trim();
        Estagiario estagiario = new Estagiario();
        estagiario.setCpf(cpf);
        estagiario.setEmail(email);
        estagiario.setSenha(senha);
        estagiario.setUsuario(this.criarUsuario());
        return estagiario;
    }

    private Usuario criarUsuario() {
        String nome = nomeCadastroEstagiario.getText().toString().trim();
        String cidade = cidadeCadastroEstagiario.getText().toString().trim();
        Usuario usuario = new Usuario();
        usuario.setNome(nome);
        usuario.setCidade(cidade);
        return usuario;
    }

    private boolean validaCampos() {
        boolean camposVazios = false;
        ArrayList<String> logError = new ArrayList<>();

        String nome = nomeCadastroEstagiario.getText().toString().trim();
        String email = emailCadastroEstagiario.getText().toString().trim();
        String senha = senhaCadastroEstagiario.getText().toString().trim();
        String cidade = cidadeCadastroEstagiario.getText().toString().trim();
        String cpf = cpfCadastroEstagirario.getText().toString().trim();
        String repeteSenha = repeteSenhaCadastroEstagiario.getText().toString().trim();

        if (camposVazios = isCampoVazio(nome)) {
            nomeCadastroEstagiario.requestFocus();
        } else if (camposVazios = isCampoVazio(email)) {
            emailCadastroEstagiario.requestFocus();
        } else if (camposVazios = isCampoVazio(cpf)) {
            cpfCadastroEstagirario.requestFocus();
        } else if (camposVazios = isCampoVazio(senha)) {
            senhaCadastroEstagiario.requestFocus();
        } else if (camposVazios = isCampoVazio(repeteSenha)) {
            repeteSenhaCadastroEstagiario.requestFocus();
        }
        else if (camposVazios = isCampoVazio(cidade)) {
            cidadeCadastroEstagiario.requestFocus();
        }
        if (camposVazios) {
            logError.add("- Preencha todos os campos vazios.");
        }

        if (!EstagiarioServices.isEmailValido(email)) {
            logError.add("- O email não é válido.");
        }

        if (!EstagiarioServices.isCPF(cpf)) {
            logError.add("- O CPF não é válido.");
        }

        if (!EstagiarioServices.isSenhaIgual(senha, repeteSenha)) {
            logError.add("- As senhas não conferem.");
        }

        if (logError.size() > 0) {
            String msg = new String();
            for (String erro : logError) {
                msg = msg.concat(erro + "\n");
            }
            AlertDialog.Builder dlg = new AlertDialog.Builder(getContext());
            dlg.setTitle("Aviso");
            dlg.setMessage(msg);
            dlg.setNeutralButton("OK", null);
            dlg.show();
            return false;
        } return true;
    }

    private boolean isCampoVazio(String valor) {
        boolean resultado = (TextUtils.isEmpty(valor) || valor.trim().isEmpty());
        return resultado;
    }




}
