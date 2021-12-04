package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class NewFan extends AbstractActor {
    private Animation newFanAnimation = new Animation("sprites/fan.png", 32, 32, 0.2f);

    public NewFan() {
        setAnimation(newFanAnimation);
    }
}

