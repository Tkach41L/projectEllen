package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.messages.Topic;

import java.util.Objects;

public class Ventilator extends AbstractActor implements Repairable {
    private boolean working;
    public static Topic<Ventilator> VENTILATOR_REPAIRED = Topic.create("VENTILATOR_REPAIRED", Ventilator.class);
    public Ventilator() {
        setAnimation(new Animation("sprites/ventilator.png", 32, 32, 0.1f));
        getAnimation().pause();
        this.working = false;
    }

    @Override
    public boolean repair() {
        if (working){
            return false;
        }
        working = true;
        getAnimation().play();
        Objects.requireNonNull(getScene()).getMessageBus().publish(VENTILATOR_REPAIRED,this);
        return true;
    }
}
