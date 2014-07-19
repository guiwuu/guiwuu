-module(roulette_sup).
-behaviour(supervisor).

-export([start/0]).
-export([init/1]).
-export([start_roulette/0]).
-export([roulette_loop/0]).

start() ->
	io:format("roulette_sup start entering...~n"),
    supervisor:start_link(
    	roulette_sup, 					% Callback module name
    	[]								% Callback function arguments
    ).

init(_Args) ->							% Fixed callback function
	io:format("roulette_sup init entering...~n"),
    {ok, 
    	{								% StartSpec
    		{one_for_one, 2, 10},		% Strategy = {How, Max, Within}
    		[{							% ChildSpec
    			roulette_worker,		% Id       = internal id  
    			{roulette_sup, start_roulette, []},	% StartFun = {M, F, A}
        		permanent,				% Restart  = permanent | transient | temporary 
        		brutal_kill,			% Shutdown = brutal_kill | int() >= 0 | infinity 
        		worker,					% Type     = worker | supervisor  
        		[roulette_sup]				% Modules  = [Module] | dynamic
        	}]			
        }
     }.		

% start roulette
start_roulette() ->
    io:format("roulette start entering...~n"),
    Pid = spawn_link(fun roulette_loop/0),
    register(receiver, Pid),
    {ok, Pid}.

% send a number, 1-6    
roulette_loop() ->
    io:format("roulette loop entering...~n"),
    receive                                 
        3 -> io:format("bang.~n"), exit({roulette_sup,die,at,erlang:time()});
        _ -> io:format("click~n"), roulette_loop()
end.	  