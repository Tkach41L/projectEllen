package sk.tuke.kpi.oop.game.characters;

import java.util.HashSet;
import java.util.Set;

public class Health {
    private int currentHealth;
    private int maxHealth;
    private Set<ExhaustionEffect> exhaustionEffects = new HashSet<>();

    public Health(int initialHealth, int maxHealth) {
        this.currentHealth = initialHealth;
        this.maxHealth = maxHealth;
    }

    public Health(int maxHealth) {
        this.currentHealth = maxHealth;
        this.maxHealth = maxHealth;
    }

    public int getValue() {
        return currentHealth;
    }

    public void refill(int amount) {
        this.currentHealth = Math.min(this.maxHealth, this.currentHealth + amount);
    }

    public void restore() {
        this.currentHealth = maxHealth;
    }

    public void drain(int amount) {
        if (currentHealth == 0) {
            return;
        }
        this.currentHealth = Math.max(0, currentHealth - amount);
        if (currentHealth == 0) {
            for (ExhaustionEffect effect : exhaustionEffects) {
                effect.apply();
            }
        }
    }

    public void exhaust() {
        if (this.currentHealth == 0){
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
