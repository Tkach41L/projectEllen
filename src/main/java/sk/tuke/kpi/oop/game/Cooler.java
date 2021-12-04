package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Cooler extends AbstractActor implements Switchable {
    private boolean on;
    private final Reactor reactor;

    public Cooler(Reactor reactor){
        this.reactor = reactor;
        this.on = true;
        setAnimation(new Animation("sprites/fan.png",32,32,0.2f));
    }

    private void coolReactor(){
        if(isOn() && this.reactor != null) {
                reactor.decreaseTemperature(1);
        }
    }
    public Reactor getReactor(){
        return this.reactor;
    }
    @Override
    public void turnOff(){
        this.on = false;
        getAnimation().pause();
    }
    @Override
    public void turnOn(){
        this.on = true;
        getAnimation().play();
    }
    public boolean isOn(){
        return this.on;
    }
    @Override
    public void addedToScene(@NotNull Scene scene){
        super.addedToScene(scene);
        if(this.reactor != null) {
            new Loop<>(new Invoke<Cooler>(this::coolReactor)).scheduleFor(this);
        }
    }
}
