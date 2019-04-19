package tk.valyfox.telepathicwizard;


import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.Comparator;

import tk.valyfox.telepathicwizard.word.Element;
import tk.valyfox.telepathicwizard.word.ElementsManager;

public class ChooseWord extends AppCompatActivity implements WordsListAdapter.ListItemClickListener{

    private WordsListAdapter mAdapter;
    private RecyclerView mWordList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_word);

        String fileName = getIntent().getStringExtra(ElementsManager.FILE_NAME);

        if(fileName != null) {
            ElementsManager.parseElements(this, fileName);
        } else {
            ElementsManager.parseElements(this, getString(R.string.file_name_cities));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            ElementsManager.elements.sort(new Comparator<Element>() {
                @Override
                public int compare(Element o1, Element o2) {
                    return o1.name.compareToIgnoreCase(o2.name);
                }
            });
        }

        mWordList = findViewById(R.id.rv_wordlist);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mWordList.setLayoutManager(layoutManager);
        mWordList.setHasFixedSize(true);


        mAdapter = new WordsListAdapter(ElementsManager.elements, this);
        mWordList.setAdapter(mAdapter);
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        Intent intent = new Intent(getApplicationContext(), Trick.class);
        intent.putExtra(ElementsManager.INDEX_ID, clickedItemIndex);
        startActivity(intent);
    }
}
