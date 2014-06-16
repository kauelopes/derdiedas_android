package com.br.lm.der_die_das.app.Model;

import android.graphics.Color;

import com.br.lm.der_die_das.app.R;

/**
 * Created by Kaue on 6/15/14.
 */
public class Word
{
    private int artigo;
    private String word;
    private String description;

    public Word(int artigo, String word){
        this.artigo = artigo;
        this.word = word;
    }

    public int getArtigo() {
        return artigo;
    }

    public String getArtigoS(){
        if(artigo == 0) {
            return "Der";
        }
        if(artigo == 1) {
            return "Die";
        }
        if(artigo == 2) {
            return "Das";
        }else return "";
    }

    public String getWord() {
        return word;
    }

    public String getDescription() {
        return description;
    }

    public int getColor(){
        if(artigo == 0) {
            return R.color.der;
        }
        if(artigo == 1) {
            return R.color.die;
        }
        if(artigo == 2) {
            return R.color.das;
        }else return R.color.das;
    }
}
