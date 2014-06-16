package com.br.lm.der_die_das.app.View;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;

public class GameActivity extends Activity {
    private ArrayList<Word> words;
    private TextView selectedWord;
    private Button die, der, das;
    private int wordnumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        selectedWord = (TextView) findViewById(R.id.Word);
        die = (Button) findViewById(R.id.die);
        der = (Button) findViewById(R.id.der);
        das = (Button) findViewById(R.id.das);
        setaBotoes();
        le();
        wordnumber = 0;
        joga();

    }

    private void setaBotoes() {
        die.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkartigo(0);
            }
        });
        der.setOnClickListener(new View.OnClickListener() {
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

    private void checkartigo(int artigo) {
        if(artigo == words.get(wordnumber).getArtigo()){
            joga();
            Log.v("Ok, Acertou", "Ok, Acertou"+ words.size());
        }else{
            joga();
            Log.v("Errou", "Errou");
        }
    }

    private void joga() {
        if(wordnumber < (words.size()-1)){
            selectedWord.setText(words.get(wordnumber).getWord());
            wordnumber++;
        }else super.finish();

    }


    public void le(){
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
}
