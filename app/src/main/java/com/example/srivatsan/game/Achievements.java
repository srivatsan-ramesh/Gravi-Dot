package com.example.srivatsan.game;

import android.content.SharedPreferences;

import com.flatearth.gravidot.R;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;

import org.andengine.ui.activity.BaseGameActivity;

/**
 * Created by srivatsan on 14/5/15.
 */
public class Achievements {
    GoogleApiClient googleApiClient;
    BaseGameActivity baseGameActivity;
    SharedPreferences example;
    SharedPreferences.Editor editor;
    AesEncrDec aesEncrDec;
    public Achievements(GoogleApiClient  googleApiClient,BaseGameActivity baseGameActivity){
        this.baseGameActivity=baseGameActivity;
        this.googleApiClient=googleApiClient;
        example = baseGameActivity.getSharedPreferences("GAME", 0);
        editor = example.edit();
        aesEncrDec = new AesEncrDec();
    }
    public void unlockScore(int score){

        if(score>5){
            if(MainActivity.isSignedIn()) {
                Games.Achievements.unlock(googleApiClient, baseGameActivity.getString(R.string.achievement_the_beginners_club));
            }
            else {
                editor.putString(aesEncrDec.encrypt("status"),aesEncrDec.encrypt("pending"));
                editor.putString(aesEncrDec.encrypt(baseGameActivity.getString(R.string.achievement_the_beginners_club)),aesEncrDec.encrypt("true"));
            }
        }
        if(score>25){
            if(MainActivity.isSignedIn()) {
                Games.Achievements.unlock(googleApiClient, baseGameActivity.getString(R.string.achievement_the_five_squared_club));
            }
            else {
                editor.putString(aesEncrDec.encrypt("status"),aesEncrDec.encrypt("pending"));
                editor.putString(aesEncrDec.encrypt(baseGameActivity.getString(R.string.achievement_the_five_squared_club)),aesEncrDec.encrypt("true"));
            }
        }
        if(score>100){
            if(MainActivity.isSignedIn()) {
                Games.Achievements.unlock(googleApiClient, baseGameActivity.getString(R.string.achievement_the_century_club));
            }
            else {
                editor.putString(aesEncrDec.encrypt("status"),aesEncrDec.encrypt("pending"));
                editor.putString(aesEncrDec.encrypt(baseGameActivity.getString(R.string.achievement_the_century_club)),aesEncrDec.encrypt("true"));
            }
        }
    }
    public void unlockSpeed(int speed){
        if(speed>30){
            if(MainActivity.isSignedIn()) {
                Games.Achievements.unlock(googleApiClient, baseGameActivity.getString(R.string.achievement_excessive_speeding_ticket));
            }
            else {
                editor.putString(aesEncrDec.encrypt("status"),aesEncrDec.encrypt("pending"));
                editor.putString(aesEncrDec.encrypt(baseGameActivity.getString(R.string.achievement_excessive_speeding_ticket)),aesEncrDec.encrypt("true"));
            }
        }
        if(speed>40){
            if(MainActivity.isSignedIn()) {
                Games.Achievements.unlock(googleApiClient, baseGameActivity.getString(R.string.achievement_god_speed));
            }
            else {
                editor.putString(aesEncrDec.encrypt("status"),aesEncrDec.encrypt("pending"));
                editor.putString(aesEncrDec.encrypt(baseGameActivity.getString(R.string.achievement_god_speed)),aesEncrDec.encrypt("true"));
            }
        }
    }
    public void unlockMinSpeed(int speed){
        if(speed<=4){
            if(MainActivity.isSignedIn()) {
                Games.Achievements.unlock(googleApiClient, baseGameActivity.getString(R.string.achievement_slow_is_better));
            }
            else {
                editor.putString(aesEncrDec.encrypt("status"),aesEncrDec.encrypt("pending"));
                editor.putString(aesEncrDec.encrypt(baseGameActivity.getString(R.string.achievement_slow_is_better)),aesEncrDec.encrypt("true"));
            }
        }
    }
    public void unlockCrashed(){
        if(MainActivity.isSignedIn()) {
            Games.Achievements.unlock(googleApiClient, baseGameActivity.getString(R.string.achievement_ouch_that_was_a_bad_hit));
        }
        else {
            editor.putString(aesEncrDec.encrypt("status"),aesEncrDec.encrypt("pending"));
            editor.putString(aesEncrDec.encrypt(baseGameActivity.getString(R.string.achievement_ouch_that_was_a_bad_hit)),aesEncrDec.encrypt("true"));
        }
    }
    public void unlockCrashedSpeed(int speed){
        if(speed<=4){
            if(MainActivity.isSignedIn()) {
                Games.Achievements.unlock(googleApiClient, baseGameActivity.getString(R.string.achievement_smooth_as_silk));
            }
            else {
                editor.putString(aesEncrDec.encrypt("status"),aesEncrDec.encrypt("pending"));
                editor.putString(aesEncrDec.encrypt(baseGameActivity.getString(R.string.achievement_smooth_as_silk)),aesEncrDec.encrypt("true"));
            }
        }
        if(speed==15){
            if(MainActivity.isSignedIn()) {
                Games.Achievements.unlock(googleApiClient, baseGameActivity.getString(R.string.achievement_well_timed_hit));
            }
            else {
                editor.putString(aesEncrDec.encrypt("status"),aesEncrDec.encrypt("pending"));
                editor.putString(aesEncrDec.encrypt(baseGameActivity.getString(R.string.achievement_well_timed_hit)),aesEncrDec.encrypt("true"));
            }
        }
        if(speed==30){
            if(MainActivity.isSignedIn()) {
                Games.Achievements.unlock(googleApiClient, baseGameActivity.getString(R.string.achievement_high_speed_crash));
            }
            else {
                editor.putString(aesEncrDec.encrypt("status"),aesEncrDec.encrypt("pending"));
                editor.putString(aesEncrDec.encrypt(baseGameActivity.getString(R.string.achievement_high_speed_crash)),aesEncrDec.encrypt("true"));
            }
        }
    }
    public void unlockSocialite(){
        editor.putString(aesEncrDec.encrypt("status"),aesEncrDec.encrypt("pending"));
        editor.putString(aesEncrDec.encrypt(baseGameActivity.getString(R.string.achievement_socialite)),aesEncrDec.encrypt("true"));
    }
    public void unlockGravirated(){
        editor.putString(aesEncrDec.encrypt("status"),aesEncrDec.encrypt("pending"));
        editor.putString(aesEncrDec.encrypt(baseGameActivity.getString(R.string.achievement_gravirated)),aesEncrDec.encrypt("true"));
    }
    public void unlockPassedDot(int passedDot){
        if(passedDot>=10){
            if(MainActivity.isSignedIn()) {
                Games.Achievements.unlock(googleApiClient, baseGameActivity.getString(R.string.achievement_clear_pass));
            }
            else {
                editor.putString(aesEncrDec.encrypt("status"),aesEncrDec.encrypt("pending"));
                editor.putString(aesEncrDec.encrypt(baseGameActivity.getString(R.string.achievement_clear_pass)),aesEncrDec.encrypt("true"));
            }
        }
        if(passedDot>=30){
            if(MainActivity.isSignedIn()) {
                Games.Achievements.unlock(googleApiClient, baseGameActivity.getString(R.string.achievement_cruising_like_a_boss));
            }
            else {
                editor.putString(aesEncrDec.encrypt("status"),aesEncrDec.encrypt("pending"));
                editor.putString(aesEncrDec.encrypt(baseGameActivity.getString(R.string.achievement_cruising_like_a_boss)),aesEncrDec.encrypt("true"));
            }
        }
        if(passedDot>=60){
            if(MainActivity.isSignedIn()) {
                Games.Achievements.unlock(googleApiClient, baseGameActivity.getString(R.string.achievement_the_sky_is_the_lower_limit));
            }
            else {
                editor.putString(aesEncrDec.encrypt("status"),aesEncrDec.encrypt("pending"));
                editor.putString(aesEncrDec.encrypt(baseGameActivity.getString(R.string.achievement_the_sky_is_the_lower_limit)),aesEncrDec.encrypt("true"));
            }
        }
    }
    public void unlockGamesPlayed(int games){
        if(games>=10){
            if(MainActivity.isSignedIn()) {
                Games.Achievements.unlock(googleApiClient, baseGameActivity.getString(R.string.achievement_10_games));
            }
            else {
                editor.putString(aesEncrDec.encrypt("status"),aesEncrDec.encrypt("pending"));
                editor.putString(aesEncrDec.encrypt(baseGameActivity.getString(R.string.achievement_10_games)),aesEncrDec.encrypt("true"));
            }
        }
        if(games>=50){
            if(MainActivity.isSignedIn()) {
                Games.Achievements.unlock(googleApiClient, baseGameActivity.getString(R.string.achievement_50_games));
            }
            else {
                editor.putString(aesEncrDec.encrypt("status"),aesEncrDec.encrypt("pending"));
                editor.putString(aesEncrDec.encrypt(baseGameActivity.getString(R.string.achievement_50_games)),aesEncrDec.encrypt("true"));
            }
        }
        if(games>=100){
            if(MainActivity.isSignedIn()) {
                Games.Achievements.unlock(googleApiClient, baseGameActivity.getString(R.string.achievement_100_games));
            }
            else {
                editor.putString(aesEncrDec.encrypt("status"),aesEncrDec.encrypt("pending"));
                editor.putString(aesEncrDec.encrypt(baseGameActivity.getString(R.string.achievement_100_games)),aesEncrDec.encrypt("true"));
            }
        }
        if(games>=200){
            if(MainActivity.isSignedIn()) {
                Games.Achievements.unlock(googleApiClient, baseGameActivity.getString(R.string.achievement_200_games));
            }
            else {
                editor.putString(aesEncrDec.encrypt("status"),aesEncrDec.encrypt("pending"));
                editor.putString(aesEncrDec.encrypt(baseGameActivity.getString(R.string.achievement_200_games)),aesEncrDec.encrypt("true"));
            }
        }
        if(games>=1000){
            if(MainActivity.isSignedIn()) {
                Games.Achievements.unlock(googleApiClient, baseGameActivity.getString(R.string.achievement_1000_games));
            }
            else {
                editor.putString(aesEncrDec.encrypt("status"),aesEncrDec.encrypt("pending"));
                editor.putString(aesEncrDec.encrypt(baseGameActivity.getString(R.string.achievement_1000_games)),aesEncrDec.encrypt("true"));
            }
        }
    }
    public void commitItem(){
        editor.commit();
    }
}
