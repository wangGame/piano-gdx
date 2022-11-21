package kw.mulitplay.game.group;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.AudioDevice;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
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
    private Color oldColor = new Color();
    private Image image;
    private Color moveColor;
    private Image pros;
    private Array<Image> disableArray;
    private Label keyInfo;
    private int keyIndex;
    private ArrayList<Integer> musicData;
    private byte[] fftData;

    public PianoKey(Texture texture, int keyIndex){
        disableArray = new Array<>();
        this.musicData = new ArrayList<>();
        this.keyIndex = keyIndex;
        image = new Image(texture);
        addActor(image);
        keyInfo = new Label("",new Label.LabelStyle(){{
            font = AssetLoadFile.getBR40();
        }});
        addActor(keyInfo);
        keyInfo.setColor(Color.BLACK);
        keyInfo.setFontScale(0.5f);
        keyInfo.setAlignment(Align.center);
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

    private boolean isTouched = false;
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
        if (show!=null) {
            show.callBack(nameKK);
        }
        if (back!=null){
            back.callBack(getFftData());
        }
        isTouched = true;
        oldColor.r = color.r;
        oldColor.g = color.g;
        oldColor.b = color.b;
        image.setColor(0.3f,0.3f,0.3f,1);
        pros = new Image(new Texture("pianoImg/white.png"));
        addActor(pros);
        pros.setColor(moveColor);
        pros.setSize(getWidth(),10);
        sound.play();
        pros.setY(image.getY(Align.top));
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
        String s = "";
        s = SoundKeyMap.indexToAG.get(keyIndex+"");
        keyInfo.setText(s);
        this.nameKK = s;
        keyInfo.setPosition(15,50,Align.center);
    }

    private String nameKK;

    private CallBack show;
    private CallBack hide;
    private CallBack back;
    public void addCallBack(CallBack show,CallBack hide,CallBack back) {
        this.show = show;
        this.hide = hide;
        this.back = back;
    }
}
