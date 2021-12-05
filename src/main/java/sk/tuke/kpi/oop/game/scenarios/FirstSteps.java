package sk.tuke.kpi.oop.game.scenarios;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.*;
import sk.tuke.kpi.oop.game.characters.Ripley;


public class FirstSteps implements SceneListener {
    @Override
    public void sceneInitialized(@NotNull Scene scene) {
        Ripley ripley = new Ripley();
        scene.addActor(ripley, 0, 0);
    }

    @Override
    public void sceneUpdating(@NotNull Scene scene) {
        int windowHeight = scene.getGame().getWindowSetup().getHeight();
        int yTextPos = windowHeight - GameApplication.STATUS_LINE_OFFSET;
        scene.getGame().getOverlay().drawText("| Energy: " + 50, 100, yTextPos);
        scene.getGame().getOverlay().drawText("| Ammo: " + 30, 240, yTextPos);
    }
}
