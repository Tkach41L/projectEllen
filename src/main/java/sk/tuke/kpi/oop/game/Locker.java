package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

import sk.tuke.kpi.oop.game.items.Usable;

public class Locker extends AbstractActor implements Usable<Actor> {
    public Locker(){
        Animation lockerAnimation = new Animation("sprites/locker.png", 16, 16);
        setAnimation(lockerAnimation);
    }
    @Override
    public void useWith(Actor actor) {

    }

    @Override
    public Class<Actor> getUsingActorClass() {
        return Actor.class;
    }
}
