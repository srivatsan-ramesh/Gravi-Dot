package com.example.srivatsan.game;

import android.content.SharedPreferences;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Joint;
import com.google.android.gms.ads.InterstitialAd;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.background.ParallaxBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.entity.util.FPSLogger;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.HorizontalAlign;
import org.andengine.util.color.Color;

import java.util.Iterator;

/**
 * Created by srivatsan on 19/4/15.
 */
public class SceneManager {
    int passedDots=0;
    static int MODE=0;
    int crashedSpeed=-1;
    Body body;
    Text SCORE,HighScore,PrevScore,speed,prevSpeed,highspeed;
    int current_speed=0,min_speed=20;
    private InterstitialAd interstitial;
    int score=0;
    int PAUSE_FLAG=0;
    Sprite PlayOrRetryButton,RepelModeButton;
    protected static final int CAMERA_WIDTH = 800;
    protected static final int CAMERA_HEIGHT = 480;
    protected static float INI_POS_X = (CAMERA_WIDTH-MainActivity.canvasSurface.getMarginHorizontal())/5;
    protected static float INI_POS_Y = ((float)CAMERA_HEIGHT)/2;
    protected static int RADIUS = 32;
    float TouchX = INI_POS_X;
    float TouchY = INI_POS_Y;
    Sprite BlueDot;
    Rectangle greenarea;
    Sprite Gravi;
    int Start=0;
    BitmapTextureAtlas MenuSceneTexture;//, FinishSceneTexture;//,playerTextureBg;
    Font TextFontMedium,/*TextFontSmall*/TextFontLarge,TextFontMediumWhite,TextFontfinishlarge,TextFontfinishsmall;
    Rectangle ground;

    PhysicsWorld physicsWorld;
    Text PauseTimerText;


    ITextureRegion playerTextureRegion,playerTextureRegionLeaderboard,playerTextureRegionRepel,playerTextureRegionAchievement,playerTextureRegionRate,playerTextureRegionHome,playerTextureRegionGameOverOverlay,playerTextureRegionGameStars,playerTextureRegionGameBg,playerTextureRegionHomeScreen,playerTextureRegionShare,playerTextureRegionRetry, playerTextureRegionAttract,playerTextureRegionMainChar;

    private AllScenes currentScene;
    BaseGameActivity activity;
    Engine engine;
    Camera camera;
    BitmapTextureAtlas splashTA;
    ITextureRegion splashTR;
    Scene splashScene,gameScene,menuScene,finishscene;
    public AllScenes getCurrentScene(){
        return currentScene;
    }
    public void setCurrentScene(AllScenes currentScene){
        this.currentScene = currentScene;
        switch (currentScene){
            case SPLASH:
                break;
            case MENU:
                //engine setscene
                //activity.getWindow().getDecorView().setBackgroundColor(android.graphics.Color.rgb(68,110,141));
                engine.setScene(menuScene);
                break;
            case GAME:
                //activity.getWindow().getDecorView().setBackgroundColor(android.graphics.Color.rgb(68,110,141));
                engine.setScene(gameScene);
                break;
            case FINISH:
                //activity.getWindow().getDecorView().setBackgroundColor(android.graphics.Color.rgb(68,110,141));
                engine.setScene(finishscene);
        }
    }
    public enum AllScenes{
        SPLASH,
        MENU,
        GAME,
        FINISH
    }
    public SceneManager(BaseGameActivity act, Engine eng ,Camera cam){
        this.activity = act;
        this.engine = eng;
        this.camera = cam;
    }
    public void loadSplashResources(){
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
        splashTA = new BitmapTextureAtlas(this.activity.getTextureManager(),1024,512);
        splashTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(splashTA,activity,"splashscreen.png",0,0);
        splashTA.load();
    }

