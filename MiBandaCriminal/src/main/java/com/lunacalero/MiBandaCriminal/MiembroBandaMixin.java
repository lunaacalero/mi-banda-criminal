package com.lunacalero.MiBandaCriminal;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Mixin de Jackson: indica cómo serializar la jerarquía de MiembroBanda
 * sin tener que modificar las clases originales. Añade un campo "tipo" que
 * permite saber qué subclase hay que crear al leer un XML/JSON.
 *
 * @author Samuel
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "tipo")
@JsonSubTypes({
        @JsonSubTypes.Type(value = PilotoHuida.class, name = "PILOTO"),
        @JsonSubTypes.Type(value = EspecialistaArmas.class, name = "ESPECIALISTA"),
        @JsonSubTypes.Type(value = Hacker.class, name = "HACKER")
})
public abstract class MiembroBandaMixin {
}