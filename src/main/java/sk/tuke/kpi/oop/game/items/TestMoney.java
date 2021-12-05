package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.characters.Ripley;

public class    TestMoney extends AbstractActor implements Collectible,Usable<Ripley> {
    private int money;
    private Animation moneyAnimation = new Animation("sprites/money.png",16,16);
    public TestMoney(){
        this.money = 0;
        setAnimation(moneyAnimation);
    }
    @Override
    public void useWith(Ripley actor) {
        if(actor != null){
            actor.setMoney(20 + actor.getMoney());
            this.getScene().removeActor(this);
        }
    }

    @Override
    public Class<Ripley> getUsingActorClass() {
        return Ripley.class;
    }


}
