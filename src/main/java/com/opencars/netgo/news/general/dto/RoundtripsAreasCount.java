package com.opencars.netgo.news.general.dto;

public class RoundtripsAreasCount {

    RoundtripsCount IT;
    RoundtripsCount Postventa;
    RoundtripsCount Finanzas;
    RoundtripsCount CCHH;
    RoundtripsCount Comercial;
    RoundtripsCount Administracion;
    RoundtripsCount Contabilidad;

    public RoundtripsAreasCount() {
    }

    public RoundtripsAreasCount(RoundtripsCount IT, RoundtripsCount postventa, RoundtripsCount finanzas, RoundtripsCount CCHH, RoundtripsCount comercial, RoundtripsCount administracion, RoundtripsCount contabilidad) {
        this.IT = IT;
        this.Postventa = postventa;
        this.Finanzas = finanzas;
        this.CCHH = CCHH;
        this.Comercial = comercial;
        this.Administracion = administracion;
        this.Contabilidad = contabilidad;
    }

    public RoundtripsCount getIT() {
        return IT;
    }

    public void setIT(RoundtripsCount IT) {
        this.IT = IT;
    }

    public RoundtripsCount getPostventa() {
        return Postventa;
    }

    public void setPostventa(RoundtripsCount postventa) {
        Postventa = postventa;
    }

    public RoundtripsCount getFinanzas() {
        return Finanzas;
    }

    public void setFinanzas(RoundtripsCount finanzas) {
        Finanzas = finanzas;
    }

    public RoundtripsCount getCCHH() {
        return CCHH;
    }

    public void setCCHH(RoundtripsCount CCHH) {
        this.CCHH = CCHH;
    }

    public RoundtripsCount getComercial() {
        return Comercial;
    }

    public void setComercial(RoundtripsCount comercial) {
        Comercial = comercial;
    }

    public RoundtripsCount getAdministracion() {
        return Administracion;
    }

    public void setAdministracion(RoundtripsCount administracion) {
        Administracion = administracion;
    }

    public RoundtripsCount getContabilidad() {
        return Contabilidad;
    }

    public void setContabilidad(RoundtripsCount contabilidad) {
        Contabilidad = contabilidad;
    }
}
