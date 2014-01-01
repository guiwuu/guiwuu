-module(map).
-export([map_get/2]).

map_get(Map, Key) ->
	F = lists:filter(fun({K, _}) -> K == Key end, Map),
	case F of
		[] -> nil;
		[{_, V}] -> V
	end.