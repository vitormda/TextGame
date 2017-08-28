package br.go.cdg.textgame.story;


import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;



/**
 * @author vitor.almeida
 */

public class Passage {
    private String name = "";
    private int id = 0;
    private boolean current = false;

    private ArrayList<String> text = new ArrayList<String>();
    private ArrayList<Link> links = new ArrayList<Link>();

    public Passage(){}

    public Passage(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Passage(JSONObject jsonPassage) throws JSONException {
        this.id = ((Long)jsonPassage.get("id")).intValue();
        this.name = (String)jsonPassage.get("name");
        this.current = (boolean)jsonPassage.get("current");

        JSONArray jsonTexts = (JSONArray) jsonPassage.get("fragments");
        for (int i = 0; i < jsonTexts.size(); i++) {
            Object jsonText = jsonTexts.get(i);

            this.text.add((String) jsonText);
        }

        JSONArray jsonLinks = (JSONArray) jsonPassage.get("links");
        for (int i = 0; i < jsonLinks.size(); i++) {
            Object jsonLink = jsonLinks.get(i);

            this.links.add(new Link((JSONObject) jsonLink));
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<String> getText() {
        return text;
    }

    public void setText(ArrayList<String> text) {
        this.text = text;
    }

    public ArrayList<Link> getLinks() {
        return links;
    }

    public void setLinks(ArrayList<Link> links) {
        this.links = links;
    }

    public boolean isCurrent() {
        return current;
    }

    public void setCurrent(boolean current) {
        this.current = current;
    }

    public String getJson() {
        String passage = "{ \"name\": \""+ name +"\", \"id\": "+ id +", \"fragments\": [";

        for (int i = 0; i < text.size(); i++) {
            passage = passage.concat("\""+text.get(i)+"\"");

            if (i != text.size() - 1) {
                passage = passage.concat(", ");
            }
        }

        passage = passage.concat("], \"links\": [");

        for (int i = 0; i < links.size(); i++) {
            passage = passage.concat(links.get(i).toString());

            if (i != links.size() - 1) {
                passage = passage.concat(", ");
            }
        }

        passage = passage.concat("], \"current\": "+current);

        passage = passage.concat("}");

        return passage;
    }

    @Override
    public String toString() {
        return String.valueOf(id).concat(" - ").concat(name);
    }
}
