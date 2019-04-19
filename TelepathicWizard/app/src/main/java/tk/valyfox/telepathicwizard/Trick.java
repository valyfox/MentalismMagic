package tk.valyfox.telepathicwizard;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Random;

import tk.valyfox.telepathicwizard.word.ElementsManager;
import tk.valyfox.telepathicwizard.word.Element;

public class Trick extends AppCompatActivity {

    Element selectedElement;

    TextView views[];
    int ids[] = {R.id.trick_1, R.id.trick_2, R.id.trick_3, R.id.trick_4, R.id.trick_5, R.id.trick_6, R.id.trick_7, R.id.trick_8, R.id.trick_9, R.id.trick_10};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trick);

        views = new TextView[ids.length];
        for(int i = 0; i < ids.length; i++) {
            views[i] = findViewById(ids[i]);
        }
        int index = getIntent().getIntExtra(ElementsManager.INDEX_ID, -1);
        if(index < 0)
            return;
        selectedElement = ElementsManager.getElementsWithIndex(index);

        shuffle();
    }

    private void shuffle() {
        Random rand = new Random();
        List<Element> otherElements;
        if(selectedElement.type == 0)
            otherElements = ElementsManager.getElementsWithType(1);
        else
            otherElements = ElementsManager.getElementsWithType(0);

        for (int i = 0; i < ids.length; i++) {
            int n = rand.nextInt(otherElements.size());
            Element other = otherElements.get(n);
            otherElements.remove(n);
            views[i].setText(other.name);
            views[i].setBackgroundColor(Color.TRANSPARENT);
            views[i].setTextColor(getResources().getColor(R.color.contrastLight));
        }

        int n = rand.nextInt(ids.length);
        views[n].setText(selectedElement.name);
        views[n].setBackgroundColor(getResources().getColor(R.color.colorAccent));
        views[n].setTextColor(getResources().getColor(R.color.contrastDark));
    }

    public void startShuffle(View view) {
        shuffle();
    }

    public void endTrick(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}
