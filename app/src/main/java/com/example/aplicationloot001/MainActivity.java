package com.example.aplicationloot001;
import java.util.Collections;
import org.springframework.http.HttpAuthentication;
import org.springframework.http.HttpBasicAuthentication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;



public class MainActivity extends AbstractAsyncActivity{
    protected static final String TAG = MainActivity.class.getSimpleName();



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        final Button regButton = (Button)findViewById(R.id.regFormBtn);
        // Initiate the request to the protected service
        final Button submitButton = (Button) findViewById(R.id.submit);
        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this , Registration.class);
                MainActivity.this.startActivity(myIntent);
            }
        });
        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new FetchSecuredResourceTask().execute();
            }
        });

    }

    private void displayResponse(Message response){
        Toast.makeText(this,response.getText(),Toast.LENGTH_LONG).show();
        TextView message = (TextView)findViewById(R.id.GetMessage);
        message.append(response.toString());
    }

    private class FetchSecuredResourceTask extends AsyncTask<Void, Void, Message>{
            private String username;
            private String password;

            @Override
            protected Message doInBackground(Void... voids) {

                final String url = "https://lootapp.herokuapp.com"+"/getmessage";

                HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
                HttpHeaders requestHeaders = new HttpHeaders();
                requestHeaders.setAuthorization(authHeader);
                requestHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

                // Create a new RestTemplate instance
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());

                try {
                    // Make the network request
                    Log.d(TAG, url);
                    ResponseEntity<Message> response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<Object>(requestHeaders), Message.class);
                    return response.getBody();
                } catch (HttpClientErrorException e) {
                    Log.e(TAG, e.getLocalizedMessage(), e);
                    return new Message(0, "Incorect data", e.getLocalizedMessage());
                } catch (ResourceAccessException e) {
                    Log.e(TAG, e.getLocalizedMessage(), e);
                    return new Message(0, e.getClass().getSimpleName(), e.getLocalizedMessage());
                }

            }
            @Override
            protected void onPostExecute(Message result) {
                if(result.getText().contains("true")) {
                    openHomeDialog();
                    dimissProgressDialog();
                }
                displayResponse(result);
            }

            @Override
            protected void onPreExecute() {
                //showLoadingProgressDialog("messagggge");
                EditText editText =(EditText) findViewById(R.id.username);
                this.username = editText.getText().toString();
                editText = (EditText)findViewById(R.id.password);
                this.password = editText.getText().toString();
            }

    }
    void openHomeDialog(){
        Intent myIntent = new Intent(MainActivity.this , HomePageActivity.class);
        MainActivity.this.startActivity(myIntent);
    }


}
