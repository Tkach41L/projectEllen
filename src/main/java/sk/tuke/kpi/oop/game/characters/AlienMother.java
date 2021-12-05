package sk.tuke.kpi.oop.game.characters;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.When;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.behaviours.RandomlyMoving;

import java.util.Objects;

public class AlienMother extends AbstractActor implements Movable, Alive {
    private int speed;
    private Health health;

    public AlienMother() {
        this.speed = 3;
        health = new Health(160);
        setAnimation(new Animation("sprites/mother.png", 112, 162, 0.1f));
        getAnimation().play();
        this.health.onExhaustion(() -> Objects.requireNonNull(this.getScene()).removeActor(this));
    }

    @Override
    public Health getHealth() {
        return health;
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
//        new RandomlyMoving().setUp(this);
        scene.getActors().forEach(
            actor -> {
                if (actor instanceof Alive && !actor.equals(this)) {
                    Alive aliveActor = (Alive) actor;
                    new Loop<>(
                        new When<>(
                            () -> actor.intersects(this),
                            new Invoke<>(() -> aliveActor.getHealth().drain(3))
                        )
                    ).scheduleFor(this);
                }
            }
        );
    }

    @Override
    public int getSpeed() {
        return speed;
    }
}
