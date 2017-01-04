package org.test.zk.subsystem;

import java.util.List;

import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.WebApp;
import org.zkoss.zk.ui.util.DesktopCleanup;
import org.zkoss.zk.ui.util.DesktopInit;
import org.zkoss.zk.ui.util.ExecutionCleanup;
import org.zkoss.zk.ui.util.ExecutionInit;
import org.zkoss.zk.ui.util.SessionCleanup;
import org.zkoss.zk.ui.util.SessionInit;
import org.zkoss.zk.ui.util.WebAppCleanup;
import org.zkoss.zk.ui.util.WebAppInit;

public class SubSystemEvent implements DesktopInit, DesktopCleanup,SessionInit,SessionCleanup,WebAppInit,WebAppCleanup,ExecutionInit,ExecutionCleanup {

	//algunos metodos seran para implementarlos mas adelante
	//init y clean son de la webapp  y de la app cuando esta en ejecucion
	@Override
	public void cleanup(Execution execution0, Execution execution1, List<Throwable> errs) throws Exception {
		
		System.out.println("  Execution cleanup");//este evento se llama cuando terminamos civilizadamente el servidor
		
	}

	@Override
	public void init(Execution execution0, Execution execution1) throws Exception {
		
		System.out.println("  Execution init");//este evento se llama cuando iniciamos el servidor
	}

	@Override
	public void cleanup(WebApp webApp) throws Exception {
		System.out.println("Web App celanup");
	}

	@Override
	public void init(WebApp webApp) throws Exception {
		System.out.println("Web App init");
		
	}

	@Override
	public void cleanup(Session session) throws Exception {
		System.out.println(" Session cleanup");
		
	}

	@Override
	public void init(Session session, Object object) throws Exception {
		System.out.println(" Session init");
		
	}

	@Override
	public void cleanup(Desktop desktop) throws Exception {
		System.out.println("Desktop Celanup");
		
	}

	@Override
	public void init(Desktop desktop, Object object) throws Exception {
		System.out.println("Desktop init");
		
	}
	
	

}
