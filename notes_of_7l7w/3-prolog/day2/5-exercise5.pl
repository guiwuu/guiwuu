cut([], N, [], []).
cut([N], N, [N], []).
cut([X], N, [X], []) :- X < N.
cut([X], N, [], [X]) :- X > N.
cut([N|Tail], N, L1, L2) :- Tail \= [], cut(Tail, N, L11, L22), L1 = [N|L11], L2 = L22.
cut([Head|Tail], N, L1, L2) :- Tail \= [], Head < N, cut(Tail, N, L11, L22), L1 = [Head|L11], L2 = L22.
cut([Head|Tail], N, L1, L2) :- Tail \= [], Head > N, cut(Tail, N, L11, L22), L1 = L11, L2 = [Head|L22].

quicksort([], []).
quicksort([X], [X]).
quicksort([Head|Tail], Sorted) :- Tail \= [], cut(Tail, Head, L1, L2), quicksort(L1, S1), quicksort(L2, S2), append(S1, [Head|S2], Sorted).