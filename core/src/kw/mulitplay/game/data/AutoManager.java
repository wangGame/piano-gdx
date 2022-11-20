package kw.mulitplay.game.data;

import com.kw.gdx.asset.Asset;

import kw.mulitplay.game.group.PianoView;

public class AutoManager {
    private AutoData leftAudoData;
    private AutoData rightAudoData;
    public AutoManager(PianoView view){
        LittleStar json = new LittleStar();
        float bpm = json.bpm;
        float v = bpm / 60.0f;
        String l = json.l;
        String[] split = l.split("\\/");
        String s = split[1];
        Integer integer = Integer.valueOf(s);
        float baseTime = v / integer;
        leftAudoData = new AutoData(json.left,view,baseTime);
        rightAudoData = new AutoData(json.right,view,baseTime);
    }

    public void update(float delta){
        leftAudoData.act(delta);
        rightAudoData.act(delta);
    }
}
