package sk.tuke.kpi.oop.game.behaviours;

import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.actions.Action;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.actions.Move;

import java.util.Random;

public class RandomlyMoving implements Behaviour<Movable>{

    public Action<Movable> movementChain(){
        return new Action<>() {
            private Movable actor;
            private boolean done;
            private final Random random = new Random();
            private Move<Movable> move;

            @Override
            public @Nullable Movable getActor() {
                return this.actor;
            }

            @Override
            public void setActor(@Nullable Movable actor) {
                this.actor = actor;
            }

            @Override
            public boolean isDone() {
                return done;
            }

            private Move<Movable> createRandomMove(){
                Direction direction = Direction.values()[random.nextInt(9)];
                return new Move<>(direction, random.nextFloat());
            }

            @Override
            public void execute(float deltaTime) {
                if(this.move == null){
                    this.move = createRandomMove();
                    this.move.scheduleFor(actor);
                }
                else if(this.move.isDone()){
                    this.move = createRandomMove();
                    this.move.scheduleFor(actor);
                }
            }

            @Override
            public void reset() {
            }
        };
    }

    @Override
    public void setUp(Movable actor) {
        if(actor == null){
            return;
        }
        new Loop<>(
            this.movementChain()
        ).scheduleFor(actor);
    }
}
