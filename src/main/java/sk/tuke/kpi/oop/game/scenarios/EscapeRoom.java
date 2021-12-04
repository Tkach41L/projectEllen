package sk.tuke.kpi.oop.game.scenarios;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.*;
import sk.tuke.kpi.oop.game.behaviours.RandomlyMoving;
import sk.tuke.kpi.oop.game.characters.Alien;
import sk.tuke.kpi.oop.game.characters.AlienMother;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.controllers.KeeperController;
import sk.tuke.kpi.oop.game.controllers.MovableController;
import sk.tuke.kpi.oop.game.controllers.ShooterController;
import sk.tuke.kpi.oop.game.items.Ammo;
import sk.tuke.kpi.oop.game.items.Energy;
import sk.tuke.kpi.oop.game.openables.Door;

public class EscapeRoom implements SceneListener {
    private Ripley ripley;

    @Override
    public void sceneInitialized(@NotNull Scene scene) {
        for (Actor actor : scene.getActors()) {
            if (actor instanceof Ripley) {
                ripley = (Ripley) actor;
                scene.follow(ripley);
                scene.getGame().pushActorContainer(ripley.getBackpack());
                MovableController movableController = new MovableController(ripley);
                ShooterController shooterController = new ShooterController(ripley);
                KeeperController keeperController = new KeeperController(ripley);
                scene.getInput().registerListener(movableController);
                scene.getInput().registerListener(shooterController);
                scene.getInput().registerListener(keeperController);
            }
        }
    }

    @Override
    public void sceneCreated(@NotNull Scene scene) {
        scene.getMessageBus().subscribe(World.ACTOR_ADDED_TOPIC, actor -> {
            if (actor instanceof Alien) {
                new RandomlyMoving().setUp((Alien) actor);
            }
        });
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
                case "alien":
                    actor = new Alien();
                    break;
                case "ammo":
                    actor = new Ammo();
                    break;
                case "alien mother":
                    actor = new AlienMother();
                    break;
                default:
                    return null;
            }
            return actor;
        }
    }

    @Override
    public void sceneUpdating(@NotNull Scene scene) {
        if(ripley == null){
            return;
        }
        ripley.showRipleyState();
    }

}
