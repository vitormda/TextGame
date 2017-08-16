package br.go.cdg.textgame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            InputStream is = getAssets().open("historias.xml");

            int length = is.available();
            byte[] data = new byte[length];
            is.read(data);
            Log.i("DATA > ", (new String(data)).toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}