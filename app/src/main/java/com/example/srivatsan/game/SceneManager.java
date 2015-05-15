package com.example.srivatsan.game;

import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Joint;
import com.google.android.gms.ads.InterstitialAd;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.RotationModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.CameraScene;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
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
    Body body;
    Text SCORE,HighScore,PrevScore,speed,prevSpeed,highspeed;
    int current_speed=0;
    private InterstitialAd interstitial;
    int score=0;
    int PAUSE_FLAG=0;
    Sprite PlayOrRetryButton;
    protected static final int CAMERA_WIDTH = 800;
    protected static final int CAMERA_HEIGHT = 480;
    protected static float INI_POS_X = CAMERA_WIDTH/5;
    protected static float INI_POS_Y = CAMERA_HEIGHT*2/3;
    protected static int RADIUS = 32;
    float TouchX = INI_POS_X;
    float TouchY = INI_POS_Y;
    Sprite BlueDot;
    Rectangle greenarea;
    Sprite Gravi;
    int Start=0;
    BitmapTextureAtlas MenuSceneTexture, FinishSceneTexture;//,playerTextureBg;
    Font TextFont;
    Rectangle ground,pausebutton;
    Sprite Pause;
    PhysicsWorld physicsWorld;
    Text PauseTimerText;

    ITextureRegion playerTextureRegion,playerTextureRegionFinishScene,playerTextureRegionRate,playerTextureRegionShare,playerTextureRegionPause,playerTextureRegionRetry,playerTextureRegionContent,playerTextureRegionPlay,playerTextureRegionMainChar,playerTextureRegionTouchStart,playerTextureRegionBg;
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
                engine.setScene(menuScene);
                break;
            case GAME:
                engine.setScene(gameScene);
                break;
            case FINISH:
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
        splashTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(splashTA,activity,"title.png",0,0);
        splashTA.load();
    }

    public void loadGameResources(){
        loadGfx();
        //createGameScene();
    }
    private void loadGfx() {
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
        //width and height 2^x
        MenuSceneTexture = new BitmapTextureAtlas(activity.getTextureManager(),1024,1024);
        playerTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(MenuSceneTexture,activity,"charnew.png",800,24);
        playerTextureRegionPause = BitmapTextureAtlasTextureRegionFactory.createFromAsset(MenuSceneTexture,activity,"pause.png",864,24);
        playerTextureRegionRetry = BitmapTextureAtlasTextureRegionFactory.createFromAsset(MenuSceneTexture,activity,"retry.gif",800,152);
        playerTextureRegionPlay = BitmapTextureAtlasTextureRegionFactory.createFromAsset(MenuSceneTexture,activity,"play.gif",800,88);
        playerTextureRegionRate = BitmapTextureAtlasTextureRegionFactory.createFromAsset(MenuSceneTexture,activity,"star.png",800,216);
        playerTextureRegionShare = BitmapTextureAtlasTextureRegionFactory.createFromAsset(MenuSceneTexture,activity,"share.png",832,216);
        playerTextureRegionMainChar = BitmapTextureAtlasTextureRegionFactory.createFromAsset(MenuSceneTexture,activity,"logo.png",915,25);
        playerTextureRegionTouchStart = BitmapTextureAtlasTextureRegionFactory.createFromAsset(MenuSceneTexture,activity,"touchtostart.png",800,0);
        FinishSceneTexture = new BitmapTextureAtlas(activity.getTextureManager(),256,256);
        playerTextureRegionFinishScene = BitmapTextureAtlasTextureRegionFactory.createFromAsset(FinishSceneTexture,activity,"sadgravi.png",0,0);
        playerTextureRegionContent = BitmapTextureAtlasTextureRegionFactory.createFromAsset(MenuSceneTexture,activity,"gravidotnobg.png",0,480);
        playerTextureRegionBg = BitmapTextureAtlasTextureRegionFactory.createFromAsset(MenuSceneTexture,activity,"background.png",0,0);
        activity.getEngine().getTextureManager().loadTexture(MenuSceneTexture);
        activity.getEngine().getTextureManager().loadTexture(FinishSceneTexture);
        this.TextFont = FontFactory.createFromAsset(activity.getFontManager(), this.activity.getTextureManager(), 256, 256, this.activity.getAssets(), "fnts/Colored Crayons.ttf", 32, true, android.graphics.Color.WHITE);
        this.TextFont.load();

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
        Sprite StarBgFinishScene = new Sprite(0,0,playerTextureRegionBg,engine.getVertexBufferObjectManager());
        finishscene.attachChild(StarBgFinishScene);
        Sprite SadGravi = new Sprite(0,0,playerTextureRegionFinishScene,engine.getVertexBufferObjectManager());
        SadGravi.registerEntityModifier(new RotationModifier(0.5f, -360, 0));
        SadGravi.registerEntityModifier(new MoveXModifier(0.5f, -playerTextureRegionFinishScene.getWidth() / 2, playerTextureRegionFinishScene.getWidth() / 2));
        //SadGravi.setScale(2f);
        SadGravi.setPosition(camera.getWidth()/2-SadGravi.getWidth()/2,SadGravi.getHeight()/2+64);
        finishscene.attachChild(SadGravi);
        PlayOrRetryButton = new Sprite(CAMERA_WIDTH-playerTextureRegionRetry.getWidth(),CAMERA_HEIGHT-playerTextureRegionRetry.getHeight(), playerTextureRegionRetry, engine.getVertexBufferObjectManager()){
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                if(pSceneTouchEvent.isActionUp()) {
                    createGameScene();
                    setCurrentScene(SceneManager.AllScenes.GAME);
                }
                return true;
            }
        };
        finishscene.registerTouchArea(PlayOrRetryButton);
        //PlayOrRetryButton.setRotation(45f);
        finishscene.attachChild(PlayOrRetryButton);
        SharedPreferences sharedPref = activity.getSharedPreferences("GAME",0);
        int hs = Integer.parseInt(sharedPref.getString("HighScore", 0 + ""));
        int hv = Integer.parseInt(sharedPref.getString("HighSpeed", 0 + ""));
        HighScore = new Text(CAMERA_WIDTH/10, CAMERA_HEIGHT/2, this.TextFont, ""+hs, new TextOptions(HorizontalAlign.CENTER), this.engine.getVertexBufferObjectManager());
        PrevScore = new Text(CAMERA_WIDTH/10, CAMERA_HEIGHT/2+40,this.TextFont, ""+score, new TextOptions(HorizontalAlign.CENTER), this.engine.getVertexBufferObjectManager());
        highspeed = new Text(CAMERA_WIDTH/10, CAMERA_HEIGHT/2+80, this.TextFont, hv+" m/s", new TextOptions(HorizontalAlign.CENTER), this.engine.getVertexBufferObjectManager());
        prevSpeed = new Text(CAMERA_WIDTH/10, CAMERA_HEIGHT/2+120,this.TextFont, current_speed+" m/s", new TextOptions(HorizontalAlign.CENTER), this.engine.getVertexBufferObjectManager());
        finishscene.attachChild(HighScore);
        finishscene.attachChild(PrevScore);
        finishscene.attachChild(highspeed);
        finishscene.attachChild(prevSpeed);
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
        splashScene.setBackground(new Background(0,0,0));
        Sprite SplashImage = new Sprite(0,0,splashTR,engine.getVertexBufferObjectManager());
        SplashImage.setPosition(camera.getWidth()/2-SplashImage.getWidth()/2,camera.getHeight()/2-SplashImage.getHeight()/2);
        splashScene.attachChild(SplashImage);
        return splashScene;
    }
    public Scene createMenuScene(){
        SharedPreferences.Editor editor = activity.getSharedPreferences("GAME", 0).edit();
        editor.putString("Adview", "false");
        editor.commit();
        SharedPreferences.Editor editorAd = activity.getSharedPreferences("AD", 0).edit();
        editorAd.putString("InterstitialAd","true");
        editorAd.commit();
        menuScene = new Scene();
        menuScene.setBackground(new Background(Color.BLACK));
        Sprite bg = new Sprite(0,0,playerTextureRegionBg,engine.getVertexBufferObjectManager());
        menuScene.attachChild(bg);
        Sprite icon = new Sprite(0,0,playerTextureRegionContent,engine.getVertexBufferObjectManager());
        //icon.setScale(2f);
        //icon.setPosition(camera.getWidth()/2-icon.getWidth()/2,icon.getHeight()/2+64);
        menuScene.attachChild(icon);
        PlayOrRetryButton = new Sprite(CAMERA_WIDTH-playerTextureRegionPlay.getWidth(),CAMERA_HEIGHT-playerTextureRegionPlay.getHeight(), playerTextureRegionPlay, engine.getVertexBufferObjectManager()){
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                if(pSceneTouchEvent.isActionUp()) {

                    //gameScene.unregisterTouchArea(PlayOrRetryButton);
                    createGameScene();
                    setCurrentScene(SceneManager.AllScenes.GAME);
                }
                return true;
            }
        };
        menuScene.registerTouchArea(PlayOrRetryButton);
        menuScene.attachChild(PlayOrRetryButton);
        SharedPreferences sharedPref = activity.getSharedPreferences("GAME",0);
        int hs = Integer.parseInt(sharedPref.getString("HighScore", 0 + ""));
        int hv = Integer.parseInt(sharedPref.getString("HighSpeed", 0 + ""));
        HighScore = new Text(CAMERA_WIDTH/20, CAMERA_HEIGHT/3, this.TextFont, "BEST SCORE : "+hs, new TextOptions(HorizontalAlign.CENTER), this.engine.getVertexBufferObjectManager());
        highspeed = new Text(CAMERA_WIDTH/20, CAMERA_HEIGHT/3+40, this.TextFont, "BEST SPEED : "+hv+" m/s", new TextOptions(HorizontalAlign.CENTER), this.engine.getVertexBufferObjectManager());
        menuScene.attachChild(HighScore);
        menuScene.attachChild(highspeed);
        Rate rate = new Rate(CAMERA_WIDTH/10,CAMERA_HEIGHT/6,playerTextureRegionRate,activity.getVertexBufferObjectManager(),0,activity);
        Rate share = new Rate(CAMERA_WIDTH/5,CAMERA_HEIGHT/6,playerTextureRegionShare,activity.getVertexBufferObjectManager(),1,activity);
        //rate.setScale(2f);
        //share.setScale(2f);
        menuScene.attachChild(rate);
        menuScene.attachChild(share);
        menuScene.registerTouchArea(rate);
        menuScene.registerTouchArea(share);

        return menuScene;
    }
    public Scene createGameScene(){
        gameScene = new Scene();
        SharedPreferences.Editor editor = activity.getSharedPreferences("GAME", 0).edit();
        editor.putString("Adview", "false");

        editor.commit();
        SharedPreferences.Editor editorAd = activity.getSharedPreferences("AD", 0).edit();
        editorAd.putString("InterstitialAd","false");
        editorAd.commit();
        gameScene.setBackground(new Background(Color.BLACK));
        greenarea = new Rectangle(CAMERA_WIDTH,0,10,CAMERA_HEIGHT,engine.getVertexBufferObjectManager());
        physicsWorld = new PhysicsWorld(new Vector2(0, 0),false);
        gameScene.registerUpdateHandler(physicsWorld);
        engine.registerUpdateHandler(new FPSLogger());
        //createWalls();
        crateRect();
        PauseTimerText = new Text(100, 40, this.TextFont, "", new TextOptions(HorizontalAlign.CENTER), this.engine.getVertexBufferObjectManager());
        PauseTimerText.setColor(Color.WHITE);
        SCORE = new Text(100, 40, this.TextFont, "999999999", new TextOptions(HorizontalAlign.CENTER), this.engine.getVertexBufferObjectManager());
        SCORE.setPosition(CAMERA_WIDTH/2, 40);
        //SCORE = new Text(CAMERA_WIDTH/2-SCORE.getWidth()/2, 40, this.TextFont, "999999999", new TextOptions(HorizontalAlign.CENTER), this.engine.getVertexBufferObjectManager());
        SCORE.setText(""+0);
        SCORE.setColor(Color.WHITE);
        gameScene.attachChild(SCORE);
        speed = new Text(0, 0, this.TextFont, "999999999 m/s", new TextOptions(HorizontalAlign.CENTER), this.engine.getVertexBufferObjectManager());
        //speed.setPosition(CAMERA_WIDTH/2, 40);
        //SCORE = new Text(CAMERA_WIDTH/2-SCORE.getWidth()/2, 40, this.TextFont, "999999999", new TextOptions(HorizontalAlign.CENTER), this.engine.getVertexBufferObjectManager());
        speed.setText("SPEED : 0 m/s");
        speed.setColor(Color.WHITE);
        gameScene.attachChild(speed);
        gameScene.attachChild(PauseTimerText);
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

        physicsWorld.registerPhysicsConnector(new PhysicsConnector(Gravi,body,true,false){
            @Override
            public void onUpdate(float pSecondsElapsed) {
                super.onUpdate(pSecondsElapsed);
                if(BlueDot !=null && Start!=0) {
                    double r = Math.sqrt(Math.pow((Gravi.getX() - BlueDot.getX()), 2) + Math.pow((Gravi.getY() - BlueDot.getY()), 2));
                    float Fx = r > 0 ? ((float) ((Gravi.getX() - BlueDot.getX() ) * 1000 / (r * r))) : 0;
                    float Fy = r > 0 ? ((float) ((Gravi.getY() - BlueDot.getY()) * 1000 / (r * r))) : 0;
                    body.applyForce(-10 * Fx, -10 * Fy, Gravi.getX() + 16, Gravi.getY() + 16);
                    if (body.getLinearVelocity().x >= 0) {
                        camera.setCenter(Gravi.getX() - INI_POS_X + CAMERA_WIDTH / 2 , CAMERA_HEIGHT / 2);
                        ground.setPosition(Gravi.getX() - INI_POS_X , 0);
                        SCORE.setPosition(Gravi.getX() - INI_POS_X +CAMERA_WIDTH/2-SCORE.getWidth()/2, 40);
                        if(score!=(ground.getX()/800)){
                            score=(int)ground.getX()/800;
                            SCORE.setText(""+score);
                        }
                        speed.setPosition(ground.getX(),ground.getY());
                        engine.runOnUpdateThread(new Runnable() {
                            @Override
                            public void run() {
                                int v = (int)body.getLinearVelocity().len();
                                speed.setText("SPEED : "+v+" m/s");
                                if(current_speed<v)
                                    current_speed = v;
                            }
                        });
                        Pause.setPosition(Gravi.getX() - INI_POS_X + CAMERA_WIDTH - 64 + 16, 0);
                        pausebutton.setPosition(Gravi.getX() - INI_POS_X + CAMERA_WIDTH - 64 + 16, 0);
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
        Pause = new Sprite(CAMERA_WIDTH-64,0,playerTextureRegionPause,engine.getVertexBufferObjectManager());
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
                    final Sprite pausedSprite = new Sprite(x, y, playerTextureRegionPlay, getVertexBufferObjectManager()) {
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
        gameScene.registerTouchArea(pausebutton);
        final Sprite touchtostart = new Sprite(CAMERA_WIDTH/2-playerTextureRegionTouchStart.getWidth()/2,CAMERA_HEIGHT/2-playerTextureRegionTouchStart.getHeight()/2,playerTextureRegionTouchStart,engine.getVertexBufferObjectManager());
        gameScene.attachChild(touchtostart);
        ground = new Rectangle(0,0,CAMERA_WIDTH,CAMERA_HEIGHT,this.engine.getVertexBufferObjectManager()){
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                {if(Start==0){
                    body.setLinearVelocity(10,-10);
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
                            gameScene.attachChild(spritesub);
                            spritesub.registerUpdateHandler(new IUpdateHandler() {
                                @Override
                                public void onUpdate(float pSecondsElapsed) {
                                   if(spritesub.getX()+spritesub.getWidth()<=0){
                                       spritesub.unregisterUpdateHandler(this);
                                       engine.runOnUpdateThread(new Runnable() {
                                           @Override
                                           public void run() {
                                               gameScene.detachChild(spritesub);
                                               spritesub.dispose();
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
                                    if(sprite.getX()+sprite.getWidth()<=0){
                                        sprite.unregisterUpdateHandler(this);
                                        engine.runOnUpdateThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                gameScene.detachChild(sprite);
                                                sprite.dispose();
                                            }
                                        });
                                    }
                                    /*if(1-pSecondsElapsed/2>0)
                                        sprite.setScale(1-pSecondsElapsed/2);
                                    else
                                        sprite.setPosition(-20,-20);*/
                                    double r = Math.sqrt(Math.pow((Gravi.getX() - sprite.getX() - 16), 2) + Math.pow((Gravi.getY() - sprite.getY() - 16), 2));
                                    if (sprite.collidesWith(Gravi) && r <= 48) {
                                        Achievements a = new Achievements(MainActivity.mGoogleApiClient,activity);
                                        a.unlockCrashed();
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
        ground.setColor(new Color(0, 0, 0, 0));
        gameScene.attachChild(ground);
        gameScene.registerTouchArea(ground);
        //scene.attachChild(pause);
        //scene.registerTouchArea(pause);

        gameScene.setTouchAreaBindingOnActionMoveEnabled(false);
    }
    public void count(){
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
                                Log.d("" + (millisUntilFinished / 1000), "" + i);
                                gameScene.detachChild(PauseTimerText);
                                PauseTimerText = new Text(Gravi.getX()-INI_POS_X+CAMERA_WIDTH/2- PauseTimerText.getWidth()/2, CAMERA_HEIGHT/2- PauseTimerText.getHeight()/2, TextFont, (String.valueOf(i)), new TextOptions(HorizontalAlign.CENTER), engine.getVertexBufferObjectManager());
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
    }
    public void restart(){
        resetPhysics();
        SharedPreferences sharedPref = activity.getSharedPreferences("GAME",0);
        int hs = Integer.parseInt(sharedPref.getString("HighScore", 0 + ""));
        int hv = Integer.parseInt(sharedPref.getString("HighSpeed", 0 + ""));
        if(hs<score){
            hs=score;
            if(MainActivity.isSignedIn()){
                Achievements a = new Achievements(MainActivity.mGoogleApiClient,activity);
                a.unlockScore(hs);
                Leaderboards l = new Leaderboards(MainActivity.mGoogleApiClient,activity);
                l.postScore(hs);
            }
        }
        if(hv<current_speed){
            hv=current_speed;
            if(MainActivity.isSignedIn()){
                Achievements a = new Achievements(MainActivity.mGoogleApiClient,activity);
                a.unlockSpeed(hv);
                Leaderboards l = new Leaderboards(MainActivity.mGoogleApiClient,activity);
                l.postSpeed(hv);
            }
        }
        SharedPreferences example = activity.getSharedPreferences("GAME", 0);
        SharedPreferences.Editor editor = example.edit();
        editor.putString("HighScore", hs + "");
        editor.putString("HighSpeed", hv + "");
        editor.commit();
        engine.start();
        body.setLinearVelocity(0,0);
        Start=0;
        INI_POS_X = CAMERA_WIDTH/5;
        INI_POS_Y = CAMERA_HEIGHT*2/3;
        createFinishScene();
        score=0;
        current_speed=0;
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
