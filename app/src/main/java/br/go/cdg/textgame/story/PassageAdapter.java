package br.go.cdg.textgame.story;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.go.cdg.textgame.R;

/**
 * Created by vitor.almeida on 18/08/2017.
 */

public class PassageAdapter extends RecyclerView.Adapter<PassageAdapter.PassageHolder> {

    ArrayList<Passage> storySoFar;
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
        passageHolder.passageText.setLines(storySoFar.get(i).getText().split("\\n").length);

        String text = storySoFar.get(i).getText().replace("\\n", "");

        passageHolder.passageText.setText(text);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        for (Link link : storySoFar.get(i).getLinks()) {
            Button hook = new Button(passageHolder.itemView.getContext());

            hook.setText(link.getText());
            hook.setPadding(0,5,0,5);
            hook.setLayoutParams(params);

            hook.setTag(link.getId());

            hook.setOnClickListener((StoryActivity)storyContext);

            passageHolder.llOptions.addView(hook);
        }
    }

    @Override
    public int getItemCount() {
        return storySoFar.size();
    }

    public void addPassage(Passage passage) {
        storySoFar.add(passage);
        notifyItemInserted(storySoFar.indexOf(passage));
    }
}
