package kw.mulitplay.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
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
import kw.mulitplay.game.midi.gamemode.AbstractModeController;
import kw.mulitplay.game.midi.gamemode.FluidModeController;
import kw.mulitplay.game.midi.handler.Channel;
import kw.mulitplay.game.midi.handler.GameModeUtils;
import kw.mulitplay.game.midi.handler.MidiDevices;
import kw.mulitplay.game.midi.handler.MidiInstruments;
import kw.mulitplay.game.midi.handler.MidiUtils;
import kw.mulitplay.game.midi.handler.Note;
import kw.mulitplay.game.midi.handler.SheetUtils;
import kw.mulitplay.game.screen.base.BaseScreen;

public class DIMIDemoScreen extends BaseScreen {
    Array<Note> array = new Array<>();
    @Override
    protected void initView() {

        try {
            Instrument[] instruments = MidiInstruments.getInstruments();
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

        FileHandle source = new FileHandle("1.mid");
        AbstractModeController controller = new FluidModeController();
        try {
            GameModeUtils.setGameMode(controller);
            SheetUtils.musicSheet = MidiUtils.getSheet(source.file());
            Channel[] channels = SheetUtils.musicSheet.getChannels();
            for (Channel channel : channels) {
                for (Note note : channel.getNotes()) {
                    array.add(note);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    float tiem = 0;
    int index = 0;

    @Override
    public void render(float delta) {
        super.render(delta);
        tiem += delta;
        if (tiem >0.5){
            tiem = 0;
            MidiInstruments.noteOn(array.get(index).getKey());
            index++;
        }


    }
}
