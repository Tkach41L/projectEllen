package sk.tuke.kpi.oop.game.weapons;

public abstract class Firearm {
    private int currentAmmo;
    private final int maxAmmo;

    public Firearm(int initAmmo, int maxAmmo){
        currentAmmo = initAmmo;
        this.maxAmmo = maxAmmo;
    }

    public Firearm(int initAmmo){
        currentAmmo = initAmmo;
        maxAmmo = initAmmo;
    }

    public int getAmmo(){
        return currentAmmo;
    }

    public void reload(int increase){
        this.currentAmmo = Math.min(this.currentAmmo + increase, this.maxAmmo);
    }

    public Fireable fire(){
        if(currentAmmo == 0){
            return null;
        }
        this.currentAmmo--;
        return createBullet();
    }

    protected abstract Fireable createBullet();

}
