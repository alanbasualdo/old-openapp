package com.opencars.netgo.dms.expertises.entity;

import com.opencars.netgo.dms.quoter.entity.TypeOfCars;
import io.swagger.annotations.ApiModel;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import javax.persistence.*;
@Entity
@Table(name = "items_control")
@ApiModel(value = "Entidad Modelo de tabla de control de items de peritajes", description = "Información completa que se almacena de precios de items de perijates")

public class ItemsControl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotEmpty
    private String item;
    @ManyToOne
    @JoinColumn(name = "type_of_cars_id")
    private TypeOfCars typeOfCars;
    @NotNull
    private Long price;

    public ItemsControl() {
    }

    public ItemsControl(String item, TypeOfCars typeOfCars, Long price) {
        this.item = item;
        this.typeOfCars = typeOfCars;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public TypeOfCars getTypeOfCars() {
        return typeOfCars;
    }

    public void setTypeOfCars(TypeOfCars typeOfCars) {
        this.typeOfCars = typeOfCars;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }
}
