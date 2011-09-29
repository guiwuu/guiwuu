@Grab(group='org.codehaus.groovy.modules', module='groovyws', version='0.5.2')
import groovyx.net.ws.WSServer
import java.math.BigDecimal

class SportsLottery {

    String helloWorld() {
        return "hello world"
    }
    
    String Print_Ticket(String str_partner, String batch_id, int repeat, int gameid, int drawid, String drawNo, BigDecimal money, String ticket_parameter, String signStr) {
        println "received"
        return "123"
    }

}

def proxy = new WSServer()
proxy.setNode("SportsLottery","http://localhost:6999/SportsLottery")
proxy.start()
println result