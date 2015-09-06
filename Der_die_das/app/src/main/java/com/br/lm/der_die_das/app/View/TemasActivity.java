package com.br.lm.der_die_das.app.View;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.br.lm.der_die_das.app.R;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


public class TemasActivity extends Activity implements View.OnClickListener {
    public static int[] temas = {R.id.btn_todos,R.id.btn_casa, R.id.btn_adjetivos, R.id.btn_animais, R.id.btn_natureza, R.id.btn_comidas, R.id.btn_roupas, R.id.btn_corpo};
    private Button[] botoes= new Button[temas.length];
    private int[] ptn;
    int chaveDoTema;
    int pontuacao = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ptn = parseToArrayOfInteger(readFromFile());
        Log.e("aess", readFromFile());
        setContentView(R.layout.activity_temas);
        setBotoes();
        colorThemes();
    }

    private void setBotoes(){
        for(int i=0;i<temas.length;i++) {
            botoes[i] = (Button) findViewById(temas[i]);
            botoes[i].setOnClickListener(this);
        }
    }


    private void writeToFile(String data) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput("opt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    private String readFromFile() {

        String ret = "";

        try {
            InputStream inputStream = openFileInput("opt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            generateFile();
            return readFromFile();
        } catch (IOException e) {
            generateFile();
            return readFromFile();
        }
        return ret;
    }

    private void generateFile(){
        String txt = "";
        for(int i=0;i<temas.length;i++){
            txt += "0 ";
        }
        writeToFile(txt);
    }

    private int[] parseToArrayOfInteger(String a){
        String[] b = a.split(" ");
        int[] resp = new int[b.length];
        for(int i=0;i<b.length;i++){
            resp[i] = Integer.parseInt(b[i]);
        }
        return resp;
    }

    private void colorThemes(){
        int i = 0;
        for(Button b : botoes){
            int pontuacao = ptn[i];
            if(pontuacao>=5 && pontuacao<10) {
                b.getBackground().setColorFilter(getResources().getColor(R.color.bronze), PorterDuff.Mode.MULTIPLY);
            }else if(pontuacao>=10 && pontuacao<15){
                b.setBackgroundColor(getResources().getColor(R.color.prata));
            }else if(ptn[i]>=15){
                b.setBackgroundColor(getResources().getColor(R.color.ouro));
            }
            i++;
        }
    }

    private int getNumeroDePalavras(int pontos){
        if(pontos<5){
            return 5;
        }
        if(pontos<10){
            return 10;
        }
        if(pontos<15){
            return 15;
        }
        return 18;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        for(int i=0;i<temas.length; i++){
            if(id == temas[i]){
                Intent b = new Intent(getApplicationContext(), GameActivity.class);
                b.putExtra("tema", ((Button) findViewById(temas[i])).getText());
                b.putExtra("numeroDePalavras",getNumeroDePalavras(ptn[i]));
                startActivityForResult(b, 1);
                Log.v("PONTOS", "SUA PONTUACAO FOI " + pontuacao);
                chaveDoTema = i;

            }
        }
    }

    private String IntegerArrayToString(int[] a){
        String txt = "";
        for(int i : a){
            txt += i + " ";
        }
        return txt;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==1){
            if(resultCode== RESULT_OK){
                pontuacao = data.getIntExtra("Acertos",0);
                ptn[chaveDoTema] = pontuacao;
                writeToFile(IntegerArrayToString(ptn));
                colorThemes();
            }
        }
    }
}
