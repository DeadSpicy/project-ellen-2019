package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Light extends AbstractActor implements Switchable, EnergyConsumer {
    private boolean isOn;
    private boolean isPowered;
    private Animation shiningAnimaton;
    private Animation defaultAnimaton;


    public Light() {
        isPowered = false;
        isOn = false;
        shiningAnimaton = new Animation("sprites/light_on.png");
        defaultAnimaton = new Animation("sprites/light_off.png");
        updateAnimation();
    }
    @Override
    public void setPowered(boolean flow) {
        isPowered = flow;
        updateAnimation();
    }

    public void toggle() {
        isOn = !isOn;
        updateAnimation();
    }


    private void updateAnimation() {
        if (isPowered && isOn) {
            setAnimation(shiningAnimaton);
        }
        else {
            setAnimation(defaultAnimaton);
        }
    }

    @Override
    public void turnOn() {
        isOn = true;
    }

    @Override
    public void turnOff() {
        isOn = false;
    }

    @Override
    public boolean isOn() {
        return isOn;
    }
}
