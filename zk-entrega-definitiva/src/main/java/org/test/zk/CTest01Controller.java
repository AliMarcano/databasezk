package org.test.zk;

import java.time.LocalDate;

import org.test.zk.datamodel.TBLPerson;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.ItemRenderer;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Selectbox;
import org.zkoss.zul.Window;

public class CTest01Controller extends SelectorComposer<Component> implements ItemRenderer<TBLPerson> {

    /**
     * 
     */
    private static final long serialVersionUID = -258902408111073465L;
    boolean aux = true;
    String ci = "";//Variables internas para agregar personas
    String Nombre = "";
    String Apellido = "";
    int Telefono = 0;
    int check = 0;
    @Wire//Cableado de todos los componentes necesarios
    Button buttontest01;
    @Wire
    Window windowtest01;
    @Wire
    Button buttontest02;
    @Wire
    Button buttontest05;
    @Wire
    Button buttontest06;
    @Wire
    Selectbox selectbox1;
    @Wire
    Selectbox selectbox2;
    protected ListModelList<String> datamodel = new ListModelList<String>();//Modelo de Cadena, para los n�meros
    protected ListModelList<TBLPerson> datamodelpersona = new ListModelList<TBLPerson>();//Modelo de persona    
    
    @Listen("onClick=#buttontest02")//Prueba de modificaci�n b�sica de bot�n
    public void onClickButtonTest02(Event event) {
        buttontest02.setLabel("�Ment�!");//S�lo se modifica el value del boton
    }

    @Listen("onClick=#buttontest05")//Adici�n de n�meros a datamodel
    public void onClickButtonTest05(Event event) {
        check = check + 1;//Se amuenta el contador
        datamodel.add(Integer.toString(check));//Se a�ade al modelo
        selectbox1.setModel(datamodel);//Se moldea la caja de selecci�n
        datamodel.addToSelection(Integer.toString(check));
        selectbox1.setSelectedIndex(0);
    }

    @Listen("onClick=#buttontest06")//A�adir una nueva persona
    public void onClickButtonTest06(Event event) {
        ci = ci + 1;//Se crean valores �nicos para cada persona
        Nombre = Nombre.concat("a");
        Apellido = Apellido.concat("b");
        Telefono = Telefono - 1;
        datamodelpersona.add(//Se crea una nueva persona con los datos �nicos y se asigna al modelo de personas
                new TBLPerson(ci, Nombre, Apellido, Integer.toString(Telefono), 1, LocalDate.parse("1995-06-28"), "Yo"));
        selectbox2.setModel(datamodelpersona);//Se asigna el modelo a la caja de selecci�n
        selectbox2.setSelectedIndex(0);//Se modiica el �ndice
        selectbox2.setItemRenderer(this);//Se renderiza el objeto
    }

    @Listen("onSelect=#selectbox1")
    public void onSelectselectbox1(Event event) {//Asignaci�n de datos al t�tulo de la ventana
        if ((selectbox1.getSelectedIndex() >= 0) && (selectbox1.getSelectedIndex() <= 3)) {
            windowtest01.setTitle(datamodel.get(selectbox1.getSelectedIndex()));
        }
    }

    @Listen("onSelect=#selectbox2")//Asignaci�n de datos al t�tulo de la ventana
    public void onSelectselectbox2(Event event) {
        TBLPerson Seleccion = datamodelpersona.get(selectbox2.getSelectedIndex());
        windowtest01.setTitle(Seleccion.getStrci() + " " + Seleccion.getnombre() + " " + Seleccion.getapellido());
    }

    @Override
    public String render(Component arg0, TBLPerson arg1, int arg2) throws Exception {//Renderizador
        return arg1.getnombre() + " " + arg1.getapellido() + " " + arg1.getStrci() ;
    }
}
