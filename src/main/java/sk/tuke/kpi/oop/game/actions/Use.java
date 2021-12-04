package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Disposable;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.items.Usable;

public class Use<A extends Actor> extends AbstractAction<A> {
    private Usable<A> usable;

    public Use(Usable<A> usable){
        this.usable = usable;
    }

    @Override
    public void execute(float deltaTime) {
        A actor = getActor();
        this.usable.useWith(actor);
        setDone(true);
    }

    public Disposable scheduleForIntersectingWith(Actor mediatingActor) {
        Scene scene = mediatingActor.getScene();
        if (scene == null) {
            return null;
        }

        Class<A> usingActorClass = usable.getUsingActorClass();

        for (Actor actor : scene) {
            if (usingActorClass.isInstance(actor) && mediatingActor.intersects(actor)) {
                return this.scheduleFor(usingActorClass.cast(actor));
            }
        }
        return null;
    }

}
