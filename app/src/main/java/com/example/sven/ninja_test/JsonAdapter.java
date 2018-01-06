package com.example.sven.ninja_test;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Mit dieser Klasse wird der Name und Highscore des Spielers in eine ListView dargestellt
 * <p>
 * Created by Claudius on 08.06.2017.
 */

public class JsonAdapter extends ArrayAdapter<Spieler> {

    ArrayList<Spieler> arrayList = new ArrayList<>();

    /**
     * Erzeugt einen ArrayAdapter, welcher den Datenbankinhalt mit einer View verknuepft
     *
     * @param context  Kontext in dem sich der Adapter befindet
     * @param resource Eigene Layout Ressource
     * @param objects  Ein Objekt der ArrayList vom Typ Spieler
     */
    public JsonAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<Spieler> objects) {
        super(context, resource, objects);
        arrayList = objects;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    /**
     *
     * @param position Gibt die Position des View Elements zurück
     * @param convertView View Anzeigen der Daten
     * @param parent Viewgroup der View
     * @return Gibt eine View zurück
     */
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row, parent, false);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.highscore = (TextView) convertView.findViewById(R.id.highscore);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.name.setText(arrayList.get(position).getName());
        holder.highscore.setText(arrayList.get(position).getHighscore());
        return convertView;
    }

    class ViewHolder {
        TextView name;
        TextView highscore;
    }
}
