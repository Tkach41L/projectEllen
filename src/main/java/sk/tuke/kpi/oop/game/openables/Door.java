package sk.tuke.kpi.oop.game.openables;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.messages.Topic;
import sk.tuke.kpi.oop.game.items.Usable;

import java.util.Objects;

import static sk.tuke.kpi.gamelib.graphics.Animation.PlayMode.ONCE;

public class Door extends AbstractActor implements Usable<Actor>, Openable {
    public static Topic<Door> DOOR_OPENED = Topic.create("door opened", Door.class);
    public static Topic<Door> DOOR_CLOSED = Topic.create("door closed", Door.class);
    private boolean opened;

    public enum Orientation {
        HORIZONTAL,
        VERTICAL
    }

    public Door(String name, Orientation orientation) {
        super(name);
        this.opened = false;
        if (orientation == Orientation.VERTICAL) {
            setAnimation(new Animation("sprites/vdoor.png", 16, 32, 0.1f, ONCE));
        }
        if (orientation == Orientation.HORIZONTAL) {
            setAnimation(new Animation("sprites/hdoor.png", 16, 32, 0.1f, ONCE));
        }
        getAnimation().pause();
    }

    @Override
    public void open() {
        if (opened) {
            return;
        }
        opened = true;
        getAnimation().play();
        Objects.requireNonNull(this.getScene()).getMessageBus().publish(DOOR_OPENED, this);
    }

    @Override
    public void close() {
        if(!opened){
            return;
        }
        opened = false;
        getAnimation().stop();
        Objects.requireNonNull(this.getScene()).getMessageBus().publish(DOOR_CLOSED, this);
    }

    @Override
    public boolean isOpen() {
        return this.opened;
    }


    @Override
    public void useWith(Actor actor) {
        if (opened) {
            close();
        }
        else {
            open();
        }
    }

    @Override
    public Class<Actor> getUsingActorClass() {
        return Actor.class;
    }
}
