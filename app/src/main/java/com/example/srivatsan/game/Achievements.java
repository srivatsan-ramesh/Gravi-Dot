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
    int mode;
    public Achievements(GoogleApiClient  googleApiClient,BaseGameActivity baseGameActivity){
        this.baseGameActivity=baseGameActivity;
        this.googleApiClient=googleApiClient;
        example = baseGameActivity.getSharedPreferences("GAME", 0);
        editor = example.edit();
        aesEncrDec = new AesEncrDec();
        mode=SceneManager.MODE;
    }
    public void post(int id){
        if(MainActivity.isSignedIn()) {
            Games.Achievements.unlock(googleApiClient, baseGameActivity.getString(id));
        }
        else {
            editor.putString(aesEncrDec.encrypt("status"),aesEncrDec.encrypt("pending"));
            editor.putString(aesEncrDec.encrypt(baseGameActivity.getString(id)),aesEncrDec.encrypt("true"));
        }
    }
    public void unlockScore(int score){

        if(score>5){
            if(mode==0)
                post(R.string.achievement_the_beginners_club);
            else
                post(R.string.achievement_the_beginners_club__repel_mode);

        }
        if(score>25){
            if(mode==0)
                post(R.string.achievement_the_five_squared_club);
            else
                post(R.string.achievement_the_five_squared_club__repel_mode);

        }
        if(score>100){
            if(mode==0)
                post(R.string.achievement_the_century_club);
            else
                post(R.string.achievement_the_century_club__repel_mode);
        }
        if(score==200){
            post(R.string.achievement_200_score);
        }
        if(score>500){
            if(mode==0)
                post(R.string.achievement_500);
            else
                post(R.string.achievement_half_way_to_a_millenium_and_beyond__repel_mode);

        }
        if(score>1000){
            if(mode==0)
                post(R.string.achievement_1000);
            else
                post(R.string.achievement_a_millennium_milestone__repel_mode);
        }
    }
    public void unlockSpeed(int speed){
        if(speed>30){
            if(mode==0)
                post(R.string.achievement_excessive_speeding_ticket);
            else
                post(R.string.achievement_too_lethargic__repel_mode);

        }
        if(speed>40){
            if(mode==0)
                post(R.string.achievement_god_speed);
            else
                post(R.string.achievement_handful_of_speed__repel_mode);

        }
        if(speed>60){
            if(mode==0)
                post(R.string.achievement_speed1);
            else
                post(R.string.achievement_overspeeding__repel_mode);

        }
        if(speed>80){
            if(mode==0)
                post(R.string.achievement_speed2);
            else
                post(R.string.achievement_reckless_speeding__repel_mode);

        }
        if(speed>100){
            if(mode==0)
                post(R.string.achievement_speed3);
            else
                post(R.string.achievement_insanity_at_its_peak__repel_mode);

        }
        if(speed>120){
            if(mode==0)
                post(R.string.achievement_speed4);
            else
                post(R.string.achievement_god_speed__repel_mode);

        }
    }
    public void unlockMinSpeed(int speed){
        if(speed<=4){
            if(mode==0)
                post(R.string.achievement_slow_is_better);
            else
                post(R.string.achievement_slow_is_better__repel_mode);

        }
    }
    public void unlockCrashed(){
        if(mode==0)
            post(R.string.achievement_ouch_that_was_a_bad_hit);
        else
            post(R.string.achievement_ouch_that_was_a_bad_hit__repel_mode);

    }
    public void unlockCrashedSpeed(int speed){
        if(speed<=4){
            if(mode==0)
                post(R.string.achievement_smooth_as_silk);
            else
                post(R.string.achievement_smooth_as_silk__repel_mode);

        }
        if(speed==25){
            if(mode==0)
                post(R.string.achievement_well_timed_hit);
            else
                post(R.string.achievement_well_timed_hit__repel_mode);

        }
        if(speed==50){
            if(mode==0)
                post(R.string.achievement_high_speed_crash);
            else
                post(R.string.achievement_high_speed_crash__repel_mode);
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
            if(mode==0)
                post(R.string.achievement_clear_pass);
            else
                post(R.string.achievement_clear_pass__repel_mode);

        }
        if(passedDot>=50){
            if(mode==0)
                post(R.string.achievement_cruising_like_a_boss);
            else
                post(R.string.achievement_cruising_like_a_boss__repel_mode);

        }
        if(passedDot>=100){
            if(mode==0)
                post(R.string.achievement_the_sky_is_the_lower_limit);
            else
                post(R.string.achievement_the_sky_is_the_lower_limit__repel_mode);

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
        if(games>=500){
            if(MainActivity.isSignedIn()) {
                Games.Achievements.unlock(googleApiClient, baseGameActivity.getString(R.string.achievement_500_games));
            }
            else {
                editor.putString(aesEncrDec.encrypt("status"),aesEncrDec.encrypt("pending"));
                editor.putString(aesEncrDec.encrypt(baseGameActivity.getString(R.string.achievement_500_games)),aesEncrDec.encrypt("true"));
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
