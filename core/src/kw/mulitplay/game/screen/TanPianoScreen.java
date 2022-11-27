package kw.mulitplay.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.kw.gdx.asset.Asset;

import java.util.HashMap;

import kw.mulitplay.game.AssetLoadFile;
import kw.mulitplay.game.SoundKeyMap;
import kw.mulitplay.game.constant.Constant;
import kw.mulitplay.game.data.Yanyuan;
import kw.mulitplay.game.group.PianoKey;
import kw.mulitplay.game.group.PianoView;
import kw.mulitplay.game.screen.base.BaseScreen;

public class TanPianoScreen extends BaseScreen {
    private PianoView view;
    private Label touchLabel;

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
        touchLabel = new Label("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx",new Label.LabelStyle(){
            {
                font = AssetLoadFile.getBR40();
            }
        });
        touchLabel.setDebug(true);
        view.setLabel(touchLabel);
        touchLabel.setWrap(true);
        touchLabel.setWidth(Constant.width);
        touchLabel.setAlignment(Align.center);
        touchLabel.setColor(Color.BLACK);
        touchLabel.setPosition(Constant.width/2, Constant.height/2, Align.center);
        stage.addActor(touchLabel);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }


    @Override
    protected void back() {
        enterScreen(new MainScreen());
    }
}
