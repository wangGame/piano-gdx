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
import kw.mulitplay.game.data.AutoManager;
import kw.mulitplay.game.data.Json;
import kw.mulitplay.game.data.Yanyuan;
import kw.mulitplay.game.group.PianoKey;
import kw.mulitplay.game.group.PianoView;
import kw.mulitplay.game.screen.base.BaseScreen;

/**
 * all screen extends BaseScreen
 */
public class AutoScreen extends BaseScreen {
    private AutoManager autoManager;
    private PianoView view;

    @Override
    protected void initView() {
        Image image = new Image(new Texture("main/white.png"));
        stage.addActor(image);
        image.setSize(Constant.width,Constant.height);
        image.setColor(246/255.0f,239/255.0f,229/255.0f,1);
        view = new PianoView();
        view.setMode(0);
        stage.addActor(view);
        view.showPianoKey();
        autoManager = new AutoManager(view);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        autoManager.update(delta);
    }

    @Override
    protected void back() {
        enterScreen(new MainScreen());
    }
}
