package kw.mulitplay.game.time;

import kw.mulitplay.game.group.PianoView;
import kw.mulitplay.game.midi.handler.MidiInstruments;
import kw.mulitplay.game.midi.handler.Note;

public class ActorTimeLine {
    private float startTime;
    private Note note;
    private float endTime;
    private int status;
    private float deley;
    private PianoView view;

    public ActorTimeLine(Note note, PianoView view, int resolution){
        this.note = note;
        this.startTime = note.getTimeStamp() * 60.0f / resolution / 120.0f*2;
        this.endTime = (note.getTimeStamp()+note.getLength()/3.0f)*60.0f / resolution / 120.0f*2;

        this.view = view;
    }

    public void act(float timeline){
        if (status == 0) {
            if (startTime <= timeline && endTime >= timeline) {
                System.out.println(endTime - startTime);
                view.getHashMap().get((note.getKey())+"").touchDownKey();

//                MidiInstruments.noteOn(note.getKey());
                status = 1;
            }
        }else {
//            note.getLength())*60.0f / resolution / 120.0f*2
            if (endTime <= timeline) {
                view.getHashMap().get((note.getKey())+"").finishTouchi();
                status = 2;
            }
        }
    }

    public int getStatus() {
        return status;
    }

    public void dispose() {
        view = null;
        note = null;
    }

    @Override
    public String toString() {
        return "ActorTimeLine{" +
                "startTime=" + startTime +
                ", endTime=" + endTime +
                ", status=" + status +
                '}';
    }
}
