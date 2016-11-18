package org.test.zk.crud;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.ItemRenderer;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class CCrudController01 extends SelectorComposer<Component> implements ItemRenderer<String> {

    /**
     * 
     */
    private static final long serialVersionUID = 3956844387875236695L;
    // la idea de esto es crear una lista de tareas Decid� simplificar el ejemplo de zkoss porque se complica enormemente
    // para algo relativamente peque�o aparte mi inexperiencia con la herramienta no me deja hacer todo lo que quiero
    // La documentaci�n en internet es insuficiente
    @Wire
    Window windowcrud;
    @Wire
    Button buttonnew;
    @Wire
    Button buttonnewcancel;
    @Wire
    Textbox textboxnuevo;
    @Wire
    Label prioridad;
    @Wire
    Button buttondelete;
    @Wire
    Label index;
    @Wire
    Button buttonmodify;
    @Wire
    Listbox box;
    protected ListModelList<String> datamodeltarea = new ListModelList<String>();
    // Empezemos con simplificar el tipo de lista a una cadena
    @Override
    public void doAfterCompose(Component comp) {
        try {
            super.doAfterCompose(comp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Listen("onClick=#buttonnew") // C de Crear
    public void onClickbuttonnew(Event event) {
        if ((textboxnuevo.getValue().equals("")) || (prioridad.getValue().equals(""))) {
            // Si uno de los dos campos est� vac�o
            Messagebox.show("Error, tarea vac�a o prioridad no seleccionada. Acci�n cancelada", "Aceptar",
                    Messagebox.OK, Messagebox.EXCLAMATION);// Damos mensaje de error
            index.setValue(String.valueOf(box.getSelectedIndex()));// Esto simplemente lo hice por tratar de depurar un error muestra el �ndice seleccionado
        } else {// sino
            Messagebox.show("Tarea agregada", "Aceptar", Messagebox.OK, Messagebox.EXCLAMATION);// Damos mensaje de �xito
            datamodeltarea.add((prioridad.getValue() + " " + textboxnuevo.getValue()));// A�adimos la tarea
            box.setModel(datamodeltarea);// La asignamos a la lista
            index.setValue(String.valueOf(box.getSelectedIndex()));
            box.setSelectedIndex(Integer.parseInt(index.getValue()));
        }
    }

    @Listen("onSelect=#box") // R de Read
    public void onSelectBox(Event event) {
        String test = "";
        test = datamodeltarea.getSelection().toString();// Se copia a una variable local
        textboxnuevo.setValue(test);// Se muestra en la caja de texto, aunque no se por que salen brackets []
        index.setValue(String.valueOf(box.getSelectedIndex()));
    }

    @Listen("onClick=#buttonmodify") // U de Upload
    public void onClickButtonModify(Event event) {
        int x = box.getSelectedIndex();// Tomamos la posici�n seleccionada
        datamodeltarea.remove(x);// Eliminamos los datos viejos
        datamodeltarea.add(x, textboxnuevo.getValue());// a�adimos los datos
        // nuevos
        box.setModel(datamodeltarea);// Fusionamos con el selectbox
        box.setSelectedIndex(x);
    }

    @Listen("onClick=#buttondelete") // D de Delete
    public void onClickButtonDelete(Event event) {// D de Delete
        index.setValue(String.valueOf(box.getSelectedIndex()));
        int x = box.getSelectedIndex();// Buscamos la posici�n seleccionada
        if (x < 0) {// Si la lista est� vac�a
            Messagebox.show("Error, lista vac�a.", "Aceptar", Messagebox.OK, Messagebox.EXCLAMATION);// Mensaje
            // de
            // error
        } else {// sino
            box.removeItemAt(x);// Removemos la posici�n seleccionada
            datamodeltarea.remove(x);// Me tom� como dos horas darme cuenta de
            // que
            box.setModel(datamodeltarea);// me faltaban estas l�nea de c�digo.
        }
        // El datamodel sobreescribe la caja, no cambia con ella (ver l�nea 79)
        // {�Por qu� rayos esto funciona as�?}
    }

    @Listen("onClick=#buttonewcancel") // Este bot�n lo ten�a ya porque intent�
    // que fuesen dos ventanas las que
    // hicieran
    // el trabajo
    public void onClickbuttoncancel(Event event) {// Sobra decir que no supe
        // como pasar datos de una
        // ventana a la otra
        Messagebox.show("Acci�n cancelada.", "Aceptar", Messagebox.OK, Messagebox.EXCLAMATION);// Se
        // obtiene
        // un
        // mensaje
    }

    @Override // No s� si esto haga algo, ya que no trabaj� con objetos, de
    // nuevo, no entend� como hacer render a una listbox, con la
    // selectbox si funcionaba. {�Podr�an hacer un video sobre eso?,
    // San Google no sabe explicarse biencon esto}
    public String render(Component arg0, String arg1, int arg2) throws Exception {
        return arg1;
    }
}
