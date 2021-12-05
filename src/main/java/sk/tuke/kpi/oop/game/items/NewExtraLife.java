package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.characters.Ripley;

public class NewExtraLife extends AbstractActor implements Usable<Ripley> {
    private Animation extraLifeAnimation = new Animation("sprites/life.png",16,16);
    public NewExtraLife(){
        setAnimation(extraLifeAnimation);
    }
    @Override
    public void useWith(Ripley actor) {
        if(actor != null && actor.getHealth().getValue() < 100){
            actor.getHealth().refill(15);
            getScene().removeActor(this);
        }
    }

    @Override
    public Class<Ripley> getUsingActorClass() {
        return Ripley.class;
    }
}
