package kw.mulitplay.game.group;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.kw.gdx.asset.Asset;

import java.util.HashMap;

import kw.mulitplay.game.AssetLoadFile;
import kw.mulitplay.game.ColorUtils;
import kw.mulitplay.game.SoundKeyMap;
import kw.mulitplay.game.callback.CallBack;
import kw.mulitplay.game.constant.Constant;

public class PianoView extends Group {
    private HashMap<String,PianoKey> hashMap = new HashMap<>();
    private Label label;
    private MusicLineActor a;
    public void showPianoKey(){
        int keyIndex = 1;
        ColorUtils utils = new ColorUtils();
        utils.color();
        label = new Label("",new Label.LabelStyle(){
            {
                font = AssetLoadFile.getBR40();
            }
        });
        addActor(label);
        label.setAlignment(Align.center);
        label.setFontScale(5);
        label.setColor(Color.RED);
//        88 - 36
        int[] arr= {
                1,0,1,1,0,1,1,1,0,1,1,0,1,1,1,0,1,1,0,1,1,1,0,1,1,0,1,1,1,0,1,1,0,1,1,1,0,1,1,0,1,1,1,0,1,1,0,1,1,1
        };
        int arr1=0;
        for (int i = 0; i < 52; i++) {
            Texture texture = new Texture("pianoImg/white.png");
            PianoKey image = new PianoKey(texture,keyIndex);
            String path = "";
            if (keyIndex<10){
                path = "0"+keyIndex;
            }else {
                path = keyIndex+"";
            }
            image.setLabel();
//            image.setSound(Asset.assetManager.get("piano2/p"+path+".mp3"));
            image.setPathSound(path);
//            System.out.println(keyIndex);
            image.setMovePanelColor(ColorUtils.array.get(keyIndex));
            image.addCallBack(new CallBack() {
                @Override
                public void callBack(Object o) {
                    label.setText((String)o);
                    label.setPosition(Constant.width/2,Constant.height-50, Align.center);
                }
            }, new CallBack() {
                @Override
                public void callBack(Object o) {
                    label.setText("");
                }
            },new CallBack(){
                @Override
                public void callBack(Object o) {
                    a.setFft((byte[]) o);
                }
            });
            hashMap.put(keyIndex+"",image);
            keyIndex++;
            image.setSize(30,230);
            addActor(image);
            image.setX(i*31);
            image.toBack();
            if (arr1>=arr.length)continue;
            if (arr[arr1++] == 0) {
                continue;
            }
            Texture texture1 = new Texture("pianoImg/white.png");
            PianoKey image1 = new PianoKey(texture1, keyIndex);
            image1.setSize(24,130);
            addActor(image1);
            String path1 = "";
            if (keyIndex<10){
                path1 = "0"+keyIndex;
            }else {
                path1 = keyIndex+"";
            }
//            image1.setSound(Asset.assetManager.get("piano2/p"+path+".mp3"));
            image.setPathSound(path);
            image1.setMovePanelColor(ColorUtils.array.get(keyIndex));
            hashMap.put(keyIndex+"",image);
            keyIndex++;
            image1.setY(120);
            image1.setColor(Color.BLACK);
            image1.setX(i*31+20);
        }

        a = new MusicLineActor(new TextureRegion(new Texture("main/white.png")));
        addActor(a);
        a.setPosition(getWidth()/2,Constant.height/2);
    }

    public HashMap<String, PianoKey> getHashMap() {
        return hashMap;
    }
}
