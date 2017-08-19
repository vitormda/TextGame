package br.go.cdg.textgame.story;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import br.go.cdg.textgame.R;

/**
 * Created by vitor.almeida on 18/08/2017.
 */

public class PassageAdapter extends RecyclerView.Adapter<PassageAdapter.PassageHolder> {

    private ArrayList<Passage> storySoFar;
    private Context storyContext;

    public static class PassageHolder extends RecyclerView.ViewHolder {

        TextView passageText;
        LinearLayout llOptions;

        public PassageHolder(View cv) {
            super(cv);

            passageText = (TextView) cv.findViewById(R.id.passageText);
            llOptions = (LinearLayout) cv.findViewById(R.id.passageHooks);
        }
    }

    public PassageAdapter(ArrayList<Passage> storySoFar, Context context) {
        this.storySoFar = storySoFar;

        this.storyContext = context;
    }

    @Override
    public PassageHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_passage, parent, false);
        PassageHolder ph = new PassageHolder(v);

        return ph;
    }

    @Override
    public void onBindViewHolder(PassageHolder passageHolder, int i) {
        passageHolder.passageText.setText(Html.fromHtml(storySoFar.get(i).getText()));

        passageHolder.llOptions.removeAllViews();

        for (Link link : storySoFar.get(i).getLinks()) {
            Button hook = new Button(passageHolder.itemView.getContext());

            hook.setText(link.getText());
            hook.setPadding(0,5,0,5);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            hook.setLayoutParams(params);

            hook.setTag(link.getId());

            if(link.getClicked()) {
                hook.setTextColor(storyContext.getColor(android.R.color.holo_red_dark));
                hook.setBackgroundColor(storyContext.getColor(R.color.cardBackground));
            }

            hook.setClickable(storySoFar.get(i).isCurrent());

            hook.setOnClickListener((StoryActivity)storyContext);
            passageHolder.llOptions.addView(hook);
        }
    }

    @Override
    public int getItemCount() {
        return storySoFar.size();
    }

    public void addPassage(Passage passage, int id) {
        for (Passage pass : storySoFar) {
            if (pass.isCurrent()) {
                pass.setCurrent(false);

                for (Link link : pass.getLinks()) {
                    if (link.getId() == id) {
                        link.setClicked(true);
                    }
                }
            }
        }

        storySoFar.add(passage);
        notifyItemInserted(storySoFar.indexOf(passage));
    }


}
