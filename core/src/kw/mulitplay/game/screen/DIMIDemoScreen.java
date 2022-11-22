package kw.mulitplay.game.screen;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;

import javax.sound.midi.Instrument;
import javax.sound.midi.MidiUnavailableException;

import kw.mulitplay.game.group.PianoView;
import kw.mulitplay.game.midi.gamemode.AbstractModeController;
import kw.mulitplay.game.midi.gamemode.FluidModeController;
import kw.mulitplay.game.midi.handler.Channel;
import kw.mulitplay.game.midi.handler.GameModeUtils;
import kw.mulitplay.game.midi.handler.MidiInstruments;
import kw.mulitplay.game.midi.handler.MidiUtils;
import kw.mulitplay.game.midi.handler.Note;
import kw.mulitplay.game.midi.handler.Sheet;
import kw.mulitplay.game.screen.base.BaseScreen;
import kw.mulitplay.game.time.ActorTimeLine;

public class DIMIDemoScreen extends BaseScreen {
    private Array<Channel> channelArray;
    private PianoView view ;
    private int resolution;
    private float timer = 0;
    private Array<ActorTimeLine> disposeNode;
    private Array<ActorTimeLine> actorTimeLines;

    public DIMIDemoScreen(){
        channelArray = new Array<>();
        disposeNode = new Array<>();
        actorTimeLines = new Array<>();
    }

    @Override
    protected void initView() {
        view = new PianoView();
        view.showPianoKey();
        stage.addActor(view);
        try {
            MidiInstruments.getInstruments();
//            MidiInstruments.selectInstrument(instruments[4]);
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
        FileHandle source = new FileHandle("3.mid");
        AbstractModeController controller = new FluidModeController();
        try {
            GameModeUtils.setGameMode(controller);
            Sheet sheet = MidiUtils.getSheet(source.file());
            Channel[] channels = sheet.getChannels();
            resolution = sheet.getResolution();
            for (Channel channel : channels) {
                channelArray.add(channel);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (Channel channel : channelArray) {
            for (Note note : channel.getNotes()) {
//                stage.addAction(Actions.);
                actorTimeLines.add(new ActorTimeLine(note,view,resolution));
            }
        }
        for (ActorTimeLine actorTimeLine : actorTimeLines) {
            System.out.println(actorTimeLine);
        }
        ArrayList<String> strings = new ArrayList<>();
        for (int i = 1; i <= 88; i++) {
            strings.add(i+"");
        }
        for (String s : view.getHashMap().keySet()) {
            strings.remove(s+"");
        }

        System.out.println();
    }


    /**
     * 节点时间：
     *ka
     */
//    private static final long TEMPO = 120;
//    private static double computeTick(long tick, Sheet sheet) {
//        return tick * 60000.0 / sheet.getResolution() / TEMPO;
//    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void back() {

    }

    @Override
    public void render(float delta) {
        super.render(delta);
        timer +=delta;
        for (ActorTimeLine actorTimeLine : actorTimeLines) {
            if (actorTimeLine.getStatus()==2){
                disposeNode.add(actorTimeLine);
            }
            actorTimeLine.act(timer);
        }
        for (ActorTimeLine actorTimeLine : disposeNode) {
            actorTimeLines.removeValue(actorTimeLine,false);
            actorTimeLine.dispose();
        }
        disposeNode.clear();
    }
}
