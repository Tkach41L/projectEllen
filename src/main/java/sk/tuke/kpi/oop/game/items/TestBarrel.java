package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

import sk.tuke.kpi.oop.game.characters.Alien;
import sk.tuke.kpi.oop.game.characters.Ripley;

import java.util.Random;

public class TestBarrel extends AbstractActor implements Usable<Ripley> {
    private Animation barrelAnimation = new Animation("sprites/barrel.png",16,16);
    public TestBarrel(){
        setAnimation(barrelAnimation);
    }
    public Actor GiveSomeItems(){
        int res = new Random().nextInt(4);
        if(res == 0){
            return new TestMoney();
        }
        if(res == 1){
            return new Energy();
        }
        if(res == 2){
            return new Ammo();
        }
        if(res == 3) {
            return new Alien();
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
