package org.test.zk.database.config.datamodel;

import java.io.File;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.test.zk.constantes.Scons;
import org.test.zk.database.CDatabaseConnection;
import org.test.zk.database.DBconfig;
import org.test.zk.database.dao.PersonDAO;
import org.test.zk.database.datamodel.TBLPerson;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import commonlibs.commonclasses.CLanguage;
import commonlibs.extendedlogger.CExtendedLogger;

public class CManagerController extends SelectorComposer<Component> {
//comentando para subir
//las librerias Common fueron creadas por el señor tomas
//la libreria de log permite seguir el proceso de un programa, 
//los breack point y el debugger son una herramienta que nos ayuda mucho 
//pero en produccion no se pueden usar normalmente, se puede hacer cn un debugger externo pero 
//es de baja seguridad y pone en riesgo el servidor
//por lo que las bitacoras o log son archivos que van guardando un registro de errores
//pero no solo eso lo que se guarde dependera de lo que el programador requiera	
	
	
	
    private static final long serialVersionUID = -1591648938821366036L;
    protected ListModelList<TBLPerson> datamodelpersona = null; //new ListModelList<TBLPerson>();
    @Wire
    Button buttonconnection;
    @Wire
    Button buttoncargar;
    @Wire
    Button buttonadd;
    @Wire
    Button buttonmodify;
    @Wire
    Button buttondelete;
    @Wire
    Listbox listboxpersons;    
    @Wire
    Window windowmanager;
    public static final String dbkey = "database";  
    protected Execution execution = Executions.getCurrent();
    protected CDatabaseConnection database = null;
    protected CExtendedLogger controllogger=null;
    protected CLanguage controllanguaje=null;
    
    public class MyRenderer implements ListitemRenderer<TBLPerson> {    
        @Override
        public void render(Listitem listitem, TBLPerson persona, int arg2) throws Exception {
            try {

                Listcell cell = new Listcell();// Se crea una nueva celda
                cell.setLabel(persona.getStrci());// Se le asigna un valor a
                                                  // la// celda
                listitem.appendChild(cell);// Se a�ade la celda a la lista
                cell = new Listcell();// Se crea una nueva celda
                cell.setLabel(persona.getnombre());// Se le asigna un valor a
                                                   // la// celda
                listitem.appendChild(cell);// Se a�ade la celda a la lista
                cell = new Listcell();// Se crea una nueva celda
                cell.setLabel(persona.getapellido());// Se le asigna un valor a

                listitem.appendChild(cell);// Se a�ade la celda a la lista                
                cell = new Listcell();// Se crea una nueva celda
                cell.setLabel(persona.getGender() == 0 ? "Femenino" : "Masculino");// Se le asigna un valor a
                                                     // la celda                
                listitem.appendChild(cell);// Se a�ade la celda a la lista
                cell = new Listcell();// Se crea una nueva celda
                cell.setLabel( persona.getCumple().toString());// Se le asigna un valor a
                                                     // la celda
                listitem.appendChild(cell);// Se a�ade la celda a la lista
                cell = new Listcell();// Se crea una nueva celda
                cell.setLabel(persona.getComment());// Se le asigna un valor a
                                                   // la// celda
                listitem.appendChild(cell);// Se a�ade la celda a la lista
                
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }

        /*
         * public void render(Listitem listitem, CPerson data, int index) {
         * Listcell cell = new Listcell(); listitem.appendChild(cell); if (data
         * instanceof String[]) { cell.appendChild(new Label(((String[])
         * data)[0].toString())); } else if (data instanceof String) {/
         * cell.appendChild(new Label(data.toString())); } else {
         * cell.appendChild(new Label("UNKNOW:" + data.toString())); } } }
         */}

