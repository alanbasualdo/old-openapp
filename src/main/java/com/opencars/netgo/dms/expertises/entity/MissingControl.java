package com.opencars.netgo.dms.expertises.entity;

import com.opencars.netgo.dms.quoter.entity.TypeOfCars;
import io.swagger.annotations.ApiModel;
import jakarta.validation.constraints.NotNull;

import javax.persistence.*;

@Entity
@Table(name = "missing_control")
@ApiModel(value = "Entidad Modelo de tabla de control de precios", description = "Información completa que se almacena de precios de faltantes de perijates")
public class MissingControl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "missing_id")
    private Missing missing;
    @ManyToOne
    @JoinColumn(name = "type_of_cars_id")
    private TypeOfCars typeOfCars;
    @NotNull
    private Long price;

    public MissingControl() {
    }

    public MissingControl(Missing missing, TypeOfCars typeOfCars, Long price) {
        this.missing = missing;
        this.typeOfCars = typeOfCars;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Missing getMissing() {
        return missing;
    }

    public void setMissing(Missing missing) {
        this.missing = missing;
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
