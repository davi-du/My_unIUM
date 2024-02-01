package com.example.my_unium;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class LoginActivity extends AppCompatActivity {
    protected EditText username, password;
    protected Button login, signUp;
    protected Intent home, sign;
    //protected Person person;
    protected TextView error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.user);
        password = findViewById(R.id.pw);
        error = findViewById(R.id.error);

        login = findViewById(R.id.login);
        signUp = findViewById(R.id.signUp);

        login.setOnClickListener(v -> {
            if (checkInput()) { // Se gli input sono validi
                home = new Intent(LoginActivity.this, HomeActivity.class);

                DatabaseClass.person.setName("Pinco");
                DatabaseClass.person.setSurname("Pallo");
                DatabaseClass.person.setCourse("Computer science");
                DatabaseClass.person.setEmail("pincopallo@gmail.com");
                DatabaseClass.person.setBirthday("23/02/1995");
                DatabaseClass.person.setPassword("qwe");
                DatabaseClass.person.setMtr("12345");
                DatabaseClass.person.setUsername("ppallo");

                DatabaseClass.userExamList.clear();

                Exam primo = new Exam();
                primo.setName("IUM");
                primo.setResult(31);
                primo.setCfu(6);
                DatabaseClass.userExamList.add(primo);

                Exam secondo = new Exam();
                secondo.setName("Reti di Calcolatori");
                secondo.setResult(27);
                secondo.setCfu(9);
                DatabaseClass.userExamList.add(secondo);

                startActivity(home); // Lancio l'intento
           }
        });

        //se clicco su registrazione mi manda all'activity apposita
        signUp.setOnClickListener(v -> {
                sign = new Intent(this,SignUpActivity.class);
                startActivity(sign); //Lancio l'intento
        });
    }


    protected boolean checkInput() {
        int errors = 0;

        if (username.getText().toString().length() == 0 || username.getText() == null) {
            errors++;
            username.setError("Enter username");
        } else
            username.setError(null);

        if (password.getText().toString().length() == 0 || password.getText() == null) {
            errors++;
            password.setError("Enter password");
        } else
            password.setError(null);

        if (errors > 0 ) {
            error.setVisibility(View.VISIBLE); // Mostro la scritta di errore generale
            error.setText(R.string.error);
        } else
            error.setVisibility(View.GONE); // Nascondo la scritta di errore generale

        return errors == 0; // True se non vi sono errori, altrimenti false
    }
}