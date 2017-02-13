package com.example.felip.firebasechat;

/**
 * Created by felip on 12/02/2017.
 */

import java.util.Date;

public class Mensajes {
    private String mensajeTexto;
    private String usuarioMensaje;
    private long mensajeHora;


    public Mensajes(String mensajeTexto, String usuarioMensaje) {
        this.mensajeTexto = mensajeTexto;
        this.usuarioMensaje = usuarioMensaje;
        mensajeHora = new Date().getTime();
    }

    public Mensajes() {
    }

    public String getMensajeTexto() {
        return mensajeTexto;
    }

    public void setMensajeTexto(String mensajeTexto) {
        this.mensajeTexto = mensajeTexto;
    }

    public String getUsuarioMensaje() {
        return usuarioMensaje;
    }

    public void setUsuarioMensaje(String usuarioMensaje) {
        this.usuarioMensaje = usuarioMensaje;
    }

    public long getMensajeHora() {
        return mensajeHora;
    }

    public void setMensajeHora(long mensajeHora) {
        this.mensajeHora = mensajeHora;
    }
}
