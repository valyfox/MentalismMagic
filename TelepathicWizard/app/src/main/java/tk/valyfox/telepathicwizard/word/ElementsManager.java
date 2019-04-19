package tk.valyfox.telepathicwizard.word;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import tk.valyfox.telepathicwizard.WordTypeFinder;

public class ElementsManager {
    public static List<Element> elements;
    public static final String INDEX_ID = "index_id";
    public static final String FILE_NAME = "file_name";

    private ElementsManager() {}

    public static void parseElements(Context context, String fileName) {
        elements = new ArrayList<>();
        try {

            InputStream inputStream = context.getAssets().open(fileName);

            InputStreamReader inputreader = new InputStreamReader(inputStream);
            BufferedReader buffreader = new BufferedReader(inputreader);
            String line;

            while (( line = buffreader.readLine()) != null) {
                int type = WordTypeFinder.getWordType(line);
                if(type >= 0) {
                    Element newElement = new Element(line, type);
                    elements.add(newElement);
                }
            }

            inputreader.close();
            buffreader.close();

        } catch (IOException e) {
            // Should never happen!
            e.printStackTrace();
            return;
        }
    }

    public static Element getElementsWithIndex(int index) {
        return elements.get(index);
    }

    public static  List<Element> getElementsWithType(int type) {
        List<Element> returnList = new ArrayList<Element>();
        for (int i = 0; i < elements.size(); i++) {
            if(elements.get(i).type == type) {
                returnList.add(elements.get(i));
            }
        }
        return returnList;
    }
}
