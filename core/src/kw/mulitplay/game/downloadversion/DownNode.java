package kw.mulitplay.game.downloadversion;

import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import kw.mulitplay.game.constant.Constant;

public class DownNode extends Image{
    private float baseNodeX;
    private float baseNodeY;
    private float length;
    private String key;
    private int status;
    private float times;
    public DownNode(NinePatch ninePatch){
        super(ninePatch);
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setBaseNodeX(float x) {
        this.baseNodeX = x;
    }

    public void setBaseNodeY(float y) {
        this.baseNodeY = y;
    }

    public void setLength(float length) {
        this.length = length;
    }

    public float getBaseNodeX() {
        return baseNodeX;
    }

    public float getBaseNodeY() {
        return baseNodeY;
    }

    public float getLength() {
        return length;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        float y = getY();
        setY(y - Constant.panelMoveSpeed/0.2f*delta);
        baseNodeY = getY();
        if (status == 0){
            if (baseNodeY<=0){
                status = 1;
            }
        }
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setTime(float times) {
        this.times = times;
    }

    public float getTimes() {
        return times;
    }
}
