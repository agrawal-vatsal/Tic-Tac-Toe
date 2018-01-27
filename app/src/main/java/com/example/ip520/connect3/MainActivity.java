package com.example.ip520.connect3;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Boolean[] state = new Boolean[10];
    /* It is a board which has configuration of the boxes, if it is null the
     box is empty if it is true then it has a red orb if false a yellow orb */
    TextView redWins, yellowWins;
    ImageView[] hiddenBox = new ImageView[10];
    ImageView[] box = new ImageView[10];

    private boolean checkWin() {
        if (state[1] == state[2] && state[2] == state[3] && state[1] != null)
            return true;
        if (state[4] == state[5] && state[6] == state[5] && state[4] != null)
            return true;
        if (state[7] == state[8] && state[9] == state[8] && state[7] != null)
            return true;
        if (state[1] == state[4] && state[4] == state[7] && state[4] != null)
            return true;
        if (state[2] == state[5] && state[8] == state[5] && state[2] != null)
            return true;
        if (state[9] == state[6] && state[6] == state[3] && state[3] != null)
            return true;
        if (state[1] == state[5] && state[9] == state[5] && state[1] != null)
            return true;
        if (state[3] == state[5] && state[5] == state[7] && state[3] != null)
            return true;
        return false;
    }

    boolean turnRed = true;   // It tells whose turn it is, if it is true is red's turn else yellow's

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        redWins = (TextView) findViewById(R.id.redWin);
        yellowWins = (TextView) findViewById(R.id.yellowWin);
        redWins.setTranslationY(-2000f);
        yellowWins.setTranslationY(2000f);
        Resources r = getResources();
        String name = getPackageName();
        int[] boxIds = new int[10];
        for (int i = 1; i < 10; i++) {
            boxIds[i] = r.getIdentifier("box" + i, "id", name);
            box[i] = (ImageView) findViewById(boxIds[i]);
        }
        int[] hiddenBoxesIds = new int[10];
        for (int i = 1; i < 10; i++) {
            hiddenBoxesIds[i] = r.getIdentifier("hiddenBox" + i, "id", name);
            hiddenBox[i] = (ImageView) findViewById(hiddenBoxesIds[i]);
        }
        for (int i = 0; i < 9; i++) {
            float position = -1 * (((i / 3) * 600) + 600);
            box[i + 1].setTranslationY(position);
        }
        for (int i = 0; i < 9; i++) {
            int I = i;
            hiddenBox[i + 1].setOnClickListener((View view) -> {
                float position = ((I / 3) * 600) + 600;
                int transitionTime = ((I / 3) * 500) + 500;
                if (!turnRed)
                    box[I + 1].setImageResource(R.drawable.yellow);
                box[I + 1].animate().translationYBy(position).setDuration(transitionTime);
                state[I + 1] = turnRed;
                hiddenBox[I + 1].setClickable(false);
                if (checkWin())
                    gameComplete();
                else turnRed = !turnRed;
            });
        }

    }

    private void gameComplete() {
        if (turnRed) {
            redWins.animate().translationYBy(2000f).setDuration(1000);
        } else {
            yellowWins.animate().translationYBy(-2000).setDuration(1000);
        }
        for (int i = 1; i < 10; i++)
            hiddenBox[i].setClickable(false);
    }
}
