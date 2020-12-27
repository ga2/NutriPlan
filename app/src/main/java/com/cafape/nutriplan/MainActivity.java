package com.cafape.nutriplan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.QuickContactBadge;
import android.widget.Toast;

import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity
{
    private Context context;
    private Button activityMain_button_login;

    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;

    //todo plot 2
    //todo consigli in base alle patologie —> cambiare inserimento patologie con checkbox secondo file che ti ho inviato 3
    //todo databasebackup 4
    //todo immagini tabella bai 5
    //todo fingerprint 6
    //todo responsiveness 7

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this.getApplicationContext();

        setUiComponents();
        setListeners();
        biometricAuthentication();
    }

    public void biometricAuthentication() {
        if(checkBiometricSupport()) {
        //if(true) {
            executor = ContextCompat.getMainExecutor(this);
            biometricPrompt = new BiometricPrompt(MainActivity.this, executor, new BiometricPrompt.AuthenticationCallback()
            {
                @Override
                public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                    super.onAuthenticationError(errorCode, errString);
                    Toast.makeText(getApplicationContext(), getString(R.string.biometric_auth_error, errString), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                    super.onAuthenticationSucceeded(result);
                    Toast.makeText(getApplicationContext(), getString(R.string.biometric_auth_succeded), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onAuthenticationFailed() {
                    super.onAuthenticationFailed();
                    Toast.makeText(getApplicationContext(), getString(R.string.biometric_auth_failed), Toast.LENGTH_SHORT).show();
                }
            });

            promptInfo = new BiometricPrompt.PromptInfo.Builder().setTitle(getString(R.string.biometric_title)).setSubtitle(getString(R.string.biometric_subtitle)).setNegativeButtonText(getString(R.string.biometric_negative)).build();

            // Prompt appears when user clicks "Log in".
            // Consider integrating with the keystore to unlock cryptographic operations,
            // if needed by your app.
            Button biometricLoginButton = findViewById(R.id.mainactivity_button_login);
            //Button biometricLoginButton = findViewById(R.id.biometric_login);
            biometricLoginButton.setOnClickListener(view -> {
                biometricPrompt.authenticate(promptInfo);
            });
        }
    }

    public void setUiComponents() {
        activityMain_button_login = (Button) findViewById(R.id.mainactivity_button_login);
    }

    public void setListeners() {
        activityMain_button_login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent intent_goToActivity = new Intent(context, ActivityMenu.class);
                startActivity(intent_goToActivity);
            }
        });
    }

    private boolean checkBiometricSupport() {

        KeyguardManager keyguardManager =
                (KeyguardManager) getSystemService(KEYGUARD_SERVICE);

        PackageManager packageManager = this.getPackageManager();

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return false;
        }

        if(!packageManager.hasSystemFeature(PackageManager.FEATURE_FINGERPRINT))
        {
            return false;
        }

        if (!keyguardManager.isKeyguardSecure()) {
            return false;
        }

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.USE_BIOMETRIC) !=
                PackageManager.PERMISSION_GRANTED) {
            return false;
        }

        return true;
    }
}