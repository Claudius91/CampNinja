package com.example.sven.ninja_test;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Dient als Downloader der Daten mithilfe des {@link JsonParser} die Daten ausgelesen werden
 * <p>
 * Created by Claudius on 07.06.2017.
 */

public class Downloader extends AsyncTask<Void, Integer, String> {

    Context c;
    String adress;
    ListView lv;
    ProgressDialog pd;

    /**
     * @param c      Kontext in dem sich der Adapter befindet
     * @param adress Adresse des PHP Files, mit der die Daten aus der Datenbank gelesen werden
     * @param lv     ListView in der die Daten angezeigt werden
     */
    public Downloader(Context c, String adress, ListView lv) {
        this.c = c;
        this.adress = adress;
        this.lv = lv;
    }

    /**
     * @param params
     * @return Liefert die Variable zurück in der die Verbindung zum Download der Daten gestartet wird
     */
    @Override
    protected String doInBackground(Void... params) {
        String data = downloadData();

        return data;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd = new ProgressDialog(c);
        pd.setTitle("Fetch Data");
        pd.setMessage("Fetching data...Please Wait");
        pd.show();
    }

    /**
     * @param s Beim erfolgreichen Empfangen der Daten wandelt man die Daten mit dem {@link JsonParser} um
     *          ansonsten bekommt man einen Toast ausgegeben
     */
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        pd.dismiss();

        if (s != null) {
            JsonParser p = new JsonParser(c, lv, s);
            p.execute();
        } else {
            Toast.makeText(c, "Unable to download data", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    //Startet die Verbindung zur URL
    private String downloadData() {
        InputStream is = null;
        String line = null;

        try {
            URL url = new URL(adress);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            is = new BufferedInputStream(con.getInputStream());

            BufferedReader br = new BufferedReader((new InputStreamReader(is)));

            StringBuffer sb = new StringBuffer();

            if (br != null) {
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
            } else return null;

            return sb.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
