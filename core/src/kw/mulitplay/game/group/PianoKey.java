package kw.mulitplay.game.group;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.kw.gdx.asset.Asset;

import java.util.ArrayList;

import kw.mulitplay.game.AssetLoadFile;
import kw.mulitplay.game.SoundKeyMap;
import kw.mulitplay.game.callback.CallBack;
import kw.mulitplay.game.constant.Constant;
import kw.mulitplay.game.data.HandleData;

public class PianoKey extends Group {
    private Sound sound;
    private Color oldColor;
    private Image image;
    private Color moveColor;
    private Image pros;
    private Array<Image> disableArray;
    private Label keyInfo;
    private int keyIndex;
    private ArrayList<Integer> musicData;
    private byte[] fftData;
    private boolean isTouched;
    private CallBack showBack;
    private CallBack hide;
    private CallBack back;


    public PianoKey(NinePatch ninePatch,int keyIndex){
        this.image = new Image(ninePatch);
        addActor(image);
        initData(keyIndex);
    }

    public PianoKey(Texture texture, int keyIndex){
        this.image = new Image(texture);
        initData(keyIndex);
    }

    private void initData(int keyIndex) {
        this.oldColor = new Color();
        this.disableArray = new Array<>();
        this.musicData = new ArrayList<>();
        this.keyIndex = keyIndex;
        initKeyInfo();
        initListener();
    }

    private void initListener() {
        addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                touchDownKey();
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                finishTouchi();
            }
        });
    }

    private void initKeyInfo() {
        this.keyInfo = new Label("",new Label.LabelStyle(){{
            font = AssetLoadFile.getBR40();
        }});
        addActor(keyInfo);
        keyInfo.setColor(Color.BLACK);
        keyInfo.setFontScale(0.5f);
        keyInfo.setAlignment(Align.center);
    }

    public void finishTouchi() {
        image.setColor(oldColor.r,oldColor.g,oldColor.b,1);
        if (pros!=null) {
            pros.clearActions();
            pros.addAction(Actions.forever(Actions.sequence(Actions.moveBy(0, Constant.panelMoveSpeed, 0.2f))));
            disableArray.add(pros);
        }
        if (hide!=null) {
            hide.callBack("");
        }
        pros = null;
    }

    public boolean isTouched() {
        return isTouched;
    }

    public void touchDownKey() {
//        AudioDevice audioDevice = Gdx.audio.newAudioDevice(44, false);
//        audioDevice.writeSamples();
        if (isTouched){
            finishTouchi();
        }
        if (showBack !=null) {
            showBack.callBack(touchButtonName);
        }
        if (back!=null){
            back.callBack(getFftData());
        }
        isTouched = true;
        oldColor.r = color.r;
        oldColor.g = color.g;
        oldColor.b = color.b;
        image.setColor(0.3f,0.3f,0.3f,1);
        pros = new Image(new Texture("main/white.png"));
        addActor(pros);
        pros.toBack();
        pros.setColor(moveColor);
        pros.setSize(getWidth(),20);
        sound.play();
        pros.setY(image.getY(Align.top),Align.top);
        pros.addAction(Actions.forever(Actions.sizeBy(0, Constant.panelMoveSpeed,0.2f)));
    }

    @Override
    public void setSize(float width, float height) {
        super.setSize(width, height);
        image.setSize(width,height);
    }

    @Override
    public void setColor(Color color) {
        super.setColor(color);
        image.setColor(color);
    }

    public void setSound(Sound newSound) {
        this.sound = newSound;
    }

    public void setPathSound(String pathSound){
        this.sound = Asset.assetManager.get("piano2/p"+pathSound+".mp3");
        FileHandle internal = Gdx.files.internal("piano2/p" + pathSound + ".mp3");
        byte[] bytes = internal.readBytes();
        HandleData.handleData(bytes,musicData,3,1024,1000);
        fftData = bytes;
    }

    public byte[] getFftData() {
        return fftData;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (disableArray.size>0) {
            for (Image image1 : disableArray) {
                if (image1.getY()>2600) {
                    image1.clearActions();
                    image1.remove();
                }
            }
        }
    }

    public void setMovePanelColor(Color color) {
        this.moveColor = color;
    }

    public void setLabel() {
        String keyNameTemp = SoundKeyMap.indexToAG.get(keyIndex+"");
        keyInfo.setText(keyNameTemp);
        this.touchButtonName = keyNameTemp;
        keyInfo.setPosition(15,50,Align.center);
    }

    private String touchButtonName;
    public void addCallBack(CallBack show,CallBack hide,CallBack back) {
        this.showBack = show;
        this.hide = hide;
        this.back = back;
    }
}
