package com.example.srivatsan.game;

import android.content.SharedPreferences;

import com.flatearth.gravidot.R;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;

import org.andengine.ui.activity.BaseGameActivity;

/**
 * Created by srivatsan on 14/5/15.
 */
public class Leaderboards {
    GoogleApiClient googleApiClient;
    BaseGameActivity baseGameActivity;
    SharedPreferences example;
    SharedPreferences.Editor editor;
    AesEncrDec aesEncrDec;
    public Leaderboards(GoogleApiClient  googleApiClient,BaseGameActivity baseGameActivity){
        this.baseGameActivity=baseGameActivity;
        this.googleApiClient=googleApiClient;
        example = baseGameActivity.getSharedPreferences("GAME", 0);
        editor = example.edit();
        aesEncrDec = new AesEncrDec();
    }
    public void postScore(int score){
        if(MainActivity.isSignedIn()) {
            Games.Leaderboards.submitScore(googleApiClient, baseGameActivity.getString(R.string.leaderboard_score), score);
        }
        else {
            editor.putString(aesEncrDec.encrypt("status"),aesEncrDec.encrypt("pending"));
            editor.putString(aesEncrDec.encrypt(baseGameActivity.getString(R.string.leaderboard_score)),aesEncrDec.encrypt(score + ""));
        }
    }
    public void postSpeed(int speed){
        if(MainActivity.isSignedIn()) {
            Games.Leaderboards.submitScore(googleApiClient, baseGameActivity.getString(R.string.leaderboard_speed), speed);
        }
        else {
            editor.putString(aesEncrDec.encrypt("status"),aesEncrDec.encrypt("pending"));
            editor.putString(aesEncrDec.encrypt(baseGameActivity.getString(R.string.leaderboard_speed)),aesEncrDec.encrypt(speed + ""));
        }
    }
    public void postScoreRepel(int score){
        if(MainActivity.isSignedIn()) {
            Games.Leaderboards.submitScore(googleApiClient, baseGameActivity.getString(R.string.leaderboard_scorerepel_mode), score);
        }
        else {
            editor.putString(aesEncrDec.encrypt("status"),aesEncrDec.encrypt("pending"));
            editor.putString(aesEncrDec.encrypt(baseGameActivity.getString(R.string.leaderboard_scorerepel_mode)),aesEncrDec.encrypt(score + ""));
        }
    }
    public void postSpeedRepel(int speed){
        if(MainActivity.isSignedIn()) {
            Games.Leaderboards.submitScore(googleApiClient, baseGameActivity.getString(R.string.leaderboard_speedrepel_mode), speed);
        }
        else {
            editor.putString(aesEncrDec.encrypt("status"),aesEncrDec.encrypt("pending"));
            editor.putString(aesEncrDec.encrypt(baseGameActivity.getString(R.string.leaderboard_speedrepel_mode)),aesEncrDec.encrypt(speed + ""));
        }
    }
    public void commitItem(){
        editor.commit();
    }
}
