package com.example.my_unium;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddActivity extends AppCompatActivity {
    public static final String PERSON_PATH = "com.example.my_unium.person";
    protected EditText examName, cfu, grade;
    protected TextView error;
    protected Button insert, clear, back;
    protected Exam exam;
    protected Intent home, anagraphic;
    //protected Person person;
    protected Serializable object;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exam);

        examName = findViewById(R.id.examName);
        cfu = findViewById(R.id.cfu);
        grade = findViewById(R.id.grade);
        error = findViewById(R.id.error);
        back = findViewById(R.id.back);

        insert = findViewById(R.id.insert_text);
        clear = findViewById(R.id.clear_text);

        exam = new Exam();

        insert.setOnClickListener(v -> {
            if (checkInput()) { // Se gli input sono validi
                try {
                    exam.setResult(Integer.parseInt(grade.getText().toString()));
                    exam.setCfu(Integer.parseInt(cfu.getText().toString()));
                    exam.setName(examName.getText().toString());
                    DatabaseClass.userExamList.add(exam);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                home = new Intent(AddActivity.this, HomeActivity.class);
                startActivity(home); // Lancio l'intento
            }
        });

        back.setOnClickListener(v -> {
            home = new Intent(AddActivity.this, HomeActivity.class);
            startActivity(home);
        });

        clear.setOnClickListener(v -> {
            examName.setText("");
            cfu.setText("");
            grade.setText("");
        });

    }

    protected boolean checkInput() {
        int errors = 0;

        if (examName.getText() == null || examName.getText().toString().length() == 0) {
            errors++;
            examName.setError("Inserire il nome dell'esame");
        } else
            examName.setError(null);

        if (cfu.getText().toString().length() == 0 ||
                Integer.parseInt(cfu.getText().toString()) > 12 || Integer.parseInt(cfu.getText().toString()) < 1) {
            errors++;
            cfu.setError("Inserire il numero di cfu");
        } else
            cfu.setError(null);

        if (grade.getText().toString().length() == 0 ||
                Integer.parseInt(grade.getText().toString()) > 31 || Integer.parseInt(grade.getText().toString()) < 0) {
            errors++;
            grade.setError("Inserire il voto");
        } else
            grade.setError(null);

        if (errors > 0 ) {
            error.setVisibility(View.VISIBLE); // Mostro la scritta di errore generale
            error.setText(R.string.error);
        } else
            error.setVisibility(View.GONE); // Nascondo la scritta di errore generale

        return errors == 0; // True se non vi sono errori, altrimenti false
    }

}


