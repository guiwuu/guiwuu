overturn([X],[X]).
overturn([Head|Tail], RList) :- overturn(Tail, RTail), append(RTail, [Head], RList).