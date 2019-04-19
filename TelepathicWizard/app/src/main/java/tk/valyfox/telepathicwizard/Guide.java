package tk.valyfox.telepathicwizard;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import tk.valyfox.telepathicwizard.word.ElementsManager;

public class Guide extends AppCompatActivity {

    TextSwitcher textSwitcher;

    private int guideStringsIds[] = {R.string.guide_trick_1, R.string.guide_trick_2,
            R.string.guide_1, R.string.guide_2, R.string.guide_3, R.string.guide_4, R.string.guide_5,
            R.string.guide_example_1, R.string.guide_example_2,
            R.string.guide_group_straight, R.string.guide_group_curved};
    int index = 0;
    Button next, back;
    Switch mSwitch;
    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        next = findViewById(R.id.button_next);
        back = findViewById(R.id.button_back);

        textSwitcher=findViewById(R.id.ts_guide); // get reference of TextSwitcher

        textSwitcher.setFactory(new ViewSwitcher.ViewFactory() {

            public View makeView() {
                TextView t = new TextView(getApplicationContext());
                t.setGravity(Gravity.CENTER);
                t.setTextSize(20);
                t.setTextColor(getResources().getColor(R.color.contrastLight));
                return t;
            }
        });

        back.setEnabled(false);
        textSwitcher.setCurrentText(getText(guideStringsIds[index]));

        mSwitch = findViewById(R.id.switch_guide_button);
        sharedPref = getApplicationContext().getSharedPreferences(getString(R.string.guide_id), Context.MODE_PRIVATE);

        checkGuideButton();
    }

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
        Toast.makeText(getApplicationContext(), R.string.guide_is_hidden, Toast.LENGTH_SHORT).show();
    }


    public void changeText(View view) {
        //textSwitcher.animate();
        Animation in = AnimationUtils.loadAnimation(this, R.anim.right_in);
        textSwitcher.setInAnimation(in);
        Animation out = AnimationUtils.loadAnimation(this, R.anim.left_out);
        textSwitcher.setOutAnimation(out);
        textSwitcher.setText(getText(guideStringsIds[++index]));


        back.setEnabled(true);
        if(index == guideStringsIds.length - 1) {
            next.setEnabled(false);
        }
    }

    public void changeTextBackward(View view) {
        //textSwitcher.animate();
        Animation in = AnimationUtils.loadAnimation(this, R.anim.left_in);
        textSwitcher.setInAnimation(in);
        Animation out = AnimationUtils.loadAnimation(this, R.anim.right_out);
        textSwitcher.setOutAnimation(out);
        textSwitcher.setText(getText(guideStringsIds[--index]));

        next.setEnabled(true);
        if(index == 0) {
            back.setEnabled(false);
        }
    }

    public void startPractice(View view) {
        Intent intent = new Intent(getApplicationContext(), ChooseWord.class);
        intent.putExtra(ElementsManager.FILE_NAME, getString(R.string.file_alphabet));
        startActivity(intent);
    }
}
