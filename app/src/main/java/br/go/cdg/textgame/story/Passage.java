package br.go.cdg.textgame.story;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

/**
 * Created by vitor.almeida on 17/08/2017.
 */

public class Passage {
    private String name;
    private int id;
    private String text;

    private ArrayList<Link> links = new ArrayList<Link>();

    public Passage(Element e) {
        this.name = e.attr("tags").replace('-', ' ');
        this.id = Integer.parseInt(e.attr("name"));

        this.text = e.text();

        Elements rawLinks = e.getElementsByTag("link");

        for(Element rawLink : rawLinks) {
            links.add(new Link(rawLink));
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ArrayList<Link> getLinks() {
        return links;
    }

    public void setLinks(ArrayList<Link> links) {
        this.links = links;
    }
}
