package org.test.zk.login.controller;



import java.io.File;
import java.time.LocalDateTime;

import org.test.zk.constantes.Scons;
import org.test.zk.database.CDatabaseConnection;
import org.test.zk.database.DBconfig;
import org.test.zk.database.dao.operatorDAO;
import org.test.zk.database.datamodel.TBLOperator;
import org.zkoss.mesg.Messageable;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;

import commonlibs.commonclasses.CLanguage;
import commonlibs.extendedlogger.CExtendedLogger;
import commonlibs.utils.ZKUtilities;

public class logincontroller extends SelectorComposer<Component> {   
    
   /**
	 * 
	 */
	private static final long serialVersionUID = -6012468690522602890L;
    public static final String dbkey = "database";  
    protected Execution execution = Executions.getCurrent();
    protected CDatabaseConnection conn = null;
    protected CExtendedLogger controllogger=null;
    protected CLanguage controllanguaje=null;
   
    @Wire
    Textbox Tuser;

    @Wire
    Textbox Tpassword;
    
    @Wire
    Label LMessage;
    
    
    @Override
	public void doAfterCompose(Component comp) {
        try {
            super.doAfterCompose(comp);
            controllogger = (CExtendedLogger) Sessions.getCurrent().getWebApp().getAttribute(Scons._Webapp_Logger_App_Attribute_Key);
        
        
        
        }catch (Exception e) {
            if(controllogger!=null){
            	controllogger.logException("-1021", e.getMessage(),e);
            	
            }
        }

    }

	@Listen("onClick=#BLogin")
    public void onClickBlogin (Event event){
		try{
			//aqui nos conectamos a laDB y verificamos que el user exista y la password sea correcta
			final String username= ZKUtilities.getTextBoxValue(Tuser, controllogger);
			final String userpassword= ZKUtilities.getTextBoxValue(Tpassword, controllogger);
			Session sesion = Sessions.getCurrent();
			if( username.isEmpty()== false && userpassword.isEmpty() ==false ){
				conn = new CDatabaseConnection(); 
				DBconfig config=new DBconfig();
				
				String patch = Sessions.getCurrent().getWebApp().getRealPath(Scons.DIR_WEB_INF) + File.separator+ Scons.DB_CONF_DIR +File.separator;
				 if( config.loadConfig(patch+Scons.DB_CONF_FILE,controllogger, controllanguaje)){//nulo por que en este momento no se necesita
			            
					 
					 	if(conn.makeConnectionToDB(config,controllogger,controllanguaje)){//Si se logra conectar  
					 		TBLOperator operador= operatorDAO.checkvalid(conn, username, userpassword, controllogger, controllanguaje);
					 		
					 		if(operador!=null){
					 			//se da un mensaje de bienvenida
					 			LMessage.setSclass("");
					 			LMessage.setValue("Welcome "+operador.getName()+"!");
					 			
					 			
					 			
					 			//si entra es porque es correcto tanto el usuario y la password entonces creamos la session
					 			//usando la session para guardar el usuario
					 			//guardamos la conexion
					 			
					 			sesion.setAttribute(dbkey, conn);
					 			//guardamos la identidad del operador
					 			sesion.setAttribute(Scons._User_Credential_Session_Key, operador);
					 			//guardamos la hora y la fecha
					 			sesion.setAttribute(Scons._Login_Date_Time_Session_Key, LocalDateTime.now().toString());
					 			
					 			//actualizamos la DB
					 			operatorDAO.updatelastlogin(conn, operador.getId(), controllogger, controllanguaje);
					 			sesion.setAttribute(Scons._User_Credential_Session_Key, operador);
					 			//redireccionamos hacia el home o zul de inicio de la aplicacion al iniciar session
					 			Executions.sendRedirect("/views/home/home.zul");
					 		
					 		}else{
					 			 LMessage.setValue("error en el usuaro o password");
					 			 //Messagebox.show("error en el usuaro o password");//Mensaje de fracaso
					 		}
			            }else{
			           
			                Messagebox.show("Conexion fallida!.");//Mensaje de fracaso
			          
			            }
			            
				 }else{
					 Messagebox.show("Error al leer el archivo de configuracion");
				 }
			}else{
				
			}
					
		}catch (Exception e) {
            if(controllogger!=null){
            	controllogger.logException("-1021", e.getMessage(),e);
            	
            }
		}
		
		
	}
	
}
