package kw.mulitplay.game.midi;

import com.badlogic.gdx.files.FileHandle;

import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

import kw.mulitplay.game.midi.gamemode.AbstractModeController;
import kw.mulitplay.game.midi.gamemode.FluidModeController;
import kw.mulitplay.game.midi.handler.GameModeUtils;
import kw.mulitplay.game.midi.handler.MidiUtils;
import kw.mulitplay.game.midi.handler.SheetUtils;

public class App {
    public static void main(String[] args) throws MidiUnavailableException, InvalidMidiDataException, IOException {
        FileHandle source = new FileHandle("");
        AbstractModeController controller = new FluidModeController();
        GameModeUtils.setGameMode(controller);
        SheetUtils.musicSheet = MidiUtils.getSheet(source.file());

    }
}
