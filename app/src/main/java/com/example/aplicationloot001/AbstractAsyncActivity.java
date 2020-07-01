package com.example.aplicationloot001;

import android.app.Activity;
import android.app.ProgressDialog;

public abstract class AbstractAsyncActivity extends Activity {
    protected static final String TAG = AbstractAsyncActivity.class.getSimpleName();
    private ProgressDialog progressDialog;

    private boolean destroyed = false;

    @Override
    protected void onDestroy(){
        super.onDestroy();
        destroyed = true;
    }


    public void showProgressDialog(CharSequence message){
        if(progressDialog == null){
                progressDialog = new ProgressDialog(this);
                progressDialog.setIndeterminate(true);
        }
        progressDialog.setMessage(message);
        progressDialog.show();
    }
    public void dimissProgressDialog(){
        if(progressDialog!=null && !destroyed){
            progressDialog.dismiss();
        }
    }
}
