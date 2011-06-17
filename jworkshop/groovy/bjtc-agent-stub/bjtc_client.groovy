@Grab(group='org.codehaus.groovy.modules', module='groovyws', version='0.5.2')
import groovyx.net.ws.WSClient

def proxy = new WSClient("http://114.251.57.106/proxyService/Service.asmx?wsdl", this.class.classLoader)
proxy.setConnectionTimeout(10)
proxy.initialize()

result = proxy.GD_Login ("001","111111","1042220977315297503")
println result.getCode()

result = proxy.GD_GetGame(0)
println result.getCode()

result = proxy.GD_Logout(0)
println result.getCode()