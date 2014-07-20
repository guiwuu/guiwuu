-module(log_service).
-behaviour(gen_server).

-export([new/2]).
-export([log/2]).
-export([init/1, handle_call/3, handle_cast/2, code_change/3, handle_info/2, terminate/2]).

new(Logger, Filename) ->
    gen_server:start_link({local, Logger}, log_service, [Filename], []).

init(Filename) ->
    file:open(Filename, [write]).

log(Logger, Msg) ->
    gen_server:call(Logger, {log, Msg}).

handle_call({log, Msg}, _From, File) ->
	io:format(File, "~p ~p~n", [erlang:time(), Msg]),
    {reply, ok, File}.

handle_cast(_, _) ->
	undef.

code_change(_, _, _) ->
	undef.

handle_info(_, _) ->
	undef.

terminate(_, _) ->
	undef.