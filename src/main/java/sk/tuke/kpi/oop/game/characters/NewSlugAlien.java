package sk.tuke.kpi.oop.game.characters;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.When;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.behaviours.Behaviour;

import java.util.Objects;

public class NewSlugAlien extends Alien{

    public NewSlugAlien(Behaviour<? super Alien> behaviour) {
        super(20, behaviour);
        setAnimation(new Animation("sprites/monster1.png", 16, 32, 0.1f));
        super.setSpeed(1);
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        if(super.getBehaviour() != null){
            this.getBehaviour().setUp(this);
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
}
