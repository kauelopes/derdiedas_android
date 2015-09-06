package com.br.lm.der_die_das.app.View;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.br.lm.der_die_das.app.Model.Word;
import com.br.lm.der_die_das.app.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.LongBuffer;
import java.util.ArrayList;
import java.util.Random;

public class GameActivity extends Activity {
    private ArrayList<Word> words;
    private ArrayList<Word> selectedWords;
    private TextView wordshow;
    private Button die, der, das;
    private int wordnumber;
    private int acertos;
    private int numeroDePalavras;
    private String tema;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            tema  = extras.getString("tema");
            numeroDePalavras = extras.getInt("numeroDePalavras");
            Log.d("Tema", tema);
        }

        wordnumber = 0;
        acertos = 0;
        setContentView(R.layout.activity_game);
        wordshow = (TextView) findViewById(R.id.Word);
        if(tema.equals("Todos")) {
            casoGeral();
        }else{
            lejson(tema);
            selectedWords = selectwords(numeroDePalavras);
        }
        setaBotoes();
        joga();

    }

    private void joga() {
        if(wordnumber < (selectedWords.size())){
            wordshow.setBackgroundColor(getResources().getColor(R.color.branco));
            wordshow.setText(selectedWords.get(wordnumber).getWord());
        }else{
            die.setClickable(false);
            der.setClickable(false);
            das.setClickable(false);
            Intent returnIntent = new Intent();
            returnIntent.putExtra("Acertos",acertos );
            setResult(RESULT_OK, returnIntent);
            mensagemFinal();

        }
    }

    private void mensagemFinal(){
        if(numeroDePalavras==acertos){
            if(acertos==18){
                alertView("Parabéns, você manda muito nessa categoria!");
            }else{
                alertView("Parabéns, você acertou todas as " + numeroDePalavras + "! Agora vamos aumentar para  " + (numeroDePalavras + 5)+ " palavras!");
            }
        }else
            alertView("De " + numeroDePalavras + " palavras você acertou " + acertos + "! Acerte todas para passar de nível!");


    }

    private void checkartigo(int artigo) {
        if(artigo == selectedWords.get(wordnumber).getArtigo()){
            if(wordshow.getText()==selectedWords.get(wordnumber).getWord()){
                acertos++;
            }
            wordnumber++;
            joga();
        }else{
            wordshow.setText(selectedWords.get(wordnumber).getArtigoS() + " " + selectedWords.get(wordnumber).getWord());
            wordshow.setBackgroundColor(getResources().getColor(selectedWords.get(wordnumber).getColor()));
        }
    }

    private void alertView( String message ) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        dialog.setTitle("Hello")
                .setIcon(R.drawable.ic_launcher)
                .setMessage(message)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialoginterface, int i) {
                    }
                }).show()
                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        finish();
                    }
                });
    }

    private ArrayList<Word> selectwords(int numero){
        ArrayList selectedWords = new ArrayList<Word>();
        Random rn = new Random();
        for(int i = 0; i <numero; i++){
            int j = rn.nextInt(words.size());
            selectedWords.add(words.get(j));
            words.remove(j);
        }
        return selectedWords;
    }

    private void casoGeral(){
        String[] casos = {"animais", "casa", "comidas", "roupas", "corpo", "natureza"};
        selectedWords = new ArrayList<Word>();
        Random rn = new Random();
        for(String a : casos){
            lejson(a);
            ArrayList h = selectwords(numeroDePalavras/casos.length);
            selectedWords.addAll(h);
        }
    }

    private void lejson(String category){
        //Passa o arquivo para a memória
        category = category.toLowerCase();
        int a = this.getResources().getIdentifier("raw/"+category, "raw",getPackageName());
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        int ctr;
        try {
            InputStream inputStream = getResources().openRawResource(a);
            ctr = inputStream.read();
            while (ctr != -1) {
                output.write(ctr);
                ctr = inputStream.read();
            }
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            finish();
        }
        try {
            Log.v("ABC", "Step 1");
            // Parse the data into jsonobject to get original data in form of json.
            JSONObject jObject = new JSONObject(output.toString());
            Log.v("ABC", "Step 2");
            Log.v("Output", output.toString());
            Log.v("Categoria", category);
            JSONObject jObjectResult = jObject.getJSONObject("derdiedas");
            JSONArray jArray = jObjectResult.getJSONArray(category);
            int artigo;
            String word;
            words = new ArrayList<Word>();
            for (int i = 0; i < jArray.length(); i++) {
                Log.d("sd", "VEZ " + i);
                artigo = jArray.getJSONObject(i).getInt("a");
                word = jArray.getJSONObject(i).getString("p");
                words.add(new Word(artigo, word));
            }

        } catch (Exception e) {
            Log.e("JSON", "Não foi possibel Tratar o erro");
        }
    }

    private void setaBotoes() {
        die = (Button) findViewById(R.id.die);
        der = (Button) findViewById(R.id.der);
        das = (Button) findViewById(R.id.das);
        der.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkartigo(0);
            }
        });
        die.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkartigo(1);
            }
        });
        das.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkartigo(2);
            }
        });
    }
}



