package sk.tuke.kpi.oop.game.controllers;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Input;
import sk.tuke.kpi.gamelib.KeyboardListener;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.actions.Drop;
import sk.tuke.kpi.oop.game.actions.Shift;
import sk.tuke.kpi.oop.game.actions.Take;
import sk.tuke.kpi.oop.game.actions.Use;
import sk.tuke.kpi.oop.game.items.Collectible;
import sk.tuke.kpi.oop.game.items.Usable;

import java.util.Objects;


public class KeeperController implements KeyboardListener {
    private Keeper actor;

    public KeeperController(Keeper actor) {
        this.actor = actor;
    }

    @Override
    public void keyPressed(@NotNull Input.Key key) {
        switch (key) {
            case BACKSPACE:
                new Drop<>().scheduleFor(actor);
                break;
            case ENTER:
                new Take<>().scheduleFor(actor);
                break;
            case S:
                new Shift<>().scheduleFor(actor);
                break;
            case U:
                for (Actor item : Objects.requireNonNull(this.actor.getScene()).getActors()) {
                    if (item instanceof Usable && item.intersects(actor)) {
                        new Use<>((Usable<?>) item).scheduleForIntersectingWith(this.actor);
                    }
                }
                break;
            case B:
                Collectible item = actor.getBackpack().peek();
                if (item instanceof Usable<?>) {
                    new Use<>((Usable<?>) item).scheduleForIntersectingWith(actor);
                }
                break;
            default:
                break;
        }
    }
}
