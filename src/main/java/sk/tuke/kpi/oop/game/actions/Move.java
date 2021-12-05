package sk.tuke.kpi.oop.game.actions;

import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.actions.Action;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;

import java.util.Objects;

public class Move<A extends Movable> implements Action<A> {
    private boolean firstMove;
    private float duration;
    private A actor;
    private Direction direction;
    private boolean done = false;
    private float timePassed = 0;

    public Move(Direction direction, float duration) {
        this.direction = direction;
        this.duration = duration;
        this.firstMove = true;
    }

    public Move(Direction direction) {
        this.direction = direction;
        this.duration = 0;
        this.firstMove = true;
    }

    @Override
    public @Nullable A getActor() {
        return this.actor;
    }

    @Override
    public void setActor(@Nullable A actor) {
        this.actor = actor;
    }

    @Override
    public boolean isDone() {
        return done;
    }

    public void stop() {
        this.done = true;
        if (this.actor == null) {
            return;
        }
        this.actor.stoppedMoving();
    }

    @Override
    public void execute(float deltaTime) {
        if (actor == null || done) {
            return;
        }
        timePassed += deltaTime;
        if (firstMove) {
            actor.startedMoving(direction);
            firstMove = false;
        }
        int oldPosX = actor.getPosX();
        int oldPosY = actor.getPosY();
        int newPosX = actor.getPosX() + direction.getDx() * actor.getSpeed();
        int newPosY = actor.getPosY() + direction.getDy() * actor.getSpeed();

        actor.setPosition(newPosX, newPosY);
        if (Objects.requireNonNull(actor.getScene()).getMap().intersectsWithWall(actor)) {
            actor.setPosition(oldPosX, oldPosY);
            actor.collidedWithWall();
        }
        if (timePassed >= duration) {
            done = true;
            actor.stoppedMoving();
        }
    }

    @Override
    public void reset() {
        this.done = false;
        this.timePassed = 0;
        this.firstMove = true;
    }


}

