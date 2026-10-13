package com.lunacalero.MiBandaCriminal;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * Contenedor raíz para exportar/importar la banda completa en XML o JSON.
 * @author Samuel
 */
@JacksonXmlRootElement(localName = "plantilla")
public class PlantillaBanda {

    @JacksonXmlElementWrapper(localName = "miembros")
    @JacksonXmlProperty(localName = "miembro")
    private List<MiembroBanda> miembros = new ArrayList<>();

    public PlantillaBanda() {
    }

    public PlantillaBanda(List<MiembroBanda> miembros) {
        this.miembros = miembros;
    }

    public List<MiembroBanda> getMiembros() {
        return miembros;
    }

    public void setMiembros(List<MiembroBanda> miembros) {
        this.miembros = miembros;
    }
}