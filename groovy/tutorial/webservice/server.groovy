@Grab(group='org.codehaus.groovy.modules', module='groovyws', version='0.5.2')
import groovyx.net.ws.WSServer

class MathService {
  double add(double arg0, double arg1) {
    return (arg0 + arg1)
  }
  double square(double arg0) {
    return (arg0 * arg0)
  }
}

def server = new WSServer()
server.setNode("MathService", "http://localhost:6980/MathService")
server.start()