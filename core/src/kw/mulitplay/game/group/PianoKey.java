package kw.mulitplay.game.group;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;

import kw.mulitplay.game.constant.Constant;

public class PianoKey extends Group {
    private Sound sound;
    private Color oldColor = new Color();
    private Image image;
    private Color moveColor;
    private Image pros;
    private Array<Image> disableArray;
    public PianoKey(Texture texture){
        disableArray = new Array<>();
        image = new Image(texture);
        addActor(image);
        addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                touchDownKey();
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                finishTouchi();
            }
        });
    }

    private boolean isTouched = false;
    public void finishTouchi() {
        image.setColor(oldColor.r,oldColor.g,oldColor.b,1);
        if (pros!=null) {
            pros.clearActions();
            pros.addAction(Actions.forever(Actions.sequence(Actions.moveBy(0, Constant.panelMoveSpeed, 0.2f))));
            disableArray.add(pros);
        }
        pros = null;
    }

    public void touchDownKey() {
        if (isTouched){
            finishTouchi();
        }
        isTouched = true;
        oldColor.r = color.r;
        oldColor.g = color.g;
        oldColor.b = color.b;
        image.setColor(0.3f,0.3f,0.3f,1);
        pros = new Image(new Texture("pianoImg/white.png"));
        addActor(pros);
        pros.setColor(moveColor);
        pros.setSize(getWidth(),1);
        sound.play();
        pros.setY(image.getY(Align.top));
        pros.addAction(Actions.forever(Actions.sizeBy(0, Constant.panelMoveSpeed,0.2f)));
    }

    @Override
    public void setSize(float width, float height) {
        super.setSize(width, height);
        image.setSize(width,height);
    }

    @Override
    public void setColor(Color color) {
        super.setColor(color);
        image.setColor(color);
    }

    public void setSound(Sound newSound) {
        this.sound = newSound;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (disableArray.size>0) {
            for (Image image1 : disableArray) {
                if (image1.getY()>600) {
                    image1.clearActions();
                    image1.remove();
                }
            }
        }
    }

    public void setMovePanelColor(Color color) {
        this.moveColor = color;
    }
}
