package com.example.srivatsan.game;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;

import org.andengine.ui.activity.BaseGameActivity;

/**
 * Created by srivatsan on 14/5/15.
 */
public class Achievements {
    GoogleApiClient googleApiClient;
    BaseGameActivity baseGameActivity;
    public Achievements(GoogleApiClient  googleApiClient,BaseGameActivity baseGameActivity){
        this.baseGameActivity=baseGameActivity;
        this.googleApiClient=googleApiClient;
    }
    public void unlockScore(int score){
        if(score>10){
            Games.Achievements.unlock(googleApiClient, baseGameActivity.getString(R.string.achievement_beginner));
        }
        if(score>100){
            Games.Achievements.unlock(googleApiClient, baseGameActivity.getString(R.string.achievement_pro));
        }
    }
    public void unlockSpeed(int speed){
        if(speed>40){
            Games.Achievements.unlock(googleApiClient, baseGameActivity.getString(R.string.achievement_god_speed));
        }
    }
    public void unlockDot(int no_of_dots){
        if(no_of_dots>4){
            Games.Achievements.unlock(googleApiClient, baseGameActivity.getString(R.string.achievement_dot_master));
        }
    }
    public void unlockCrashed(){
        Games.Achievements.unlock(googleApiClient, baseGameActivity.getString(R.string.achievement_crashed));
    }
}
