package com.banco.producer;

public class Transaction {
    private String idTransaccion;
    private double monto;
    private String moneda;
    private String cuentaOrigen;
    private String bancoDestino;
    private Detalle detalle;
    
    private String nombre;
    private String carnet;
    
    public Transaction() {
    }

    public String getIdTransaccion() {
        return idTransaccion;
    }

    public void setIdTransaccion(String idTransaccion) {
        this.idTransaccion = idTransaccion;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public String getCuentaOrigen() {
        return cuentaOrigen;
    }

    public void setCuentaOrigen(String cuentaOrigen) {
        this.cuentaOrigen = cuentaOrigen;
    }

    public String getBancoDestino() {
        return bancoDestino;
    }

    public void setBancoDestino(String bancoDestino) {
        this.bancoDestino = bancoDestino;
    }

    public Detalle getDetalle() {
        return detalle;
    }

    public void setDetalle(Detalle detalle) {
        this.detalle = detalle;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCarnet() {
        return carnet;
    }

    public void setCarnet(String carnet) {
        this.carnet = carnet;
    }
    
    
    public static class Detalle {
        private String nombreBeneficiario;
        private String tipoTransferencia;
        private String descripcion;
        private Referencias referencias;

        public Detalle() {
        }

        public String getNombreBeneficiario() {
            return nombreBeneficiario;
        }

        public void setNombreBeneficiario(String nombreBeneficiario) {
            this.nombreBeneficiario = nombreBeneficiario;
        }

        public String getTipoTransferencia() {
            return tipoTransferencia;
        }

        public void setTipoTransferencia(String tipoTransferencia) {
            this.tipoTransferencia = tipoTransferencia;
        }

        public String getDescripcion() {
            return descripcion;
        }

        public void setDescripcion(String descripcion) {
            this.descripcion = descripcion;
        }

        public Referencias getReferencias() {
            return referencias;
        }

        public void setReferencias(Referencias referencias) {
            this.referencias = referencias;
        }
    }

    public static class Referencias {
        private String factura;
        private String codigoInterno;

        public Referencias() {
        }

        public String getFactura() {
            return factura;
        }

        public void setFactura(String factura) {
            this.factura = factura;
        }

        public String getCodigoInterno() {
            return codigoInterno;
        }

        public void setCodigoInterno(String codigoInterno) {
            this.codigoInterno = codigoInterno;
        }
    }
}
