musician(armorm, guitar).
musician(bergwolf, drum).
musician(langlang, piano).
musician(diancang, guitar).

musician_style(armorm, rock).
musician_style(bergwolf, rock).
musician_style(langlang, classic).
musician_style(diancang, popular).

musician_suggest(X, Y, Z) :- musician(X, Y), musician_style(X, Z).