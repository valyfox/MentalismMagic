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


    Button guideButton;

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

        guideButton = findViewById(R.id.button_guide);

        checkGuideVisible();
    }

    private void checkGuideVisible() {
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(getString(R.string.guide_id), Context.MODE_PRIVATE);
        if(sharedPref.getBoolean(getString(R.string.guide_id), false)) {
            guideButton.setText("");
            guideButton.setBackgroundColor(Color.TRANSPARENT);
        } else {
            guideButton.setText(R.string.guide);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                guideButton.setBackground(getDrawable(R.drawable.button_selector));
            } else {
                guideButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            }
        }
    }

    public void start(View view) {
          Intent intent = new Intent(getApplicationContext(), PackSelection.class);
          startActivity(intent);
    }

    public void guide(View view) {
        Intent intent = new Intent(getApplicationContext(), Guide.class);
        startActivity(intent);
    }
}