    public void doAfterCompose(Component comp) {
        try {
            super.doAfterCompose(comp);            
           /* TBLPerson persona1 = new TBLPerson("1", "Roger", "Paesani", "04129193576",1,LocalDate.parse("1995-08-28"),"Yo");
            TBLPerson persona2 = new TBLPerson("2", "Chito", "Narv�ez", "6942069",1,LocalDate.parse("2013-02-21"),"Perro");
            TBLPerson persona3 = new TBLPerson("3", "Asunci�n", "Narv�ez", "04160980720",0,LocalDate.parse("1967-05-28"),"Mam�");
            datamodelpersona.setMultiple(true);
            datamodelpersona.add(persona1);
            datamodelpersona.add(persona2);
            datamodelpersona.add(persona3);*/
            listboxpersons.setModel(datamodelpersona);
            listboxpersons.setItemRenderer(new MyRenderer());
            Session sesion = Sessions.getCurrent();//Se crea variable sesion
            //obtenemos el loger del webapp y guardamos una referencia eb la variable
            controllogger = (CExtendedLogger) Sessions.getCurrent().getWebApp().getAttribute(Scons._Webapp_Logger_App_Attribute_Key);
            if(sesion.getAttribute(dbkey)instanceof CDatabaseConnection){
                //recuperamos la session anterior
            	database=(CDatabaseConnection) sesion.getAttribute(dbkey);
                buttonconnection.setLabel("Desconectar");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Listen("onClick=#buttoncargar")
    public void onClickbuttoncargar (Event event){
        listboxpersons.setModel((ListModelList<?>) null);//Se limpia la listbox
        Session sesion = Sessions.getCurrent();//Se recupera la sesi�n
        if(sesion.getAttribute(dbkey)instanceof CDatabaseConnection){//Si se est� conectado
            database=(CDatabaseConnection) sesion.getAttribute(dbkey);//Se asigna la direcci�n de la bd
            List<TBLPerson>listData=PersonDAO.searchData(database, controllogger, controllanguaje);//Se llama al m�todo de b�squeda y se asigna a la lista de persona            
            datamodelpersona=new ListModelList<TBLPerson>(listData);//Se crea un nuevo modelo con la lista de personas
            listboxpersons.setModel(datamodelpersona);//se le asigna al listbox
            listboxpersons.setItemRenderer((new MyRenderer()));
        }
    }
    @Listen("onClick=#buttonconnection")    
    public void onClickbuttonconnection(Event event){
        Session sesion = Sessions.getCurrent();
        //obtenemos el loger del objeto webapp
        
        if(database==null){//Si se va a conectar            
                database = new CDatabaseConnection();//Se instancia
                DBconfig confi = new DBconfig();
                //obtenemos la ruta del archivo
                String patch = Sessions.getCurrent().getWebApp().getRealPath(Scons.DIR_WEB_INF) + File.separator+ Scons.DB_CONF_DIR +File.separator;
                
         if( confi.loadConfig(patch+Scons.DB_CONF_FILE,controllogger, controllanguaje)){//nulo por que en este momento no se necesita
            if(database.makeConnectionToDB(confi,controllogger,controllanguaje)){//Si se logra conectar  
            	
            	//guardamos la conf en database
            	database.setDBConnectionConfig(confi, controllogger, null);
            	
                sesion.setAttribute(dbkey, database);//Se crea la sesi�n
                buttonconnection.setLabel("Desconectar");//Se cambia el contexto                
                Messagebox.show("       �Conexi�n exitosa!.", "Aceptar", Messagebox.OK, Messagebox.EXCLAMATION);//Mensaje de exito
                Events.echoEvent("onClick", buttoncargar, null);
            }else{//sino 
                Messagebox.show("       �Conexi�n fallida!.", "Aceptar", Messagebox.OK, Messagebox.EXCLAMATION);//Mensaje de fracaso
            }
          }
          else{
        	  Messagebox.show("error al leer el archivo");
          }
        }else{//Si se va a desconectar
         if (database!=null){//Si la variable no es nula
        	 //obtenemos el loger de webapp
        	 
             sesion.setAttribute(dbkey, null);//Se limpia la sesi�n
             buttonconnection.setLabel("Conectar");//Se cambia el contexto
             if(database.closeConnectionToDB(controllogger, null)){//Se cierra la conexi�n
                 database=null;
                 Messagebox.show("       �Conexi�n cerrada!.", "Aceptar", Messagebox.OK, Messagebox.EXCLAMATION);
                 listboxpersons.setModel((ListModelList<?>) null);//Se limpia la listbox
             }else{
                 Messagebox.show("       �Fallo al cerrar conexi�n!.", "Aceptar", Messagebox.OK, Messagebox.EXCLAMATION);
             }
         }else{
             Messagebox.show("       �No est�s conectado!.", "Aceptar", Messagebox.OK, Messagebox.EXCLAMATION);
         }
        }
    }
    
    @Listen("onClick=#buttonadd")
    public void onClickbuttonadd(Event event) {
        TBLPerson vacio = new TBLPerson(null, null, null, null, 0, null, null);
        listboxpersons.setSelectedIndex(-1);        
        Map<String,Object> arg = new HashMap<String,Object>();
        arg.put("personToModify", vacio);
        arg.put("buttonadd", buttonadd);
        arg.put("buttonmodify", buttonmodify);
        arg.put("ModifyModel", datamodelpersona);
        Window win = (Window) Executions.createComponents("/views/person/editor/dialog.zul", null,arg);
        win.doModal();
    }    
    @Listen("onClick=#buttonmodify")
    public void onClickbuttonmodify(Event event) {
        Set<TBLPerson> selecteditems = datamodelpersona.getSelection();//Se crea una lista de personas con los elementos seleccionados
        if ((selecteditems != null) && (datamodelpersona.getSelection().size() > 0)) {//Si hay elementos
            TBLPerson person = selecteditems.iterator().next();
            Map<String,Object> arg = new HashMap<String,Object>();
            arg.put("personToModify", person);
            arg.put("buttonadd", buttonadd);
            arg.put("buttonmodify", buttonmodify);
            arg.put("PersonaCi", person.getStrci());
            Window win = (Window) Executions.createComponents("/views/person/editor/dialog.zul", null , arg);
            win.doModal();                        
        }else{ //sino
            Messagebox.show("       Error, no hay selecci�n.", "Aceptar", Messagebox.OK, Messagebox.EXCLAMATION);
            //Se da un mensaje de error
        }
    }
    @Listen("onKek=#buttonmodify")
    public void onDialogFinishbuttonmodify(Event event) {
        TBLPerson personToModif = (TBLPerson) event.getData();
        int index = listboxpersons.getSelectedIndex();
        if (index>=0){
            datamodelpersona.remove(index);
            datamodelpersona.add(index, personToModif);
            listboxpersons.setModel(datamodelpersona);
            listboxpersons.setItemRenderer((new MyRenderer()));
            PersonDAO.updateData(database, personToModif,controllogger,controllanguaje);
            Messagebox.show("       �Persona modificada!.", "Aceptar", Messagebox.OK, Messagebox.EXCLAMATION);
            Events.echoEvent("onClick", buttoncargar, null);
        }else{
            PersonDAO.insertData(database, personToModif,controllogger,controllanguaje);
            datamodelpersona.add(personToModif);
            listboxpersons.setModel(datamodelpersona);
            listboxpersons.setItemRenderer((new MyRenderer()));
            Messagebox.show("       �Lista agregada!.", "Aceptar", Messagebox.OK, Messagebox.EXCLAMATION);
            //hacemos que se refresque la vista
            Events.echoEvent( new Event("onClick", buttoncargar));
        }        
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Listen("onClick=#buttondelete")
    public void onClickbuttondelete(Event event) {//Si se hace click en el bot�n borrar
        Set<TBLPerson> selecteditems = datamodelpersona.getSelection();//Se crea una lista de personas con los elementos seleccionados
        if ((selecteditems != null) && (datamodelpersona.getSelection().size() > 0)) {//Si hay elementos
            String buffer = null;//Se crea un buffer
            for (TBLPerson persona : selecteditems) {//Por cada persona seleccionada
                if (buffer == null) {//Si el buffer est� vac�o
                    buffer = persona.getStrci() + " " + persona.getnombre() + " " + persona.getapellido() + "";//Se toma el primer elemento
                } else {//sino
                    buffer = buffer + "\n" + persona.getStrci() + " " + persona.getnombre() + " "+ persona.getapellido()+ " ";//se toman el siguiente
                    // Messagebox.show(buffer, "Aceptar", Messagebox.OK,
                    // Messagebox.EXCLAMATION);
                }//fin si
            }//fin por
                Messagebox.show("�Seguro que desea eliminar los " + Integer.toString(selecteditems.size())+ " elementos seleccionados?\n"+buffer,"Confirm Dialog", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION,new org.zkoss.zk.ui.event.EventListener() {//Validaci�n
                            public void onEvent(Event evt) throws InterruptedException {
                                if (evt.getName().equals("onOK")) {//Si la respuesta es s�
                                    alert("�Elementos borrados!");//Se da un aviso
                                    //mientras haya elementos seleccionados
                                        TBLPerson persona = selecteditems.iterator().next();//se toma el elemento
                                        //selecteditems.iterator().remove();
                                        PersonDAO.deleteData(database, persona.getStrci(),controllogger,controllanguaje);
                                        //hacemos que se refresque la vista
                                        Events.echoEvent("onClick", buttoncargar, null);
                                   
                                }//fin si
                            }
                        });
            }else{ //sino
            Messagebox.show("       Error, no hay selecci�n.", "Aceptar", Messagebox.OK, Messagebox.EXCLAMATION);
            //Se da un mensaje de error
        }
    }
}   
