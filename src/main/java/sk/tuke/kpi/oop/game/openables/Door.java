package sk.tuke.kpi.oop.game.openables;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.map.MapTile;
import sk.tuke.kpi.gamelib.messages.Topic;
import sk.tuke.kpi.oop.game.items.Usable;

import java.util.Objects;

import static sk.tuke.kpi.gamelib.graphics.Animation.PlayMode.ONCE;

public class Door extends AbstractActor implements Usable<Actor>, Openable {
    public static Topic<Door> DOOR_OPENED = Topic.create("door opened", Door.class);
    public static Topic<Door> DOOR_CLOSED = Topic.create("door closed", Door.class);
    private boolean opened;
    private int tile1x;
    private int tile1y;
    private int tile2x;
    private int tile2y;

    public enum Orientation {
        HORIZONTAL,
        VERTICAL
    }

    public Door(String name, Orientation orientation) {
        super(name);
        this.opened = false;
        this.tile1x = this.getPosX() / 16;
        this.tile1y = this.getPosY() / 16;
        if (orientation == Orientation.VERTICAL) {
            setAnimation(new Animation("sprites/vdoor.png", 16, 32, 0.1f, ONCE));
            this.tile2x = this.getPosX() / 16 + 1;
            this.tile2y = this.getPosX() / 16;
        }
        else if (orientation == Orientation.HORIZONTAL) {
            setAnimation(new Animation("sprites/hdoor.png", 16, 32, 0.1f, ONCE));
            this.tile2x = this.getPosX() / 16;
            this.tile2y = this.getPosX() / 16 + 1;
        }
        getAnimation().pause();
    }

    @Override
    public void open() {
        if (opened) {
            return;
        }
        opened = true;
        getAnimation().play();
        Objects.requireNonNull(this.getScene()).getMessageBus().publish(DOOR_OPENED, this);
        updateTileMap();
    }

    @Override
    public void close() {
        if(!opened){
            return;
        }
        opened = false;
        getAnimation().stop();
        Objects.requireNonNull(this.getScene()).getMessageBus().publish(DOOR_CLOSED, this);
        updateTileMap();
    }

    @Override
    public boolean isOpen() {

        return this.opened;
    }


    @Override
    public void useWith(Actor actor) {
        if (opened) {
            close();
        }
        else {
            open();
        }
    }

    @Override
    public Class<Actor> getUsingActorClass() {
        return Actor.class;
    }

    private void updateTileMap() {
        if (this.opened) {
            Objects.requireNonNull(getScene()).getMap().getTile(tile1x, tile1y).setType(MapTile.Type.CLEAR);
            getScene().getMap().getTile(tile2x, tile2y).setType(MapTile.Type.CLEAR);
        }
        else {
            Objects.requireNonNull(getScene()).getMap().getTile(tile1x, tile1y).setType(MapTile.Type.WALL);
            getScene().getMap().getTile(tile2x, tile2y).setType(MapTile.Type.WALL);
        }
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        updateTileMap();
    }
}
