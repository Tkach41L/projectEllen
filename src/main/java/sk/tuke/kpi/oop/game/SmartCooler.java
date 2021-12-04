package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.actions.Loop;

public class SmartCooler extends Cooler{
    public SmartCooler(Reactor reactor) {
        super(reactor);
        super.turnOff();
    }

    public void coolReactor(){
        Reactor reactor = getReactor();
        if (reactor == null) {
            return;
        }
        if (this.isOn()) {
            reactor.decreaseTemperature(1);
        }
    }
    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        new Loop<>(new Invoke<>(this::coolReactor)).scheduleFor(this);
    }
}
