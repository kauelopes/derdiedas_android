package com.br.lm.der_die_das.app.View;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.br.lm.der_die_das.app.R;


public class TemasActivity extends Activity implements View.OnClickListener {
    private Button[] botoes= new Button[10];
    public static int[] temas = {R.id.btn_todos,R.id.btn_casa, R.id.btn_adjetivos, R.id.btn_animais, R.id.btn_escritorio, R.id.btn_comidas, R.id.btn_roupas, R.id.btn_profissoes, R.id.btn_esportes, R.id.btn_lugares };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_temas);
        setBotoes();
    }

    private void setBotoes(){
        for(int i=0;i<temas.length;i++) {
            botoes[i] = (Button) findViewById(temas[i]);
            botoes[i].setOnClickListener(this);
        }
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        for(int i=0;i<temas.length; i++){
            if(id == temas[i]){
                Intent b = new Intent(getApplicationContext(), GameActivity.class);
                b.putExtra("tema", ((Button) findViewById(temas[i])).getText());
                startActivity(b);
            }

        }

    }
}
