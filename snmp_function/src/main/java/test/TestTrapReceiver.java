package test;



import java.io.IOException;
import java.net.UnknownHostException;

import org.snmp4j.CommandResponder;

import Eastgate.snmp_function.ProcessPdu;
import Eastgate.snmp_function.ProcessPduMessage;
import Eastgate.snmp_function.TrapReceiver;

public class TestTrapReceiver  {

	public static void main(String[] args) throws UnknownHostException, IOException {
		TrapReceiver trapReceiver = new TrapReceiver();
		CommandResponder[] crs = {new ProcessPdu(), new ProcessPduMessage()};
		trapReceiver.setMultiCommandResponder(crs);
	}
}
