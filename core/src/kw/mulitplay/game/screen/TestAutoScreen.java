package kw.mulitplay.game.screen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import kw.mulitplay.game.constant.Constant;
import kw.mulitplay.game.data.AutoManager;
import kw.mulitplay.game.data.TestAutoManager;
import kw.mulitplay.game.group.PianoView;
import kw.mulitplay.game.screen.base.BaseScreen;

/**
 * all screen extends BaseScreen
 */
public class TestAutoScreen extends BaseScreen {
    private TestAutoManager autoManager;
    private PianoView view;

    @Override
    protected void initView() {
        Image image = new Image(new Texture("main/white.png"));
        stage.addActor(image);
        image.setSize(Constant.width, Constant.height);
        image.setColor(246 / 255.0f, 239 / 255.0f, 229 / 255.0f, 1);
        view = new PianoView();
        view.setMode(0);
        stage.addActor(view);
        view.showPianoKey();
        autoManager = new TestAutoManager(view);
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