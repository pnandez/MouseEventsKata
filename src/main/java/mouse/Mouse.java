package mouse;

import org.apache.maven.surefire.shade.booter.org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

public class Mouse {
    private List<MouseEventListener> listeners = new ArrayList<>();
    private final long timeWindowInMillisecondsForDoubleClick = 500;
    private Boolean wasLeftButtonPressed = false;
    private Boolean isDoubleClick = false;
    private long millisLastLeftButtonEvent = Long.MAX_VALUE;


    public void pressLeftButton(long currentTimeInMilliseconds) {
        wasLeftButtonPressed = true;
        if(Math.abs(millisLastLeftButtonEvent - currentTimeInMilliseconds) < timeWindowInMillisecondsForDoubleClick){
            isDoubleClick = true;
        }
        millisLastLeftButtonEvent = currentTimeInMilliseconds;
    }

    public void releaseLeftButton(long currentTimeInMilliseconds) {
        if(isDoubleClick && wasLeftButtonPressed){
            wasLeftButtonPressed = false;
            isDoubleClick = false;
            notifySubscribers(MouseEventType.DoubleClick);
        }
        if(wasLeftButtonPressed){
            wasLeftButtonPressed = false;
            notifySubscribers(MouseEventType.SingleClick);
        }
    }

    public void move(MousePointerCoordinates from, MousePointerCoordinates to, long
            currentTimeInMilliseconds) {
        /*... implement this method ...*/
    }

    public void subscribe(MouseEventListener listener) {
        listeners.add(listener);
    }

    private void notifySubscribers(MouseEventType eventType) {
        listeners.forEach(listener -> listener.handleMouseEvent(eventType));
    }
}