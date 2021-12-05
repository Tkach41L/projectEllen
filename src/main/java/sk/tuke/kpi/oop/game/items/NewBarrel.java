package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

import sk.tuke.kpi.oop.game.characters.Alien;
import sk.tuke.kpi.oop.game.characters.Ripley;

import java.util.Random;

public class NewBarrel extends AbstractActor implements Usable<Ripley> {
    private Animation barrelAnimation = new Animation("sprites/barrel.png",16,16);
    public NewBarrel(){
        setAnimation(barrelAnimation);
    }
    public Actor GiveSomeItems(){
        int res = new Random().nextInt(3);
        if(res == 0){
            return new NewMoney();
        }
        if(res == 1){
            return new Energy();
        }
        if(res == 2){
            return new Ammo();
        }
        return null;
    }
    @Override
    public void useWith(Ripley actor) {
        if(actor != null){
            Actor newActor;
            newActor = GiveSomeItems();
            actor.hitBarrel();
            getScene().addActor(newActor,getPosX(),getPosY());
            getScene().removeActor(this);
        }
    }

    @Override
    public Class<Ripley> getUsingActorClass() {
        return Ripley.class;
    }
}
