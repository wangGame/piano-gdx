package kw.mulitplay.game.downloadversion;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.BinaryHeap;
import com.kw.gdx.asset.Asset;

import java.util.HashMap;

import kw.mulitplay.game.SoundKeyMap;
import kw.mulitplay.game.constant.Constant;
import kw.mulitplay.game.data.LittleStar;
import kw.mulitplay.game.group.PianoKey;
import kw.mulitplay.game.group.PianoView;

public class NodeManager {
    private Array<DownNode> downNodes;
    public NodeManager(PianoView view) {
        LittleStar json = new LittleStar();
        float bpm = json.bpm;
        float v1 = bpm / 60.0f;
        String l = json.l;
        String[] split = l.split("\\/");
        String s11 = split[1];
        Integer integer = Integer.valueOf(s11);
        float baseTime = v1 / integer;

        downNodes = new Array<>();
        float target = 0;
        int indexCount = 0;
        String v[] = json.right;
        for (int index = 0; index < v.length; index++) {
            indexCount++;
            target = baseTime;
            if (!v[index].equals("0")) {
                DownNode node = new DownNode(new NinePatch(Asset.getAsset().getTexture("pianoImg/black.png")));
                downNodes.add(node);
                String s3 = v[index];
                System.out.println(s3+"     "+index);
                boolean addNum = s3.contains("+");
                boolean subNum = s3.contains("-");
                boolean khNum = s3.contains("(");
                int numKh = 0;
                for (int i = 0; i < s3.length(); i++) {
                    char c = s3.charAt(i);
                    if (c == '(') {
                        numKh++;
                    }
                }
                s3 = s3.replace("+", "");
                s3 = s3.replace("-", "");
                s3 = s3.replace("(", "");

                HashMap<String, String> jianpuToAG = SoundKeyMap.jianpuToAG;
                String s = jianpuToAG.get(s3);
                float times = 0.5f;
                String s2 = SoundKeyMap.AGToIndex.get(s);
                PianoKey actor = view.getHashMap().get(s2);
                node.setKey(s2);
                node.setBaseNodeX(actor.getX());

//                actor.touchDownKey();
                if (index + 1 < v.length) {
                    if (v[index + 1].equals("0")) {
                        times = 2 * baseTime;
                    }
                }
                if (addNum) {
                    target = baseTime + baseTime / 2.0f;
                    times = target;
                }
                if (subNum) {
                    target = baseTime - baseTime / 2.0f;
                    times = target;
                }
                if (khNum) {
                    target = baseTime - baseTime / (2.0f * numKh);
                    times = target;
                }


                float v2 = Constant.panelMoveSpeed / 0.2f;
                node.setLength(v2 * times - 0.1f);
                node.setTime(times);
                node.setBaseNodeY(offY);
                offY += node.getLength()+5;
            }
        }
    }

    float offY = 0;

    public Array<DownNode> getDownNodes() {
        return downNodes;
    }
}
