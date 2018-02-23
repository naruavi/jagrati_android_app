package com.example.lenovopc.jagrati;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Window;

import com.android.volley.RequestQueue;

@SuppressLint("Registered")
public class BaseActivity extends Activity {
    public String jwtVal;
    public String apiURL;
    public RequestQueue queue;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        Cursor userRow = dbHelper.getRow();

        queue = VolleySingleton.getInstance(
                getApplicationContext()
        ).getRequestQueue();
        apiURL = getString(R.string.api_url);

        if (userRow.getCount() != 0) {
            userRow.moveToFirst();
            jwtVal = userRow.getString(2);
        } else {
            jwtVal = "";
        }
    }
}
