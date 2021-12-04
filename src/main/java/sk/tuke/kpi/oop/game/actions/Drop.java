package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;

import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.items.Collectible;

import java.util.Objects;


public class Drop<A extends Keeper> extends AbstractAction<A> {
    public Drop(){
    }

    @Override
    public void execute(float deltaTime) {//?
        this.setDone(true);
        Keeper keeper = getActor();
        if(keeper == null){
            return;
        }
        Collectible item = keeper.getBackpack().peek();
        if (item != null) {
            int itemPosX = keeper.getPosX() + item.getWidth() / 2;
            int itemPosY = keeper.getPosY() + item.getHeight() / 2;
            Objects.requireNonNull(keeper.getScene()).addActor(item, itemPosX, itemPosY);
            keeper.getBackpack().remove(item);
        }
    }

}
