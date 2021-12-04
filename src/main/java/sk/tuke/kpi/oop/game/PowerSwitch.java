package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.graphics.Color;

public class PowerSwitch extends AbstractActor implements Switchable {
    private Switchable device;

    public PowerSwitch(Switchable device) {
        this.device = device;
        Animation switchAnimation = new Animation("sprites/switch.png");
        setAnimation(switchAnimation);
    }

    public Switchable getDevice() {
        return this.device;
    }

    public void turnOn() {
        if (device == null) {
            return;
        }
        device.turnOn();
        getAnimation().setTint(Color.WHITE);
    }

    public void turnOff() {
        if (device == null) {
            return;
        }
        device.turnOff();
        getAnimation().setTint(Color.GRAY);
    }

    @Override
    public boolean isOn() {
        return device.isOn();
    }
}
