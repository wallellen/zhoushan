package jt.readerdata.main;

import jt.readerdata.collector.ReaderCollectorServer;

public class StartReaderCenter {
	
	
	public void start( ) {
		// TODO Auto-generated method stub

		new ReaderCollectorServer().startServer();
		
	}

}
