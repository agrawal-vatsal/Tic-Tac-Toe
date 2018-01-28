package com.example.ip520.connect3;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Boolean[] state = new Boolean[10];
    /* It is a board which has configuration of the boxes, if it is null the
     box is empty if it is true then it has a red orb if false a yellow orb */
    ImageView[] box = new ImageView[10];
    final long duration = 500;
    GridLayout board;
    ImageView boardImage;
    LinearLayout winnerLabel;
    TextView winnerLabelText;
    Button playAgain;

    private boolean checkDraw() {
        for (int i = 1; i < 10; i++)
            if (state[i] == null)
                return false;
        return true;
    }

    private void reset() {
        winnerLabel.animate().alpha(0).setDuration(duration);
        for (int i = 1; i < 10; i++) {
            box[i].setClickable(true);
            state[i] = null;
            box[i].setImageResource(R.drawable.red);
        }
        boardImage.animate().alpha(1).setDuration(duration);
        turnRed = true;
    }

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
        winnerLabel = (LinearLayout) findViewById(R.id.winnerLabel);
        winnerLabelText = (TextView) findViewById(R.id.winnerLabelText);
        playAgain = (Button) findViewById(R.id.playAgain);
        board = (GridLayout) findViewById(R.id.board);
        boardImage = (ImageView) findViewById(R.id.boardImage);
        Resources r = getResources();
        String name = getPackageName();
        int[] boxIds = new int[10];
        for (int i = 1; i < 10; i++) {
            boxIds[i] = r.getIdentifier("box" + i, "id", name);
            box[i] = (ImageView) findViewById(boxIds[i]);
        }
        for (int i = 0; i < 9; i++) {
            int I = i;
            box[i + 1].setOnClickListener((View view) -> {
                imageClicked(I, box[I + 1]);
                state[I + 1] = turnRed;
                if (checkWin())
                    gameComplete(false);
                else if (checkDraw())
                    gameComplete(true);
                else turnRed = !turnRed;
            });
        }
        playAgain.setOnClickListener((View view) -> reset());
    }

    private void imageClicked(int i, ImageView box) {
        box.setAlpha(1f);
        float position = -1 * (((i / 3) * 600) + 600);
        box.setTranslationY(position);
        if (!turnRed)
            box.setImageResource(R.drawable.yellow);
        box.animate().translationYBy(-1 * position).setDuration(duration);
        box.setClickable(false);
    }

    private void gameComplete(boolean isDraw) {
        if (isDraw) {
            winnerLabelText.setText("DRAW");
            winnerLabel.setBackgroundColor(getResources().getColor(R.color.green));
        } else if (turnRed) {
            winnerLabelText.setText("RED WINS");
            winnerLabel.setBackgroundColor(getResources().getColor(R.color.red));
        } else {
            winnerLabelText.setText("YELLOW WINS");
            winnerLabel.setBackgroundColor(getResources().getColor(R.color.yellow));
        }
        for (int i = 1; i < 10; i++) {
            box[i].setClickable(false);
            box[i].animate().alpha(0f).setDuration(duration);
        }
        boardImage.animate().alpha(0f).setDuration(duration);
        winnerLabel.animate().alpha(1).setDuration(duration);
    }
}
