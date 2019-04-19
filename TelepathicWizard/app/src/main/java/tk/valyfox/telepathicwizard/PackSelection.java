package tk.valyfox.telepathicwizard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.android.billingclient.api.SkuDetailsResponseListener;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import tk.valyfox.telepathicwizard.pack.Pack;
import tk.valyfox.telepathicwizard.pack.PackManager;
import tk.valyfox.telepathicwizard.word.ElementsManager;

public class PackSelection extends AppCompatActivity  implements PackListAdapter.ListItemClickListener {
    private PackListAdapter mAdapter;
    private RecyclerView mWordList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pack_selection);

        PackManager.initPacks(getApplicationContext());

        mWordList = findViewById(R.id.rv_packlist);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mWordList.setLayoutManager(layoutManager);
        mWordList.setHasFixedSize(true);

        mAdapter = new PackListAdapter(PackManager.packs, this, getApplicationContext());
        mWordList.setAdapter(mAdapter);
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        Pack selctedPack = PackManager.packs.get(clickedItemIndex);
        goToChooseWord(selctedPack);

    }

    private void goToChooseWord(Pack selctedPack) {
        Intent intent = new Intent(getApplicationContext(), ChooseWord.class);
        intent.putExtra(ElementsManager.FILE_NAME, selctedPack.fileName);
        startActivity(intent);
    }
}
