package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.*;
import sk.tuke.kpi.gamelib.backends.lwjgl.LwjglBackend;
import sk.tuke.kpi.oop.game.scenarios.MyScenario;

public class Main {
    public static void main(String[] args) {
        WindowSetup windowSetup = new WindowSetup("Project Ellen", 800, 600);

        Game game = new GameApplication(windowSetup, new LwjglBackend());

        Scene scene = new World("world" ,"maps/map.tmx", new MyScenario.Factory());
        scene.addListener(new MyScenario());

        game.addScene(scene);
        game.getInput().onKeyPressed(Input.Key.ESCAPE, game::stop);
        game.start();
    }
}
