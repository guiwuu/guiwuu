println "array:"
array = [1,2,3,4]
array.collect(
	{println it*it}
)

println "\nmap:"
map = ["j":"Java", "c":"Class"]
map.each(
	{key, value -> println "${key}=${value}"}
)

println "\nmapreduce:"
array = [1,2,3,4,5,6]
square = { it * it }
result = 0
array.collect(square).collect(
	{ println "result is ${result}" ; result += it }
)
println "result is ${result}"

println "\ninc:"
inc = {
    private i=0;
    { -> ++i};
}
a = inc()
b = inc()
println "a: ${a()}"
println "a: ${a()}"
println "b: ${b()}"