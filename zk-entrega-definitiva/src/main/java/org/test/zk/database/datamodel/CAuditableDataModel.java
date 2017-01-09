package org.test.zk.database.datamodel;

import java.time.LocalDate;
import java.time.LocalTime;

public class CAuditableDataModel implements IAuditableDataModel {

    private static final long serialVersionUID = 3960765858795905937L;
    protected String strCreadoPor = null;
    protected LocalDate CreadoFecha = null;
    protected LocalTime CreadoHora = null;
    protected String strActualizadoPor = null;
    protected LocalDate ActualizadoFecha = null;
    protected LocalTime ActualizadoHora = null;

    @Override
    public String getCreadoPor() {
        return strCreadoPor;
    }

    @Override
    public void setCreadoPor(String strCreadoPor) {
        this.strCreadoPor = strCreadoPor;
    }

    @Override
    public LocalDate getCreadoFecha() {
        return CreadoFecha;
    }

    @Override
    public void setCreadoFecha(LocalDate CreadoFecha) {
        this.CreadoFecha = CreadoFecha;
    }

    @Override
    public LocalTime getCreadoHora() {
        return CreadoHora;
    }

    @Override
    public void setCreadoHora(LocalTime CreadoHora) {
        this.CreadoHora = CreadoHora;
    }

    @Override
    public String getActualizadoPor() {
        return strActualizadoPor;
    }

    @Override
    public void setActualizadoPor(String strActualizadoPor) {
        this.strActualizadoPor = strActualizadoPor;
    }

    @Override
    public LocalDate getActualizadoFecha() {
        return ActualizadoFecha;
    }

    @Override
    public void setActualizadoFecha(LocalDate ActualizadoFecha) {
        this.ActualizadoFecha = ActualizadoFecha;
    }

    @Override
    public LocalTime getActualizadoHora() {
        return ActualizadoHora;
    }

    @Override
    public void setActualizadoHora(LocalTime ActualizadoHora) {
        this.ActualizadoHora = ActualizadoHora;
    }

}
