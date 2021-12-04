package sk.tuke.kpi.oop.game.openables;

import sk.tuke.kpi.gamelib.Actor;

public class LockedDoor extends Door {
    private boolean locked;

    public LockedDoor(String name, Orientation orientation) {
        super(name, orientation);
        this.locked = true;
    }

    public void lock(){
        if(locked) {
            return;
        }
        locked = true;
        super.close();
    }

    public void unlock(){
        if(!locked) {
            return;
        }
        locked = false;
        super.open();
    }

    @Override
    public void useWith(Actor actor) {
        if(!locked){
            lock();
        }
        else{
            unlock();
        }
    }

    public boolean isLocked(){
        return this.locked;
    }

}
