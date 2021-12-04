package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Disposable;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.actions.Loop;

import java.util.Random;

public class DefectiveLight extends Light implements Repairable{
    private Random random;
    private boolean repaired;
    private Disposable coolingAction;
    public DefectiveLight(){
        super();
        this.repaired = false;
        this.random = new Random();
    }
    public void brokenToggle(){
        if(random.nextInt(20) == 0){
            super.toggle();
        }
    }
    @Override
    public void addedToScene(@NotNull Scene lightScene) {
        super.addedToScene(lightScene);
        coolingAction = new Loop<>(new Invoke<DefectiveLight>(this::brokenToggle)).scheduleFor(this);
    }
    public void method(){
        this.repaired = !repaired;
    }

    @Override
    public boolean repair() {
        if(!repaired){
            coolingAction.dispose();
            this.turnOn();
            method();
            coolingAction = new ActionSequence<>(new Wait<>(10),
                new Invoke<>(this::method),
                new Loop<>(new Invoke<>(this::brokenToggle))
                ).scheduleFor(this);
            return true;
        }
        return false;
    }
}
