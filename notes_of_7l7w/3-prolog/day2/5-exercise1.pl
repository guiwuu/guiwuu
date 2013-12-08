fibonacci(1, 1).
fibonacci(2, 1).
fibonacci(N, Out) :- N > 2, N1 is N - 1, N2 is N - 2, fibonacci(N1, Out1), fibonacci(N2, Out2), Out is Out1 + Out2.