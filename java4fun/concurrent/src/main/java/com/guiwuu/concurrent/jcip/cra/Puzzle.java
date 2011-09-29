package com.guiwuu.concurrent.jcip.cra;

import java.util.Set;

/**
 *
 * @author guiwuu
 */
public interface Puzzle<P, M> {

    P initialPosizion();

    boolean isGoal(P position);

    Set<M> legalMoves(P position);

    P move(P position, M move);
}
