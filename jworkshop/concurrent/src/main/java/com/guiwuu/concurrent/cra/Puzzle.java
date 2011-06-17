package com.guiwuu.concurrent.cra;

import java.util.Set;

/**
 *
 * @author daijun
 */
public interface Puzzle<P, M> {

    P initialPosizion();

    boolean isGoal(P position);

    Set<M> legalMoves(P position);

    P move(P position, M move);
}
