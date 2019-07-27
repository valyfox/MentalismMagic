package tk.valyfox.telepathicwizard.pack;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import tk.valyfox.telepathicwizard.R;

public class PackManager {
    public static List<Pack> packs;
    public static final int PACK_FILE_NAMES[] = {R.string.file_name_cities, R.string.file_name_fruits, R.string.file_name_colours, R.string.file_name_videogames, R.string.file_name_celebrities};

    private PackManager() {}

    public static void initPacks(Context context) {
        packs = new ArrayList<>();

        for (int i = 0; i < PACK_FILE_NAMES.length; i++) {

            try {
                String fileName = context.getString(PACK_FILE_NAMES[i]);
                InputStream inputStream = context.getAssets().open(fileName);

                InputStreamReader inputreader = new InputStreamReader(inputStream);
                BufferedReader buffreader = new BufferedReader(inputreader);
                String packName;

               packName = buffreader.readLine();

               Pack p = new Pack(fileName, packName);
               packs.add(p);

                inputreader.close();
                buffreader.close();

            } catch (IOException e) {
                // Should never happen!
                e.printStackTrace();
            }
        }

        }

}
