package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.messages.Topic;
import sk.tuke.kpi.oop.game.characters.Ripley;

import java.util.Objects;

public class NewButton extends AbstractActor implements Usable<Ripley> {
    public enum TYPE {
        PURPLE, RED, YELLOW, BASIC
    }

    public static Topic<NewButton> PURPLE_BUTTON_WAS_PRESSED = Topic.create("BUTTON_WAS_PRESSED", NewButton.class);
    public static Topic<NewButton> RED_BUTTON_WAS_PRESSED = Topic.create("BUTTON_WAS_PRESSED", NewButton.class);
    public static Topic<NewButton> YELLOW_BUTTON_WAS_PRESSED = Topic.create("BUTTON_WAS_PRESSED", NewButton.class);
    public static Topic<NewButton> BASIC_BUTTON_WAS_PRESSED = Topic.create("BUTTON_WAS_PRESSED", NewButton.class);
    private boolean isPressed = false;
    private TYPE type;

    public NewButton(TYPE type) {
        this.type = type;
        if (type == TYPE.PURPLE) {
            setAnimation(new Animation("sprites/button_purple.png", 16, 16));
        }
        else if (type == TYPE.RED) {
            setAnimation(new Animation("sprites/button_red.png", 16, 16));
        }
        else if (type == TYPE.YELLOW) {
            setAnimation(new Animation("sprites/button_yellow.png", 16, 16));
        }
        else if (type == TYPE.BASIC){
            setAnimation(new Animation("sprites/button_green.png", 16, 16));
        }
    }

    @Override
    public void useWith(Ripley actor) {
        if(actor == null){
            return;
        }
        if (type == TYPE.PURPLE) {
            Objects.requireNonNull(getScene()).getMessageBus().publish(PURPLE_BUTTON_WAS_PRESSED, this);
        }
        else if (type == TYPE.RED) {
            Objects.requireNonNull(getScene()).getMessageBus().publish(RED_BUTTON_WAS_PRESSED, this);
        }
        else if (type == TYPE.YELLOW) {
            Objects.requireNonNull(getScene()).getMessageBus().publish(YELLOW_BUTTON_WAS_PRESSED, this);
        }
        else if(type == TYPE.BASIC){
            Objects.requireNonNull(getScene()).getMessageBus().publish(BASIC_BUTTON_WAS_PRESSED, this);
        }
        isPressed = true;
    }

    @Override
    public Class<Ripley> getUsingActorClass() {
        return Ripley.class;
    }


    public boolean isPressed() {
        return isPressed;
    }

    public void setPressed(boolean pressed) {
        isPressed = pressed;
    }
}
