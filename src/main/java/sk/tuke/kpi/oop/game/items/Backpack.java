package sk.tuke.kpi.oop.game.items;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.ActorContainer;

import java.util.*;

public class Backpack implements ActorContainer<Collectible> {
    private String name;
    private int capacity;
    private List<Collectible> items;

    public Backpack(String name, int capacity){
        this.items = new ArrayList<>();
        this.capacity = capacity;
        this.name = name;
    }

    @Override
    public @NotNull List<Collectible> getContent() {
        return new ArrayList<>(items);
    }

    @Override
    public int getCapacity() {
        return this.capacity;
    }

    @Override
    public @NotNull String getName() {
        return this.name;
    }

    @Override
    public void add(@NotNull Collectible actor) {
        if(items.size() >= capacity){
            throw new IllegalStateException(name + "is full");
        }
        items.add(actor);
    }

    @Override
    public void remove(@NotNull Collectible actor) {
            this.items.remove(actor);
    }

    @Override
    public @Nullable Collectible peek() {
        if(getSize() == 0) {
            return null;
        }
        return this.items.get(getSize() - 1);
    }

    @Override
    public void shift() {
        Collections.rotate(this.items, 1);
    }

    @Override
    public int getSize() {
        return this.items.size();
    }

    @NotNull
    @Override
    public Iterator<Collectible> iterator() {
        return items.iterator();
    }
}
