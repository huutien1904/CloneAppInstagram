package com.example.cloneappinstagram;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {


    private EditText username, fullname,email,password;
    private Button register;
    private TextView txt_login;

    FirebaseAuth auth;
    DatabaseReference reference;
    ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();
        auth = FirebaseAuth.getInstance();

        txt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
            }
        });
//        click button register
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd = new ProgressDialog(RegisterActivity.this);
                pd.setMessage("Please wait ...");
                Log.i("button register run", "function register");
                pd.show();

                String str_username = username.getText().toString();
                String str_fullname = fullname.getText().toString();
                String str_email = email.getText().toString();
                String str_password = password.getText().toString();
//                check validate
                if(TextUtils.isEmpty(str_username) || TextUtils.isEmpty(str_fullname)
                        || TextUtils.isEmpty(str_email) ||TextUtils.isEmpty(str_password) ){
                    Toast.makeText(RegisterActivity.this, "ALL fileds are requied", Toast.LENGTH_SHORT).show();
//                    pd.dismiss();
                }else if(str_password.length() < 6){
                    Toast.makeText(RegisterActivity.this, "Password must have 6 characters", Toast.LENGTH_SHORT).show();
//                    pd.dismiss();
                }else{
                    Log.i("button register run", "function register");
//                    run function register
                    register(str_username,str_fullname,str_email,str_password);
//                    pd.dismiss();
                }
            }
        });
    }

    private void initView() {
        username = findViewById(R.id.username);
        fullname = findViewById(R.id.fullname);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        register = findViewById(R.id.register);
        txt_login = findViewById(R.id.txt_login);
    }

    private void register(String username,String fullname, String email, String password){
        Log.i("check", "register: ");

        auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(
                        RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        task.getException().getMessage();
                        Log.i("TASK", task.toString());
//                        Log.i("TASK user", auth.getCurrentUser().toString());
                        if(task.isSuccessful()){
                            FirebaseUser firebaseUser = auth.getCurrentUser();
                            Log.i("TASK user", auth.getCurrentUser().toString());
                            String userId = firebaseUser.getUid();

                            reference = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);

                            HashMap<String ,Object> hashMap = new HashMap<>();
                            hashMap.put("id",userId);
                            hashMap.put("username",username.toLowerCase());
                            hashMap.put("fullname",fullname);
                            hashMap.put("bio","");
                            hashMap.put("imageurl","https://firebasestorage.googleapis.com/v0/b/instagramapp-b7821.appspot.com/o/placehoder.png?alt=media&token=c5af7d29-e2d9-4468-b04e-2282deb3a446");

                            reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        pd.dismiss();
                                        Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                    }
                                }
                            });
                        }else{
                            pd.dismiss();
                            Toast.makeText(RegisterActivity.this, "You can't register with th?? email or password", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}