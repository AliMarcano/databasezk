package org.test.zk.subsystem;

import java.util.List;

import org.test.zk.constantes.Scons;
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

import commonlibs.commonclasses.CLanguage;
import commonlibs.extendedlogger.CExtendedConfigLogger;
import commonlibs.extendedlogger.CExtendedLogger;

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
	    try{
	    	//obtener el logger
	    	CExtendedLogger webAppLogger = CExtendedLogger.getLogger(Scons._Webapp_Logger_Name);
	    	if(webAppLogger!=null){
	    		//escribimos un mensaje en el log
	    		webAppLogger.logMessage("1", CLanguage.translateIf(null, "Webapp endig now"));
	    		//cerrar el log
	    		webAppLogger.flushAndClose();
	    	}
	    	//eliminamos el atributo del webApp
	    	webApp.removeAttribute(Scons._Webapp_Logger_Name);
			
		}catch (Exception e) {
			
		}
	}

	@Override
	public void init(WebApp webApp) throws Exception {
		System.out.println("Web App init");
		try{
			String run_path = webApp.getRealPath(Scons.DIR_WEB_INF)+"/";
			// se encargara de  leer el archivo logger.config.xml
			CExtendedConfigLogger confLogger = new CExtendedConfigLogger();
			//aqui le decimos la ruta de el archivo logger.config.xml
			String confi_path = run_path + Scons.DB_CONF_DIR + Scons._Logger_Conf_Name;
			if(confLogger.loadConfig(confi_path, null, null)){//carga la configuracion			
				//crea el logger
				CExtendedLogger webappLogger = CExtendedLogger.getLogger(Scons._Webapp_Logger_Name);
				
				if(webappLogger.getSetupSet()==false){//para saber si esta configurado o no	
					
					String logpath = run_path+Scons._Logs_Dir+Scons._System_Dir;//aqui le decimos donde crear los archivos para el log WEB-INF/logs/system
					
					//configuramos el loger segun los parametros del archivo logger.config.xml y la ruta para escribir los archivos
					webappLogger.setupLogger( confLogger.getInstanceID(), confLogger.getLogToScreen(), logpath, Scons._Webapp_Logger_File_Log, confLogger.getClassNameMethodName(), confLogger.getExactMatch(), confLogger.getLevel(), confLogger.getLogIP(), confLogger.getLogPort(), confLogger.getHTTPLogURL(), confLogger.getHTTPLogUser(), confLogger.getHTTPLogPassword(), confLogger.getProxyIP(), confLogger.getProxyPort(), confLogger.getProxyUser(), confLogger.getProxyPassword() );
					//guardamos el loger principal en un atributo del webapp
					 webApp.setAttribute( Scons._Webapp_Logger_App_Attribute_Key, webappLogger );
				}
				//escrribimos el log en un archivo en WEB-INF/logs/system/webapps.log
				webappLogger.logMessage( "1" , CLanguage.translateIf( null, "Webapp logger loaded and configured [%s].", Scons._Webapp_Logger_Name ) );
				//clanguage es una clase de las que importamos del señor tomas
			}
			
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
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
