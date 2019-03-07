package com.trainee2.GUI.estagiario;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.trainee2.R;
import com.trainee2.dominio.Curriculo;
import com.trainee2.infra.BancoDados;
import com.trainee2.infra.SessaoEstagiario;
import com.trainee2.negocio.LoginServices;

import java.util.ArrayList;

public class TelaCadastroCurriculo extends AppCompatActivity implements AdapterView.OnItemSelectedListener  {
    Spinner Segmento;
    Button botCadastroCurriculo;
    EditText instituicaoCadastro, cursoCadastro;
    private SessaoEstagiario sessaoEstagiario = SessaoEstagiario.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_cadastro_curriculo);

        instituicaoCadastro = (EditText)findViewById(R.id.instituicaoCadastro);
        cursoCadastro = (EditText)findViewById(R.id.cursoCadastro);

        Segmento = (Spinner)findViewById(R.id.Segmento);
        Segmento.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.fields, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Segmento.setAdapter(adapter);

        botCadastroCurriculo = (Button)findViewById(R.id.botCadastroCurriculo);
        botCadastroCurriculo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Inserir();

            }
        });
    }
    private void Inserir() {
        Curriculo daSessao = new Curriculo();
        if (!this.validaCampos()) {
            return;
        }
        long resultado;
        BancoDados mDataBase = new BancoDados(this);
        LoginServices loginServices = new LoginServices(this);
        daSessao.setEstagiario(SessaoEstagiario.getInstance().getEstagiario());
        if (daSessao == null) {
            Toast.makeText(getApplicationContext(), "Curriculo não inserido.", Toast.LENGTH_SHORT).show();
        }
        mDataBase.close();
        resultado = daSessao.getId();
        if (resultado != -1){
            Intent abreTelaPrincipalEstagiario = new Intent(TelaCadastroCurriculo.this, TelaPrincipalEstagiario.class);
            startActivity(abreTelaPrincipalEstagiario);
            Toast.makeText(getApplicationContext(), "Conta cadastrada.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getApplicationContext(), "Conta não cadastrada.", Toast.LENGTH_SHORT).show();


        }

    }

    private Curriculo criarCurriculo() {
        String curso = cursoCadastro.getText().toString().trim();
        String instituicao = instituicaoCadastro.getText().toString().trim();
        String area = Segmento.getSelectedItem().toString();
        Curriculo curriculo= new Curriculo();
        curriculo.setArea(area);
        curriculo.setCurso(curso);
        curriculo.setInstituicao(instituicao);
        return curriculo;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) { }
    @Override
    public void onNothingSelected(AdapterView<?> parent) { }

    private boolean validaCampos() {
        boolean camposVazios = false;
        ArrayList<String> logError = new ArrayList<>();

        String instituicao = instituicaoCadastro.getText().toString().trim();
        String curso = cursoCadastro.getText().toString().trim();
        String area = Segmento.getSelectedItem().toString();

        if (camposVazios = isCampoVazio(instituicao)) {
            instituicaoCadastro.requestFocus();
        } else if (camposVazios = isCampoVazio(curso)) {
            cursoCadastro.requestFocus();

            if (camposVazios) {
                logError.add("- Preencha todos os campos vazios.");
            }

            if (logError.size() > 0) {
                String msg = new String();
                for (String erro : logError) {
                    msg = msg.concat(erro + "\n");
                }
                AlertDialog.Builder dlg = new AlertDialog.Builder(getApplicationContext());
                dlg.setTitle("Aviso");
                dlg.setMessage(msg);
                dlg.setNeutralButton("OK", null);
                dlg.show();
                return false;
            }

        }return true;

    }
    private boolean isCampoVazio (String valor){
        boolean resultado = (TextUtils.isEmpty(valor) || valor.trim().isEmpty());
        return resultado;
    }


}
