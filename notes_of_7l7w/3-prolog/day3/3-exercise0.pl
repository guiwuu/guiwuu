fifteen(1, 5, 9).
fifteen(1, 6, 8).
fifteen(2, 4, 9).
fifteen(2, 5, 8).
fifteen(2, 6, 7).
fifteen(3, 4, 8).
fifteen(3, 5, 7).
fifteen(4, 5, 6).
fifteen(5, 5, 5).

valid([]).
valid([Head|Tail]) :- Head = [N1, N2, N3], fifteen(N1, N2, N3), valid(Tail).
valid([Head|Tail]) :- Head = [N1, N2, N3], fifteen(N1, N3, N2), valid(Tail).
valid([Head|Tail]) :- Head = [N1, N2, N3], fifteen(N2, N1, N3), valid(Tail).
valid([Head|Tail]) :- Head = [N1, N2, N3], fifteen(N2, N3, N1), valid(Tail).
valid([Head|Tail]) :- Head = [N1, N2, N3], fifteen(N3, N1, N2), valid(Tail).
valid([Head|Tail]) :- Head = [N1, N2, N3], fifteen(N3, N2, N1), valid(Tail).

nine(Nine, Solution) :- 
	Nine = Solution, 
	Nine = [N11, N12, N13, N21, N22, N23, N31, N32, N33],
	fd_domain(Nine, 1, 9), 
	fd_all_different(Nine),
    ROW1 = [N11, N12, N13], ROW2 = [N21, N22, N23], ROW3 = [N31, N32, N33],
    COL1 = [N11, N21, N31], COL2 = [N12, N22, N32], COL3 = [N13, N23, N33],
    SLASH1 = [N11, N22, N33], SLASH2 = [N13, N22, N31],
    valid([ROW1, ROW2, ROW3, COL1, COL2, COL3, SLASH1, SLASH2]),
    nl,
    write(N11), write(', '), write(N12), write(', '), write(N13), nl,
    write(N21), write(', '), write(N22), write(', '), write(N23), nl,
    write(N31), write(', '), write(N32), write(', '), write(N33), nl.