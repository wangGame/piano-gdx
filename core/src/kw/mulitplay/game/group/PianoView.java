package kw.mulitplay.game.group;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.kw.gdx.asset.Asset;

import java.util.HashMap;

import kw.mulitplay.game.AssetLoadFile;
import kw.mulitplay.game.ColorUtils;
import kw.mulitplay.game.callback.CallBack;
import kw.mulitplay.game.constant.Constant;

public class PianoView extends Group {
    private HashMap<String,PianoKey> hashMap;
    private Label touchDownKey;
    private MusicLineActor line;
    private ColorUtils utils;

    public PianoView(){
        hashMap = new HashMap<>();
        utils = new ColorUtils();
    }

    public void showPianoKey(){
        utils.color();
        initLabel();
        initKeys();
        waveLine();
    }

    private void waveLine() {
        line = new MusicLineActor(new TextureRegion(new Texture("main/white.png")));
        addActor(line);
        line.setPosition(getWidth()/2,Constant.height/2);
    }

    private void initKeys() {
        //        88 - 36
        int[] blackKeyArr= {
                1,0,1,1,0,1,1,1,0,1,1,0,1,1,1,0,1,1,0,1,1,1,0,1,1,0,1,1,1,0,1,1,0,1,1,1,0,1,1,0,1,1,1,0,1,1,0,1,1,1
        };
//        int arr1=0;
        int blockIndex = 0;
        int keyIndex = 1;
        for (int i = 0; i < 52; i++) {
            PianoKey whitePianoKey = getPianoKey(keyIndex);
            whitePianoKey.setSize(30,230);
            whitePianoKey.setX(i *31);
            hashMap.put(keyIndex +"",whitePianoKey);
            keyIndex++;
            if (blockIndex>=blackKeyArr.length)continue;
            if (blackKeyArr[blockIndex++] == 0) {
                continue;
            }

            PianoKey image1 = getPianoKey(keyIndex);
            image1.setMovePanelColor(ColorUtils.array.get(keyIndex));
            hashMap.put(keyIndex+"",image1);
            keyIndex++;
            image1.setY(120);
            image1.setColor(Color.BLACK);
            image1.setX(i*31+15f);
            image1.setSize(26,100);
            image1.toFront();
        }
    }

    private PianoKey getPianoKey(int keyIndex) {
        NinePatch ninePatch = new NinePatch(Asset.getAsset().getTexture("pianoImg/white.png"),5,5,5,5);
        PianoKey pianoKey = new PianoKey(ninePatch, keyIndex);
        pianoKey.setMode(mode);
        String path = "";
        if (keyIndex <10){
            path = "0"+ keyIndex;
        }else {
            path = keyIndex +"";
        }
        pianoKey.setLabel();
        pianoKey.setPathSound(path);
        pianoKey.setMovePanelColor(ColorUtils.array.get(keyIndex));
        pianoKey.addCallBack(new CallBack() {
            @Override
            public void callBack(Object o) {
                setTouchLabel((String)o);
                touchDownKey.setText((String)o);
                touchDownKey.setPosition(Constant.width/2,Constant.height-50, Align.center);
            }
        }, new CallBack() {
            @Override
            public void callBack(Object o) {
                touchDownKey.setText("");
            }
        },new CallBack(){
            @Override
            public void callBack(Object o) {
                line.setFft((byte[]) o);
            }
        });
        addActor(pianoKey);
        pianoKey.toBack();
        return pianoKey;
    }

    private void initLabel() {
        touchDownKey = new Label("",new Label.LabelStyle(){
            {
                font = AssetLoadFile.getBR40();
            }
        });
        addActor(touchDownKey);
        touchDownKey.setAlignment(Align.center);
        touchDownKey.setFontScale(5);
        touchDownKey.setColor(Color.RED);
    }

    private Label touLabel;
    public void setLabel(Label touLabel){
        this.touLabel = touLabel;
    }

    StringBuilder builder = new StringBuilder();
    public void setTouchLabel(String txt){
        if (touLabel==null)return;
        builder.append(txt+" ");

        touLabel.setText(builder.toString());
    }

    public HashMap<String, PianoKey> getHashMap() {
        return hashMap;
    }

    private int mode;
    public void setMode(int down) {
        this.mode = down;
    }
}
