-module(ttt).
-export([line/1]).
-export([board/1]).

line(Line) -> 
	Sum = lists:foldl(fun(E, {A, B, C}) -> 
		case E of 
			x -> {A+1, B, C};
			o -> {A, B+1, C};
			_ -> {A, B, C+1}
		end
	end, {0,0,0}, Line),
	case Sum of 
		{3, 0, 0} -> x;
		{0, 3, 0} -> o;
		{_, _, 0} -> cat;
		_ -> no_winner
	end.

board([A, B, C, 
	   D, E, F,
	   G, H, I]) ->
	R1 = line([A, B, C]), R2 = line([D, E, F]), R3 = line([G, H, I]),
	C1 = line([A, D, G]), C2 = line([B, E, H]), C3 = line([C, F, I]),
	D1 = line([A, E, I]), D2 = line([C, E, G]),
	Lines = [R1, R2, R3, C1, C2, C3, D1, D2],
	Rows = [R1, R2, R3],
	WinX = lists:any(fun(Element) -> Element == x end, Lines),
	WinO = lists:any(fun(Element) -> Element == o end, Lines),
	Cat = lists:all(fun(Element) -> Element == cat end, Rows),
	if
		WinX -> x;
		WinO -> o;
		Cat -> cat;
		true -> no_winner
	end.
