package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.Actor;

public abstract class BreakableTool<A extends Actor> extends AbstractActor implements Usable<A> {
    private int remainingUses;

    public BreakableTool(int remainingUses) {

        this.remainingUses = remainingUses;
    }

    @Override
    public void useWith(A hammer) {
        if (getRemainingUses() <= 0) {
            return;
        }
        this.remainingUses -= 1;
        if (this.remainingUses == 0) {
            Scene nova = getScene();
            if (nova != null) {
                nova.removeActor(this);
            }
        }
    }

    public int getRemainingUses() {
        return this.remainingUses;
    }

    public void setRemainingUses(int remainingUses){
        this.remainingUses = remainingUses;
    }

}
