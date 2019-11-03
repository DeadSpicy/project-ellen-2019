package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Cooler extends AbstractActor implements Switchable {
    private Reactor reactor;
    private boolean on;

    public Cooler(Reactor reactor) {
        on = false;
        setAnimation(new Animation("sprites/fan.png", 32, 32, 0.2f));
        getAnimation().pause();
        this.reactor = reactor;
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        new Loop<>(new Invoke<>(this::coolReactor)).scheduleFor(this);
    }

    private void coolReactor() {
        if (on) {
            reactor.decreaseTemperature(1);
        }
    }

    @Override
    public void turnOn() {
        on = true;
        getAnimation().play();
    }

    @Override
    public void turnOff() {
        on = false;
        getAnimation().pause();
    }

    @Override
    public boolean isOn() {
        return on;
    }
}
