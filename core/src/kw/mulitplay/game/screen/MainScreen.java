package kw.mulitplay.game.screen;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.kw.gdx.asset.Asset;

import kw.mulitplay.game.constant.Constant;
import kw.mulitplay.game.screen.base.BaseScreen;

public class MainScreen extends BaseScreen {
    @Override
    protected void initView() {
        Table table = new Table(){{
            Image selfPlay = new Image(Asset.getAsset().getTexture("main/3.png"));
            add(selfPlay).padLeft(50).padRight(50);
            selfPlay.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    enterScreen(new TanPianoScreen());
                }
            });
            Image autoPlay = new Image(Asset.getAsset().getTexture("main/4.png"));
            add(autoPlay).padLeft(50).padRight(50);
            autoPlay.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    enterScreen(new AutoScreen());
                }
            });
            Image midi = new Image(Asset.getAsset().getTexture("main/3.png"));
            add(midi).padLeft(50).padRight(50);
            midi.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    enterScreen(new DIMIDemoScreen());
                }
            });
            Image downMode = new Image(Asset.getAsset().getTexture("main/3.png"));
            add(downMode).padLeft(50).padRight(50);
            downMode.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    enterScreen(new DownScreen());
                }
            });
            Image sandBank = new Image(Asset.getAsset().getTexture("main/3.png"));
            add(sandBank).padLeft(50).padRight(50);
            sandBank.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    enterScreen(new DeviceMidePlayerScreen());
                }
            });
            Image testJson = new Image(Asset.getAsset().getTexture("main/3.png"));
            add(testJson).padLeft(50).padRight(50);
            testJson.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    enterScreen(new TestAutoScreen());
                }
            });
            pack();
            setPosition(Constant.width/2,Constant.height/2, Align.center);
        }};
        stage.addActor(table);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void back() {

    }
}
