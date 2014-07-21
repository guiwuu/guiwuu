-module(translate_service).
-behaviour(gen_server).
-export([start/2]).
-export([recv/1]).
-export([connect/1]).
-export([translate/1]).
-export([init/1, handle_call/3, handle_cast/2, code_change/3, handle_info/2, terminate/2]).

-define(TCP_OPTS, [binary, {packet, raw}, {nodelay, true}, {reuseaddr, true}, {active, once}]).

start(Service, Port) ->
    gen_server:start_link({local, Service}, ?MODULE, Port, []).

init(Port) ->
    case gen_tcp:listen(Port, ?TCP_OPTS) of
        {ok, Listen} -> 
        	spawn(?MODULE, connect, [Listen]),
    		io:format("~p Server Started.~n", [erlang:localtime()]);
        Error ->
            io:format("Error: ~p~n", [Error])
    end,
    {ok, self()}.

connect(Listen) ->
    {ok, Socket} = gen_tcp:accept(Listen),
    inet:setopts(Socket, ?TCP_OPTS),
    spawn(fun() -> connect(Listen) end),
    recv(Socket),
    gen_tcp:close(Socket).

recv(Socket) ->
    inet:setopts(Socket, [{active, once}]),
    receive
        {tcp, Socket, Data} ->
            io:format("~p ~p ~p~n", [inet:peername(Socket), erlang:localtime(), Data]),
            Result = translate(binary:bin_to_list(Data)),
            gen_tcp:send(Socket, binary:list_to_bin("\r\n" ++ Result ++ "\r\n>")),
            recv(Socket);
        {tcp_closed, Socket} ->
            io:format("~p Client Disconnected.~n", [erlang:localtime()])
    end.

translate(Word) ->
    case Word of
        "a" -> "apple";
        "b" -> "boy";
        _ -> "I don't understand: " ++ Word
    end.

handle_call(_, _, _) ->
	undef.

handle_cast(_, _) ->
	undef.

code_change(_, _, _) ->
	undef.

handle_info(_, _) ->
	undef.

terminate(_, _) ->
	undef.