package main.common.line;

import main.common.stop.StopGetter;
import main.common.dataTypes.*;
import java.util.List;
import java.util.HashMap;

public class Lines {

    private final LineFactory lineFactory;
    private final StopGetter stops;
    private final HashMap<LineName, Line> lines = new HashMap<>();

    public Lines(LineFactory lineFactory, StopGetter stops) {
        this.lineFactory = lineFactory;
        this.stops = stops;
    }

    public Time updateReachable(List<LineName> lineNames, StopName stop, Time time) {
        int lowestStartTime = Integer.MAX_VALUE;
        for(LineName x : lineNames){
            if(!lines.containsKey(x)) {
                lines.put(x, lineFactory.createLine(x, stops));
                lowestStartTime = Math.min(lowestStartTime, lines.get(x).updateReachable(stop, time));
            }
        }
        if(lowestStartTime == Integer.MAX_VALUE) return new Time(-1);
        else return new Time(lowestStartTime);
    }

    public StopName updateCapacityAndGetPreviousStop(LineName lineName, StopName stopName, Time time){
        return lines.get(lineName).updateCapacityAndGetPreviousStop(stopName, time);
    }

    public void clean() {
        lines.clear();
    }
}