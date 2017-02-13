package io.improbable.scienceos.test;

import io.improbable.scienceos.Reference;

import java.util.concurrent.CompletableFuture;

/**
 * Created by daniel on 07/02/17.
 */
public class TileReference extends Reference<Tile> {

    public TileReference(int size) {
        super(new Tile(size));
    }

    CompletableFuture<Void> step() {
        return CompletableFuture.runAsync(() -> referent.step(), executor).thenRunAsync(() ->{}, pool.currentExecutor());
    }

    CompletableFuture<Void> update() {
        return CompletableFuture.runAsync(() -> referent.update(), executor).thenRunAsync(() ->{}, pool.currentExecutor());
    }

    CompletableFuture<Void> setLeftTile(TileReference tile) {
        return CompletableFuture.runAsync(() -> referent.setLeftTile(tile), executor).thenRunAsync(() ->{}, pool.currentExecutor());
    }

    CompletableFuture<Void> setRightTile(TileReference tile) {
        return CompletableFuture.runAsync(() -> referent.setRightTile(tile), executor).thenRunAsync(() ->{}, pool.currentExecutor());
    }

    CompletableFuture<Void> printState(int xOrigin, int yOrigin) {
        return CompletableFuture.runAsync(() -> referent.printState(xOrigin, yOrigin), executor).thenRunAsync(() ->{}, pool.currentExecutor());
    }

    CompletableFuture<boolean []> getLeftBoundary() {
        return CompletableFuture.supplyAsync(() -> referent.getLeftBoundary(), executor).thenApplyAsync((i) ->{return(i);}, pool.currentExecutor());
    }

    CompletableFuture<boolean []> getRightBoundary() {
        return CompletableFuture.supplyAsync(() -> referent.getRightBoundary(), executor).thenApplyAsync((i) ->{return(i);}, pool.currentExecutor());
    }

}
