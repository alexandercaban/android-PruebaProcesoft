package com.brycolram.prueba;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginActivity extends AppCompatActivity {

    private GoogleSignInOptions googleSignInOptions;
    private GoogleSignInClient googleSignInClient;
    private int GOOGLE_SIGN_IN = 7;
    private FirebaseAuth firebaseAuth;
    EditText txtLogin;
    EditText txtContrasena;
    Button fab, btnCrearCuenta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);

        fab = findViewById(R.id.btnIniciar);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                accionIngresar(view);
            }
        });

        btnCrearCuenta = findViewById(R.id.btn_ir_crearCuenta);
        btnCrearCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegistroUsuarioActivity.class);
                startActivity(intent);
            }
        });

        txtLogin = findViewById(R.id.txtLogin);
        txtContrasena = findViewById(R.id.txtContrasena);
        firebaseAuth = FirebaseAuth.getInstance();

    }

    public void accionIngresar(final View view) {
        try {
            if (txtLogin.getText().toString().equals("")) {
                Snackbar.make(view, "Digite Login", Snackbar.LENGTH_LONG).show();
                return;
            }
            if (txtContrasena.getText().toString().equals("")) {
                Snackbar.make(view, "Digite Contraseña", Snackbar.LENGTH_LONG).show();
                return;
            }

            firebaseAuth.signInWithEmailAndPassword(txtLogin.getText().toString(), txtContrasena.getText().toString()).
                    addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Snackbar.make(view, "Sesion iniciada correctamente", Snackbar.LENGTH_LONG).show();
                                FirebaseUser user = firebaseAuth.getCurrentUser();
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));

                            } else {
                                Snackbar.make(view, "No ha sido posible iniciar sesión " + task.getException().getMessage(), Snackbar.LENGTH_LONG).show();
                            }
                        }
                    });
        } catch (Exception e) {
            Log.e("ERROR", e.getMessage());
        }
    }


    private void updateUI(FirebaseUser user) {
        if (user != null) {
            String sbUser = user.getDisplayName();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        updateUI(null);
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        updateUI(currentUser);
    }


    private void signIn() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, GOOGLE_SIGN_IN);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GOOGLE_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.e("ERROR", "Google sign in failed", e);
                // ...
            }
        }
        //if(requestCode == FACEBOOK_SIGN_IN){
        // mCallbackManager.onActivityResult(requestCode, resultCode, data);
        //}
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.i("INFO", "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.i("INFO", "signInWithCredential:success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.e("ERROR", "signInWithCredential:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }

                    //...
                });
    }


}
