package io.improbable.scienceos.test;

import java.io.Serializable;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

/**
 * Created by daniel on 07/02/17.
 */
public class Tile implements Serializable {
    Random rnd = new Random();

    public boolean[][] state;
    public boolean[][] newstate;
    public TileReference leftTile;
    public TileReference rightTile;

    public Tile(int size) {
        state = new boolean[size+1][size+1];
        newstate = new boolean[size+1][size+1];
        // set up top and bottom boundaries
        int i,j;
        for(i = 0; i<state.length; i++) {
            state[i][0] = false;
            state[i][state[0].length-1] = false;
        }
        for(i=1; i<state.length-1; ++i) {
            for(j=1; j<state[0].length-1; ++j) {
                state[i][j] = rnd.nextBoolean();
            }
        }
    }

    public static void join(TileReference [] tiles) {
        int i;
        for(i=1; i<tiles.length; ++i) {
            tiles[i-1].setRightTile(tiles[i]);
            tiles[i].setLeftTile(tiles[i-1]);
        }
        i--;
        tiles[i].setRightTile(tiles[0]);
        tiles[0].setLeftTile(tiles[i]);
    }

    public void setLeftTile(TileReference tile) {
        leftTile = tile;
    }

    public void setRightTile(TileReference tile) {
        rightTile = tile;
    }

    public boolean[] getLeftBoundary() {
        return state[1];
    }

    public boolean[] getRightBoundary() {
        return state[state.length-2];
    }

    public void step() {

//        System.out.println("Stepping "+this);
        // set up left and right boundaries
        CompletableFuture<boolean []> leftBoundary = leftTile.getRightBoundary();
        CompletableFuture<boolean []> rightBoundary = rightTile.getLeftBoundary();
        leftBoundary.thenAcceptBoth(rightBoundary, (left, right) -> {
//            System.out.println("Got boudnary "+left+", "+right);
            int k;
            for(k=1; k<state[0].length-1; ++k) {
                state[0][k] = left[k];
                state[state.length-1][k] = right[k];
            }
        }).thenRun(() -> {
//            System.out.println("calculating "+this);
            int i,j,n;
            for(i = 1; i<state.length-1; i++) {
                for(j=1; j<state[0].length-1; j++) {
                    n = countNeighbours(i,j);
                    if(state[i][j] == true) {
                        if (n < 2 || n > 3) {
                            newstate[i][j] = false;
                        } else {
                            newstate[i][j] = true;
                        }
                    } else {
                        if (n == 3) {
                            newstate[i][j] = true;
                        } else {
                            newstate[i][j] = false;
                        }
                    }
                }
            }
//            System.out.println("done calculating "+this);
        });

    }

    private int countNeighbours(int i, int j) {
        return((state[i+1][j+1]?1:0)+
                (state[i+1][j]?1:0)+
                (state[i+1][j-1]?1:0)+
                (state[i][j+1]?1:0)+
                (state[i][j-1]?1:0)+
                (state[i-1][j+1]?1:0)+
                (state[i-1][j]?1:0)+
                (state[i-1][j-1]?1:0)
        );
    }

    public void update() {
//        System.out.println("updating "+this);
        boolean[][] swapstate; // just swap pointers
        swapstate = state;
        state = newstate;
        newstate = swapstate;
    }

    public void printState(int xOrigin, int yOrigin) {
        int i,j;
        for(i=1; i<state.length-1; ++i) {
            for (j = 1; j < state[0].length - 1; ++j) {
                System.out.println((i+xOrigin-1)+" "+(j+yOrigin-1)+" "+(state[j][i]?1:0));
            }
            System.out.println("");
        }
    }

}
