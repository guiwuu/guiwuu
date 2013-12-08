hanoi(1, A, _, C) :- write(A), write(' => '), write(C), nl.
hanoi(N, A, B, C) :- N > 1, N1 is N - 1, hanoi(N1, A, C, B), hanoi(1, A, _, C), hanoi(N1, B, A, C).