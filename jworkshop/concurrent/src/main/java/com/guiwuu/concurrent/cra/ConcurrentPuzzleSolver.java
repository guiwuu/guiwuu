package com.guiwuu.concurrent.cra;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;

/**
 *
 * @author daijun
 */
public class ConcurrentPuzzleSolver<P, M> {

    protected final Puzzle<P, M> puzzle;
    protected final ExecutorService exec;
    protected final ConcurrentHashMap<P, Boolean> seen = new ConcurrentHashMap<P, Boolean>();
    final ValueLatch<Node<P, M>> solution = new ValueLatch<Node<P, M>>();

    public ConcurrentPuzzleSolver(Puzzle<P, M> puzzle, ExecutorService exec) {
        this.puzzle = puzzle;
        this.exec = exec;
    }

    public List<M> solve() throws InterruptedException {
        try {
            P p = puzzle.initialPosizion();
            exec.execute(newTask(p, null, null));

            // blocked until find a result, if there is no solution it will hang forever
            // to solve this see the PuzzleSolver class
            Node<P, M> solnNode = solution.getValue();
            return (solnNode == null) ? null : solnNode.asMoveList();
        } finally {
            // if find solution, unfinished task will continue, but no more new task will be submitted
            // moreover we want to stop these unifinished tasks but wait them complete
            exec.shutdown();
        }
    }

    protected Runnable newTask(P p, M m, Node<P, M> n) {
        return new SolverTask(p, m, n);
    }

    class SolverTask extends Node<P, M> implements Runnable {

        SolverTask(P p, M m, Node<P, M> n) {
            super(p, m, n);
        }

        public void run() {
            if (solution.isSet() || seen.putIfAbsent(pos, Boolean.TRUE) != null) {
                return;
            }
            if (puzzle.isGoal(pos)) {
                solution.setValue(this);
            } else {
                for (M m : puzzle.legalMoves(pos)) {
                    exec.execute(newTask(puzzle.move(pos, m), m, this));
                }
            }
        }
    }
}
