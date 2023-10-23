package com.example.applistview;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {
    // Model: Record (intents=puntuació, nom)
    static class Record {
        public int intents;
        public String nom;

        public Record(int _intents, String _nom ) {
            intents = _intents;
            nom = _nom;
        }
    }
    // Model = Taula de records: utilitzem ArrayList
    ArrayList<Record> records;

    // ArrayAdapter serà l'intermediari amb la ListView
    ArrayAdapter<Record> adapter;
    ArrayList<String> nomsRandom = new ArrayList<>();
    ArrayList<String> cognomsRandom = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cognomsRandom.add("Suarez");
        cognomsRandom.add("Perez");
        cognomsRandom.add("Costa");
        cognomsRandom.add("Garcia");
        cognomsRandom.add("Lopez");
        cognomsRandom.add("Fernandez");
        cognomsRandom.add("Rossi");
        cognomsRandom.add("Vitale");
        cognomsRandom.add("Coppola");
        cognomsRandom.add("Leone");

        nomsRandom.add("Andrea");
        nomsRandom.add("Juan");
        nomsRandom.add("Jesus");
        nomsRandom.add("David");
        nomsRandom.add("Lorenzo");
        nomsRandom.add("Pablo");
        nomsRandom.add("Andres");
        nomsRandom.add("Elena");
        nomsRandom.add("Francisco");
        nomsRandom.add("Sebastian");


        // Inicialitzem model
        records = new ArrayList<Record>();
        // Afegim alguns exemples
        records.add(new Record(33, "Manolo"));
        records.add(new Record(12, "Pepe"));
        records.add(new Record(42, "Laura"));

        // Inicialitzem l'ArrayAdapter amb el layout pertinent
        adapter = new ArrayAdapter<Record>( this, R.layout.list_item, records )
        {
            @Override
            public View getView(int pos, View convertView, ViewGroup container)
            {
                // getView ens construeix el layout i hi "pinta" els valors de l'element en la posició pos
                if( convertView==null ) {
                    // inicialitzem l'element la View amb el seu layout
                    convertView = getLayoutInflater().inflate(R.layout.list_item, container, false);
                }
                // Creamos el imageView para cambiar la imagen luego
                //ImageView imagen = findViewById(R.id.fotoPerfil);
                switch (getRandomNumber()){
                    case 0: ((ImageView) convertView.findViewById(R.id.fotoPerfil)).setImageResource(R.drawable.img1); break;
                    case 1: ((ImageView) convertView.findViewById(R.id.fotoPerfil)).setImageResource(R.drawable.img2); break;
                    case 2: ((ImageView) convertView.findViewById(R.id.fotoPerfil)).setImageResource(R.drawable.img3); break;
                }
                Log.i("INFO","El número introduït és: " + getRandomNumber());
                // "Pintem" valors (també quan es refresca)
                ((TextView) convertView.findViewById(R.id.nom)).setText(getItem(pos).nom);
                ((TextView) convertView.findViewById(R.id.intents)).setText(Integer.toString(getItem(pos).intents));
                return convertView;
            }
        };
        // busquem la ListView i li endollem el ArrayAdapter
        ListView lv = (ListView) findViewById(R.id.recordsView);
        lv.setAdapter(adapter);

        // botó per afegir entrades a la ListView
        Button b = (Button) findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i=0;i<500;i++) {
                    int rand1 = (int)(Math.random() * 10);
                    int rand2 = (int)(Math.random() * 10);
                    records.add(new Record((int)(Math.random() * 100), nomsRandom.get(rand1) + " " + cognomsRandom.get(rand2)));
                }
                // notificar l'adapter dels canvis al model
                adapter.notifyDataSetChanged();
            }
        });
        Button ordenar = findViewById(R.id.button2);
        ordenar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Sort the records ArrayList based on the 'intents' field in ascending order
                Collections.sort(records, new Comparator<Record>() {
                    @Override
                    public int compare(Record record1, Record record2) {
                        return Integer.compare(record1.intents, record2.intents);
                    }
                });

                // Notify the adapter of the data change
                adapter.notifyDataSetChanged();
            }
        });

    }
    public int getRandomNumber() {
        return (int)(Math.random()*3);
    }
}