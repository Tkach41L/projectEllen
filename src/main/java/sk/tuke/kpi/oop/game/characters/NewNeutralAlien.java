package sk.tuke.kpi.oop.game.characters;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.When;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Movable;

public class NewNeutralAlien extends AbstractActor implements Movable,Alive {
    private int speed;
    private Health health;
    private Animation neutralAlienAnimation = new Animation("sprites/spitter_alien.png",32,32,0.1f);
    public NewNeutralAlien(){
        this.speed = 3;
        health = new Health(150);
        setAnimation(neutralAlienAnimation);
        neutralAlienAnimation.play();
        this.health.onExhaustion(() -> {
            this.getScene().removeActor(this);
        });
    }
    @Override
    public Health getHealth() {
        return health;
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
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
