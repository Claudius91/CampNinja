package com.example.sven.ninja_test.weapons;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sven.ninja_test.MainActivity;
import com.example.sven.ninja_test.R;
import com.example.sven.ninja_test.WeaponActivity;

import java.util.ArrayList;

import android.os.Handler;

/**
 * Dient als Kauf und Wechseln der einzelnen Waffen
 * <p>
 * Created by Julian on 09.06.2017.
 */

public class MyGridAdapter extends ArrayAdapter implements AdapterView.OnItemClickListener {
    private Context ctx;

    /**
     * Die {@link ArrayList} von {@link Weapon}
     */
    private ArrayList<Weapon> wpns = new ArrayList<>();

    /**
     *
     * @param context  Kontext in dem sich der Adapter befindet
     * @param resource Eigene Layout Ressource
     * @param objects  Eine ArrayList mit den zur Verfügung stehenden Waffen
     */
    public MyGridAdapter(Context context, int resource, ArrayList<Weapon> objects) {
        super(context, resource, objects);

        ctx = context;
        wpns = objects;
    }

    @Override
    public int getCount() {
        int count = wpns.size();
        return count;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.weapon_item_activity, null);
        ImageView imgView = (ImageView) convertView.findViewById(R.id.grid_item);
        imgView.setImageResource(wpns.get(position).getImg());


        return convertView;
    }

    // Zeigt den AlertDialog zum Kauf der Waffe an oder dient zur Auswahl der freigeschalteten Waffen
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        final int value = i;

        Handler handler = new Handler(ctx.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {

                if (wpns.get(value).isUnlocked()) {
                    SharedPreferences preferences = ctx.getSharedPreferences("Lokal Data", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putInt("waffe", value).apply();

                    Toast.makeText(ctx, wpns.get(value).getName() + " selected", Toast.LENGTH_SHORT).show();


                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(ctx).create();
                    alertDialog.setTitle("Buying ?");
                    alertDialog.setMessage("Do you want to buy this weapon for " + wpns.get(value).getValue() + " coins ?");
                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "BUY",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    SharedPreferences preferences = ctx.getSharedPreferences(MainActivity.PREFERENCE, Context.MODE_PRIVATE);
                                    int coins = preferences.getInt("coins", 0);
                                    int price = wpns.get(value).getValue();

                                    if (coins >= price) {
                                        SharedPreferences.Editor editor = preferences.edit();
                                        int actualCoins = coins - price;
                                        editor.putInt("coins", actualCoins);

                                        ((WeaponActivity) ctx).update(String.valueOf(actualCoins));

                                        wpns.get(value).unlock();
                                        editor.putBoolean(wpns.get(value).getName(), true);
                                        editor.apply();

                                        notifyDataSetChanged();
                                        Toast.makeText(ctx, wpns.get(value).getName() + " bought", Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    } else {
                                        Toast.makeText(ctx, "More coins needed", Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    }
                                }
                            });
                    alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "CANCEL",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });

                    TextView title = new TextView(ctx);
                    title.setText("Buying ?");
                    title.setGravity(Gravity.CENTER);
                    title.setTextSize(30);
                    alertDialog.setCustomTitle(title);
                    alertDialog.show();
                    TextView dialogMessage = (TextView) alertDialog.findViewById(android.R.id.message);
                    dialogMessage.setTextSize(25);

                }


            }
        });

    }
}
