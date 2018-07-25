package Eastgate.snmp_function;

import java.util.Vector;

import org.snmp4j.CommandResponder;
import org.snmp4j.CommandResponderEvent;
import org.snmp4j.smi.VariableBinding;

public class ProcessPdu implements CommandResponder{

	public void processPdu(CommandResponderEvent event) {
		if (event == null || event.getPDU() == null) {
			System.out.println("[Warn] ResponderEvent or PDU is null");
			return;
		}
		Vector<? extends VariableBinding> vbs = event.getPDU().getVariableBindings();
		for (VariableBinding vb : vbs) {
			System.out.println(vb.getOid().toString() + " = " + vb.getVariable().toString());
		}
	}	
}


