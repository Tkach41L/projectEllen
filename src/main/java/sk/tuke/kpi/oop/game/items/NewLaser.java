package sk.tuke.kpi.oop.game.items;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.characters.Alive;

import java.util.Objects;

public class NewLaser extends AbstractActor {
    public enum TYPE{
        PURPLE, RED, YELLOW, BASIC
    }

    private TYPE type;

    public NewLaser(TYPE type) {
        this.type = type;
        if(type == TYPE.PURPLE){
            setAnimation(new Animation("sprites/laser_purple.png",16,48,0.1f, Animation.PlayMode.LOOP_PINGPONG));
        }
        else if (type == TYPE.RED){
            setAnimation(new Animation("sprites/laser_red.png",16,48,0.1f, Animation.PlayMode.LOOP_PINGPONG));
        }
        else if (type == TYPE.YELLOW){
            setAnimation(new Animation("sprites/laser_yellow.png",16,48,0.1f, Animation.PlayMode.LOOP_PINGPONG));
        }
        else if(type == TYPE.BASIC){
            setAnimation(new Animation("sprites/laser.png",16,48,0.1f, Animation.PlayMode.LOOP_PINGPONG));
        }
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        if(type == TYPE.PURPLE) {
            Objects.requireNonNull(getScene()).getMessageBus().subscribe(NewButton.PURPLE_BUTTON_WAS_PRESSED, button -> new Invoke<>(() -> getScene().removeActor(this)).scheduleFor(this));
        }
        else if (type == TYPE.RED){
            Objects.requireNonNull(getScene()).getMessageBus().subscribe(NewButton.RED_BUTTON_WAS_PRESSED, button -> new Invoke<>(() -> getScene().removeActor(this)).scheduleFor(this));
        }
        else if (type == TYPE.YELLOW){
            Objects.requireNonNull(getScene()).getMessageBus().subscribe(NewButton.YELLOW_BUTTON_WAS_PRESSED, button -> new Invoke<>(() -> getScene().removeActor(this)).scheduleFor(this));
        }
        else if(type == TYPE.BASIC){
            Objects.requireNonNull(getScene()).getMessageBus().subscribe(NewButton.BASIC_BUTTON_WAS_PRESSED, button -> new Invoke<>(() -> getScene().removeActor(this)).scheduleFor(this));
        }
        new Loop<>(new Invoke<>(this::doDamage)).scheduleFor(this);
    }

    public void doDamage(){
        for(Actor actor : Objects.requireNonNull(getScene()).getActors()){
            if(actor instanceof Alive && intersects(actor)){
                ((Alive) actor).getHealth().drain(10);
            }
        }
    }
}
