package Eastgate.snmp_function;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;

import org.snmp4j.CommandResponder;
import org.snmp4j.MessageDispatcherImpl;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.mp.MPv1;
import org.snmp4j.mp.MPv2c;
import org.snmp4j.mp.MPv3;
import org.snmp4j.security.SecurityModels;
import org.snmp4j.security.SecurityProtocols;
import org.snmp4j.security.USM;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.TcpAddress;
import org.snmp4j.smi.UdpAddress;
import org.snmp4j.transport.DefaultTcpTransportMapping;
import org.snmp4j.transport.DefaultUdpTransportMapping;
import org.snmp4j.util.MultiThreadedMessageDispatcher;
import org.snmp4j.util.ThreadPool;

public class TrapReceiver {

	private Address listenAddress;
	private Snmp snmp;

	public TrapReceiver() throws UnknownHostException, IOException {
		listenAddress = GenericAddress.parse("localhost/162");
	}

	public TrapReceiver(String address) throws UnknownHostException, IOException {
		listenAddress = GenericAddress.parse(address);
	}

	public void init() throws UnknownHostException, IOException {
		ThreadPool threadPool = ThreadPool.create("TrapPool", 2);
		MultiThreadedMessageDispatcher dispatcher = new MultiThreadedMessageDispatcher(threadPool,
				new MessageDispatcherImpl());
		TransportMapping transport;
		if (listenAddress instanceof UdpAddress) {
			transport = new DefaultUdpTransportMapping((UdpAddress) listenAddress);
		} else {
			transport = new DefaultTcpTransportMapping((TcpAddress) listenAddress);
		}
		snmp = new Snmp(dispatcher, transport);
		snmp.getMessageDispatcher().addMessageProcessingModel(new MPv1());
		snmp.getMessageDispatcher().addMessageProcessingModel(new MPv2c());
		snmp.getMessageDispatcher().addMessageProcessingModel(new MPv3());
		USM usm = new USM(SecurityProtocols.getInstance(), new OctetString(MPv3.createLocalEngineID()), 0);
		SecurityModels.getInstance().addSecurityModel(usm);
		snmp.listen();
	}

	public void setCommandResponder(CommandResponder cr) throws UnknownHostException, IOException {
		snmp.addCommandResponder(cr);
	}

	public void setMultiCommandResponder(List<CommandResponder> crs) throws UnknownHostException, IOException {
		for (CommandResponder cr : crs) {
			setCommandResponder(cr);
		}
	}
}