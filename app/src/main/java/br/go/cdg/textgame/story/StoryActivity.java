package br.go.cdg.textgame.story;

import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;

import br.go.cdg.textgame.R;

public class StoryActivity extends AppCompatActivity implements View.OnClickListener {

    private ArrayList<Passage> story = new ArrayList<Passage>();
    private ArrayList<Passage> storySoFar = new ArrayList<Passage>();

    private PassageAdapter passageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);

        String storyName = getIntent().getStringExtra("historia");
        String fileName = storyName.concat(".html");

        String stringHTML = "";

        try {
            InputStream is = getAssets().open("historias/"+fileName, AssetManager.ACCESS_BUFFER);

            stringHTML = StreamToString(is);

            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (stringHTML.equals("")) {
            Toast.makeText(StoryActivity.this, "História não existe", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Document htmlDocument = Jsoup.parse(stringHTML);

            Elements storyDataElements = htmlDocument.getElementsByTag("tw-storydata");

            if (storyDataElements.size() != 1) {
                Toast.makeText(StoryActivity.this, "Algo está errado.", Toast.LENGTH_SHORT).show();
            } else {
                Element htmlStory = storyDataElements.get(0);

                Elements htmlPassageData = htmlStory.getElementsByTag("tw-passagedata");

                for (Element passageDataElement : htmlPassageData) {
                    Passage passage = new Passage(passageDataElement);
                    story.add(passage);

                    if(passage.getId() == 0) {
                        storySoFar.add(passage);
                    }
                }
            }
        }

        RecyclerView rv = (RecyclerView) findViewById(R.id.storyRecycler);

        rv.hasFixedSize();
        rv.setLayoutManager(new LinearLayoutManager(this));

        passageAdapter = new PassageAdapter(storySoFar, this);
        rv.setAdapter(passageAdapter);
    }

    @Override
    public void onClick(View view) {
        int id = ((Integer)view.getTag()).intValue();
        Log.i("OPÇÃO ESCOLHIDA",String.valueOf(id));

        /*for (int i = 0; i < story.size(); i++) {
            if (story.get(i).getId() == id) {
                passageAdapter.addPassage(story.get(i));
            }
        }*/
    }

    public static String StreamToString(InputStream in) throws IOException {
        if(in == null) {
            return "";
        }
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        } finally {
        }
        return writer.toString();
    }
}
