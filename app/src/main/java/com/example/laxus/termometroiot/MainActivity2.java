package com.example.laxus.termometroiot;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class MainActivity2 extends AppCompatActivity {

    String lectura;
    private static final String TAG = "MainActivity2";
    FirebaseDatabase database;
    DatabaseReference myRef;

    ListView listView;
    ArrayList<Integer> arrayList = new ArrayList<>();;
    ArrayAdapter<Integer> arrayAdapter;

    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        listView = (ListView) findViewById(R.id.listview1);
        arrayAdapter = new ArrayAdapter<Integer>(com.example.laxus.termometroiot.MainActivity2.this,android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(arrayAdapter);


        database = FirebaseDatabase.getInstance();
        lectura = getIntent().getStringExtra("key");
        myRef = database.getReference(lectura);


        ValueEventListener postListener = new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                //Post post = dataSnapshot.getValue(Post.class);
                //Log.d(TAG, " "+dataSnapshot.getValue());
                arrayList.clear();

                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Integer value = postSnapshot.getValue(Integer.class);
                    arrayList.add(value);
                }

                arrayAdapter.notifyDataSetChanged();

                Log.d(TAG, " "+arrayList);


                // ...
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        };
        myRef.addValueEventListener(postListener);

    }
}
