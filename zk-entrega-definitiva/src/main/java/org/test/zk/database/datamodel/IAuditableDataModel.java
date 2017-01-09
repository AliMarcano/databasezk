package org.test.zk.database.datamodel;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

public interface IAuditableDataModel extends Serializable {
    public String getCreadoPor();
    public void setCreadoPor(final String strCreadoPor);
    
    public LocalDate getCreadoFecha();
    public void setCreadoFecha(final LocalDate CreadoFecha);
    
    public LocalTime getCreadoHora();
    public void setCreadoHora(final LocalTime CreadoHora);
    
    public String getActualizadoPor();
    public void setActualizadoPor(final String strActualizadoPor);
    
    public LocalDate getActualizadoFecha();
    public void setActualizadoFecha(final LocalDate ActualizadoFecha);
    
    public LocalTime getActualizadoHora();
    public void setActualizadoHora(final LocalTime ActualizadoHora);
}
