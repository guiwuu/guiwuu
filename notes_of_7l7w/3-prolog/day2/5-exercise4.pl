min(X, X, X).
min(X, Y, X) :- X < Y.
min(X, Y, Y) :- X > Y.
min([X], X).
min([Head|Tail], Min) :- Tail \= [], min(Tail, TailMin), min(Head, TailMin, Min).