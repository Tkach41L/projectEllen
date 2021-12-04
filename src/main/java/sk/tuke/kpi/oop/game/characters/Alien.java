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

import java.util.Objects;

public class Alien extends AbstractActor implements Movable, Enemy, Alive {
    private int speed;
    private Health health;

    public Alien() {
        this.speed = 2;
        setAnimation(new Animation("sprites/alien.png", 32, 32, 0.1f));
        this.health = new Health(40);
        this.health.onExhaustion(() ->
            Objects.requireNonNull(this.getScene()).removeActor(this)
        );
    }

    @Override
    public int getSpeed() {
        return speed;
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
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
}
