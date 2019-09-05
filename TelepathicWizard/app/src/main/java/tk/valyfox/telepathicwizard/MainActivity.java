package tk.valyfox.telepathicwizard;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    Button menuButton;

    @Override
    protected void onResume()
    {
        super.onResume();
        checkGuideVisible();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        menuButton = findViewById(R.id.button_menu);

        checkGuideVisible();
    }

    private void checkGuideVisible() {
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(getString(R.string.guide_id), Context.MODE_PRIVATE);
        if(sharedPref.getBoolean(getString(R.string.guide_id), false)) {
            menuButton.setText("");
            menuButton.setBackgroundColor(Color.TRANSPARENT);
        } else {
            menuButton.setText(R.string.menu_button);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                menuButton.setBackground(getDrawable(R.drawable.button_selector));
            } else {
                menuButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            }
        }
    }

    public void start(View view) {
          Intent intent = new Intent(getApplicationContext(), PackSelection.class);
          startActivity(intent);
    }

    public void guide(View view) {
        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
        startActivity(intent);
    }
}
