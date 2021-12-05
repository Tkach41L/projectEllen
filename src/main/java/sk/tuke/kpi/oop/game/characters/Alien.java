package sk.tuke.kpi.oop.game.characters;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.When;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.behaviours.Behaviour;

import java.util.Objects;

public class Alien extends AbstractActor implements Movable, Enemy, Alive {
    private int speed;
    private Health health;
    private Behaviour<? super Alien> behaviour;

    public Alien() {
        this.speed = 2;
        setAnimation(new Animation("sprites/alien.png", 32, 32, 0.1f));
        this.health = new Health(45);
        this.health.onExhaustion(() ->
            Objects.requireNonNull(this.getScene()).removeActor(this)
        );
    }

    public Alien(int health, Behaviour<? super Alien> behaviour) {
        this.speed = 2;
        setAnimation(new Animation("sprites/alien.png", 32, 32, 0.1f));
        this.health = new Health(health);
        this.health.onExhaustion(() ->
            Objects.requireNonNull(this.getScene()).removeActor(this)
        );
        this.behaviour = behaviour;
    }

    @Override
    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        if(this.behaviour != null){
            this.behaviour.setUp(this);
        }
        for (Actor actor : Objects.requireNonNull(getScene()).getActors()) {
            if (actor instanceof Ripley) {
                Alive target = (Alive) actor;
                new Loop<>(
                    new When<>(
                        () -> this.intersects(target),
                        new Invoke<>(() -> target.getHealth().drain(1))
                    )
                ).scheduleFor(this);
            }
        }
    }

    @Override
    public Health getHealth() {
        return health;
    }

    @Override
    public void startedMoving(Direction direction) {
        if (direction == Direction.NONE) {
            return;
        }
        getAnimation().setRotation(direction.getAngle());
        getAnimation().play();
    }

    public Behaviour<? super Alien> getBehaviour() {
        return behaviour;
    }
}
