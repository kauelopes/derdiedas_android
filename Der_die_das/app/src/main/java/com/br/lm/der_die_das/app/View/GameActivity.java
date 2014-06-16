package com.br.lm.der_die_das.app.View;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.br.lm.der_die_das.app.Model.Word;
import com.br.lm.der_die_das.app.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class GameActivity extends Activity {
    private ArrayList<Word> words;
    private ArrayList<Word> selectedWords;
    private TextView wordshow;
    private Button die, der, das;
    private int wordnumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        wordshow = (TextView) findViewById(R.id.Word);
        die = (Button) findViewById(R.id.die);
        der = (Button) findViewById(R.id.der);
        das = (Button) findViewById(R.id.das);
        setaBotoes();
        lejson();
        wordnumber = 0;
        joga();

    }



    private void joga() {
        if(wordnumber < (words.size())){
            wordshow.setBackgroundColor(0xff33b5e5);
            wordshow.setBackgroundColor(getResources().getColor(R.color.branco));
            wordshow.setText(words.get(wordnumber).getWord());
        }else super.finish();

    }

    private void checkartigo(int artigo) {
        if(artigo == words.get(wordnumber).getArtigo()){
            Log.v("Ok, Acertou", "Ok, Acertou" + words.size());
            wordnumber++;
            joga();
        }else{
            Log.v("Errou", "Errou");
            wordshow.setText(words.get(wordnumber).getArtigoS() + " " + words.get(wordnumber).getWord());
            wordshow.setBackgroundColor(getResources().getColor(words.get(wordnumber).getColor()));
        }
    }


    private void selectwords(){

    }




    private void lejson(){
        InputStream inputStream = getResources().openRawResource(R.raw.words);
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        int ctr;
        try {
            ctr = inputStream.read();
            while (ctr != -1) {
                output.write(ctr);
                ctr = inputStream.read();
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            // Parse the data into jsonobject to get original data in form of json.
            JSONObject jObject = new JSONObject(output.toString());
            JSONObject jObjectResult = jObject.getJSONObject("derdiedas");
            JSONArray jArray = jObjectResult.getJSONArray("words");
            int artigo;
            String word;
            words = new ArrayList<Word>();
            for (int i = 0; i < jArray.length(); i++) {
                artigo = jArray.getJSONObject(i).getInt("artigo");
                word = jArray.getJSONObject(i).getString("palavra");
                Log.v("Cat ID", artigo+ "");
                Log.v("Cat Name", word);
                words.add(new Word(artigo, word));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }





    private void setaBotoes() {

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
