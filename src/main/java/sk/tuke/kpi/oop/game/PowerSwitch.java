package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class PowerSwitch extends AbstractActor {
    private Switchable switchable;

    public PowerSwitch(Switchable switchable) {
        this.switchable = switchable;
        setAnimation(new Animation("sprites/switch.png", 16, 16));
    }

    public void toggle() {
        if (switchable.isOn()) {
            switchable.turnOff();
        }
        else {
            switchable.turnOn();
        }
    }
}
