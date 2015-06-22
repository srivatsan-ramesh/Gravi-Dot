package com.example.srivatsan.game;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;


import com.google.android.gms.games.Games;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.BaseGameActivity;

/**
 * Created by srivatsan on 8/5/15.
 */
public class OptionButton extends Sprite {
    protected int ra_sh_le;
    protected BaseGameActivity act;
    public OptionButton(float pX, float pY, ITextureRegion pTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager, int ra_sh_le, BaseGameActivity act) {
        super(pX, pY, pTextureRegion, pVertexBufferObjectManager);
        this.ra_sh_le=ra_sh_le;
        this.act=act;
    }
    ScaleModifier shrink,blow;

    @Override
    public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
        if(pSceneTouchEvent.isActionUp() || pSceneTouchEvent.isActionMove()) {

            if(blow!=null)
            this.unregisterEntityModifier(blow);
            this.registerEntityModifier(shrink = new ScaleModifier(0.1f,1.4f,1){
                @Override
                protected void onModifierFinished(IEntity pItem)
                {
                    super.onModifierFinished(pItem);
                    SharedPreferences s = act.getSharedPreferences("GAME",0);
                    String sign = s.getString("SignedIn","false");
                    if(!pSceneTouchEvent.isActionMove())
                    switch (ra_sh_le){
                        case 0://leaderboard
                        {
                            try {
                                act.startActivityForResult(Games.Leaderboards.getAllLeaderboardsIntent(MainActivity.mGoogleApiClient),
                                        MainActivity.RC_UNUSED);
                            } catch (Exception e){
                                MainActivity.googleConnect((MainActivity)act);
                                // BaseGameUtils.makeSimpleDialog(act, act.getString(R.string.leaderboards_not_available)).show();
                            }
                        }
                        break;
                        case 1://achievement
                            try {
                                act.startActivityForResult(Games.Achievements.getAchievementsIntent(MainActivity.mGoogleApiClient),
                                        MainActivity.RC_UNUSED);
                            } catch(Exception e) {
                                MainActivity.googleConnect((MainActivity)act);
                                // BaseGameUtils.makeSimpleDialog(act, act.getString(R.string.leaderboards_not_available)).show();
                            }
                            break;
                        case 2://share
                            Intent textShareIntent = new Intent(Intent.ACTION_SEND);
                            textShareIntent.putExtra(Intent.EXTRA_TEXT, "Tackle the dots. Battle against gravity. Try the all new #GraviDot now! https://play.google.com/store/apps/details?id=com.flatearth.gravidot");
                            textShareIntent.setType("text/plain");
                            act.startActivity(Intent.createChooser(textShareIntent, "Share with..."));
                            Achievements a = new Achievements(MainActivity.mGoogleApiClient,act);
                            a.unlockSocialite();
                            a.commitItem();
                            break;
                        case 3://rate
                            act.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.flatearth.gravidot")));
                            Achievements ac = new Achievements(MainActivity.mGoogleApiClient,act);
                            ac.unlockGravirated();
                            ac.commitItem();

                            break;


                    }

                }
            });

        }
        else if(pSceneTouchEvent.isActionDown()){

                if(shrink!=null){
                    this.unregisterEntityModifier(shrink);
                }
                this.registerEntityModifier(blow = new ScaleModifier(0.1f,1,1.4f){
                    @Override
                    protected void onModifierFinished(IEntity pItem)
                    {
                        super.onModifierFinished(pItem);
                        //pItem.registerEntityModifier(new ScaleModifier(0.1f,1.4f,1));
                        // Your action after finishing modifier


                    }
                });
        }

        return true;
    }
}
