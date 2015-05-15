package com.example.srivatsan.game;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;

import org.andengine.ui.activity.BaseGameActivity;

/**
 * Created by srivatsan on 14/5/15.
 */
public class Leaderboards {
    GoogleApiClient googleApiClient;
    BaseGameActivity baseGameActivity;
    public Leaderboards(GoogleApiClient  googleApiClient,BaseGameActivity baseGameActivity){
        this.baseGameActivity=baseGameActivity;
        this.googleApiClient=googleApiClient;
    }
    public void postScore(int score){
        Games.Leaderboards.submitScore(googleApiClient, baseGameActivity.getString(R.string.leaderboard_score),score);
    }
    public void postSpeed(int speed){
        Games.Leaderboards.submitScore(googleApiClient, baseGameActivity.getString(R.string.leaderboard_speed),speed);
    }
}