    public void loadGameResources(){
        loadGfx();
        //createGameScene();
    }
    private void loadGfx() {
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
        //width and height 2^x
        MenuSceneTexture = new BitmapTextureAtlas(activity.getTextureManager(),2048,1024);
        /*playerTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(MenuSceneTexture,activity,"charnew.png",800,24);
        playerTextureRegionPause = BitmapTextureAtlasTextureRegionFactory.createFromAsset(MenuSceneTexture,activity,"pause.png",864,24);
        playerTextureRegionRetry = BitmapTextureAtlasTextureRegionFactory.createFromAsset(MenuSceneTexture,activity,"retry.gif",800,152);
        playerTextureRegionAttract = BitmapTextureAtlasTextureRegionFactory.createFromAsset(MenuSceneTexture,activity,"play.gif",800,88);
        playerTextureRegionRate = BitmapTextureAtlasTextureRegionFactory.createFromAsset(MenuSceneTexture,activity,"star.png",800,216);
        playerTextureRegionShare = BitmapTextureAtlasTextureRegionFactory.createFromAsset(MenuSceneTexture,activity,"share.png",832,216);
        playerTextureRegionMainChar = BitmapTextureAtlasTextureRegionFactory.createFromAsset(MenuSceneTexture,activity,"logo.png",915,25);
        playerTextureRegionTouchStart = BitmapTextureAtlasTextureRegionFactory.createFromAsset(MenuSceneTexture,activity,"touchtostart.png",800,0);
        FinishSceneTexture = new BitmapTextureAtlas(activity.getTextureManager(),256,256);
        playerTextureRegionFinishScene = BitmapTextureAtlasTextureRegionFactory.createFromAsset(FinishSceneTexture,activity,"sadgravi.png",0,0);
        playerTextureRegionContent = BitmapTextureAtlasTextureRegionFactory.createFromAsset(MenuSceneTexture,activity,"gravidotnobg.png",0,480);
        playerTextureRegionBg = BitmapTextureAtlasTextureRegionFactory.createFromAsset(MenuSceneTexture,activity,"homescreenamall.png",0,0);*/
        //playerTextureRegionHomeScreen = BitmapTextureAtlasTextureRegionFactory.createFromAsset(MenuSceneTexture,activity,"homescreenamall.png",0,0);
        playerTextureRegionGameBg = BitmapTextureAtlasTextureRegionFactory.createFromAsset(MenuSceneTexture,activity,"skybg.png",0,0);
        playerTextureRegionGameStars = BitmapTextureAtlasTextureRegionFactory.createFromAsset(MenuSceneTexture,activity,"stars.png",800,0);
        playerTextureRegionGameOverOverlay = BitmapTextureAtlasTextureRegionFactory.createFromAsset(MenuSceneTexture,activity,"blackoverlay.png",980,480);
        playerTextureRegionRepel = BitmapTextureAtlasTextureRegionFactory.createFromAsset(MenuSceneTexture,activity,"repelmode.png",0,480);
        playerTextureRegionAttract = BitmapTextureAtlasTextureRegionFactory.createFromAsset(MenuSceneTexture,activity,"attractmode.png",400,480);
        playerTextureRegionRetry = BitmapTextureAtlasTextureRegionFactory.createFromAsset(MenuSceneTexture,activity,"replay.png",540,480);
        playerTextureRegionHome = BitmapTextureAtlasTextureRegionFactory.createFromAsset(MenuSceneTexture,activity,"home.png",680,480);
        playerTextureRegionRate = BitmapTextureAtlasTextureRegionFactory.createFromAsset(MenuSceneTexture,activity,"rate.png",740,480);
        playerTextureRegionShare = BitmapTextureAtlasTextureRegionFactory.createFromAsset(MenuSceneTexture,activity,"share.png",800,480);
        playerTextureRegionAchievement = BitmapTextureAtlasTextureRegionFactory.createFromAsset(MenuSceneTexture,activity,"achievements.png",860,480);
        playerTextureRegionLeaderboard = BitmapTextureAtlasTextureRegionFactory.createFromAsset(MenuSceneTexture,activity,"leaderboard.png",920,480);
        playerTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(MenuSceneTexture,activity,"charnew.png",400,620);
        playerTextureRegionMainChar = BitmapTextureAtlasTextureRegionFactory.createFromAsset(MenuSceneTexture,activity,"logo.png",464,620);
        activity.getEngine().getTextureManager().loadTexture(MenuSceneTexture);
        //activity.getEngine().getTextureManager().loadTexture(FinishSceneTexture);
        this.TextFontMedium = FontFactory.createFromAsset(activity.getFontManager(), this.activity.getTextureManager(), 256, 256, this.activity.getAssets(), "fnts/manteka.ttf", 40, true, android.graphics.Color.rgb(127,140,140));
        this.TextFontMedium.load();
        this.TextFontMediumWhite = FontFactory.createFromAsset(activity.getFontManager(), this.activity.getTextureManager(), 256, 256, this.activity.getAssets(), "fnts/manteka.ttf", 32, true, android.graphics.Color.rgb(255,255,255));
        this.TextFontMediumWhite.load();
        //this.TextFontSmall = FontFactory.createFromAsset(activity.getFontManager(), this.activity.getTextureManager(), 256, 256, this.activity.getAssets(), "fnts/manteka.ttf", , true, android.graphics.Color.rgb(127,140,140));
        //this.TextFontSmall.load();
        this.TextFontLarge = FontFactory.createFromAsset(activity.getFontManager(), this.activity.getTextureManager(), 256, 256, this.activity.getAssets(), "fnts/manteka.ttf", 72, true, android.graphics.Color.rgb(127,140,140));
        this.TextFontLarge.load();
        this.TextFontfinishlarge = FontFactory.createFromAsset(activity.getFontManager(), this.activity.getTextureManager(), 256, 256, this.activity.getAssets(), "fnts/manteka.ttf", 45, true, android.graphics.Color.rgb(127,140,140));
        this.TextFontfinishlarge.load();
        this.TextFontfinishsmall = FontFactory.createFromAsset(activity.getFontManager(), this.activity.getTextureManager(), 256, 256, this.activity.getAssets(), "fnts/manteka.ttf", 22, true, android.graphics.Color.rgb(127,140,140));
        this.TextFontfinishsmall.load();

    }
    public void loadMenuResources(){
        //createMenuScene();
    }
    public Scene createFinishScene(){
        finishscene = new Scene();
        SharedPreferences.Editor editor = activity.getSharedPreferences("GAME", 0).edit();
        editor.putString("Adview", "true");
        editor.commit();
        SharedPreferences.Editor editorAd = activity.getSharedPreferences("AD", 0).edit();
        editorAd.putString("InterstitialAd","true");
        editorAd.commit();
        finishscene.setBackground(new Background(Color.BLACK));
        Sprite StarBgFinishScene = new Sprite(CAMERA_WIDTH/2-playerTextureRegionGameBg.getWidth()/2-MainActivity.canvasSurface.getMarginHorizontal()/2,CAMERA_HEIGHT/2-playerTextureRegionGameBg.getHeight()/2,playerTextureRegionGameBg,engine.getVertexBufferObjectManager());
        finishscene.attachChild(StarBgFinishScene);
        finishscene.attachChild(new Sprite(CAMERA_WIDTH/2-playerTextureRegionGameStars.getWidth()/2-MainActivity.canvasSurface.getMarginHorizontal()/2,CAMERA_HEIGHT/2-playerTextureRegionGameStars.getHeight()/2,playerTextureRegionGameStars,engine.getVertexBufferObjectManager()));
        float BcenterX=400-MainActivity.canvasSurface.getMarginHorizontal()/2,BcenterY=230;

        /*Sprite SadGravi = new Sprite(0,0,playerTextureRegionFinishScene,engine.getVertexBufferObjectManager());
        SadGravi.registerEntityModifier(new RotationModifier(0.5f, -360, 0));
        SadGravi.registerEntityModifier(new MoveXModifier(0.5f, -playerTextureRegionFinishScene.getWidth() / 2, playerTextureRegionFinishScene.getWidth() / 2));
        //SadGravi.setScale(2f);
        SadGravi.setPosition(camera.getWidth()/2-SadGravi.getWidth()/2,SadGravi.getHeight()/2+64);
        finishscene.attachChild(SadGravi);*/
        Sprite blackoverlay = new Sprite(StarBgFinishScene.getX()+BcenterX-playerTextureRegionGameOverOverlay.getWidth()/2,StarBgFinishScene.getY()+BcenterY-playerTextureRegionGameOverOverlay.getHeight()/2,playerTextureRegionGameOverOverlay,engine.getVertexBufferObjectManager());
        finishscene.attachChild(blackoverlay);
        PlayOrRetryButton = new Sprite(StarBgFinishScene.getX()+BcenterX-playerTextureRegionRetry.getWidth()/2,StarBgFinishScene.getY()+BcenterY+125-playerTextureRegionRetry.getHeight()/2, playerTextureRegionRetry, engine.getVertexBufferObjectManager()){
            ScaleModifier shrink,blow;
            @Override
            public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                if(pSceneTouchEvent.isActionUp() || pSceneTouchEvent.isActionMove()) {

                    //gameScene.unregisterTouchArea(PlayOrRetryButton);
                    if(blow!=null)
                        this.unregisterEntityModifier(blow);
                    this.registerEntityModifier(shrink = new ScaleModifier(0.1f,1.4f,1){
                        @Override
                        protected void onModifierFinished(IEntity pItem)
                        {
                            super.onModifierFinished(pItem);
                            if(!pSceneTouchEvent.isActionMove()) {
                                createGameScene();
                                setCurrentScene(SceneManager.AllScenes.GAME);
                            }
                        }
                    });

                }
                if(pSceneTouchEvent.isActionDown()){
                    if(shrink!=null)
                        this.unregisterEntityModifier(shrink);
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
        };
        finishscene.registerTouchArea(PlayOrRetryButton);
        //PlayOrRetryButton.setRotation(45f);
        finishscene.attachChild(PlayOrRetryButton);

        Text gameover = new Text(StarBgFinishScene.getX()+BcenterX,StarBgFinishScene.getY()+BcenterY-125,this.TextFontfinishlarge,"GAME OVER",new TextOptions(HorizontalAlign.CENTER), this.engine.getVertexBufferObjectManager());
        gameover.setPosition(StarBgFinishScene.getX()+BcenterX-gameover.getWidth()/2,StarBgFinishScene.getY()+BcenterY-125-gameover.getHeight()/2);
        finishscene.attachChild(gameover);
        Text Score = new Text(StarBgFinishScene.getX()+BcenterX,StarBgFinishScene.getY()+BcenterY-125,this.TextFontfinishsmall,"SCORE ",new TextOptions(HorizontalAlign.CENTER), this.engine.getVertexBufferObjectManager());
        Score.setPosition(StarBgFinishScene.getX()+BcenterX-Score.getWidth()/2-135,StarBgFinishScene.getY()+BcenterY-35-Score.getHeight()/2);
        finishscene.attachChild(Score);
        Text highscore = new Text(StarBgFinishScene.getX()+BcenterX,StarBgFinishScene.getY()+BcenterY-125,this.TextFontfinishsmall,"HIGHEST ",new TextOptions(HorizontalAlign.CENTER), this.engine.getVertexBufferObjectManager());
        highscore.setPosition(StarBgFinishScene.getX()+BcenterX-highscore.getWidth()/2+70,StarBgFinishScene.getY()+BcenterY-35-highscore.getHeight()/2);
        finishscene.attachChild(highscore);

        PrevScore = new Text(Score.getX()+Score.getWidth(), Score.getY()+Score.getHeight()-45,this.TextFontfinishlarge, ""+score, new TextOptions(HorizontalAlign.CENTER), this.engine.getVertexBufferObjectManager());
        if(highscore.getX()-Score.getX()+Score.getWidth()<PrevScore.getWidth()){
            PrevScore.setScale((highscore.getX()-Score.getX()+Score.getWidth())/PrevScore.getWidth());
        }
        finishscene.attachChild(PrevScore);

        SharedPreferences sharedPref = activity.getSharedPreferences("GAME",0);
        int hs = Integer.parseInt(sharedPref.getString("HighScore", 0 + ""));
        int hs_repel = Integer.parseInt(sharedPref.getString("HighScoreRepel", 0 + ""));
        if(MODE==1){
            hs=hs_repel;
        }
        HighScore = new Text(highscore.getX()+highscore.getWidth(), highscore.getY()+highscore.getHeight()-45, this.TextFontfinishlarge, ""+hs, new TextOptions(HorizontalAlign.CENTER), this.engine.getVertexBufferObjectManager());
        if(blackoverlay.getX()+blackoverlay.getWidth()-highscore.getX()-highscore.getWidth()<HighScore.getWidth()){
            HighScore.setScale((blackoverlay.getX()+blackoverlay.getWidth()-highscore.getX()-highscore.getWidth())/HighScore.getWidth());
        }
        //highspeed = new Text(CAMERA_WIDTH/10, CAMERA_HEIGHT/2+80, this.TextFontMedium, hv+" m/s", new TextOptions(HorizontalAlign.CENTER), this.engine.getVertexBufferObjectManager());
        Text topspeed = new Text(0,0,this.TextFontfinishsmall,"TOP SPEED ", new TextOptions(HorizontalAlign.CENTER), this.engine.getVertexBufferObjectManager());
        topspeed.setPosition(StarBgFinishScene.getX()+BcenterX-topspeed.getWidth()/2-85,StarBgFinishScene.getY()+BcenterY+30-topspeed.getHeight()/2);
        finishscene.attachChild(topspeed);
        prevSpeed = new Text(topspeed.getX()+topspeed.getWidth(), topspeed.getY()+topspeed.getHeight()-45,this.TextFontfinishlarge, current_speed+" KMPH", new TextOptions(HorizontalAlign.CENTER), this.engine.getVertexBufferObjectManager());
        finishscene.attachChild(HighScore);
        //finishscene.attachChild(highspeed);
        finishscene.attachChild(prevSpeed);
        OptionButton home = new OptionButton(StarBgFinishScene.getX()+BcenterX-190-playerTextureRegionHome.getWidth()/2,StarBgFinishScene.getY()+BcenterY+135-playerTextureRegionHome.getHeight()/2,playerTextureRegionHome,activity.getVertexBufferObjectManager(),4,activity){
            ScaleModifier shrink,blow;
            @Override
            public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                if(pSceneTouchEvent.isActionUp() || pSceneTouchEvent.isActionMove()) {

                    //gameScene.unregisterTouchArea(PlayOrRetryButton);
                    if(blow!=null)
                        this.unregisterEntityModifier(blow);
                    this.registerEntityModifier(shrink = new ScaleModifier(0.1f,1.4f,1){
                        @Override
                        protected void onModifierFinished(IEntity pItem)
                        {
                            super.onModifierFinished(pItem);
                            if(!pSceneTouchEvent.isActionMove()) {
                                createMenuScene();
                                setCurrentScene(AllScenes.MENU);
                            }
                        }
                    });

                }
                if(pSceneTouchEvent.isActionDown()){
                        if(shrink!=null)
                            this.unregisterEntityModifier(shrink);
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


        };
        OptionButton share = new OptionButton(StarBgFinishScene.getX()+BcenterX-115-playerTextureRegionRate.getWidth()/2,StarBgFinishScene.getY()+BcenterY+135-playerTextureRegionRate.getHeight()/2,playerTextureRegionShare,activity.getVertexBufferObjectManager(),2,activity);
        OptionButton achievement = new OptionButton(StarBgFinishScene.getX()+BcenterX+115-playerTextureRegionAchievement.getWidth()/2,StarBgFinishScene.getY()+BcenterY+135-playerTextureRegionAchievement.getHeight()/2,playerTextureRegionAchievement,activity.getVertexBufferObjectManager(),1,activity);
        OptionButton leaderboard = new OptionButton(StarBgFinishScene.getX()+BcenterX+190-playerTextureRegionLeaderboard.getWidth()/2,StarBgFinishScene.getY()+BcenterY+135-playerTextureRegionLeaderboard.getHeight()/2,playerTextureRegionLeaderboard,activity.getVertexBufferObjectManager(),0,activity);

        finishscene.attachChild(home);
        finishscene.attachChild(share);
        finishscene.registerTouchArea(home);
        finishscene.registerTouchArea(share);
        finishscene.attachChild(achievement);
        finishscene.attachChild(leaderboard);
        finishscene.registerTouchArea(achievement);
        finishscene.registerTouchArea(leaderboard);


        return finishscene;
    }
    public Scene createSplashScene(){
        SharedPreferences.Editor editor = activity.getSharedPreferences("GAME", 0).edit();
        editor.putString("Adview", "true");
        editor.commit();
        SharedPreferences.Editor editorAd = activity.getSharedPreferences("AD", 0).edit();
        editorAd.putString("InterstitialAd","true");
        editorAd.commit();
        splashScene = new Scene();
        splashScene.setBackground(new Background(((float)68)/255,((float)110)/255,((float)141)/255));
        Sprite SplashImage = new Sprite(CAMERA_WIDTH/2-splashTR.getWidth()/2-MainActivity.canvasSurface.getMarginHorizontal(),0,splashTR,engine.getVertexBufferObjectManager());
        SplashImage.setPosition(camera.getWidth()/2-SplashImage.getWidth()/2-MainActivity.canvasSurface.getMarginHorizontal(),camera.getHeight()/2-SplashImage.getHeight()/2);
        splashScene.attachChild(SplashImage);
        return splashScene;
    }
    public Scene createMenuScene(){
        SharedPreferences.Editor editor = activity.getSharedPreferences("GAME", 0).edit();
        editor.putString("Adview", "true");
        editor.commit();
        SharedPreferences.Editor editorAd = activity.getSharedPreferences("AD", 0).edit();
        editorAd.putString("InterstitialAd","true");
        editorAd.commit();
        menuScene = new Scene();
        menuScene.setBackground(new Background(((float)44)/255,((float)62)/255,((float)80)/255));
        Sprite bg = new Sprite(CAMERA_WIDTH/2-playerTextureRegionGameBg.getWidth()/2-MainActivity.canvasSurface.getMarginHorizontal()/2,CAMERA_HEIGHT/2-playerTextureRegionGameBg.getHeight()/2,playerTextureRegionGameBg,engine.getVertexBufferObjectManager());
        menuScene.attachChild(bg);
        Sprite stars = new Sprite(CAMERA_WIDTH/2-playerTextureRegionGameStars.getWidth()/2-MainActivity.canvasSurface.getMarginHorizontal()/2,CAMERA_HEIGHT/2-playerTextureRegionGameStars.getHeight()/2,playerTextureRegionGameStars,engine.getVertexBufferObjectManager());
        menuScene.attachChild(stars);
        //Sprite icon = new Sprite(0,0,playerTextureRegionContent,engine.getVertexBufferObjectManager());
        //icon.setScale(2f);
        //icon.setPosition(camera.getWidth()/2-icon.getWidth()/2,icon.getHeight()/2+64);
        //menuScene.attachChild(icon);
        int OFFSET_FOR_MODES=80;
        PlayOrRetryButton = new Sprite(stars.getX()+400-OFFSET_FOR_MODES- playerTextureRegionAttract.getWidth()/2-MainActivity.canvasSurface.getMarginHorizontal()/2,stars.getY()+245- playerTextureRegionAttract.getHeight()/2, playerTextureRegionAttract, engine.getVertexBufferObjectManager()){
            ScaleModifier shrink,blow;
            @Override
            public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                if(pSceneTouchEvent.isActionUp() || pSceneTouchEvent.isActionMove()) {

                    //gameScene.unregisterTouchArea(PlayOrRetryButton);
                    if(blow!=null)
                        this.unregisterEntityModifier(blow);
                    this.registerEntityModifier(shrink = new ScaleModifier(0.1f,1.4f,1){
                        @Override
                        protected void onModifierFinished(IEntity pItem)
                        {
                            super.onModifierFinished(pItem);
                            if(!pSceneTouchEvent.isActionMove()) {
                                MODE=0;
                                createGameScene();
                                setCurrentScene(SceneManager.AllScenes.GAME);
                            }
                        }
                    });

                }
                else if(pSceneTouchEvent.isActionDown()){
                    if(shrink!=null)
                        this.unregisterEntityModifier(shrink);
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
        };
        RepelModeButton = new Sprite(stars.getX()+400+OFFSET_FOR_MODES- playerTextureRegionRepel.getWidth()/2-MainActivity.canvasSurface.getMarginHorizontal()/2,stars.getY()+245- playerTextureRegionRepel.getHeight()/2, playerTextureRegionRepel, engine.getVertexBufferObjectManager()){
            ScaleModifier shrink,blow;
            @Override
            public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                if(pSceneTouchEvent.isActionUp() || pSceneTouchEvent.isActionMove()) {

                    //gameScene.unregisterTouchArea(PlayOrRetryButton);
                    if(blow!=null)
                        this.unregisterEntityModifier(blow);
                    this.registerEntityModifier(shrink = new ScaleModifier(0.1f,1.4f,1){
                        @Override
                        protected void onModifierFinished(IEntity pItem)
                        {
                            super.onModifierFinished(pItem);
                            if(!pSceneTouchEvent.isActionMove()) {
                                MODE=1;
                                createGameScene();
                                setCurrentScene(SceneManager.AllScenes.GAME);
                            }
                        }
                    });

                }
                else if(pSceneTouchEvent.isActionDown()){
                    if(shrink!=null)
                        this.unregisterEntityModifier(shrink);
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
        };
        menuScene.registerTouchArea(PlayOrRetryButton);
        menuScene.attachChild(PlayOrRetryButton);
        menuScene.registerTouchArea(RepelModeButton);
        menuScene.attachChild(RepelModeButton);
        /*HighScore = new Text(CAMERA_WIDTH/20, CAMERA_HEIGHT/3, this.TextFontMedium, "BEST SCORE : "+hs, new TextOptions(HorizontalAlign.CENTER), this.engine.getVertexBufferObjectManager());
        highspeed = new Text(CAMERA_WIDTH/20, CAMERA_HEIGHT/3+40, this.TextFontMedium, "BEST SPEED : "+hv+" m/s", new TextOptions(HorizontalAlign.CENTER), this.engine.getVertexBufferObjectManager());
        menuScene.attachChild(HighScore);
        menuScene.attachChild(highspeed);*/
        Text gravidot = new Text(0, 0, this.TextFontLarge, "GRAVIDOT", new TextOptions(HorizontalAlign.CENTER), this.engine.getVertexBufferObjectManager());

        OptionButton rate = new OptionButton(stars.getX()+520-playerTextureRegionRate.getWidth()/2-MainActivity.canvasSurface.getMarginHorizontal()/2,stars.getY()+380-playerTextureRegionRate.getHeight()/2,playerTextureRegionRate,activity.getVertexBufferObjectManager(),3,activity);
        OptionButton share = new OptionButton(stars.getX()+280-playerTextureRegionRate.getWidth()/2-MainActivity.canvasSurface.getMarginHorizontal()/2,stars.getY()+380-playerTextureRegionRate.getHeight()/2,playerTextureRegionShare,activity.getVertexBufferObjectManager(),2,activity);
        OptionButton achievement = new OptionButton(stars.getX()+360-playerTextureRegionAchievement.getWidth()/2-MainActivity.canvasSurface.getMarginHorizontal()/2,stars.getY()+380-playerTextureRegionAchievement.getHeight()/2,playerTextureRegionAchievement,activity.getVertexBufferObjectManager(),1,activity);
        OptionButton leaderboard = new OptionButton(stars.getX()+440-playerTextureRegionLeaderboard.getWidth()/2-MainActivity.canvasSurface.getMarginHorizontal()/2,stars.getY()+380-playerTextureRegionLeaderboard.getHeight()/2,playerTextureRegionLeaderboard,activity.getVertexBufferObjectManager(),0,activity);

        //rate.setScale(2f);
        //share.setScale(2f);
        gravidot.setPosition(stars.getX()+400-gravidot.getWidth()/2-MainActivity.canvasSurface.getMarginHorizontal()/2,stars.getY()+100-gravidot.getHeight()/2);
        menuScene.attachChild(gravidot);
        menuScene.attachChild(rate);
        menuScene.attachChild(share);
        menuScene.registerTouchArea(rate);
        menuScene.registerTouchArea(share);
        menuScene.attachChild(achievement);
        menuScene.attachChild(leaderboard);
        menuScene.registerTouchArea(achievement);
        menuScene.registerTouchArea(leaderboard);

        return menuScene;
    }
    public Scene createGameScene(){
        gameScene = new Scene();
        SharedPreferences.Editor editor = activity.getSharedPreferences("GAME", 0).edit();
        editor.putString("Adview", "false");

        editor.commit();
        SharedPreferences.Editor editorAd = activity.getSharedPreferences("AD", 0).edit();
        editorAd.putString("InterstitialAd", "false");
        editorAd.commit();
        /*ParallaxLayer backgroundParallax = new ParallaxLayer(camera, true);*/
        Sprite galaxySprite = new Sprite(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT, playerTextureRegionGameBg, engine.getVertexBufferObjectManager());
        Sprite starsSprite = new Sprite(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT, playerTextureRegionGameStars, engine.getVertexBufferObjectManager());
/*
        backgroundParallax.attachParallaxEntity(new ParallaxLayer.ParallaxEntity(1, starsSprite, true));
        //backgroundParallax.attachParallaxEntity(new ParallaxLayer.ParallaxEntity(1, galaxySprite, true));
        //gameScene.setBackground(new Background(((float)44)/255,((float)62)/255,((float)80)/255));
        gameScene.attachChild(backgroundParallax);*/

        ParallaxBackground background = new ParallaxBackground(0.3f, 0.3f, 0.9f) {

            /*
             * We'll use these values to calculate the parallax value of the
             * background
             */
            float cameraPreviousX = 0;
            float parallaxValueOffset = 0;

            /*
             * onUpdates to the background, we need to calculate new parallax
             * values in order to apply movement to the background objects (the
             * hills in this case)
             */
            @Override
            public void onUpdate(float pSecondsElapsed) {

				/* Obtain the camera's current center X value */
                final float cameraCurrentX = camera.getCenterX();

				/*
				 * If the camera's position has changed since last update...
				 */
                if (cameraPreviousX != cameraCurrentX) {

					/*
					 * Calculate the new parallax value offset by subtracting
					 * the previous update's camera x coordinate from the
					 * current update's camera x coordinate
					 */
                    parallaxValueOffset += cameraCurrentX - cameraPreviousX;

					/*
					 * Apply the parallax value offset to the background, which
					 * will in-turn offset the positions of entities attached to
					 * the background
					 */
                    this.setParallaxValue(parallaxValueOffset);

					/*
					 * Update the previous camera X since we're finished with
					 * this update
					 */
                    cameraPreviousX = cameraCurrentX;
                }

                super.onUpdate(pSecondsElapsed);
            }
        };
        background.attachParallaxEntity(new ParallaxBackground.ParallaxEntity(-0.5f,galaxySprite ));
        background.attachParallaxEntity(new ParallaxBackground.ParallaxEntity(-0.2f, starsSprite));
       // background.attachParallaxEntity(new ParallaxBackground.ParallaxEntity(15, hillClosest));

		/* Set & Enabled the background */
        gameScene.setBackground(background);
        gameScene.setBackgroundEnabled(true);
        greenarea = new Rectangle(CAMERA_WIDTH,0,10,CAMERA_HEIGHT,engine.getVertexBufferObjectManager());
        physicsWorld = new PhysicsWorld(new Vector2(0, 0),false);
        gameScene.registerUpdateHandler(physicsWorld);
        engine.registerUpdateHandler(new FPSLogger());
        //createWalls();
        crateRect();
        PauseTimerText = new Text(100, 40, this.TextFontMedium, "", new TextOptions(HorizontalAlign.CENTER), this.engine.getVertexBufferObjectManager());
        PauseTimerText.setColor(Color.WHITE);
        SCORE = new Text(100, 40, this.TextFontMediumWhite, "99999999", new TextOptions(HorizontalAlign.CENTER), this.engine.getVertexBufferObjectManager());
        SCORE.setText(""+0);
        SCORE.setPosition(CAMERA_WIDTH/2-SCORE.getWidth()/2-MainActivity.canvasSurface.getMarginHorizontal(), 40);

        //SCORE = new Text(CAMERA_WIDTH/2-SCORE.getWidth()/2, 40, this.TextFontMedium, "999999999", new TextOptions(HorizontalAlign.CENTER), this.engine.getVertexBufferObjectManager());

        //SCORE.setColor(new Color(((float)127)/255,((float)140)/255,((float)140)/255));
        gameScene.attachChild(SCORE);
        speed = new Text(0, 0, this.TextFontMediumWhite, "999999999 KMPH", new TextOptions(HorizontalAlign.CENTER), this.engine.getVertexBufferObjectManager());
        speed.setScale(0.75f);
        //speed.setPosition(CAMERA_WIDTH/2, 40);
        //SCORE = new Text(CAMERA_WIDTH/2-SCORE.getWidth()/2, 40, this.TextFontMedium, "999999999", new TextOptions(HorizontalAlign.CENTER), this.engine.getVertexBufferObjectManager());
        speed.setText("0 KMPH");
        //speed.setColor(new Color(((float)127)/255,((float)140)/255,((float)140)/255));
        gameScene.attachChild(speed);
        //gameScene.attachChild(PauseTimerText);
        populateScene();
        return gameScene;
    }

    private void populateScene() {
        float x=INI_POS_X;
        float y =INI_POS_Y;
        //scene.detachChild(Gravi);
        Gravi = new Sprite(x,y,playerTextureRegionMainChar,engine.getVertexBufferObjectManager());
        Gravi.setRotation(45.0f);
        final FixtureDef FIX1 = PhysicsFactory.createFixtureDef(10.0f, 0.5f, 0.0f);
        body = PhysicsFactory.createCircleBody(physicsWorld, Gravi, BodyDef.BodyType.DynamicBody,FIX1);
        gameScene.attachChild(Gravi);
        body.setLinearVelocity(0,0);
        physicsWorld.registerPhysicsConnector(new PhysicsConnector(Gravi,body,true,false){
            @Override
            public void onUpdate(float pSecondsElapsed) {
                super.onUpdate(pSecondsElapsed);
                if(BlueDot !=null && Start!=0) {
                    double r = Math.sqrt(Math.pow((Gravi.getX() - BlueDot.getX()), 2) + Math.pow((Gravi.getY() - BlueDot.getY()), 2));
                    float Fx = r > 0 ? ((float) ((Gravi.getX() - BlueDot.getX() ) * 1000 / (r * r))) : 0;
                    float Fy = r > 0 ? ((float) ((Gravi.getY() - BlueDot.getY()) * 1000 / (r * r))) : 0;
                    if(MODE==0){
                        Fx=-Fx;
                        Fy=-Fy;
                    }
                    body.applyForce(10 * Fx, 10 * Fy, Gravi.getX() + 16, Gravi.getY() + 16);
                    if (body.getLinearVelocity().x >= 0) {
                        camera.setCenter(Gravi.getX() - INI_POS_X + CAMERA_WIDTH / 2 , CAMERA_HEIGHT / 2);
                        ground.setPosition(Gravi.getX() - INI_POS_X, 0);
                        SCORE.setPosition(Gravi.getX() - INI_POS_X +CAMERA_WIDTH/2-SCORE.getWidth()/2-MainActivity.canvasSurface.getMarginHorizontal(), 40);
                        if(score!=(ground.getX()/800)){
                            score=(int)ground.getX()/800;
                            SCORE.setText(""+score);
                        }
                        speed.setPosition(ground.getX(),ground.getY());
                        engine.runOnUpdateThread(new Runnable() {
                            @Override
                            public void run() {
                                int v = (int)body.getLinearVelocity().len();
                                speed.setText(v+" KMPH");
                                if(current_speed<v)
                                    current_speed = v;
                                if(min_speed>v){
                                    min_speed = v;
                                }
                            }
                        });

                    }
                    else {
                        INI_POS_X= Gravi.getX()-ground.getX();
                        //INI_POS_Y=Gravi.getY();
                    }
                }
                if(Gravi.getX()<ground.getX() || (Gravi.getY()<-Gravi.getHeight() || Gravi.getY()>CAMERA_HEIGHT)){
                    engine.stop();
                    /*Vibrator v = (Vibrator) activity.getSystemService(Context.VIBRATOR_SERVICE);

                    v.vibrate(500);*/
                    restart();
                }
            }
        });
    }

    private void crateRect() {
        /*Pause = new Sprite(CAMERA_WIDTH-64,0,playerTextureRegionPause,engine.getVertexBufferObjectManager());
        Pause.setScale(0.7f);
        gameScene.attachChild(Pause);
        pausebutton = new Rectangle(CAMERA_WIDTH-64,0,64,64,this.engine.getVertexBufferObjectManager()){
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                Log.d("Touch", "Touch");
                if(Start==1) {
                    final CameraScene mPauseScene = new CameraScene(camera);
                    final int x = (int) (camera.getWidth() / 2 - playerTextureRegionPause
                            .getWidth() / 2);
                    final int y = (int) (camera.getHeight() / 2 - playerTextureRegionPause
                            .getHeight() / 2);
                    final Sprite pausedSprite = new Sprite(x, y, playerTextureRegionAttract, getVertexBufferObjectManager()) {
                        @Override
                        public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                            if (pSceneTouchEvent.isActionDown()) {
                                Log.d("resume", "touch");
                                mPauseScene.unregisterTouchArea(this);
                                PauseTimerText.setPosition(Gravi.getX() - PauseTimerText.getWidth() / 2, CAMERA_HEIGHT / 2 - PauseTimerText.getHeight() / 2);
                                mPauseScene.detachChild(this);
                                count();


                                //mEngine.start();
                            }
                            return true;
                        }

                    };
                    mPauseScene.attachChild(pausedSprite);
                    mPauseScene.registerTouchArea(pausedSprite);
                    mPauseScene.setBackground(new Background(0, 15, 50, 0));
                    pausedSprite.setIgnoreUpdate(true);
                    mPauseScene.setBackgroundEnabled(false);
                    if (pSceneTouchEvent.isActionDown() && engine.isRunning()) {
                        //scene.setIgnoreUpdate(true);
                        gameScene.setChildScene(mPauseScene, false, true, true);
                        gameScene.setIgnoreUpdate(true);
                        //mEngine.stop();
                        // pausedSprite.setIgnoreUpdate(false);


                    }
                }
                //else mEngine.start();
                return true;
            }


        };
        pausebutton.setColor(new Color( 0, 0, 0, 0));
        gameScene.attachChild(pausebutton);
        gameScene.registerTouchArea(pausebutton);*/

        String INSTRUCTIONS = "TOUCH TO PLACE A GREEN DOT\nTO ATTRACT THE RED DOT\n\n\n\n\n\nNEITHER ALLOW THE RED DOT TO ESCAPE\nNOR GET CRASHED INTO THE GREEN DOT!";
        if(MODE==1){
            INSTRUCTIONS="TOUCH TO PLACE A GREEN DOT\nTO REPEL THE RED DOT\n\n\n\n\n\nNEITHER ALLOW THE RED DOT TO ESCAPE\nNOR GET CRASHED INTO THE GREEN DOT!";
        }
        Text touchtostart_sub = HighScore = new Text(0,0, this.TextFontfinishsmall, INSTRUCTIONS, new TextOptions(HorizontalAlign.CENTER), this.engine.getVertexBufferObjectManager());
        final Text touchtostart=new Text(CAMERA_WIDTH/2-touchtostart_sub.getWidth()/2-MainActivity.canvasSurface.getMarginHorizontal()/2,CAMERA_HEIGHT/2-touchtostart_sub.getHeight()/2, this.TextFontfinishsmall,INSTRUCTIONS, new TextOptions(HorizontalAlign.CENTER), this.engine.getVertexBufferObjectManager());
        gameScene.attachChild(touchtostart);
        ground = new Rectangle(0,0,CAMERA_WIDTH,CAMERA_HEIGHT,this.engine.getVertexBufferObjectManager()){
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                {if(Start==0){
                    body.setLinearVelocity(10,0);
                    gameScene.detachChild(touchtostart);
                    Start=1;
                }
                else if(pSceneTouchEvent.isActionUp()) {
                    double r = Math.sqrt(Math.pow((pSceneTouchEvent.getX() - TouchX), 2) + Math.pow((pSceneTouchEvent.getY() - TouchY), 2));
                    if (PAUSE_FLAG == 0) {
                        if (r > 2 * RADIUS) {
                            BlueDot = new Sprite(pSceneTouchEvent.getX()-32, pSceneTouchEvent.getY()-32, playerTextureRegion, getVertexBufferObjectManager());
                            BlueDot.setRotation(45.0f);
                            BlueDot.registerEntityModifier(new ScaleModifier(0.1f, 0f, 1f));
                            TouchX = pSceneTouchEvent.getX();
                            TouchY = pSceneTouchEvent.getY();
                            final Sprite sprite= BlueDot;
                            gameScene.attachChild(sprite);
                            final Sprite spritesub=new Sprite(pSceneTouchEvent.getX()+CAMERA_WIDTH, pSceneTouchEvent.getY(), playerTextureRegion, getVertexBufferObjectManager());
                            spritesub.setRotation(45.0f);
                            gameScene.attachChild(spritesub);
                            spritesub.registerUpdateHandler(new IUpdateHandler() {
                                @Override
                                public void onUpdate(float pSecondsElapsed) {
                                   if(spritesub.getX()+spritesub.getWidth()<=ground.getX()){
                                       spritesub.unregisterUpdateHandler(this);
                                       engine.runOnUpdateThread(new Runnable() {
                                           @Override
                                           public void run() {
                                               gameScene.detachChild(spritesub);
                                               spritesub.dispose();
                                               passedDots++;
                                           }
                                       });
                                   }
                                    /*spritesub.setScaleCenter(0,0);
                                   if(1-pSecondsElapsed/2>0)
                                    spritesub.setScale(1-pSecondsElapsed/2);
                                   else
                                       spritesub.setPosition(-20,-20);*/
                                    double r = Math.sqrt(Math.pow((Gravi.getX() - spritesub.getX() - 16), 2) + Math.pow((Gravi.getY() - spritesub.getY() - 16), 2));
                                    if (spritesub.collidesWith(Gravi) && r <= 48) {
                                        crashedSpeed=(int)body.getLinearVelocity().len();
                                        engine.stop();
                                        /*Vibrator v = (Vibrator) activity.getSystemService(Context.VIBRATOR_SERVICE);
                                        // Vibrate for 500 milliseconds
                                        v.vibrate(500);*/
                                        restart();
                                    }
                                }

                                @Override
                                public void reset() {

                                }
                            });
                            sprite.registerUpdateHandler(new IUpdateHandler() {
                                @Override
                                public void onUpdate(float pSecondsElapsed) {
                                    if(sprite.getX()+sprite.getWidth()<=ground.getX()){
                                        sprite.unregisterUpdateHandler(this);
                                        engine.runOnUpdateThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                gameScene.detachChild(sprite);
                                                sprite.dispose();
                                                passedDots++;
                                            }
                                        });
                                    }
                                    /*if(1-pSecondsElapsed/2>0)
                                        sprite.setScale(1-pSecondsElapsed/2);
                                    else
                                        sprite.setPosition(-20,-20);*/
                                    double r = Math.sqrt(Math.pow((Gravi.getX() - sprite.getX() - 16), 2) + Math.pow((Gravi.getY() - sprite.getY() - 16), 2));
                                    if (sprite.collidesWith(Gravi) && r <= 48) {
                                        crashedSpeed=(int)body.getLinearVelocity().len();
                                        Achievements a = new Achievements(MainActivity.mGoogleApiClient,activity);
                                        a.unlockCrashed();
                                        a.commitItem();
                                        engine.stop();
                                        /*Vibrator v = (Vibrator) activity.getSystemService(Context.VIBRATOR_SERVICE);
                                        // Vibrate for 500 milliseconds
                                        v.vibrate(500);*/
                                        restart();
                                    }
                                }

                                @Override
                                public void reset() {

                                }
                            });
                            //physicsWorld.registerPhysicsConnector(new PhysicsConnector(BlueDot, body1, true, true));
                        }
                    } else PAUSE_FLAG = 0;
                }}
                return true;
            }
        };
        ground.setColor(new Color(0,0,0,0));
        gameScene.attachChild(ground);
        gameScene.registerTouchArea(ground);
        //scene.attachChild(pause);
        //scene.registerTouchArea(pause);

        gameScene.setTouchAreaBindingOnActionMoveEnabled(false);
    }
    /*public void count(){
        final Handler handler = new Handler(Looper.getMainLooper());
        handler.post(
                new Runnable()
                {
                    @Override
                    public void run()
                    {
                        new CountDownTimer(4000, 1000) {
                            int i=3;
                            public void onTick(long millisUntilFinished) {
                                //PauseTimerText.setText(""+(millisUntilFinished/1000));

                                gameScene.detachChild(PauseTimerText);
                                PauseTimerText = new Text(Gravi.getX()-INI_POS_X+CAMERA_WIDTH/2- PauseTimerText.getWidth()/2, CAMERA_HEIGHT/2- PauseTimerText.getHeight()/2, TextFontMedium, (String.valueOf(i)), new TextOptions(HorizontalAlign.CENTER), engine.getVertexBufferObjectManager());
                                PauseTimerText.setColor(Color.WHITE);
                                i--;
                                gameScene.attachChild(PauseTimerText);
                            }
                            @Override
                            public void onFinish() {
                                engine.runOnUpdateThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        gameScene.detachChild(PauseTimerText);
                                    }
                                });
                                gameScene.clearChildScene();
                                gameScene.setIgnoreUpdate(false);
                                PAUSE_FLAG = 1;
                            }
                        }.start();
                    }
                }
        );
    }*/
    public void restart(){
        resetPhysics();
        SharedPreferences sharedPref = activity.getSharedPreferences("GAME",0);
        int hs = Integer.parseInt(sharedPref.getString("HighScore", 0 + ""));
        int hv = Integer.parseInt(sharedPref.getString("HighSpeed", 0 + ""));
        int hs_repel = Integer.parseInt(sharedPref.getString("HighScoreRepel", 0 + ""));
        int hv_repel = Integer.parseInt(sharedPref.getString("HighSpeedRepel", 0 + ""));
        int no_of_games = Integer.parseInt(sharedPref.getString("GamesPlayed",0+""));
        no_of_games++;
        SharedPreferences example = activity.getSharedPreferences("GAME", 0);
        SharedPreferences.Editor editor = example.edit();
        AesEncrDec aesEncrDec = new AesEncrDec();
        if(hs<score && MODE==0){
            hs=score;
                Achievements a = new Achievements(MainActivity.mGoogleApiClient,activity);
                a.unlockScore(hs);
                Leaderboards l = new Leaderboards(MainActivity.mGoogleApiClient,activity);
                l.postScore(hs);
                a.commitItem();
            l.commitItem();
        }
        if(hv<current_speed && MODE==0){
            hv=current_speed;
            Achievements a = new Achievements(MainActivity.mGoogleApiClient,activity);
            a.unlockSpeed(hv);
            Leaderboards l = new Leaderboards(MainActivity.mGoogleApiClient,activity);
            l.postSpeed(hv);
            a.commitItem();
            l.commitItem();
        }
        if(hs_repel<score && MODE==1){
            hs_repel=score;
            Achievements a = new Achievements(MainActivity.mGoogleApiClient,activity);
            a.unlockScore(hs_repel);
            Leaderboards l = new Leaderboards(MainActivity.mGoogleApiClient,activity);
            l.postScoreRepel(hs_repel);
            a.commitItem();
            l.commitItem();
        }
        if(hv_repel<current_speed && MODE==1){
            hv_repel=current_speed;
            Achievements a = new Achievements(MainActivity.mGoogleApiClient,activity);
            a.unlockSpeed(hv_repel);
            Leaderboards l = new Leaderboards(MainActivity.mGoogleApiClient,activity);
            l.postSpeedRepel(hv_repel);
            a.commitItem();
            l.commitItem();
        }
        Achievements a = new Achievements(MainActivity.mGoogleApiClient,activity);
        if(score==200)
            a.unlockScore(score);
        a.unlockGamesPlayed(no_of_games);
        a.unlockPassedDot(passedDots);
        if(crashedSpeed!=-1){
            a.unlockCrashedSpeed(crashedSpeed);
        }
        a.unlockMinSpeed(min_speed);
        a.commitItem();
        editor.putString("HighScore",hs + "");
        editor.putString("HighSpeed", hv + "");
        editor.putString("HighScoreRepel",hs_repel + "");
        editor.putString("HighSpeedRepel", hv_repel + "");
        editor.putString("GamesPlayed",no_of_games+"");
        editor.commit();
        engine.start();
        body.setLinearVelocity(0, 0);
        Start=0;
        INI_POS_X = (CAMERA_WIDTH-MainActivity.canvasSurface.getMarginHorizontal())/5;
        INI_POS_Y = CAMERA_HEIGHT/2;
        createFinishScene();
        passedDots=0;
        crashedSpeed=-1;
        score=0;
        current_speed=0;
        min_speed=0;
        camera.setCenter(CAMERA_WIDTH/2,CAMERA_HEIGHT/2);
        setCurrentScene(AllScenes.FINISH);

    }



    public void resetPhysics() {
        physicsWorld.getPhysicsConnectorManager().clear();

        Iterator<Joint> joints = physicsWorld.getJoints();
        while (joints.hasNext()) {
            Joint j = joints.next();
            if (j != null) physicsWorld.destroyJoint(j);
        }

        Iterator<Body> bodies = physicsWorld.getBodies();
        while (bodies.hasNext()) {
            Body b = bodies.next();
            if (b != null) physicsWorld.destroyBody(b);
        }
    }
}
