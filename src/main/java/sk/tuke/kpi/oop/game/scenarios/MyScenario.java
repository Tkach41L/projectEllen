package sk.tuke.kpi.oop.game.scenarios;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.*;
import sk.tuke.kpi.oop.game.NewFan;
import sk.tuke.kpi.oop.game.NewVentilator;
import sk.tuke.kpi.oop.game.behaviours.RandomlyMoving;
import sk.tuke.kpi.oop.game.characters.*;
import sk.tuke.kpi.oop.game.controllers.KeeperController;
import sk.tuke.kpi.oop.game.controllers.MovableController;
import sk.tuke.kpi.oop.game.controllers.ShooterController;
import sk.tuke.kpi.oop.game.items.*;


public class MyScenario implements SceneListener {
    private Ripley someActor;
    private Disposable m;
    private Disposable k;
    private Disposable s;

    @Override
    public void sceneInitialized(@NotNull Scene scene) {
        for (Actor actor : scene.getActors()) {
            if (actor instanceof Ripley) {
                someActor = (Ripley) actor;
                scene.follow(someActor);
                scene.getGame().pushActorContainer(someActor.getBackpack());
             m = scene.getInput().registerListener(new MovableController(someActor));
             k =  scene.getInput().registerListener(new KeeperController(someActor));
             s =  scene.getInput().registerListener(new ShooterController(someActor));
            }
        }
        scene.getMessageBus().subscribe(Ripley.RIPLEY_DIED,someActor ->{
            m.dispose();
            k.dispose();
            s.dispose();
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
                case "alien":
                    actor = new Alien(45, new RandomlyMoving());
                    break;
                case "alienTutorial":
                    actor = new AlienTutorial();
                    break;
                case "ammo":
                    actor = new Ammo();
                    break;
                case "money":
                    actor = new NewMoney();
                    break;
                case "barrel":
                    actor = new NewBarrel();
                    break;
                case "life":
                    actor = new NewExtraLife();
                    break;
                case "laser":
                    actor = new NewLaser(NewLaser.TYPE.BASIC);
                    break;
                case "button":
                    actor = new NewButton(NewButton.TYPE.BASIC);
                    break;
                case "laserPurple":
                    actor = new NewLaser(NewLaser.TYPE.PURPLE);
                    break;
                case "buttonPurple":
                    actor = new NewButton(NewButton.TYPE.PURPLE);
                    break;
                case "laserRed":
                    actor = new NewLaser(NewLaser.TYPE.RED);
                    break;
                case "buttonRed":
                    actor = new NewButton(NewButton.TYPE.RED);
                    break;
                case "laserYellow":
                    actor = new NewLaser(NewLaser.TYPE.YELLOW);
                    break;
                case "buttonYellow":
                    actor = new NewButton(NewButton.TYPE.YELLOW);
                    break;
                case "mother":
                    actor = new AlienMother();
                    break;
                case "ventilator":
                    actor = new NewVentilator();
                    break;
                case "fan":
                    actor = new NewFan();
                    break;
                case "spitter":
                    actor = new NewSpitterAlien(new RandomlyMoving());
                    break;
                case "slug":
                    actor = new NewSlugAlien(new RandomlyMoving());
                    break;
                default:
                    return null;
            }
            return actor;
        }
    }
    @Override
    public void sceneUpdating(@NotNull Scene scene) {
        if(someActor != null){
            someActor.showRipleyState();
        }
    }

}
