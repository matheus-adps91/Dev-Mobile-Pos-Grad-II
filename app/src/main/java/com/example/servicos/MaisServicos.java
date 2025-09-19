package com.example.servicos;

import static com.example.servicos.R.string.inf_indisponivel;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.servicos.api.InvertextoApi;
import com.example.servicos.model.Cnpj;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MaisServicos extends AppCompatActivity {

    private final int UNPROCESSABLE_ENTITY = 422;
    private final int CNPJ_SIZE = 14;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_mais_servicos);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText etCnpj = findViewById(R.id.etCnpj);
        Button btConsultar = findViewById(R.id.btConsultar);

        btConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String numeroCnpj = etCnpj.getText().toString();
                boolean ehValido = ehCnpjValido(numeroCnpj);
                if(!ehValido){
                    Toast.makeText(
                            MaisServicos.this,
                            getString(R.string.cnpjInvalido),
                            Toast.LENGTH_LONG)
                    .show();
                    return;
                }
                consultar(numeroCnpj);
            }
        });
    }

    private void consultar(String numeroCnpj) {
        Gson gson = getGson();
        Retrofit retrofit = getRetrofit(gson);

        InvertextoApi invertextoApi = retrofit.create(InvertextoApi.class);
        Call<Cnpj> call = invertextoApi.getCnpj(
                numeroCnpj, Constantes.TOKEN_VALIDA_CNPJ
        );

        ProgressBar pbCarregando = findViewById(R.id.pbCarregando);
        pbCarregando.setVisibility(View.VISIBLE);
        TextView tvRazaoSocial = findViewById(R.id.tvRazaoSocial);
        TextView tvNomeFantasia = findViewById(R.id.tvNomeFantasia);
        TextView tvNaturezaJuridica = findViewById(R.id.tvNaturezaJuridica);
        TextView tvCapitalSocial = findViewById(R.id.tvCapitalSocial);
        TextView tvDataInicio = findViewById(R.id.tvDataInicio);
        TextView tvPorte = findViewById(R.id.tvPorte);
        TextView tvTipo = findViewById(R.id.tvTipo);
        TextView tvTelefone1 = findViewById(R.id.tvTelefone1);
        TextView tvEmail = findViewById(R.id.tvEmail);

        call.enqueue(new Callback<Cnpj>() {
            @Override
            public void onResponse(Call<Cnpj> call, Response<Cnpj> response) {
                pbCarregando.setVisibility(View.GONE);
                if (response.raw().code() == UNPROCESSABLE_ENTITY) {
                    tvRazaoSocial.setText(String.join(" : ", getString(R.string.razaoSocial), (getString(inf_indisponivel))));
                    tvNomeFantasia.setText(String.join(" : ", getString(R.string.nomeFantasia), (getString(inf_indisponivel))));
                    tvNaturezaJuridica.setText(String.join(" : ", getString(R.string.naturezaJuridica), (getString(inf_indisponivel))));
                    tvCapitalSocial.setText(String.join(" : ", getString(R.string.captialSocial), (getString(inf_indisponivel))));
                    tvDataInicio.setText(String.join(" : ", getString(R.string.dataInicio), (getString(inf_indisponivel))));
                    tvPorte.setText(String.join(" : ", getString(R.string.porte), (getString(inf_indisponivel))));
                    tvTipo.setText(String.join(" : ", getString(R.string.tipo), (getString(inf_indisponivel))));
                    tvTelefone1.setText(String.join(" : ", getString(R.string.telefone1), (getString(inf_indisponivel))));
                    tvEmail.setText(String.join(" : ", getString(R.string.email), (getString(inf_indisponivel))));
                }
                if (response.isSuccessful()) {
                    Cnpj cnpj = response.body();

                    tvRazaoSocial.setText(String.join(" : ", getString(R.string.razaoSocial), cnpj.getRazaoSocial()));
                    tvNomeFantasia.setText(String.join(" : ", getString(R.string.nomeFantasia), cnpj.getNomeFantasia()));
                    tvNaturezaJuridica.setText(String.join(" : ", getString(R.string.naturezaJuridica), cnpj.getNaturezaJuridica()));
                    tvCapitalSocial.setText(String.join(" : ", getString(R.string.captialSocial), cnpj.getCapitalSocial()));
                    tvDataInicio.setText(String.join(" : ", getString(R.string.dataInicio), cnpj.getDataInicio()));
                    tvPorte.setText(String.join(" : ", getString(R.string.porte), cnpj.getPorte()));
                    tvTipo.setText(String.join(" : ", getString(R.string.tipo), cnpj.getTipo()));
                    tvTelefone1.setText(String.join(" : ", getString(R.string.telefone1), cnpj.getTelefone1()));
                    tvEmail.setText(String.join(" : ", getString(R.string.email), cnpj.getEmail()));
                }
            }

            @Override
            public void onFailure(Call<Cnpj> call, Throwable throwable) {

            }
        });
    }

    @NonNull
    private static Retrofit getRetrofit(Gson gson) {
        return new Retrofit.Builder()
                .baseUrl(Constantes.URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    @NonNull
    private static Gson getGson() {
        return new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
    }

    private boolean ehCnpjValido(String numeroCnpj) {
        return numeroCnpj.length() == CNPJ_SIZE;
    }
}