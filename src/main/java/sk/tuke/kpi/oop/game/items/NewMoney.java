package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.characters.Ripley;

public class NewMoney extends AbstractActor implements Collectible,Usable<Ripley> {
    private int money;
    private Animation moneyAnimation = new Animation("sprites/money.png",16,16);
    public NewMoney(){
        this.money = 0;
        setAnimation(moneyAnimation);
    }
    @Override
    public void useWith(Ripley actor) {
        if(actor != null){
            actor.setMoney(25 + actor.getMoney());
            this.getScene().removeActor(this);
        }
    }

    @Override
    public Class<Ripley> getUsingActorClass() {
        return Ripley.class;
    }


}
