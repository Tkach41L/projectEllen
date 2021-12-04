package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class NewVentilator extends AbstractActor{
    private Animation newVentilatorAnimation = new Animation("sprites/ventilator.png",32,32,0.1f);

    public NewVentilator() {
        setAnimation(newVentilatorAnimation);
    }


}
