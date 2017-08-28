package br.go.cdg.textgame.story;


import org.json.JSONException;
import org.json.simple.JSONObject;


/**
 * @author vitor.almeida
 */
public class Link {
    private int id;
    private String text;
    private boolean clicked = false;

    public Link(){}

    public Link(JSONObject jsonLink) throws JSONException {
        this.id = ((Long)jsonLink.get("id")).intValue();
        this.text = (String)jsonLink.get("text");
        this.clicked = (boolean)jsonLink.get("clicked");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean getClicked() {
        return clicked;
    }

    public void setClicked(boolean clicked) {
        this.clicked = clicked;
    }

    @Override
    public String toString() {
        return "{\"id\": "+id+", \"text\": \""+text+"\", \"clicked\": false}";
    }
}
