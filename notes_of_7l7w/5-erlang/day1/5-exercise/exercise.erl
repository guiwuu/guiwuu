-module(exercise).
-export([strlen/1]).
-export([count/1]).
-export([log/1]).

strlen("") -> 0;
strlen([_|Tail]) -> 1 + strlen(Tail).

count(0) -> 0;
count(N) -> if
	N < 0 -> -1;
	N > 0 -> 1 + count(N - 1)
end.

log({error, Message}) -> io:fwrite("~s~s~n", ["error: ", Message]);
log(success) -> io:fwrite("success~n").