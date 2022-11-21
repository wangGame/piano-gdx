package kw.mulitplay.game.group;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class MusicLineActor extends Actor {
    private TextureRegion region;
    private byte[] fft;
    private float[] height;
    private float[] vex;
    private float[] vex2;
    private int count=41;
    private ShapeRenderer shapeRenderer;
    private Color color;
    private float width1=30;

    public MusicLineActor(TextureRegion region){

        this.region=new TextureRegion(region);
        color=new Color(0,0,0,1);
        //color=new Color(216/255f,116/255f,212/255f,0.5f);
        //color=new Color(192/255f,126/255f,253/255f,0.5f);

        fft = new byte[4098];
        height=new float[count];
        vex=new float[count*2];
        vex2=new float[count*2];
        shapeRenderer=new ShapeRenderer();
        setDebug(true);
    }


    public void setFft(byte[] ftt) {
        this.fft = ftt;
    }

    public void setColor(float r, float g, float b, float a){
        this.color.set(r,g,b,a);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    private int offset = 4;   //4

    float maxHeight = 60;
    private float speed = 20;
    private float downSpeed = 10;
    //+Math.abs( fft[1+j+1 + (i * offset)]))
    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if(fft == null) return;
        batch.end();
        Gdx.gl.glEnable(GL20.GL_BLEND);

//        System.out.println("---------------------");
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.setTransformMatrix(batch.getTransformMatrix());
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        //shapeRenderer.setColor(Color.WHITE);
        //shapeRenderer.setColor(43/255f,224f/255,1,0.5f);
        shapeRenderer.setColor(color);

        for(int i =0;i<count;i++){
            double accum = 0;
            for(int j = 0;j< offset-1;j+=2){
                double temp = Math.abs(fft[ 1 + j + (i * offset)])+Math.abs( fft[1+j+1 + (i * offset)]);
                float m = count/2;
                double temp2 = temp * (0.54f + 0.46f * Math.cos((i - m) * Math.PI / (m + 1)));
                accum += temp2;
            }
            accum = Math.sqrt(accum);
            accum/=2500F;
            if(accum<0){
                accum = 0;
            }
            accum = Math.min(accum,1);
            float newHeight = maxHeight * (float) accum;
            if(newHeight > height[i]+ speed){
                height[i] = height[i]+speed;
            }else if(newHeight < height[i]-downSpeed){
                height[i] = height[i] - downSpeed;
            }else{
                height[i] = newHeight;
            }
//            System.out.println(height[i]);
            vex[i*2]=getX()+width1*i+40;
            vex[2*i+1]=getY()+height[i]*700;
            vex2[i*2]=getX()+width1*i+40;
            vex2[2*i+1]=getY();
            shapeRenderer.line(vex[i*2],vex[2*i+1],vex2[i*2],vex2[i*2+1]);
//            patch.draw(batch,getX() ,getY()+ i* 22,getOriginX(),10,height[i],20,getScaleX(),getScaleY(),getRotation());
            //batch.draw(region,getX()+i*14,getY()+height[i]);

        }


        shapeRenderer.polyline(vex);
        shapeRenderer.polyline(vex2);
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
        batch.begin();
    }
}


