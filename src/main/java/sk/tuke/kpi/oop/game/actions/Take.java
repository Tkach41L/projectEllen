package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.Actor;

import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.items.Collectible;

import java.util.Objects;

public class Take<A extends Keeper> extends AbstractAction<A> {
    public Take() {
    }

    @Override
    public void execute(float deltaTime) {
        this.setDone(true);
        Keeper keeper = getActor();
        if (keeper == null || keeper.getBackpack().getCapacity() <= keeper.getBackpack().getSize()) {
            return;
        }
        for (Actor target : (Objects.requireNonNull(keeper.getScene())).getActors()) {
            if (keeper.intersects(target) && keeper != target && target instanceof Collectible) {
                keeper.getBackpack().add((Collectible) target);
                (Objects.requireNonNull(target.getScene())).removeActor(target);
                return;
            }
        }
    }
}

