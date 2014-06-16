package com.br.lm.der_die_das.app.Model;

/**
 * Created by Kaue on 6/15/14.
 */
public class Word
{
    private int artigo;
    private String word;
    private String description;

    public Word(int artigo, String word, String description){
        this.artigo = artigo;
        this.word = word;
        this. description = description;

    }

    public int getArtigo() {
        return artigo;
    }

    public String getWord() {
        return word;
    }

    public String getDescription() {
        return description;
    }
}
