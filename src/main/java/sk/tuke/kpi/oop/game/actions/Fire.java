package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.characters.Armed;
import sk.tuke.kpi.oop.game.weapons.Fireable;

import java.util.Objects;

public class Fire<A extends Armed>  extends AbstractAction<A> {
    public Fire(){
    }

    @Override
    public void execute(float deltaTime) {
        setDone(true);
        Armed armedActor = super.getActor();
        if(armedActor == null){
            return;
        }
        Fireable bullet = armedActor.getFirearm().fire();
        if(bullet  == null){
            return;
        }
        Objects.requireNonNull(armedActor.getScene()).addActor(bullet , armedActor.getPosX(), armedActor.getPosY());
        new Move<>(Direction.fromAngle(armedActor.getAnimation().getRotation()), Float.MAX_VALUE).scheduleFor(bullet);
    }
}
