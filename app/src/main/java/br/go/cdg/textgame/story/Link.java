package br.go.cdg.textgame.story;

import org.jsoup.nodes.Element;

/**
 * Created by vitor.almeida on 17/08/2017.
 */

public class Link {
    private int id;
    private String text;
    private boolean clicked = false;

    public Link(){}

    public Link(Element e) {
        String[] values = e.attr("text").split("\\|");

        this.text = values[0];
        this.id = Integer.parseInt(values[1]);
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
}
