package sk.tuke.kpi.oop.game.scenarios;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.*;
import sk.tuke.kpi.oop.game.Locker;
import sk.tuke.kpi.oop.game.Ventilator;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.controllers.KeeperController;
import sk.tuke.kpi.oop.game.controllers.MovableController;
import sk.tuke.kpi.oop.game.items.AccessCard;
import sk.tuke.kpi.oop.game.items.Energy;
import sk.tuke.kpi.oop.game.openables.Door;

public class MissionImpossible implements SceneListener {
    private Ripley someActor;

    @Override
    public void sceneInitialized(@NotNull Scene scene) {
        for (Actor actor : scene.getActors()) {
            if (actor instanceof Ripley) {
                someActor = (Ripley) actor;
                scene.follow(someActor);
                scene.getInput().registerListener(new MovableController(someActor));
                scene.getInput().registerListener(new KeeperController(someActor));
                scene.getGame().pushActorContainer(someActor.getBackpack());
            }
        }
    }

    static public class Factory implements ActorFactory {

        @Override
        public @Nullable Actor create(@Nullable String type, @Nullable String name) {
            if (name == null) {
                return null;
            }
            Actor actor;
            switch (name) {
                case "ellen":
                    actor = new Ripley();
                    break;
                case "energy":
                    actor = new Energy();
                    break;
                case "door":
                    actor = new Door("door", Door.Orientation.VERTICAL);
                    break;
                case "access card":
                    actor = new AccessCard();
                    break;
                case "locker":
                    actor = new Locker();
                    break;
                case "ventilator":
                    actor = new Ventilator();
                    break;
                default:
                    actor = null;
                    break;
            }

            return actor;
        }
    }

    @Override
    public void sceneUpdating(@NotNull Scene scene) {
        if (someActor == null) {
            return;
        }
        someActor.showRipleyState();
    }
}


