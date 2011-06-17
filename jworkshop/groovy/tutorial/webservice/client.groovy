@Grab(group='org.codehaus.groovy.modules', module='groovyws', version='0.5.2')
import groovyx.net.ws.WSClient

def proxy = new WSClient("http://localhost:6980/MathService?wsdl", this.class.classLoader)
proxy.initialize() // from 0.5.0
def result = proxy.add(1.0 as double, 2.0 as double)
println result
assert (result == 3.0)

result = proxy.square(3.0 as double)
println result
assert (result == 9.0)