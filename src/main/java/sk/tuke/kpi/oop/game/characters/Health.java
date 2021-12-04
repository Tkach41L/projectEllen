package sk.tuke.kpi.oop.game.characters;

import java.util.HashSet;
import java.util.Set;

public class Health {
    private int health;
    private int maxHealth;
    private Set<ExhaustionEffect> exhaustionEffects = new HashSet<>();

    public Health(int initialHealth, int maxHealth) {
        this.health = initialHealth;
        this.maxHealth = maxHealth;
    }

    public Health(int maxHealth) {
        this.health = maxHealth;
        this.maxHealth = maxHealth;
    }

    public int getValue() {
        return health;
    }

    public void refill(int amount) {
        this.health = Math.min(this.maxHealth, this.health + amount);
    }

    public void restore() {
        this.health = maxHealth;
    }

    public void drain(int amount) {
        if (health == 0) {
            return;
        }
        this.health = Math.max(0, health - amount);
        if (health == 0) {
            for (ExhaustionEffect effect : exhaustionEffects) {
                effect.apply();
            }
        }
    }

    public void exhaust() {
        if (this.health == 0){
            return;
        }
        drain(maxHealth);
    }

    @FunctionalInterface
    public interface ExhaustionEffect {
        void apply();
    }

    public void onExhaustion(ExhaustionEffect effect) {
        this.exhaustionEffects.add(effect);
    }
}
