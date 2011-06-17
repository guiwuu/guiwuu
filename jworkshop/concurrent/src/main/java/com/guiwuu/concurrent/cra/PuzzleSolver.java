package com.guiwuu.concurrent.cra;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author daijun
 */
public class PuzzleSolver<P, M> extends ConcurrentPuzzleSolver<P, M> {

    private final AtomicInteger taskCount = new AtomicInteger(0);

    public PuzzleSolver(Puzzle<P, M> p, ExecutorService exec) {
        super(null, exec);
    }

    @Override
    protected Runnable newTask(P p, M m, Node<P, M> n) {
        return new CountingSolverTask(p, m, n);
    }

    class CountingSolverTask extends SolverTask {

        CountingSolverTask(P pos, M move, Node<P, M> prev) {
            super(pos, move, prev);
            taskCount.incrementAndGet();
        }

        @Override
        public void run() {
            try {
                super.run();
            } finally {
                // avoid right answer is replated by null, when the answer is right the last node
                if (taskCount.decrementAndGet() == 0 && !solution.isSet()) {
                    solution.setValue(null);
                }
            }
        }
    }
}
