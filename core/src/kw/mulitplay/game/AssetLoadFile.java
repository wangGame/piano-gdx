package kw.mulitplay.game;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.kw.gdx.asset.Asset;

import java.util.HashMap;

public class AssetLoadFile {
    public static HashMap<String,Sound> hashMap = new HashMap<>();
    public static void loadSound(){
        HashMap<String, String> agToResouce = SoundKeyMap.AGToResouce;
        for (String value : agToResouce.values()) {
            System.out.println(value);
            Asset.assetManager.load(value,Sound.class);
        }
    }

    public static void loadFile(){
        Asset.assetManager.load("Bahnschrift-Regular_40_1.fnt", BitmapFont.class);
    }

    public static BitmapFont getBR40(){
        return Asset.assetManager.get("Bahnschrift-Regular_40_1.fnt",BitmapFont.class);
    }
}
