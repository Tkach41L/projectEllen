package sk.tuke.kpi.oop.game.characters;


import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.GameApplication;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.messages.Topic;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.items.Backpack;
import sk.tuke.kpi.oop.game.weapons.Firearm;
import sk.tuke.kpi.oop.game.weapons.Gun;

import java.util.Objects;

public class Ripley extends AbstractActor implements Movable, Keeper, Alive, Armed {
    private int speed;
    private int ammo;
    private Backpack backpack;
    private Health health;
    private Animation movingAnimation;
    private Animation deathAnimation;
    public static Topic<Ripley> RIPLEY_DIED = Topic.create("RIPLEY_DIED", Ripley.class);
    private Firearm firearm;
    private int money;

    public Ripley() {
        super("Ellen");
        this.movingAnimation = new Animation("sprites/player.png", 32, 32, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        this.deathAnimation = new Animation("sprites/player_die.png", 32, 32, 0.1f, Animation.PlayMode.ONCE);
        this.speed = 2;
        this.money = 0;
        this.backpack = new Backpack("Ripley's backpack", 10);
        this.health = new Health(50, 100);
        setFirearm(new Gun(15, 500));
        setAnimation(movingAnimation);
        movingAnimation.pause();
        health.onExhaustion(() -> {
            setAnimation(deathAnimation);
            Objects.requireNonNull(getScene()).getMessageBus().publish(RIPLEY_DIED, this);
            getScene().cancelActions(this);
        });
    }


    @Override
    public int getSpeed() {
        return this.speed;
    }

    @Override
    public void startedMoving(Direction direction) {
        if (direction == Direction.NONE) {
            return;
        }
        movingAnimation.setRotation(direction.getAngle());
        movingAnimation.play();
    }

    @Override
    public void stoppedMoving() {
        movingAnimation.stop();
    }

    public int getBullets() {
        return ammo;
    }

    public void setBullets(int bullets) {
        this.ammo = bullets;
    }

    @Override
    public Backpack getBackpack() {
        return backpack;
    }

    public void showRipleyState() {
        Scene scene = this.getScene();
        if (scene == null) {
            return;
        }
        int windowHeight = scene.getGame().getWindowSetup().getHeight();
        int yTextPos = windowHeight - GameApplication.STATUS_LINE_OFFSET;
        scene.getGame().getOverlay().drawText("Energy: " + this.getHealth().getValue(), 12, yTextPos - 20);
        scene.getGame().getOverlay().drawText("Ammo: " + this.getFirearm().getAmmo(), 12, yTextPos - 40);
        scene.getGame().getOverlay().drawText("Money: " + this.getMoney(), 12, yTextPos - 60);
    }

    @Override
    public Health getHealth() {
        return health;
    }

    @Override
    public Firearm getFirearm() {
        return firearm;
    }

    @Override
    public void setFirearm(Firearm weapon) {
        this.firearm = weapon;
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void hitBarrel() {
    }
}
