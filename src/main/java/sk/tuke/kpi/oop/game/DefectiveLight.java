package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Disposable;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.actions.Loop;

public class DefectiveLight extends Light implements Repairable {
    private Disposable loop;

    private void toggleRandom() {
        int value = (int) (Math.random() * ((20 - 1) + 1)) + 1;
        if (value == 2) {
            toggle();
        }
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        loop = new Loop<>(new Invoke<>(this::toggleRandom)).scheduleFor(this);
    }

    private void refreshLoop() {
        loop = new Loop<>(new Invoke<>(this::toggleRandom)).scheduleFor(this);
    }

    @Override
    public boolean repair() {
        turnOn();
        loop.dispose();
        new ActionSequence<>(
            new Wait<>(10),
            new Invoke<>(this::refreshLoop)
        ).scheduleFor(this);
        return true;
    }
}
