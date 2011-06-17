@Grab(group='org.codehaus.groovy.modules', module='groovyws', version='0.5.2')
import groovyx.net.ws.WSClient
import java.math.BigDecimal

def proxy = new WSClient("http://localhost:6999/SportsLottery?wsdl", this.class.classLoader)
proxy.initialize()
def result = proxy.helloWorld("ccccccccc")
println result

def money = BigDecimal.ONE
result = proxy.Print_Ticket("1000","1_2_3",0,61,256,"00003",money as BigDecimal,"123","456")
println result