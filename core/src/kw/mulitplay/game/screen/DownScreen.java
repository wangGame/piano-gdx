package kw.mulitplay.game.screen;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
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
        stage.addActor(view);
        view.showPianoKey();
        NodeManager nodeManager = new NodeManager(view);

        downNodes = nodeManager.getDownNodes();

        for (DownNode downNode : downNodes) {
            stage.addActor(downNode);
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

    }

    Array<Sound> playKey = new Array<>();

    @Override
    public void render(float delta) {
        super.render(delta);
        for (DownNode downNode : downNodes) {
            if (downNode.getStatus() == 1) {
                PianoKey pianoKey = view.getHashMap().get(downNode.getKey());
                playKey.add(pianoKey.getSound());
                downNode.setStatus(2);
            }
        }
        for (Sound sound : playKey) {
            sound.play();
        }
        playKey.clear();
    }
}
