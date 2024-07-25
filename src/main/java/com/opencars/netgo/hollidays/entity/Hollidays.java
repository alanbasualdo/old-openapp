package com.opencars.netgo.hollidays.entity;

import com.opencars.netgo.users.entity.User;
import io.swagger.annotations.ApiModel;
import jakarta.validation.constraints.NotNull;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@ApiModel(value = "Entidad Modelo de Solicitud de Vacaciones", description = "Información completa que se almacena de una solicitud de vacaciones")
public class Hollidays {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private LocalDate dateInit;

    @NotNull
    private LocalDate dateEnd;

    @NotNull
    private int days;

    @NotNull
    private StatesHollidays state;

    private String observation;

    @NotNull
    private LocalDateTime dateCreated;

    private LocalDateTime dateAuthorized;

    private LocalDateTime dateTaking;

    private LocalDateTime dateNoTaking;

    @NotNull
    @ManyToOne
    private User colaborator;

    @NotNull
    @ManyToOne
    private User authorizer;

    private int rejection;

    private String reasonRejection;

    public Hollidays() {
    }

    public Hollidays(int id) {
        this.id = id;
    }

    public Hollidays(LocalDate dateInit, LocalDate dateEnd, int days, String observation, User colaborator) {
        this.dateInit = dateInit;
        this.dateEnd = dateEnd;
        this.days = days;
        this.observation = observation;
        this.colaborator = colaborator;
    }
}
