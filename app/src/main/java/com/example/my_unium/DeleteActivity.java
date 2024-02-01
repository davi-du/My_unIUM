package com.example.my_unium;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;

public class DeleteActivity extends AppCompatActivity implements RecyclerViewAdapter.ItemClickListener{
    public static final String PERSON_PATH = "com.example.my_unium.person";
    RecyclerViewAdapter adapter;
    protected Button back;
    protected Intent home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        //anagrafica, media e voto stimato
        back = findViewById(R.id.back);

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rvExams);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerViewAdapter(this, DatabaseClass.userExamList);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        back.setOnClickListener(v -> {
                home = new Intent(DeleteActivity.this, HomeActivity.class);
                startActivity(home);
        });

    }

    @Override
    public void onItemClick(View view, int position) {
        Exam tmp = DatabaseClass.userExamList.get(position);
        String data = "You're sure that you want to delete " + tmp.getName() + "?";
        String title = "ATTENTION!";

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(data);
        builder.setPositiveButton("Confirm",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseClass.userExamList.remove(position);
                        home = new Intent(DeleteActivity.this, HomeActivity.class);
                        //home.putExtra(PERSON_PATH, person); // Passo dati all'intento
                        startActivity(home);
                    }
                });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
