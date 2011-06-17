@Grab(group='org.codehaus.groovy.modules', module='groovyws', version='0.5.2')
import groovyx.net.ws.WSClient

class TryIt {
    
    groovy.swing.SwingBuilder swing = new groovy.swing.SwingBuilder()
    
    def proxy = new WSClient("http://www.webservicex.net/CurrencyConvertor.asmx?WSDL", TryIt.class.classLoader)
    def currency = ['USD', 'EUR', 'CAD', 'GBP', 'AUD', 'SGD']
    def rate = 0.0

    void main() {

        def refresh = swing.action(
          name:'Refresh',
          closure:this.&refreshText,
          mnemonic:'R'
        )

        def frame = swing.frame(title:'Currency Demo') {
          panel {
            label 'Currency rate from '
            comboBox(id:'from', items:currency)
            label ' to '
            comboBox(id:'to', items:currency)
            label ' is '
            textField(id:'currency', columns:10, rate.toString())
            button(text:'Go !', action:refresh)
          }
        }
        frame.pack()
        frame.show()
        
    }
    
    def refreshText(event) {
          rate = proxy.ConversionRate(swing.from.getSelectedItem(), swing.to.getSelectedItem())
          swing.currency.text = rate
    }
    
}