package com.example.my_unium;

import static android.app.PendingIntent.getActivity;
import static com.example.my_unium.R.*;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class SignUpActivity extends AppCompatActivity {
    public static final String PERSON_PATH = "com.example.my_unium.person";

    protected EditText name, surname, birthdate, mtr, email, course, username, password, confirmPassword;
    protected TextView error;
    protected Button insert, clear, back;
    protected Calendar calendar;
    protected SimpleDateFormat simpleDateFormat;
    protected Intent home, login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_signup);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);  //attiva la barra di navigazione?

        name = findViewById(id.name);
        surname = findViewById(R.id.surname);
        birthdate = findViewById(R.id.birthdate);
        mtr = findViewById(R.id.mtr);
        email = findViewById(R.id.email);
        course = findViewById(R.id.course);
        username = findViewById(id.username);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirmPassword);
        error = findViewById(R.id.error);

        insert = findViewById(id.insert_text);
        clear = findViewById(id.clear_text);
        back = findViewById(id.back);

        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ITALY);

        insert.setOnClickListener(v -> {
            if (checkInput()) { // Se gli input sono validi
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.activity_result_title).setMessage(R.string.new_account).setPositiveButton(R.string.ok, (dialog, id) -> {
                    home = new Intent(SignUpActivity.this, HomeActivity.class);

                    try {
                        DatabaseClass.person.setName(name.getText().toString());
                        DatabaseClass.person.setSurname(surname.getText().toString());
                        DatabaseClass.person.setMtr(mtr.getText().toString());
                        DatabaseClass.person.setEmail(email.getText().toString());
                        DatabaseClass.person.setCourse(course.getText().toString());
                        DatabaseClass.person.setUsername(username.getText().toString());
                        DatabaseClass.person.setPassword(password.getText().toString());
                        DatabaseClass.person.setBirthday(birthdate.getText().toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    DatabaseClass.userExamList.clear();
                    startActivity(home); // Lancio l'intento
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        clear.setOnClickListener(v -> {
            name.setText("");
            surname.setText("");
            birthdate.setText("");
            mtr.setText("");
            email.setText("");
            course.setText("");
            username.setText("");
            password.setText("");
            confirmPassword.setText("");
        });

        back.setOnClickListener(v -> {
            login = new Intent(SignUpActivity.this, LoginActivity.class);
            startActivity(login); // Lancio l'intento
        });

        //Mostra il DatePickerFragment in caso di focus attivo
        birthdate.setOnFocusChangeListener((v, hasFocus) -> { if (hasFocus) showDialog(); });

        //Mostra il DatePickerFragment in caso di click
        birthdate.setOnClickListener(v -> showDialog());
    }

    // Funzione che mostra il DatePickerFragment tramite il DialogFragment
    protected void showDialog() {
        DialogFragment dialogFragment = DatePickerFragment.newInstance();
        dialogFragment.show(getSupportFragmentManager(), "dialog");
    }

    // Questa funzione viene eseguita in caso venga premuto il tasto per confermare
    protected void doPositiveClick(Calendar calendar) {
        Calendar today = Calendar.getInstance();

        int age = today.get(Calendar.YEAR) - calendar.get(Calendar.YEAR);

        if (today.get(Calendar.MONTH) < calendar.get(Calendar.MONTH)) {
            age--;
        } else
        if (today.get(Calendar.MONTH) == calendar.get(Calendar.MONTH)
                && today.get(Calendar.DAY_OF_MONTH) < calendar.get(Calendar.DAY_OF_MONTH)) {
            age--;
        }

        if (age >= 18) {
            birthdate.setText(simpleDateFormat.format(calendar.getTime()));
            birthdate.setError(null);
        } else {
            birthdate.setText("");
            birthdate.setError("Inserire la data, devi essere maggiorenne");
        }
    }

    // Questa funzione viene eseguita in caso venga premuto il tasto per annullare
    protected void doNegativeClick() {}



    protected boolean checkInput() {
        int errors = 0;

        if (name.getText() == null || name.getText().length() == 0) {
            errors++;
            name.setError("Inserire il nome");
        } else
            name.setError(null);

        if (surname.getText() == null || surname.getText().length() == 0) {
            errors++;
            surname.setError("Inserire il cognome");
        } else
            surname.setError(null);

        if (birthdate.getText() == null || birthdate.getText().length() == 0) {
            errors++;
            birthdate.setError("Inserire la data");
        } else
            birthdate.setError(null);

        if (mtr.getText().toString().length() != 5) {
            errors++;
            mtr.setError("Inserire il matricola");
        } else
            mtr.setError(null);

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
            errors++;
            email.setError("Inserire l'email");
        } else
            email.setError(null);

        if (course.getText().toString().length() == 0) {
            errors++;
            course.setError("Inserire corso");
        } else
            course.setError(null);

        if (username.getText().toString().length() == 0) {
            errors++;
            username.setError("Inserire username");
        } else
            username.setError(null);

        if (!(password.getText().toString().equals(confirmPassword.getText().toString()))) {
            errors++;
            password.setError("Le password non coincidono");
            confirmPassword.setError("Le password non coincidono");
        } else{
            if (password.getText().toString().length() == 0 || confirmPassword.getText().toString().length() == 0) {
                errors++;
                password.setError("Riempi entrambi i campi");
                confirmPassword.setError("Riempi entrambi i campi");
            } else{
                password.setError(null);
                confirmPassword.setError(null);
            }
        }

        if (errors > 0 ) {
            error.setVisibility(View.VISIBLE); // Mostro la scritta di errore generale
            error.setText(string.error);
        } else
            error.setVisibility(View.GONE); // Nascondo la scritta di errore generale

        return errors == 0; // True se non vi sono errori, altrimenti false
    }
}

