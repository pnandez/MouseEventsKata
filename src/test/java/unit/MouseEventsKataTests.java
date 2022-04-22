package unit;

import mouse.Mouse;
import mouse.MouseEventListener;
import mouse.MouseEventType;
import mouse.MousePointerCoordinates;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


// TODO:
//  single click
//     no click, several clicks after a long time, multiples clicks without release?
// double click
//     clicks, click + move + click != double click
// triple click
//     click + move + click != double click
// drag
//       move without press
// drop
//       no move

class Listener implements MouseEventListener{

    public List<MouseEventType> events = new ArrayList<>();


    @Override
    public void handleMouseEvent(MouseEventType eventType) {
        events.add(eventType);
    }
}

public class MouseEventsKataTests {

    private void simulateOneSingleClick(Mouse m){
        m.pressLeftButton(System.currentTimeMillis());
        m.releaseLeftButton(System.currentTimeMillis());
    }

    private void simulateTwoSingleClicks(Mouse m){
        simulateOneSingleClick(m);
        try {
            Thread.sleep(501);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        simulateOneSingleClick(m);
    }

    private void simulateDoubleClick(Mouse m) {
        simulateOneSingleClick(m);
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        simulateOneSingleClick(m);
    }



    @Test
    public void should_detect_a_single_click(){
        Mouse mouse = new Mouse();
        Listener listener = new Listener();
        mouse.subscribe(listener);

        simulateOneSingleClick(mouse);

        assertThat(listener.events.get(0)).isEqualTo(MouseEventType.SingleClick);
    }

    @Test
    public void should_detect_two_single_clicks(){
        Mouse mouse = new Mouse();
        Listener listener = new Listener();
        mouse.subscribe(listener);
        simulateTwoSingleClicks(mouse);

        assertThat(listener.events.get(0)).isEqualTo(MouseEventType.SingleClick);
        assertThat(listener.events.get(1)).isEqualTo(MouseEventType.SingleClick);
    }

    @Test
    public void should_detect_double_click(){
        Mouse mouse = new Mouse();
        Listener listener = new Listener();
        mouse.subscribe(listener);

        simulateDoubleClick(mouse);

        assertThat(listener.events.get(listener.events.size()-1)).isEqualTo(MouseEventType.DoubleClick);
    }



}
