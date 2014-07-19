-module(doctor).
-export([start/2]).
-export([loop/2]).

start(Name, Fun) ->
	Doctor = spawn(doctor, loop, [Name, Fun]),
	Doctor ! new,
	Doctor.

loop(Name, Fun) ->
	io:format("Monitoring ~p~n", [Name]), 
	process_flag(trap_exit, true),
	receive
		new -> 
			io:format("Staring ~p~n", [Name]), 
			Pid = spawn_link(Fun),
			io:format("Registering ~p with ~p~n", [Name, Pid]), 
			register(Name, Pid), 
			loop(Name, Fun);
		{'EXIT', From, Reason} -> 
			io:format("The ~p ~p died with reason ~p~n", [Name, From, Reason]), 
			io:format("Restarting ~p~n", [Name]), 
			self() ! new, 
			loop(Name, Fun);
		exit -> exit("die at", erlang:time())
end.

