package com.example.srivatsan.game;

import android.content.SharedPreferences;

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
    int achievements[];
    public PushOnConnection(GoogleApiClient googleApiClient,BaseGameActivity baseGameActivity){
        this.googleApiClient = googleApiClient;
        this.baseGameActivity = baseGameActivity;
        AesEncrDec aesEncrDec = new AesEncrDec();
        Achievements a = new Achievements(googleApiClient,baseGameActivity);
        SharedPreferences s = baseGameActivity.getSharedPreferences("GAME",0);
        SharedPreferences.Editor e = s.edit();
        achievements = new int[] {
                R.string.achievement_the_beginners_club,
                R.string.achievement_the_five_squared_club,
                R.string.achievement_the_century_club,
                R.string.achievement_200_score,
                R.string.achievement_500,
                R.string.achievement_1000,
                R.string.achievement_excessive_speeding_ticket,
                R.string.achievement_god_speed,
                R.string.achievement_speed1,
                R.string.achievement_speed2,
                R.string.achievement_speed3,
                R.string.achievement_speed4,
                R.string.achievement_slow_is_better,
                R.string.achievement_ouch_that_was_a_bad_hit,
                R.string.achievement_smooth_as_silk,
                R.string.achievement_well_timed_hit,
                R.string.achievement_high_speed_crash,
                R.string.achievement_gravirated,
                R.string.achievement_socialite,
                R.string.achievement_clear_pass,
                R.string.achievement_cruising_like_a_boss,
                R.string.achievement_the_sky_is_the_lower_limit,
                R.string.achievement_10_games,
                R.string.achievement_50_games,
                R.string.achievement_100_games,
                R.string.achievement_200_games,
                R.string.achievement_500_games,
                R.string.achievement_1000_games,
                R.string.achievement_the_beginners_club__repel_mode,
                R.string.achievement_the_five_squared_club__repel_mode,
                R.string.achievement_the_century_club__repel_mode,
                R.string.achievement_half_way_to_a_millenium_and_beyond__repel_mode,
                R.string.achievement_a_millennium_milestone__repel_mode,
                R.string.achievement_slow_is_better__repel_mode,
                R.string.achievement_too_lethargic__repel_mode,
                R.string.achievement_handful_of_speed__repel_mode,
                R.string.achievement_overspeeding__repel_mode,
                R.string.achievement_reckless_speeding__repel_mode,
                R.string.achievement_insanity_at_its_peak__repel_mode,
                R.string.achievement_god_speed__repel_mode,
                R.string.achievement_ouch_that_was_a_bad_hit__repel_mode,
                R.string.achievement_smooth_as_silk__repel_mode,
                R.string.achievement_well_timed_hit__repel_mode,
                R.string.achievement_high_speed_crash__repel_mode,
                R.string.achievement_clear_pass__repel_mode,
                R.string.achievement_cruising_like_a_boss__repel_mode,
                R.string.achievement_the_sky_is_the_lower_limit__repel_mode
        };
        String status = s.getString(aesEncrDec.encrypt("status"), "");
        //Log.d("achievementsconnect",achievements.length+"");
        if(!status.equals("")){
            e.remove(aesEncrDec.encrypt("status"));
            for(int i=0;i<achievements.length;i++){
                //Log.d("achievement",baseGameActivity.getString(achievements[1]));
                if(s.contains(aesEncrDec.encrypt(baseGameActivity.getString(achievements[i])))){
                    //s.edit().remove(aesEncrDec.encrypt(baseGameActivity.getString(R.string.achievement_beginner)));

                    Games.Achievements.unlock(googleApiClient,baseGameActivity.getString(achievements[i]));
                }
            }
            if(s.contains(aesEncrDec.encrypt(baseGameActivity.getString(R.string.leaderboard_score)))){
                Games.Leaderboards.submitScore(googleApiClient, baseGameActivity.getString(R.string.leaderboard_score), Integer.parseInt(aesEncrDec.decrypt(s.getString(aesEncrDec.encrypt(baseGameActivity.getString(R.string.leaderboard_score)), ""))));
                e.remove(aesEncrDec.encrypt(baseGameActivity.getString(R.string.leaderboard_score)));
            }
            if(s.contains(aesEncrDec.encrypt(baseGameActivity.getString(R.string.leaderboard_speed)))){
                Games.Leaderboards.submitScore(googleApiClient, baseGameActivity.getString(R.string.leaderboard_speed),Integer.parseInt(aesEncrDec.decrypt(s.getString(aesEncrDec.encrypt(baseGameActivity.getString(R.string.leaderboard_speed)),""))));
                e.remove(aesEncrDec.encrypt(baseGameActivity.getString(R.string.leaderboard_speed)));
            }
            if(s.contains(aesEncrDec.encrypt(baseGameActivity.getString(R.string.leaderboard_scorerepel_mode)))){
                Games.Leaderboards.submitScore(googleApiClient, baseGameActivity.getString(R.string.leaderboard_scorerepel_mode), Integer.parseInt(aesEncrDec.decrypt(s.getString(aesEncrDec.encrypt(baseGameActivity.getString(R.string.leaderboard_scorerepel_mode)), ""))));
                e.remove(aesEncrDec.encrypt(baseGameActivity.getString(R.string.leaderboard_scorerepel_mode)));
            }
            if(s.contains(aesEncrDec.encrypt(baseGameActivity.getString(R.string.leaderboard_speedrepel_mode)))){
                Games.Leaderboards.submitScore(googleApiClient, baseGameActivity.getString(R.string.leaderboard_speedrepel_mode),Integer.parseInt(aesEncrDec.decrypt(s.getString(aesEncrDec.encrypt(baseGameActivity.getString(R.string.leaderboard_speedrepel_mode)),""))));
                e.remove(aesEncrDec.encrypt(baseGameActivity.getString(R.string.leaderboard_speedrepel_mode)));
            }
            e.commit();
        }
    }
}
