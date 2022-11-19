package kw.mulitplay.game.group;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;

import java.util.HashMap;

import kw.mulitplay.game.ColorUtils;

public class PianoView extends Group {
    private HashMap<String,PianoKey> hashMap = new HashMap<>();
    public void showPianoKey(){
        int keyIndex = 1;
        ColorUtils utils = new ColorUtils();
        utils.color();
//        88 - 36
        int[] arr= {
                1,0,1,1,0,1,1,1,0,1,1,0,1,1,1,0,1,1,0,1,1,1,0,1,1,0,1,1,1,0,1,1,0,1,1,1,0,1,1,0,1,1,1,0,1,1,0,1,1,1
        };
        int xxx = 0;
        int arr1=0;
        for (int i = 0; i < 52; i++) {
            Texture texture = new Texture("pianoImg/white.png");
            PianoKey image = new PianoKey(texture);
            String path = "";
            if (keyIndex<10){
                path = "0"+keyIndex;
            }else {
                path = keyIndex+"";
            }
            image.setSound(Gdx.audio.newSound(Gdx.files.internal("piano2/p"+path+".mp3")));
            System.out.println(keyIndex);
            image.setMovePanelColor(ColorUtils.array.get(keyIndex));
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
            xxx++;

            Texture texture1 = new Texture("pianoImg/white.png");
            PianoKey image1 = new PianoKey(texture1);
            image1.setSize(24,130);
            addActor(image1);
            String path1 = "";
            if (keyIndex<10){
                path1 = "0"+keyIndex;
            }else {
                path1 = keyIndex+"";
            }
            image1.setSound(Gdx.audio.newSound(Gdx.files.internal("piano2/p"+path1+".mp3")));
            image1.setMovePanelColor(ColorUtils.array.get(keyIndex));
            hashMap.put(keyIndex+"",image);
            keyIndex++;
            image1.setY(120);
            image1.setColor(Color.BLACK);
            image1.setX(i*31+20);
        }
    }

    public HashMap<String, PianoKey> getHashMap() {
        return hashMap;
    }
}
