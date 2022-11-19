package kw.mulitplay.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import java.util.HashMap;
import java.util.Map;

import kw.mulitplay.game.MapKeySound;
import kw.mulitplay.game.SoundKeyMap;
import kw.mulitplay.game.constant.Constant;
import kw.mulitplay.game.data.Json;
import kw.mulitplay.game.group.PianoKey;
import kw.mulitplay.game.group.PianoView;
import kw.mulitplay.game.screen.base.BaseScreen;

/**
 * all screen extends BaseScreen
 */
public class LoadingScreen extends BaseScreen {
    private float time = 0;
    private float time1 = 0;

    //    private MapKeySound sound;
    private String[] left;
    private String[] right;
    private PianoView view;
    @Override
    protected void initView() {
//        sound = new MapKeySound();
//        sound.test();
        Image image = new Image(new Texture("main/white.png"));
        stage.addActor(image);
        image.setSize(Constant.width,Constant.height);
        image.setColor(246/255.0f,239/255.0f,229/255.0f,1);
        view = new PianoView();
        stage.addActor(view);
        view.showPianoKey();
        Json json = new Json();
        left = json.left;
        right = json.right;

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    private int index = 0;
    float target = 0.5f;
    private int index1 = 0;
    float target1 = 0.5f;


    @Override
    public void render(float delta) {
        super.render(delta);
        time += delta;
        time1 += delta;

        if (time1>target1) {
            if (index1 >= left.length) {
                System.out.println("---------left end");
            } else {
                target1 = 0.5f;
                if (left[index1].equals("0")) {

                } else {
                    HashMap<String, String> jianpuToAG = SoundKeyMap.jianpuToAG;
                    String s3 = left[index1];
                    boolean contains = s3.contains("+");
                    boolean contains1 = s3.contains("-");
                    boolean contains2 = s3.contains("(");
                    s3 = s3.replace("+", "");
                    s3 = s3.replace("-", "");
                    s3 = s3.replace("(", "");
                    String s = jianpuToAG.get(s3);
                    System.out.println(s);
                    float times = 0.5f;
                    System.out.println(s);
                    String s2 = SoundKeyMap.AGToIndex.get(s);
                    PianoKey actor = view.getHashMap().get(s2);
                    actor.touchDownKey();
                    if (index1 + 1 < right.length) {
                        if (left[index1 + 1].equals("0")) {
                            times = 1f;
                        }
                    }
                    if (contains) {
                        target1 = 0.7f;
                        times = 0.7f;
                    }
                    if (contains1) {
                        target1 = 0.3f;
                        times = 0.3f;
                    }
                    if (contains2) {
                        target1 = 0.25f;
                        times = 0.25f;
                    }
                    actor.addAction(Actions.delay(times, Actions.run(() -> {
                        actor.finishTouchi();
                    })));
                    String s1 = SoundKeyMap.AGToResouce.get(s);
                    Sound sound = Gdx.audio.newSound(Gdx.files.internal(s1));
                    sound.play();
                }

            }
                time1 = 0;
                index1++;

        }
        if (time > target) {
            if (index >= right.length) {
                System.out.println("---------right end");
            } else {
                target = 0.5f;

                if (right[index].equals("0")) {

                } else {
                    HashMap<String, String> jianpuToAG = SoundKeyMap.jianpuToAG;
                    String s3 = right[index];
                    boolean contains = s3.contains("+");
                    boolean contains1 = s3.contains("-");
                    boolean contains2 = s3.contains("(");
                    s3 = s3.replace("+", "");
                    s3 = s3.replace("-", "");
                    s3 = s3.replace("(", "");
                    String s = jianpuToAG.get(s3);
                    System.out.println(s);
                    float times = 0.5f;
                    System.out.println(s);
                    String s2 = SoundKeyMap.AGToIndex.get(s);
                    PianoKey actor = view.getHashMap().get(s2);
                    actor.touchDownKey();
                    if (index + 1 < right.length) {
                        if (right[index + 1].equals("0")) {
                            times = 1f;
                        }
                    }
                    if (contains) {
                        target = 0.7f;
                        times = 0.7f;
                    }
                    if (contains1) {
                        target = 0.3f;
                        times = 0.3f;
                    }
                    if (contains2) {
                        target = 0.25f;
                        times = 0.25f;
                    }
                    actor.addAction(Actions.delay(times, Actions.run(() -> {
                        actor.finishTouchi();
                    })));
                    String s1 = SoundKeyMap.AGToResouce.get(s);
                    Sound sound = Gdx.audio.newSound(Gdx.files.internal(s1));
                    sound.play();
                }
            }
                time = 0;
                index++;

//            target = sound.xx(sound.str[index]);
//            index++;
//            if (sound.str.length<=index){
//                index = 0;
//            }
//            time = 0;

        }
    }
}
