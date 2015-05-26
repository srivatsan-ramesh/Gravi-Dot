package com.example.srivatsan.game;

import android.content.SharedPreferences;
import android.util.Log;

import com.flatearth.gravidot.R;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;

import org.andengine.ui.activity.BaseGameActivity;

/**
 * Created by srivatsan on 18/5/15.
 */
public class PushOnConnection {
    GoogleApiClient googleApiClient;
    BaseGameActivity baseGameActivity;
    public PushOnConnection(GoogleApiClient googleApiClient,BaseGameActivity baseGameActivity){
        this.googleApiClient = googleApiClient;
        this.baseGameActivity = baseGameActivity;
        AesEncrDec aesEncrDec = new AesEncrDec();
        Achievements a = new Achievements(googleApiClient,baseGameActivity);
        SharedPreferences s = baseGameActivity.getSharedPreferences("GAME",0);
        SharedPreferences.Editor e = s.edit();
        Log.d(aesEncrDec.encrypt("status"),"encryption");
        String status = s.getString(aesEncrDec.encrypt("status"), "");
        if(!status.equals("")){
            e.remove(aesEncrDec.encrypt("status"));
            if(s.contains(aesEncrDec.encrypt(baseGameActivity.getString(R.string.achievement_the_beginners_club)))){
                //s.edit().remove(aesEncrDec.encrypt(baseGameActivity.getString(R.string.achievement_beginner)));
                Games.Achievements.unlock(googleApiClient,baseGameActivity.getString(R.string.achievement_the_beginners_club));
            }
            if(s.contains(aesEncrDec.encrypt(baseGameActivity.getString(R.string.achievement_the_five_squared_club)))){
                //s.edit().remove(aesEncrDec.encrypt(baseGameActivity.getString(R.string.achievement_pro)));
                Games.Achievements.unlock(googleApiClient,baseGameActivity.getString(R.string.achievement_the_five_squared_club));
            }
            if(s.contains(aesEncrDec.encrypt(baseGameActivity.getString(R.string.achievement_the_century_club)))){
                //s.edit().remove(aesEncrDec.encrypt(baseGameActivity.getString(R.string.achievement_pro)));
                Games.Achievements.unlock(googleApiClient,baseGameActivity.getString(R.string.achievement_the_century_club));
            }
            if(s.contains(aesEncrDec.encrypt(baseGameActivity.getString(R.string.achievement_excessive_speeding_ticket)))){
                //s.edit().remove(aesEncrDec.encrypt(baseGameActivity.getString(R.string.achievement_beginner)));
                Games.Achievements.unlock(googleApiClient, baseGameActivity.getString(R.string.achievement_excessive_speeding_ticket));
            }
            if(s.contains(aesEncrDec.encrypt(baseGameActivity.getString(R.string.achievement_god_speed)))){
                //s.edit().remove(aesEncrDec.encrypt(baseGameActivity.getString(R.string.achievement_beginner)));
                Games.Achievements.unlock(googleApiClient, baseGameActivity.getString(R.string.achievement_god_speed));
            }
            if(s.contains(aesEncrDec.encrypt(baseGameActivity.getString(R.string.achievement_slow_is_better)))){
                //s.edit().remove(aesEncrDec.encrypt(baseGameActivity.getString(R.string.achievement_beginner)));
                Games.Achievements.unlock(googleApiClient, baseGameActivity.getString(R.string.achievement_slow_is_better));
            }

            if(s.contains(aesEncrDec.encrypt(baseGameActivity.getString(R.string.achievement_ouch_that_was_a_bad_hit)))){
                //s.edit().remove(aesEncrDec.encrypt(baseGameActivity.getString(R.string.achievement_beginner)));
                Games.Achievements.unlock(googleApiClient,baseGameActivity.getString(R.string.achievement_ouch_that_was_a_bad_hit));
            }
            if(s.contains(aesEncrDec.encrypt(baseGameActivity.getString(R.string.achievement_smooth_as_silk)))){
                //s.edit().remove(aesEncrDec.encrypt(baseGameActivity.getString(R.string.achievement_beginner)));
                Games.Achievements.unlock(googleApiClient,baseGameActivity.getString(R.string.achievement_smooth_as_silk));
            }
            if(s.contains(aesEncrDec.encrypt(baseGameActivity.getString(R.string.achievement_well_timed_hit)))){
                //s.edit().remove(aesEncrDec.encrypt(baseGameActivity.getString(R.string.achievement_beginner)));
                Games.Achievements.unlock(googleApiClient,baseGameActivity.getString(R.string.achievement_well_timed_hit));
            }
            if(s.contains(aesEncrDec.encrypt(baseGameActivity.getString(R.string.achievement_high_speed_crash)))){
                //s.edit().remove(aesEncrDec.encrypt(baseGameActivity.getString(R.string.achievement_beginner)));
                Games.Achievements.unlock(googleApiClient,baseGameActivity.getString(R.string.achievement_high_speed_crash));
            }
            if(s.contains(aesEncrDec.encrypt(baseGameActivity.getString(R.string.achievement_socialite)))){
                //s.edit().remove(aesEncrDec.encrypt(baseGameActivity.getString(R.string.achievement_beginner)));
                Games.Achievements.unlock(googleApiClient,baseGameActivity.getString(R.string.achievement_socialite));
            }
            if(s.contains(aesEncrDec.encrypt(baseGameActivity.getString(R.string.achievement_gravirated)))){
                //s.edit().remove(aesEncrDec.encrypt(baseGameActivity.getString(R.string.achievement_beginner)));
                Games.Achievements.unlock(googleApiClient,baseGameActivity.getString(R.string.achievement_gravirated));
            }
            if(s.contains(aesEncrDec.encrypt(baseGameActivity.getString(R.string.achievement_clear_pass)))){
                //s.edit().remove(aesEncrDec.encrypt(baseGameActivity.getString(R.string.achievement_beginner)));
                Games.Achievements.unlock(googleApiClient,baseGameActivity.getString(R.string.achievement_clear_pass));
            }
            if(s.contains(aesEncrDec.encrypt(baseGameActivity.getString(R.string.achievement_cruising_like_a_boss)))){
                //s.edit().remove(aesEncrDec.encrypt(baseGameActivity.getString(R.string.achievement_beginner)));
                Games.Achievements.unlock(googleApiClient,baseGameActivity.getString(R.string.achievement_cruising_like_a_boss));
            }
            if(s.contains(aesEncrDec.encrypt(baseGameActivity.getString(R.string.achievement_the_sky_is_the_lower_limit)))) {
                //s.edit().remove(aesEncrDec.encrypt(baseGameActivity.getString(R.string.achievement_beginner)));
                Games.Achievements.unlock(googleApiClient, baseGameActivity.getString(R.string.achievement_the_sky_is_the_lower_limit));
            }
            if(s.contains(aesEncrDec.encrypt(baseGameActivity.getString(R.string.achievement_10_games)))){
                //s.edit().remove(aesEncrDec.encrypt(baseGameActivity.getString(R.string.achievement_beginner)));
                Games.Achievements.unlock(googleApiClient,baseGameActivity.getString(R.string.achievement_10_games));
            }
            if(s.contains(aesEncrDec.encrypt(baseGameActivity.getString(R.string.achievement_50_games)))){
                //s.edit().remove(aesEncrDec.encrypt(baseGameActivity.getString(R.string.achievement_beginner)));
                Games.Achievements.unlock(googleApiClient,baseGameActivity.getString(R.string.achievement_50_games));
            }
            if(s.contains(aesEncrDec.encrypt(baseGameActivity.getString(R.string.achievement_100_games)))){
                //s.edit().remove(aesEncrDec.encrypt(baseGameActivity.getString(R.string.achievement_beginner)));
                Games.Achievements.unlock(googleApiClient,baseGameActivity.getString(R.string.achievement_100_games));
            }
            if(s.contains(aesEncrDec.encrypt(baseGameActivity.getString(R.string.achievement_200_games)))){
                //s.edit().remove(aesEncrDec.encrypt(baseGameActivity.getString(R.string.achievement_beginner)));
                Games.Achievements.unlock(googleApiClient,baseGameActivity.getString(R.string.achievement_200_games));
            }
            if(s.contains(aesEncrDec.encrypt(baseGameActivity.getString(R.string.achievement_1000_games)))){
                //s.edit().remove(aesEncrDec.encrypt(baseGameActivity.getString(R.string.achievement_beginner)));
                Games.Achievements.unlock(googleApiClient,baseGameActivity.getString(R.string.achievement_1000_games));
            }
            if(s.contains(aesEncrDec.encrypt(baseGameActivity.getString(R.string.leaderboard_score)))){
                Games.Leaderboards.submitScore(googleApiClient, baseGameActivity.getString(R.string.leaderboard_score), Integer.parseInt(aesEncrDec.decrypt(s.getString(aesEncrDec.encrypt(baseGameActivity.getString(R.string.leaderboard_score)), ""))));
                e.remove(aesEncrDec.encrypt(baseGameActivity.getString(R.string.leaderboard_score)));
            }
            if(s.contains(aesEncrDec.encrypt(baseGameActivity.getString(R.string.leaderboard_speed)))){
                Games.Leaderboards.submitScore(googleApiClient, baseGameActivity.getString(R.string.leaderboard_speed),Integer.parseInt(aesEncrDec.decrypt(s.getString(aesEncrDec.encrypt(baseGameActivity.getString(R.string.leaderboard_speed)),""))));
                e.remove(aesEncrDec.encrypt(baseGameActivity.getString(R.string.leaderboard_speed)));
            }
            e.commit();
        }
    }
}
