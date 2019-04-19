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

public class PackSelection extends AppCompatActivity  implements PackListAdapter.ListItemClickListener, PurchasesUpdatedListener {
    private PackListAdapter mAdapter;
    private RecyclerView mWordList;

    private BillingClient mBillingClient;
    List<String> skuList;
    List<SkuDetails> items;

    String base64encodedString = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgx03duIbCPensEH95P9chAeMo9/j/7NcSVZef5PfP6RT6qzDUQ85rLPbtOuBwuqCUqPApAT+PaV8PKafDqpJ+cWbbOfH1WRhV/DHb6ZmitIxfkqbWTG5eMookYP8s4kAkKHAgmkPgnCcxjFYYS9p/TC5Jx6WDTIP/fpMNREL5qBgbLhNJsbWqeRujdcysjWdpxQqoJo9NahLbRS624uPgSez4+7YJG56TrKKPy+K7pcUIm0hwmLKTNqcVRSARsVMvPaCjMdEmv+x4sxTTSWOYKJccAlNw1esO+gEMAafEuADJt2j4jLhqZlQuQrbShBocRQ+OOl4Kq8jnO/UKFV3gwIDAQAB";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pack_selection);

        PackManager.initPacks(getApplicationContext());

        mWordList = findViewById(R.id.rv_packlist);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mWordList.setLayoutManager(layoutManager);
        mWordList.setHasFixedSize(true);

        skuList = new ArrayList<>();
        for(int i = 0; i < PackManager.packs.size(); i++) {
            Pack p = PackManager.packs.get(i);
            if(p.productId != null) {
                skuList.add(p.productId);
            }
        }

        skuList = new ArrayList<>();
        items = new ArrayList<>();

        mBillingClient = BillingClient.newBuilder(this).setListener(this).build();
        mBillingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingSetupFinished(@BillingClient.BillingResponse int billingResponseCode) {
                if (billingResponseCode == BillingClient.BillingResponse.OK) {
                    // The billing client is ready. You can query purchases here.
                    for(int i = 0; i < PackManager.packs.size(); i++) {
                        Pack p = PackManager.packs.get(i);
                        if(p.productId != null) {
                            skuList.add(p.productId);
                        }
                    }

                    SkuDetailsParams.Builder params = SkuDetailsParams.newBuilder();
                    params.setSkusList(skuList).setType(BillingClient.SkuType.INAPP);
                    mBillingClient.querySkuDetailsAsync(params.build(),
                            new SkuDetailsResponseListener() {
                                @Override
                                public void onSkuDetailsResponse(int responseCode, List<SkuDetails> skuDetailsList) {
                                    // Process the result.
                                    items = skuDetailsList;
                                }
                            });

                    Purchase.PurchasesResult purchasesResult = mBillingClient.queryPurchases(BillingClient.SkuType.INAPP);
                    List<Purchase> purchasedItems= purchasesResult.getPurchasesList();
                    for (Purchase purchase : purchasedItems) {
                        for (Pack pack : PackManager.packs) {
                            if(purchase.getSku().equals(pack.productId)) {
                                pack.locked = false;
                            }
                        }
                    }

                }
            }
            @Override
            public void onBillingServiceDisconnected() {
                // Try to restart the connection on the next request to
                // Google Play by calling the startConnection() method.
            }
        });

        mAdapter = new PackListAdapter(PackManager.packs, this, getApplicationContext());
        mWordList.setAdapter(mAdapter);
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        Pack selctedPack = PackManager.packs.get(clickedItemIndex);
        if(selctedPack.locked) {
            for (SkuDetails item : items) {
                if(selctedPack.productId.equals(item.getSku())) {
                    BillingFlowParams.Builder builder = BillingFlowParams.newBuilder();
                    builder.setSkuDetails(item).build();
                    BillingFlowParams flowParams = builder.build();
                    int responseCode = mBillingClient.launchBillingFlow(this, flowParams);
                    if(responseCode == BillingClient.BillingResponse.OK) {
                        goToChooseWord(selctedPack);
                    }
                }
            }
        } else {
            goToChooseWord(selctedPack);
        }
    }

    private void goToChooseWord(Pack selctedPack) {
        Intent intent = new Intent(getApplicationContext(), ChooseWord.class);
        intent.putExtra(ElementsManager.FILE_NAME, selctedPack.fileName);
        startActivity(intent);
    }

    @Override
    public void onPurchasesUpdated(@BillingClient.BillingResponse int responseCode, List<Purchase> purchases) {
        if (responseCode == BillingClient.BillingResponse.OK
                && purchases != null) {
            for (Purchase purchase : purchases) {
                for (Pack pack : PackManager.packs) {
                    if(purchase.getSku().equals(pack.productId)) {
                        pack.locked = false;
                    }
                }
            }
            Toast.makeText(getApplicationContext(), "Purchase completed", Toast.LENGTH_SHORT).show();

        } else if (responseCode == BillingClient.BillingResponse.USER_CANCELED) {
            // Handle an error caused by a user cancelling the purchase flow.
            Toast.makeText(getApplicationContext(), "Purchase cancelled", Toast.LENGTH_SHORT).show();
        } else {
            // Handle any other error codes.
            Toast.makeText(getApplicationContext(), "Error with the purchase", Toast.LENGTH_SHORT).show();
        }
    }
}
