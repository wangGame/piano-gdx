package kw.mulitplay.game.screen;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;

import kw.mulitplay.game.constant.Constant;
import kw.mulitplay.game.downloadversion.DownNode;
import kw.mulitplay.game.downloadversion.NodeManager;
import kw.mulitplay.game.group.PianoKey;
import kw.mulitplay.game.group.PianoView;
import kw.mulitplay.game.screen.base.BaseScreen;

public class DownScreen extends BaseScreen {
    private PianoView view;
    @Override
    protected void initView() {
        Image image = new Image(new Texture("main/white.png"));
        stage.addActor(image);
        image.setSize(Constant.width,Constant.height);
        image.setColor(246/255.0f,239/255.0f,229/255.0f,1);
        view = new PianoView();

        view.setMode(Constant.DOWN);
        view.showPianoKey();
        NodeManager nodeManager = new NodeManager(view);
        Group group = new Group();
        group.setSize(Constant.width,Constant.height-300);
        group.setY(230);
        group.setDebug(true);
        stage.addActor(group);
        stage.addActor(view);
        downNodes = nodeManager.getDownNodes();
        for (DownNode downNode : downNodes) {
            group.addActor(downNode);
            downNode.setX(downNode.getBaseNodeX());
            downNode.setY(downNode.getBaseNodeY()+400);
            downNode.setSize(30,downNode.getLength());
        }
    }

    private Array<DownNode> downNodes;

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

    Array<PianoKey> playKey = new Array<>();

    @Override
    public void render(float delta) {
        super.render(delta);
        for (DownNode downNode : downNodes) {
            if (downNode.getStatus() == 1) {
                PianoKey pianoKey = view.getHashMap().get(downNode.getKey());
                playKey.add(pianoKey);
                downNode.setStatus(2);
                pianoKey.touchDownKey();
                pianoKey.addAction(Actions.delay(downNode.getTimes(), Actions.run(()->{
                    pianoKey.finishTouchi();
                })));
                pianoKey.addAction(Actions.delay(downNode.getLength()));
            }
        }
        for (PianoKey sound: playKey) {
            sound.touchDownKey();
        }
        playKey.clear();
    }


}
