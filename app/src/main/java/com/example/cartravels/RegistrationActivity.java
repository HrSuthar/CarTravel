package com.example.cartravels;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegistrationActivity extends AppCompatActivity {
    EditText fullName, pass, confPass, contact, email,city;
    RadioGroup radioGroup;
    RadioButton radioButton;
    PersonalInfo pInfo;
    Spinner type_spinner;
    DatabaseReference myRef,loginRef;
    Button register_btn;
    String type_String, str_Type_forDb, str_Add_Db;
    String[] spinner_items;
    SharedPreferences pref;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_registration);
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            str_Type_forDb = "Registered_Customers";
            str_Add_Db = "customer: 0";
            spinner_items= new String[]{"Customer", "Travel Agent"};

            fullName= findViewById(R.id.editTextName);
            radioGroup= findViewById(R.id.radioGroup);
            radioButton = findViewById(radioGroup.getCheckedRadioButtonId());
            pass = findViewById(R.id.editTextPassword);
            confPass = findViewById(R.id.editTextConfirmPass);
            email = findViewById(R.id.editTextEmail);
            contact = findViewById(R.id.editTextContactReg);
            city=findViewById(R.id.editTextCity);
            register_btn = findViewById(R.id.buttonRegister);
            pref = getSharedPreferences("userPreferences",MODE_PRIVATE);
            type_spinner = findViewById(R.id.Usertype_spinner);

            loginRef = FirebaseDatabase.getInstance().getReference().child("Login Credential");
            mAuth = FirebaseAuth.getInstance();
            pInfo = new PersonalInfo();

            ArrayAdapter<String> myAdapt= new ArrayAdapter<>(RegistrationActivity.this, android.R.layout.simple_list_item_1, spinner_items);
            myAdapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            type_spinner.setAdapter(myAdapt);
            type_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    type_String = type_spinner.getSelectedItem().toString().trim();
                    if(type_String.equals("Travel Agent")) {
                        str_Type_forDb = "Registered_TravelAgents";
                        str_Add_Db = "travelAgent: 0";
                    }
                    myRef = FirebaseDatabase.getInstance().getReference().child(str_Type_forDb);
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // TODO Auto-generated method stub
                    Toast.makeText(RegistrationActivity.this,"Non selected",Toast.LENGTH_SHORT).show();
                }
            });
    }

    public void Register(View view) {

        if (ValidEntry()) {
            pInfo.setFullName(fullName.getText().toString());
            pInfo.setGender(radioButton.getText().toString().trim());
            pInfo.setPassword(pass.getText().toString().trim());
            pInfo.setEmail(email.getText().toString().trim());
            pInfo.setContact(Long.parseLong(contact.getText().toString().trim()));
            pInfo.setUserType(type_String);
            pInfo.setCityName(city.getText().toString().trim());

            createAccount();
        }else
            Toast.makeText(this,"Invalid Data Entry ",Toast.LENGTH_SHORT).show();
    }

    void createAccount() {
        mAuth.createUserWithEmailAndPassword(email.getText().toString(), pass.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            openLoginActivity(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegistrationActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            openLoginActivity(null);
                        }
                    }
                });
    }

    private void openLoginActivity(FirebaseUser account) {
        final long[] cnt_Reg = {0};
        if(account!=null) {
                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        cnt_Reg[0] = dataSnapshot.getChildrenCount();
                        ++cnt_Reg[0];
                        myRef.child(str_Add_Db+cnt_Reg[0]).setValue(pInfo);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            loginRef.child(email.getText().toString().trim().replace(".","_"))
                    .setValue(pass.getText().toString().trim()+"#"+type_String);
            SharedPreferences.Editor editor= pref.edit();
            editor.putString("UserType",type_String);
            editor.putBoolean("LoggedIn",true);
            editor.apply();
            Toast.makeText(getBaseContext(), "Login successful", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(RegistrationActivity.this, HomePage.class));
            finish();

            }else
                Toast.makeText(this,"U Didn't signed in",Toast.LENGTH_LONG).show();
    }

    private boolean ValidEntry(){
        return ValidOtherData();
    }
    private boolean ValidOtherData(){
        return isValidEmail() & isValidMobile() & isValidPass() & isValidName() & isValidCity();
    }
    private boolean isValidName(){
        if(!TextUtils.isEmpty(fullName.getText())) {
            fullName.setError(null);
            return true;
        }else {
            fullName.setError("This Field Cannot Be Blank");
            return false;
        }
    }
    private boolean isValidEmail() {
        if (!TextUtils.isEmpty(email.getText()) & Patterns.EMAIL_ADDRESS.matcher(email.getText()).matches()) {
            email.setError(null);
            return true;
        }else {
            email.setError("Enter Proper Email");
            return false;
        }
    }
    private boolean isValidMobile() {
        if(!TextUtils.isEmpty(contact.getText()) & Patterns.PHONE.matcher(contact.getText()).matches()) {
            contact.setError(null);
            return true;
        }else {
            contact.setError("This Field Cannot Be Blank");
            return false;
        }
    }
    private boolean isValidPass(){
        if(pass.getText().toString().length() >= 8 & confPass.getText().toString().equals(pass.getText().toString())){
            pass.setError(null);
            return true;
        }else {
            pass.setError("Field Should Has Atleast 8 Char");
            return false;
        }
    }
    private boolean isValidCity(){
        if(!TextUtils.isEmpty(city.getText())){
            city.setError(null);
            return true;
        }else {
            city.setError("This Field Cannot Be Blank");
            return false;
        }
    }
}