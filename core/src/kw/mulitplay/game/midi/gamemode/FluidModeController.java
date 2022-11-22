/*
 * Copyright (C) 2020 david
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package kw.mulitplay.game.midi.gamemode;

import java.util.Arrays;
import kw.mulitplay.game.midi.handler.MidiInstruments;
import kw.mulitplay.game.midi.handler.Note;

/**
 *
 * @author david
 */
public class FluidModeController extends AbstractModeController {

    private static final int NOTE_OFF = -1;
    private static final int NOTE_RANGE = 200;

    private final double[] note_start;
    private final double[] note_end;

    public FluidModeController() {
        super();
        note_start = new double[NOTE_RANGE];
        note_end = new double[NOTE_RANGE];
        Arrays.fill(note_start, NOTE_OFF);
    }

    @Override
    public void onUserPress(int key) {
        if (note_start[key] != NOTE_OFF) {
            note_start[key] = NOTE_OFF;
        }
//        KeyboardRenderer.pressKey(key);
    }

    @Override
    public void onUserRelease(int key) {
//        KeyboardRenderer.releaseKey(key);
    }

    @Override
    public long getScore() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
