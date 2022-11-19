package kw.mulitplay.game.screen;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import java.util.HashMap;
import java.util.Map;

import kw.mulitplay.game.MapKeySound;
import kw.mulitplay.game.constant.Constant;
import kw.mulitplay.game.group.PianoView;
import kw.mulitplay.game.screen.base.BaseScreen;

/**
 * all screen extends BaseScreen
 */
public class LoadingScreen extends BaseScreen {
//    private float time = 0;
//    private MapKeySound sound;
    @Override
    protected void initView() {
//        sound = new MapKeySound();
//        sound.test();
        Image image = new Image(new Texture("main/white.png"));
        stage.addActor(image);
        image.setSize(Constant.width,Constant.height);
        image.setColor(246/255.0f,239/255.0f,229/255.0f,1);
        PianoView view = new PianoView();
        stage.addActor(view);
        view.showPianoKey();
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    private int index = 0;
    float target = 0;

    @Override
    public void render(float delta) {
        super.render(delta);
//        time += delta;
//        if (time > target) {
//            target = sound.xx(sound.str[index]);
//            index++;
//            if (sound.str.length<=index){
//                index = 0;
//            }
//            time = 0;
//        }
    }
}
