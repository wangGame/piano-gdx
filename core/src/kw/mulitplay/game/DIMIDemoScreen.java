package kw.mulitplay.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;

import javax.sound.midi.Instrument;
import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiUnavailableException;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import kw.mulitplay.game.data.AutoManager;
import kw.mulitplay.game.group.PianoKey;
import kw.mulitplay.game.group.PianoView;
import kw.mulitplay.game.midi.gamemode.AbstractModeController;
import kw.mulitplay.game.midi.gamemode.FluidModeController;
import kw.mulitplay.game.midi.handler.Channel;
import kw.mulitplay.game.midi.handler.GameModeUtils;
import kw.mulitplay.game.midi.handler.MidiDevices;
import kw.mulitplay.game.midi.handler.MidiInstruments;
import kw.mulitplay.game.midi.handler.MidiUtils;
import kw.mulitplay.game.midi.handler.Note;
import kw.mulitplay.game.midi.handler.Sheet;
import kw.mulitplay.game.midi.handler.SheetUtils;
import kw.mulitplay.game.screen.base.BaseScreen;
import kw.mulitplay.game.time.ActorTimeLine;

public class DIMIDemoScreen extends BaseScreen {
    private Array<Channel> array = new Array<>();
    private Image image;
    private Image image1;
    private PianoView view ;
    private ActorTimeLine timeLine;
    private int resolution;
    private Array<ActorTimeLine> actorTimeLines;
    @Override
    protected void initView() {
        actorTimeLines = new Array<>();
        view = new PianoView();
        view.showPianoKey();
        stage.addActor(view);
        try {
            MidiInstruments.getInstruments();
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
        final ArrayList<MidiDevice> devices = MidiDevices.getDevices();
            devices.stream().map((MidiDevice device) -> {
                RadioMenuItem item = new RadioMenuItem(device.getDeviceInfo().getName());
                item.setOnAction((ActionEvent t) -> {
                    try {
                        MidiDevices.selectDevice(device);
                        MidiDevices.setGameController(GameModeUtils.getGameMode());
                    } catch (MidiUnavailableException ex) {
                        final Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText(ex.getLocalizedMessage());
                        alert.showAndWait();
                    }
                });
                return item;
            }).forEachOrdered((item) -> {
            });

        FileHandle source = new FileHandle("2.mid");
        AbstractModeController controller = new FluidModeController();
        try {
            GameModeUtils.setGameMode(controller);
            SheetUtils.musicSheet = MidiUtils.getSheet(source.file());
            Sheet musicSheet = SheetUtils.musicSheet;
            resolution = musicSheet.getResolution();
            Channel[] channels = SheetUtils.musicSheet.getChannels();
            for (Channel channel : channels) {
                array.add(channel);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        for (Channel channel : array) {
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

    float tiem = 0;
    int index = 0;
    private float tiem1;
    private int index11;

    private float target;
    private float target1;

    private float timsss = 0;
    Array<ActorTimeLine> dispose = new Array<>();
    @Override
    public void render(float delta) {
        super.render(delta);
        timsss+=delta;
        for (ActorTimeLine actorTimeLine : actorTimeLines) {
            if (actorTimeLine.getStatus()==2){
                dispose.add(actorTimeLine);
            }
            actorTimeLine.act(timsss);
        }


//        for (ActorTimeLine actorTimeLine : dispose) {
//            actorTimeLines.removeValue(actorTimeLine,false);
//            actorTimeLine.dispose();
//        }

        dispose.clear();
//        tiem1 += delta;
//        if (tiem1 >target){
//            Channel channel = array.get(0);
//                Note[] notes = channel.getNotes();
//                if (index11 < notes.length) {
////                MidiInstruments.noteOn(notes[index].getKey());
//                    PianoKey pianoKey = view.getHashMap().get("" + (notes[index11].getKey() - 20));
//                    if (pianoKey.isTouched()) {
//                        pianoKey.finishTouchi();
//                    }
////                    13693182282
//                    pianoKey.clearActions();
//                    pianoKey.touchDownKey();
//                    image = new Image(new Texture("main/2.png"));
//                    stage.addActor(image);
//                    long length = notes[index11].getLength();
//                    image.setHeight(notes[index11].getLength() / 5.0f);
//                    image.addAction(Actions.forever(Actions.moveBy(0, 50, 0.3F)));
//                    quxiao(pianoKey, notes[index11].getLength());
//                    image = null;
//                    target = length / 250.0f;
//                }
//
//            index11++;
//            tiem1 = 0;
//        }
//
//
//
//        tiem += delta;
//        if (tiem >target1){
//            for (Channel channel : array) {
//                Note[] notes = channel.getNotes();
////                MidiInstruments.noteOn(notes[index].getKey());
//                if (index <notes.length) {
////
//                    PianoKey pianoKey = view.getHashMap().get("" + (notes[index].getKey() - 20));
//                    if (pianoKey.isTouched()) {
//                        pianoKey.finishTouchi();
//                    }
//                    pianoKey.clearActions();
//                    pianoKey.touchDownKey();
//                    image1 = new Image(new Texture("main/2.png"));
//                    image1.setX(200);
//                    image1.setHeight(notes[index].getLength() / 5);
//                    stage.addActor(image1);
//                    image1.addAction(Actions.forever(Actions.moveBy(0, 50, 0.3F)));
//                    quxiao(pianoKey, notes[index].getLength());
//                    image1 = null;
//                    long length = notes[index].getLength();
//
//                    target1 = length / 250;
//                }
////                image = null;
////                double show_time = computeTick(i.getTimeStamp());
////                double start_time = show_time;
////                double end_time = computeTick(i.getTimeStamp() + i.getLength());
//            }
//            tiem = 0;
//            index++;
//        }
    }

    private void quxiao(final PianoKey pianoKey,final float time) {
        pianoKey.addAction(Actions.delay(time/1000.0f,Actions.run(()->{
            pianoKey.finishTouchi();
        })));
    }
}
