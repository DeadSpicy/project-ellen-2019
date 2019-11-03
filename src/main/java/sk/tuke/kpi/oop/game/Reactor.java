package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.actions.PerpetualReactorHeating;
import sk.tuke.kpi.oop.game.tools.FireExtinguisher;
import sk.tuke.kpi.oop.game.tools.Hammer;

import java.util.HashSet;
import java.util.Set;

public class Reactor extends AbstractActor implements Switchable, Repairable {
    private int temperature;
    private int damage;
    private Animation normalAnimation;
    private Animation overheadAnimation;
    private Animation brokenAnimation;
    private Animation offAnimation;

    private EnergyConsumer device;

    private Set<EnergyConsumer> devices;

    private boolean working;

    public Reactor() {
        super("Reactor");
        devices = new HashSet<>();
        working = false;
        temperature = 0;
        damage = 0;
        normalAnimation = new Animation("sprites/reactor_on.png", 80, 80, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        overheadAnimation = new Animation("sprites/reactor_hot.png", 80, 80, 0.05f, Animation.PlayMode.LOOP_PINGPONG);
        brokenAnimation = new Animation("sprites/reactor_broken.png", 80, 80, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        offAnimation = new Animation("sprites/reactor.png");

        setAnimation(offAnimation);
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        new PerpetualReactorHeating(1).scheduleFor(this);

    }

    public void addDevice(EnergyConsumer device) {
        this.device = device;
        devices.add(device);
    }

    public void removeDevice(EnergyConsumer device) {
        devices.remove(device);
    }

    private void setDevicesState() {
        for (var device :
            devices) {
            device.setPowered(working && damage < 100);
        }
    }

    public void turnOn() {
        working = true;
        setDevicesState();
        updateAnimation();
    }

    public void turnOff() {
        working = false;
        setDevicesState();
        updateAnimation();
    }

    public int getTemperature() {
        return temperature;
    }

    public boolean isOn() {
        return working;
    }

    public int getDamage() {
        return damage;
    }

    private void updateAnimation() {
        if (temperature >= 6000) {
            setAnimation(brokenAnimation);
            return;
        }
        if (working) {
            if (temperature < 4000) {
                setAnimation(normalAnimation);
            }
            else if (temperature < 6000) {
//            следующая строчка убивает прогу но мб это только на маке так так что лучше затести
//            overheadAnimation.setFrameDuration((float)(200/temperature)); //так должно быть

//            абсолютно неграмотно и ненаучно
                overheadAnimation = new Animation("sprites/reactor_hot.png", 80, 80, (float) 200 / temperature, Animation.PlayMode.LOOP_PINGPONG);
                setAnimation(overheadAnimation);
            }
        }
        else {
            setAnimation(offAnimation);
        }
    }

    public void increaseTemperature(int increment) {
        //f(2000) = 0
        //f(6000) = 100
        //f = (x/40) - 50
        if (working) {

            if (damage >= 100 || increment < 0) {
                working = false;
                updateAnimation();
                setDevicesState();
                return;
            }
            if (damage < 33) {
                temperature += increment;
            }
            else if (damage <= 66) {
                temperature += Math.round(1.5 * increment);
            }
            else {
                temperature += 2 * increment;
            }
            if (temperature > 2000) {
                damage = temperature / 40 - 50;
            }
            setDevicesState();
            updateAnimation();
        }
    }

    public EnergyConsumer getDevice() {
        return device;
    }

    public void decreaseTemperature(int decrement) {
        if (working) {
            if (damage >= 100 || decrement < 0) {
                return;
            }
            if (damage >= 50) {
                temperature -= Math.floor(decrement / 2);
            }
            else {
                temperature -= decrement;
            }
            updateAnimation();
        }
    }

    public void extinguish() {
        if (temperature > 4000) {
            temperature = 4000;
        }
        setAnimation(new Animation("sprites/reactor_extinguished.png"));

    }

    @Override
    public boolean repair() {
             /*
        temperature: 2000, damage: 0
        temperature: 6000, damage: 100
        temperature = 2000 + 40 * damage
        */
        if (damage >= 100) {
            return false;
        }
        damage -= 55;
        int newTemperature = 2000 + 40 * damage;
        if (damage < 0) damage = 0;
        if (newTemperature < temperature) {
            temperature = newTemperature;
        }
        updateAnimation();
        return true;
    }
}
