package com.trainee2.GUI.fragment.empregador;

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

import com.trainee2.GUI.empregador.TelaPrincipalEmpregador;
import com.trainee2.GUI.estagiario.TelaCadastroCurriculo;
import com.trainee2.R;
import com.trainee2.dominio.Empregador;
import com.trainee2.dominio.Usuario;
import com.trainee2.infra.BancoDados;
import com.trainee2.infra.SessaoEmpregador;
import com.trainee2.infra.SessaoEstagiario;
import com.trainee2.infra.TraineeApp;
import com.trainee2.negocio.LoginServices;

import java.util.ArrayList;

public class FragmentCadastroEmpregador extends Fragment {
    private SessaoEmpregador sessaoEmpregador = SessaoEmpregador.getInstance();
    private TraineeApp traineeApp = new TraineeApp();

    Button botCadastroEmpresa;
    EditText nomeCadastroEmpregador, emailCadastroEmpregador, cnpjCadastroEmpresa, senhaCadastroEmpresa,
            repeteSenhaCadastroEmpresa, cidadeCadastroEmpresa;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater Inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = Inflater.inflate(R.layout.activity_fragment_cadastro_empregador, container, false);

        nomeCadastroEmpregador = (EditText)v.findViewById(R.id.nomeCadastroEmpregador);
        emailCadastroEmpregador = (EditText)v.findViewById(R.id.emailCadastroEmpregador);
        cnpjCadastroEmpresa = (EditText)v.findViewById(R.id.cnpjCadastroEmpresa);
        senhaCadastroEmpresa = (EditText)v.findViewById(R.id.senhaCadastroEmpresa);
        repeteSenhaCadastroEmpresa = (EditText)v.findViewById(R.id.repeteSenhaCadastroEmpresa);
        cidadeCadastroEmpresa = (EditText)v.findViewById(R.id.cidadeCadastroEmpresa);


        botCadastroEmpresa= (Button)v.findViewById(R.id.botCadastroEmpresa);
        botCadastroEmpresa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add();
            }
        });
        return v;

    }

    public void add(){
        Empregador empregador;

        long result;
        BancoDados mDataBase = new BancoDados(getContext());
        LoginServices loginServices = new LoginServices(getContext());
        empregador = loginServices.cadastrarEmpregador(criarEmpregador(), traineeApp.getBaseContext());
        if (empregador == null){
            Toast.makeText(getContext(), "Email já cadastrado.", Toast.LENGTH_SHORT).show();
            return;
        }else{
            result = empregador.getId();
        }
        mDataBase.close();
        if (result == -1){
            Toast.makeText(getContext(), "Conta não cadastrada.", Toast.LENGTH_SHORT).show();
        }else{
            SessaoEmpregador.getInstance().setEmpregador(empregador);
            Toast.makeText(getContext(), "Informações inseridas.", Toast.LENGTH_SHORT).show();
            Intent abreTelaCadastroCurriculo = new Intent(getActivity(), TelaPrincipalEmpregador.class);
            startActivity(abreTelaCadastroCurriculo);
        }
    }

    public Empregador criarEmpregador(){
        Empregador empregador = new Empregador();
        Usuario usuario = criarUsuario();
        empregador.setUsuario(usuario);
        String email = emailCadastroEmpregador.getText().toString().trim();
        String senha = senhaCadastroEmpresa.getText().toString().trim();
        String cnpj = cnpjCadastroEmpresa.getText().toString().trim();
        empregador.setEmail(email);
        empregador.setSenha(senha);
        empregador.setCnpj(cnpj);
        return empregador;


    }

    public Usuario criarUsuario(){
        String nome = nomeCadastroEmpregador.getText().toString().trim();
        String cidade = cidadeCadastroEmpresa.getText().toString().trim();
        Usuario usuario = new Usuario();
        usuario.setNome(nome);
        usuario.setCidade(cidade);
        return usuario;
    }
    /*
    private boolean validaCampos() {
        boolean camposVazios = false;
        ArrayList<String> logError = new ArrayList<>();

        String nome = nomeCadastroEmpregador.getText().toString().trim();
        String email = emailCadastroEmpregador.getText().toString().trim();
        String senha = senhaCadastroEmpresa.getText().toString().trim();
        String cidade = cidadeCadastroEmpresa.getText().toString().trim();
        String cnpj = cnpjCadastroEmpresa.getText().toString().trim();
        String repeteSenha = repeteSenhaCadastroEmpresa.getText().toString().trim();

        if (camposVazios = isCampoVazio(nome)) {
            nomeCadastroEmpregador.requestFocus();
        } else if (camposVazios = isCampoVazio(email)) {
            emailCadastroEmpregador.requestFocus();
        } else if (camposVazios = isCampoVazio(senha)) {
            senhaCadastroEmpresa.requestFocus();
        } else if (camposVazios = isCampoVazio(repeteSenha)) {
            repeteSenhaCadastroEmpresa.requestFocus();
        }
        else if (camposVazios = isCampoVazio(cidade)) {
            cidadeCadastroEmpresa.requestFocus();
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
    */

}
