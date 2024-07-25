package com.opencars.netgo.dms.quoter.entity;

import com.opencars.netgo.dms.clients.entity.Clients;
import com.opencars.netgo.users.entity.User;
import io.swagger.annotations.ApiModel;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@ApiModel(value = "Entidad Modelo de Cotización", description = "Información completa que se almacena de una cotización de VO")
public class Cotization implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    private LocalDateTime dateToOrder;
    @NotEmpty
    private String brand;
    @NotEmpty
    private String model;
    @NotNull
    private int year;
    @NotNull
    private float priceFinal;
    @NotNull
    private long km;
    private long kmAverage;
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Clients client;

    @ManyToOne
    @JoinColumn(name = "type_operation_id")
    private TypeOfSale typeOperation;

    @ManyToOne
    @JoinColumn(name = "type_of_car_id")
    private TypeOfCars typeOfCar;
    @ManyToOne
    @JoinColumn(name = "quoted_by_id")
    private User quotedBy;
    @NotEmpty
    private String patent;

    private String observation;

    @PrePersist
    public void persistDate(){
        this.date = LocalDate.now();
        this.dateToOrder = LocalDateTime.now();
    }

    public Cotization() {
    }

    public Cotization(String brand, String model, int year, float priceFinal, long km, Clients client, TypeOfSale typeOperation, TypeOfCars typeOfCar, User quotedBy, String patent, String observation) {
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.priceFinal = priceFinal;
        this.km = km;
        this.client = client;
        this.typeOperation = typeOperation;
        this.typeOfCar = typeOfCar;
        this.quotedBy = quotedBy;
        this.patent = patent;
        this.observation = observation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDateTime getDateToOrder() {
        return dateToOrder;
    }

    public void setDateToOrder(LocalDateTime dateToOrder) {
        this.dateToOrder = dateToOrder;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public float getPriceFinal() {
        return priceFinal;
    }

    public void setPriceFinal(float priceFinal) {
        this.priceFinal = priceFinal;
    }

    public long getKm() {
        return km;
    }

    public void setKm(long km) {
        this.km = km;
    }

    public long getKmAverage() {
        return kmAverage;
    }

    public void setKmAverage(long kmAverage) {
        this.kmAverage = kmAverage;
    }

    public Clients getClient() {
        return client;
    }

    public void setClient(Clients client) {
        this.client = client;
    }

    public TypeOfSale getTypeOperation() {
        return typeOperation;
    }

    public void setTypeOperation(TypeOfSale typeOperation) {
        this.typeOperation = typeOperation;
    }

    public User getQuotedBy() {
        return quotedBy;
    }

    public void setQuotedBy(User quotedBy) {
        this.quotedBy = quotedBy;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getPatent() {
        return patent;
    }

    public void setPatent(String patent) {
        this.patent = patent;
    }

    public TypeOfCars getTypeOfCar() {
        return typeOfCar;
    }

    public void setTypeOfCar(TypeOfCars typeOfCar) {
        this.typeOfCar = typeOfCar;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }
}
