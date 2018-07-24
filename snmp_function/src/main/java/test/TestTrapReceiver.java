package test;



import Eastgate.snmp_function.TrapReceiver;

public class TestTrapReceiver  {

	public static void main(String[] args) {
		TrapReceiver trapReceiver = new TrapReceiver();
		trapReceiver.run();
	}
}
