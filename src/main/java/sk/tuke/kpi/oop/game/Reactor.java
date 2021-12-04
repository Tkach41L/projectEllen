package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.actions.PerpetualReactorHeating;

import javax.swing.table.TableRowSorter;
import java.util.HashSet;
import java.util.Set;

public class Reactor extends AbstractActor implements Switchable, Repairable {
    private Light light;
    private int temperature;
    private int damage;
    private boolean working;
    private Set<EnergyConsumer> devices;
    private final Animation extinguished = new Animation("sprites/reactor_extinguished.png");
    private final Animation reactorOff;
    private final Animation normalAnimation = new Animation("sprites/reactor_on.png", 80, 80, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
    private final Animation damagedAnimation = new Animation("sprites/reactor_hot.png", 80, 80, 0.05f, Animation.PlayMode.LOOP_PINGPONG);
    private final Animation brokenAnimation = new Animation("sprites/reactor_broken.png", 80, 80, 0.1f, Animation.PlayMode.LOOP_PINGPONG);

    public Reactor() {
        this.devices = new HashSet<>();
        this.working = false;
        this.reactorOff = new Animation("sprites/reactor.png");
        this.temperature = 0;
        this.damage = 0;
        setAnimation(reactorOff);
    }

    public int getTemperature() {
        return this.temperature;
    }

    public int getDamage() {
        return this.damage;
    }

    public void increaseTemperature(int increment) {
        if (increment < 0 || !this.working) {
            return;
        }
        if (damage >= 33 && damage <= 66) {
            temperature += increment * 1.5;
        }
        else if (damage > 66) {
            temperature += increment * 2;
        }
        else {
            temperature += increment;
        }

        if (temperature >= 6000) {
            damage = 100;
            setAnimation(brokenAnimation);
        }
        else if (temperature > 2000) {
            damage = (temperature - 2000) / 40;
        }

        updateAnimation();
    }

    public void decreaseTemperature(int decrement) {
        if (decrement <= 0 || !this.working) {
            return;
        }
        if (this.temperature <= 0) {
            this.temperature = 0;
        }
        if (damage < 100) {
            if (damage >= 50) {
                temperature = (int) Math.floor(temperature -= (decrement / 2));
            }
            if (damage < 50) {
                temperature -= decrement;
            }
        }
        updateAnimation();
    }

    private void updateAnimation() {
        if (damage >= 100 || temperature >= 6000) {
            damage = 100;
            turnOff();
            setAnimation(brokenAnimation);
        }
        else if (this.working) {
            setAnimation(reactorOff);
        }
        else if (temperature >= 4000) {
            setAnimation(damagedAnimation);
        }
        else if (temperature >= 0) {
            setAnimation(normalAnimation);
        }
    }

    public void turnOn() {
        this.working = true;
        updatePowered();
        updateAnimation();
    }

    public void turnOff() {
        this.working = false;
        updatePowered();
        setAnimation(reactorOff);
    }

    private void updatePowered(){
        for (EnergyConsumer energyConsumer : devices) {
            energyConsumer.setPowered(this.working);
        }
    }

    public boolean isOn() {
        return this.working;
    }

    public void addLight(Light light) {
        if (light == null) {
            return;
        }
        this.light = light;
        light.setElectricityFlow(this.working);
    }

    public void removeLight() {
        light.setElectricityFlow(false);
        this.light = null;
    }

    public boolean extinguish() {
        if (damage >= 100 || temperature > 4000) {
            this.temperature = 4000;
            setAnimation(extinguished);
        }
        return damage >= 100 || temperature > 4000;
    }

    @Override
    public boolean repair() {
        if (damage == 0 || damage == 100) {
            return false;
        }
        this.damage = Math.max(0, damage - 50);
        temperature = this.damage * 40 + 2000;
        updateAnimation();
        return true;
    }

    public void addDevice(EnergyConsumer device) {
        if (device == null) {
            return;
        }
        this.devices.add(device);
        device.setPowered(damage != 100 || isOn());
    }

    public void removeDevice(EnergyConsumer device) {
        if (device == null) {
            return;
        }
        this.devices.remove(device);
        device.setPowered(false);
    }

    @Override
    public void addedToScene(@NotNull Scene reactorScene) {
        super.addedToScene(reactorScene);
        new PerpetualReactorHeating(1).scheduleFor(this);
    }

    public Light getLight() {
        return light;
    }
}
