package com.br.lm.der_die_das.app.View;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
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
            JSONObject jObjectResult = jObject.getJSONObject("Categories");
            JSONArray jArray = jObjectResult.getJSONArray("Category");
            String cat_Id = "";
            String cat_name = "";
            ArrayList<String[]> data = new ArrayList<String[]>();
            for (int i = 0; i < jArray.length(); i++) {
                cat_Id = jArray.getJSONObject(i).getString("cat_id");
                cat_name = jArray.getJSONObject(i).getString("cat_name");
                Log.v("Cat ID", cat_Id);
                Log.v("Cat Name", cat_name);
                data.add(new String[] { cat_Id, cat_name });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
