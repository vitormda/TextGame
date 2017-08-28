package br.go.cdg.textgame.story;

import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
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
        String fileName = storyName.concat(".json");

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(getAssets().open("historias/"+fileName, AssetManager.ACCESS_BUFFER), StandardCharsets.UTF_8));

            JSONParser jsonp = new JSONParser();

            JSONObject jsono = (JSONObject) jsonp.parse(br);

            JSONArray jsona = (JSONArray) jsono.get("passages");

            for (int i = 0; i < jsona.size(); i++) {
                Passage pass =  new Passage((JSONObject) jsona.get(i));

                story.add(pass);

                if(pass.getId() == 0) {
                    storySoFar.add(clone(pass));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.setTitle(storyName);

        RecyclerView rv = (RecyclerView) findViewById(R.id.storyRecycler);

        rv.hasFixedSize();
        rv.setLayoutManager(new LinearLayoutManager(this));

        passageAdapter = new PassageAdapter(storySoFar, this);
        rv.setAdapter(passageAdapter);
    }

    @Override
    public void onClick(View view) {

        CardView cv = (CardView) view.getParent().getParent().getParent();

        Button clicked = (Button) view;
        int id = ((Integer)view.getTag()).intValue();

        clicked.setTextColor(getColor(android.R.color.holo_red_dark));
        clicked.setBackgroundColor(getColor(R.color.cardBackground));

        clicked.setClickable(false);

        LinearLayout buttonLayout = (LinearLayout)clicked.getParent();

        for(int i = 0; i < buttonLayout.getChildCount(); i++) {
            buttonLayout.getChildAt(i).setClickable(false);
        }

        int passageIndex = cv.getId();

        Passage newPassage = new Passage();

        for (int i = 0; i < story.size(); i++) {
            if (story.get(i).getId() == id) {
                newPassage = clone(story.get(i));
                newPassage.setCurrent(true);
                break;
            }
        }

        storySoFar.get(passageIndex).setCurrent(false);

        for (int i = 0; i < storySoFar.get(passageIndex).getLinks().size(); i++) {
            if (storySoFar.get(passageIndex).getLinks().get(i).getId() == id) {
                storySoFar.get(passageIndex).getLinks().get(i).setClicked(true);
            }
        }

        storySoFar.add(newPassage);

        passageAdapter.updateStory(storySoFar);
        passageAdapter.notifyDataSetChanged();
    }

    public Passage clone(Passage passage) {
        Passage cloned = new Passage();

        cloned.setId(passage.getId());
        cloned.setName(passage.getName());
        cloned.setCurrent(passage.isCurrent());

        ArrayList<String> texts = new ArrayList<String>();
        for (String text : passage.getText()) {
            cloned.getText().add(text);
        }

        ArrayList<Link> links = new ArrayList<Link>();
        for (Link ink : passage.getLinks()) {
            Link newLink = new Link();

            newLink.setId(ink.getId());
            newLink.setText(ink.getText());
            newLink.setClicked(ink.getClicked());

            links.add(newLink);
        }

        cloned.setLinks(links);

        return cloned;
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
