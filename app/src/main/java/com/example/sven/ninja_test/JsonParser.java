package com.example.sven.ninja_test;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Liest das JSON File aus und holt sich den Benutzernamen und den Highscore und fügt es der ArrayList hinzu
 * <p>
 * Created by Claudius on 07.06.2017.
 */

public class JsonParser extends AsyncTask<Void, Integer, Integer> {

    Context c;
    ListView lv;
    String data;

    /**
     * Die {@link ArrayList} von {@link Spieler}
     */
    ArrayList<Spieler> arrayList = new ArrayList<>();
    ProgressDialog pd;

    /**
     * @param c Kontext in dem sich der Adapter befindet
     * @param lv ListView in der die Daten angezeigt werden
     * @param data In dieser Variable werden die ganzen Infos der Datenbank gespeichert und später in ein JSONArray umgewandelt
     */
    public JsonParser(Context c, ListView lv, String data) {
        this.c = c;
        this.lv = lv;
        this.data = data;
    }

    @Override
    protected Integer doInBackground(Void... params) {
        return this.parse();
    }

    //Zeigt ein ProgressDialog an, während die Daten ausgelesen werden
    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        pd = new ProgressDialog(c);
        pd.setTitle("Parser");
        pd.setMessage("Parsing data...Please Wait");
        pd.show();
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);

        pd.dismiss();

        if (integer != null) {

            JsonAdapter myAdapter = new JsonAdapter(c, R.layout.row, arrayList);
            lv.setAdapter(myAdapter);

/*
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //Snackbar.make(view,statistik.get(position),Snackbar.LENGTH_SHORT).show();
                }
            });*/
        } else {
            Toast.makeText(c, "Unable to parse", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Erstellt ein JSONArray zum Auslesen und schreibt die Infos in eine Stringvariable
     *
     * @throws JSONException
     */
    private int parse() {
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject = null;

            arrayList.clear();

            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);
                String highscore = jsonObject.getString("highscore");
                String name = jsonObject.getString("benutzername");
                arrayList.add(new Spieler(name, highscore));
            }

            return 1;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
