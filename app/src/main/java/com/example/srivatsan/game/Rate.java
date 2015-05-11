package com.example.srivatsan.game;

import android.content.Intent;

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
public class Rate extends Sprite {
    protected int ra_sh_le;
    protected BaseGameActivity act;
    public Rate(float pX, float pY, ITextureRegion pTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager,int ra_sh_le,BaseGameActivity act) {
        super(pX, pY, pTextureRegion, pVertexBufferObjectManager);
        this.ra_sh_le=ra_sh_le;
        this.act=act;
    }
    @Override
    public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
        if(pSceneTouchEvent.isActionDown()){
            this.registerEntityModifier(new ScaleModifier(0.1f,1,0.6f){
                @Override
                protected void onModifierFinished(IEntity pItem)
                {
                    super.onModifierFinished(pItem);
                    pItem.registerEntityModifier(new ScaleModifier(0.1f,0.6f,1));
                    // Your action after finishing modifier

                }
            });
        }
        else {
            switch (ra_sh_le){
                case 0://rate
                     {
                    if (MainActivity.isSignedIn()) {
                        act.startActivityForResult(Games.Leaderboards.getAllLeaderboardsIntent(MainActivity.mGoogleApiClient),
                                MainActivity.RC_UNUSED);
                    } else {

                       // BaseGameUtils.makeSimpleDialog(act, act.getString(R.string.leaderboards_not_available)).show();
                    }
                }
                    break;
                case 1:Intent textShareIntent = new Intent(Intent.ACTION_SEND);
                    textShareIntent.putExtra(Intent.EXTRA_TEXT, " https://play.google.com/store/apps/details?id-com. ");
                    textShareIntent.setType("text/plain");
                    act.startActivity(Intent.createChooser(textShareIntent, "Share with..."));
                    break;
                case 2://leaderboard
                    break;
            }
        }
        return true;
    }
}
