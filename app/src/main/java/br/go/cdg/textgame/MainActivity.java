package br.go.cdg.textgame;

import android.content.Intent;
import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.IOException;

import br.go.cdg.textgame.story.StoryActivity;

public class MainActivity extends AppCompatActivity {

    String[] stories = new String[0];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView storyList = (ListView) findViewById(R.id.storyList);

        try {
            AssetManager am = getAssets();

            stories = am.list("historias");

            for (int i = 0; i < stories.length; i++) {
                stories[i] = stories[i].split("\\.")[0];
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, stories);

        if (storyList != null) {
            storyList.setAdapter(adapter);
        }

        storyList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent it = new Intent(MainActivity.this, StoryActivity.class);

                it.putExtra("historia", stories[i]);

                startActivity(it);
            }
        });
    }
}