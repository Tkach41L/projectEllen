package sk.tuke.kpi.oop.game.controllers;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Input;
import sk.tuke.kpi.gamelib.KeyboardListener;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.actions.Move;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MovableController implements KeyboardListener {
    private Movable actor;
    private Move<Movable> move;
    private final Map<Input.Key, Direction> keyDirectionMap = Map.ofEntries(
        Map.entry(Input.Key.UP, Direction.NORTH),
        Map.entry(Input.Key.DOWN, Direction.SOUTH),
        Map.entry(Input.Key.LEFT, Direction.WEST),
        Map.entry(Input.Key.RIGHT, Direction.EAST)
    );
    private final Set<Direction> set = new HashSet<>();

    public MovableController(Movable actor){
        this.actor = actor;
    }

    public void stop(){
        if(this.move != null) {
            move.stop();
            if(this.actor != null){
                actor.stoppedMoving();
            }
        }
    }

    @Override
    public void keyPressed(@NotNull Input.Key key) {
        if(keyDirectionMap.containsKey(key)) {
            set.add(keyDirectionMap.get(key));
            if(move != null){
                stop();
            }
            Direction newDirection = Direction.NONE;
            for(Direction direction: set){
                newDirection = newDirection.combine(direction);
            }
            actor.startedMoving(newDirection);
            move = new Move<>(newDirection, Float.MAX_VALUE);
            move.scheduleFor(actor);
        }
    }

    @Override
    public void keyReleased(@NotNull Input.Key key) {
        if(keyDirectionMap.containsKey(key)){
            set.remove(keyDirectionMap.get(key));
            if(move != null){
                stop();
            }
            Direction newDirection = Direction.NONE;
            for(Direction direction: set){
                newDirection = newDirection.combine(direction);
            }
            move = new Move<>(newDirection, Float.MAX_VALUE);
            move.scheduleFor(actor);
        }
    }

}
