package tk.valyfox.telepathicwizard;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

public class MenuActivity extends AppCompatActivity {

    private Toast mToast = null;
    Switch mSwitch, highlightSwithch;
    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        mSwitch = findViewById(R.id.switch_guide_button);
        highlightSwithch = findViewById(R.id.switch_highlight);
        sharedPref = getApplicationContext().getSharedPreferences(getString(R.string.guide_id), Context.MODE_PRIVATE);

        checkGuideButton();
        updateHighlight();
    }


    //hide MenuActivity button
    private void checkGuideButton() {
        mSwitch.setChecked(sharedPref.getBoolean(getString(R.string.guide_id), false));
    }

    public void checkHideGuideButton(View view) {
        if(mSwitch.isChecked()) {
            hideGuideButton();
        } else {
            showGuideButton();
        }
        checkGuideButton();
    }

    private void showGuideButton() {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(getString(R.string.guide_id), false);
        editor.apply();
    }

    private void hideGuideButton() {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(getString(R.string.guide_id), true);
        editor.apply();
        if(mToast != null) mToast.cancel();
        mToast = Toast.makeText(getApplicationContext(), R.string.guide_is_hidden, Toast.LENGTH_SHORT);
        mToast.show();
    }

    //Highlght
    private void updateHighlight() {
        highlightSwithch.setChecked(sharedPref.getBoolean(getString(R.string.highlight_id), true));
    }

    public void checkHighlight(View view) {
        SharedPreferences.Editor editor = sharedPref.edit();
        if(highlightSwithch.isChecked()) {
            editor.putBoolean(getString(R.string.highlight_id), true);
        } else  {
            editor.putBoolean(getString(R.string.highlight_id), false);
        }
        editor.apply();
    }


    //goto guide
    public void guide(View view) {
        Intent intent = new Intent(getApplicationContext(), Guide.class);
        startActivity(intent);
    }
}
