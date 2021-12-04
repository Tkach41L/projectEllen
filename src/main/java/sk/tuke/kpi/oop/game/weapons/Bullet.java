package sk.tuke.kpi.oop.game.weapons;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.characters.Alive;
import sk.tuke.kpi.oop.game.characters.Ripley;

public class Bullet extends AbstractActor implements Fireable {
    private int speed;

    public Bullet() {
        this.speed = 4;
        setAnimation(new Animation("sprites/bullet.png", 16, 16));
    }

    @Override
    public int getSpeed() {
        return speed;
    }

    @Override
    public void startedMoving(Direction direction) {
        getAnimation().setRotation(direction.getAngle());
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        new Loop<>(new Invoke<>(() -> {
            for (Actor actor : scene.getActors()) {
                if (actor instanceof Alive && this.intersects(actor) && !(actor instanceof Ripley)) {
                    ((Alive) actor).getHealth().drain(20);
                    scene.removeActor(this);
                }
            }
        }
        )).scheduleFor(this);
    }

    @Override
    public void collidedWithWall() {
        if (getScene() != null) {
            this.getScene().removeActor(this);
        }
    }

}
