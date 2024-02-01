package com.example.my_unium;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;

public class HomeActivity extends AppCompatActivity implements RecyclerViewAdapter.ItemClickListener{
    public static final String PERSON_PATH = "com.example.my_unium.person";
    RecyclerViewAdapter adapter;
    protected TextView userInfo, course, gpaText, cfuText, eggText;
    protected Button newExam, logout, profile, delete;
    protected Intent anagraphic, addExam, logOut, deleteExam;
    protected Float gpa = 0.0f, egg = 0.0f;
    private Integer countCfu = 0;
    private float countGpa = 0, countEgg = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //anagrafica, media e voto stimato
        userInfo = findViewById(R.id.anagraph);
        course = findViewById(R.id.course);
        newExam = findViewById(R.id.newExam);
        gpaText = findViewById(R.id.gpa);
        cfuText = findViewById(R.id.cfuCount);
        eggText = findViewById(R.id.egg);
        logout = findViewById(R.id.logout);
        profile = findViewById(R.id.profile);
        delete = findViewById(R.id.deleteExam);

        anagraphic = getIntent(); // Recupero l'intento

        String info = DatabaseClass.person.getName().concat( " ").concat(DatabaseClass.person.getSurname().concat(" ").concat(DatabaseClass.person.getMtr()));
        userInfo.setText(info);
        course.setText(DatabaseClass.person.getCourse());

        for (Exam tmp:DatabaseClass.userExamList) {
                countGpa = countGpa + tmp.getResult();
                countCfu = countCfu + tmp.getCfu();
                countEgg = countEgg + (tmp.getResult() * tmp.getCfu());
        }

        gpa = countGpa / (DatabaseClass.userExamList.size());
        egg = ((countEgg / countCfu) * 110) / 30;

        //stampa post conti
        if (gpa <= 30)
            gpaText.setText("Grade point average: " + gpa.toString());
        else if (gpa > 30)
            gpaText.setText("Grade point average: 30L");
        else
            gpaText.setText("Grade point average: 0");

        if (egg <= 110)
            eggText.setText("Estimated graduation grade: " + egg.toString());
        else if (egg > 110)
            eggText.setText("Estimated graduation grade: 110L");
        else
            eggText.setText("Estimated graduation grade: 0");

        cfuText.setText("CFU: " + countCfu.toString() + "/180");

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rvExams);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerViewAdapter(this, DatabaseClass.userExamList);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        //aggiunta dell'esame
        int finalCountCfu = countCfu;
        newExam.setOnClickListener(v -> {
            if (finalCountCfu < 180){
                addExam = new Intent(HomeActivity.this, AddActivity.class);
                startActivity(addExam);
            }
        });

        delete.setOnClickListener(v -> {
            if(DatabaseClass.userExamList.size() > 0) {
                deleteExam = new Intent(HomeActivity.this, DeleteActivity.class);
                startActivity(deleteExam);
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Error").setMessage("You must insert exams before!").setPositiveButton(R.string.ok, (dialog, id) -> {});
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        logout.setOnClickListener(v -> {
            logOut = new Intent(HomeActivity.this, LoginActivity.class);
            DatabaseClass.userExamList.clear();
            startActivity(logOut);
        });

        profile.setOnClickListener(v -> {
            String title = DatabaseClass.person.getUsername();
            String data = DatabaseClass.person.getName() + " " + DatabaseClass.person.getSurname() + "\n" +
                    DatabaseClass.person.getCourse() + " (" + DatabaseClass.person.getMtr() + ") \n" +
                    DatabaseClass.person.getBirthday() + "\n" +
                    DatabaseClass.person.getEmail();

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(title).setMessage(data).setPositiveButton(R.string.ok, (dialog, id) -> {});
            AlertDialog dialog = builder.create();
            dialog.show();
        });

    }

    @Override
    public void onItemClick(View view, int position) {
        Exam tmp = DatabaseClass.userExamList.get(position);
        String title = tmp.getName();
        String data;
        if (tmp.getResult() == 31)
            data = "Grade: 30L" + "\n" + "CFU: " + tmp.getCfu();
        else
            data = "Grade: " + tmp.getResult() + "\n" + "CFU: " + tmp.getCfu();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title).setMessage(data).setPositiveButton(R.string.ok, (dialog, id) -> {});
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
