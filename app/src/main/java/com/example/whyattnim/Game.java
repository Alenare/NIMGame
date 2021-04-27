package com.example.whyattnim;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Game extends AppCompatActivity {
        public int stickCnt = 0;
        public boolean a2P = false;
        public String currP = "Player 1";


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            MainActivity newVar = new MainActivity();

            super.onCreate(savedInstanceState);

            if (newVar.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                setContentView(R.layout.landscape_nim);
            } else {
                setContentView(R.layout.portrait_nim);
            }

            if(!newVar.opponent.equals("Computer")){
                a2P = true;
            }else{
                a2P = false;
            }
            InitializeGame();
    }


        public void InitializeGame(){
            TextView changeView = (TextView)findViewById(R.id.stickView);
            TextView playerView = (TextView)findViewById(R.id.currPlay);
            String stickAmnt = "";
            int rand = new Random().nextInt(20);
            while(rand < 10 && rand < 21){
                rand = new Random().nextInt(20);
            }

            stickCnt = rand;

            stickAmnt = GenString(rand);

            changeView.setText(stickAmnt);
            playerView.setText(currP);

        }

        public String GenString(int value){
            String rv = "";

            for(int i = 0; i < value; i++){
                rv = rv + "|";
            }

            return rv;
        }

        public void GameLogic( View v ){
            Button subStick = (Button)v;
            TextView currPlayer = (TextView)findViewById(R.id.currPlay);
            TextView nStickView = (TextView)findViewById(R.id.stickView);
            String newSticks = "";
            String tValue = subStick.getText().toString();
            tValue = tValue.substring(tValue.length()-1);
            int subBy = 0;

            if(currP.equals("Player 1") || currP.equals("Player 2")) {

                subBy = Integer.parseInt(tValue);

                stickCnt = stickCnt - subBy;

                newSticks = GenString(stickCnt);

                nStickView.setText(newSticks);

                WinCondition();

                if(a2P == false){
                    currP= "Computer";
                    currPlayer.setText(currP);
                }else if(a2P == true && currP.equals("Player 1")){
                    currP= "Player 2";
                    currPlayer.setText(currP);
                }else if(currP.equals("Player 2")){
                    currP= "Player 1";
                    currPlayer.setText(currP);
                }

            }

            if(currP.equals("Computer")){
                currPlayer.setText("Computer");
                final Handler handler = new Handler(Looper.getMainLooper());
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        int subBy = AILogic();
                        stickCnt = stickCnt - subBy;
                        String newSticks = GenString(stickCnt);
                        nStickView.setText(newSticks);
                        WinCondition();
                        currP = "Player 1";
                        currPlayer.setText(currP);
                    }
                }, 5000);
            }
       }

        public int AILogic(){
            int rv = 0;

            if(stickCnt >= 13){
                while(rv == 0) {
                    rv = new Random().nextInt(3);
                }
            }else if(stickCnt == 12){
                rv = 3;
            } else if(stickCnt == 10 || stickCnt == 9 || stickCnt == 6 || stickCnt == 5 || stickCnt == 2 || stickCnt == 1){
                rv = 1;
            }else if( stickCnt == 8){
                rv = 3;
            }else if(stickCnt == 7 || stickCnt == 11){
                rv = 2;
            }else if(stickCnt == 4){
                rv = 3;
            }else if(stickCnt == 3){
                rv = 2;
            }

            return rv;
        }

        public void WinCondition(){
            if(stickCnt <= 0){
                if(currP.equals("Player 1")){
                    if(a2P == true){
                        currP = "Player 2";
                    }else if(a2P == false){
                        currP = "Computer";
                    }
                }else if(currP.equals("Computer") || currP.equals("Player 2")){
                    currP = "Player 1";
                }

                Toast.makeText(Game.this, "Congratulations " + currP + " You Won!", Toast.LENGTH_LONG).show();
                stickCnt = 0;
                currP = "Player 1";
                Intent myIntent = new Intent(this,MainActivity.class);
                this.startActivity(myIntent);
            }
        }

        public void ReturnHome(View v){
            stickCnt = 0;
            currP = "Player 1";
            Intent myIntent = new Intent(this,MainActivity.class);
            this.startActivity(myIntent);
        }

}
