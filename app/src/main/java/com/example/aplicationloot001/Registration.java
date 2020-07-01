package com.example.aplicationloot001;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.example.aplicationloot001.DTOmodel.UserDtoModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;
import org.springframework.http.HttpAuthentication;
import org.springframework.http.HttpBasicAuthentication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;


public class Registration extends AppCompatActivity {
    protected static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        FloatingActionButton fab = findViewById(R.id.fab);
        final TextView textView1 = (TextView) findViewById(R.id.raspuns);

        Button nextStepButton = (Button)findViewById(R.id.regButton);
        nextStepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FetchSecuredResourceTaskk().execute();
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Return To login page", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }




    private class FetchSecuredResourceTaskk extends AsyncTask<Void, Void, Message>{
        private String usernameREG;
        private String passwordREG;
        @Override
        protected Message doInBackground(Void... voids) {

            final String url = "http://lootapp.herokuapp.com"+"/registration/add";

            HttpAuthentication authHeader = new HttpBasicAuthentication("lootapp1", "lootapp1");
            HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.setAuthorization(authHeader);
            requestHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            requestHeaders.setContentType(MediaType.APPLICATION_JSON);

            UserDtoModel userTTT=new UserDtoModel(usernameREG,passwordREG);

            // Create a new RestTemplate instance
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
            HttpEntity<UserDtoModel> request = new HttpEntity<UserDtoModel>(userTTT, requestHeaders);

            try {
                // Make the network request
                Log.d(TAG, url);
                ResponseEntity<Message> response =  restTemplate.
                        postForEntity(url, request,Message.class);
                return response.getBody();
            } catch (HttpClientErrorException e) {
                Log.e(TAG, e.getLocalizedMessage(), e);
                return new Message(0, e.getStatusText(), e.getLocalizedMessage());
            } catch (ResourceAccessException e) {
                Log.e(TAG, e.getLocalizedMessage(), e);
                return new Message(0, e.getClass().getSimpleName(), e.getLocalizedMessage());
            }


        }

        @Override
        protected void onPostExecute(Message result) {
            if(!result.getText().contains("Already Exists"))
            {
                goToLoginPage();
            }
            displayResponse(result);
        }
        protected void displayResponse(Message result)
        {
            TextView raspusn = (TextView)findViewById(R.id.raspuns);
            raspusn.append(result.getSubject()+result.getText());
        }

        @Override
        protected void onPreExecute() {
            EditText username = (EditText) findViewById(R.id.usernameLabel);
            EditText pass = (EditText)findViewById(R.id.passLabel);
            this.usernameREG = username.getText().toString();
            this.passwordREG = pass.getText().toString();
        }

    }
    void goToLoginPage(){
        Intent myIntent = new Intent(Registration.this , MainActivity.class);
        Registration.this.startActivity(myIntent);
    }
}