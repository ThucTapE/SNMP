package Eastgate.snmp_function;

import org.snmp4j.CommandResponder;
import org.snmp4j.CommandResponderEvent;

public class ProcessPduMessage implements CommandResponder {
	public void processPdu(CommandResponderEvent event) {
		System.out.println("This is trapReceiver function");
	}
}
