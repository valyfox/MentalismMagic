package tk.valyfox.telepathicwizard.customiser;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.Comparator;

import tk.valyfox.telepathicwizard.R;
import tk.valyfox.telepathicwizard.word.Element;
import tk.valyfox.telepathicwizard.word.ElementsManager;

public class CategoryEditor extends AppCompatActivity implements CategoryEditorAdapter.ListItemClickListener {

    private CategoryEditorAdapter mAdapter;
    private RecyclerView mWordList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_editor);

        String fileName = getIntent().getStringExtra(ElementsManager.FILE_NAME);

        if(fileName != null) {
            ElementsManager.parseElements(this, fileName);
        } else {
            //TODO use string file
            ElementsManager.parseElements(this, "custom1.txt");
            //ElementsManager.parseElements(this, getString(R.string.file_name_cities);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            ElementsManager.elements.sort(new Comparator<Element>() {
                @Override
                public int compare(Element o1, Element o2) {
                    return o1.name.compareToIgnoreCase(o2.name);
                }
            });
        }

        mWordList = findViewById(R.id.rv_wordlist_customizable);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mWordList.setLayoutManager(layoutManager);
        mWordList.setHasFixedSize(true);


        mAdapter = new CategoryEditorAdapter(ElementsManager.elements, this);
        mWordList.setAdapter(mAdapter);
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {

    }

    public void confirmPack(View view) {
    }
}
