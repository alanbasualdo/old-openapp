package com.opencars.netgo.dms.expertises.entity;

import com.opencars.netgo.news.roundtrips.dto.Attacheds;
import com.opencars.netgo.users.entity.User;
import io.swagger.annotations.ApiModel;
import jakarta.validation.constraints.NotNull;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "expertises")
@ApiModel(value = "Entidad Modelo de Peritaje", description = "Información completa que se almacena del peritaje")
public class Expertise {

    /* Attributes */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Column(length = 20)
    private LocalDate date;
    @NotNull
    private String name_client;
    @NotNull
    @Column(length = 30)
    private String phone_client;
    @NotNull
    @OneToOne
    private User assesor;
    @NotNull
    @Column(length = 15)
    private String domain;
    @NotNull
    @Column(length = 30)
    private String mark;
    @NotNull
    private String model;
    @NotNull
    private int year;
    @NotNull
    @Column(length = 10)
    private String km;
    @NotNull
    private String color;
    @NotNull
    @Column(length = 30)
    private String motor;
    @NotNull
    @Column(length = 20)
    private String transmision;
    @NotNull
    @Column(length = 5)
    private String traction;
    @NotNull
    @Column(length = 15)
    private String fuel;
    @NotNull
    @Column(length = 10)
    private String state_motor;
    @Column(columnDefinition = "TEXT")
    private String obs_motor;
    private Long cost_motor_rep;
    @NotNull
    @Column(length = 10)
    private String state_fluid;
    @Column(columnDefinition = "TEXT")
    private String obs_fluid;
    private Long cost_fluid_rep;
    @NotNull
    @Column(length = 10)
    private String state_box;
    @Column(columnDefinition = "TEXT")
    private String obs_box;
    private Long cost_box_rep;
    @Column(length = 10)
    private String state_traccion4;
    @Column(columnDefinition = "TEXT")
    private String obs_traccion4;
    private Long cost_traccion4_rep;
    @NotNull
    @Column(length = 10)
    private String state_suspension;
    @Column(columnDefinition = "TEXT")
    private String obs_suspension;
    private Long cost_suspension_rep;
    @NotNull
    @Column(length = 10)
    private String state_brakes;
    @Column(columnDefinition = "TEXT")
    private String obs_brakes;
    private Long cost_brakes_rep;
    @NotNull
    @Column(length = 10)
    private String state_wearbrakes;
    @Column(columnDefinition = "TEXT")
    private String obs_wearbrakes;
    @Column(length = 15)
    private String cost_wearbrakes_rep;
    @NotNull
    @Column(length = 10)
    private String state_electric;
    @Column(columnDefinition = "TEXT")
    private String obs_electric;
    private Long cost_electric_rep;
    @NotNull
    @Column(length = 10)
    private String state_headlights;
    @Column(columnDefinition = "TEXT")
    private String obs_headlights;
    private Long cost_headlights_rep;
    @NotNull
    @Column(length = 10)
    private String state_upholstered;
    @Column(columnDefinition = "TEXT")
    private String obs_upholstered;
    private Long cost_upholstered_rep;
    @NotNull
    @Column(length = 10)
    private String state_wheel;
    @Column(columnDefinition = "TEXT")
    private String obs_wheel;
    private Long cost_wheel_rep;
    @NotNull
    @Column(length = 10)
    private String state_accesories;
    @Column(columnDefinition = "TEXT")
    private String obs_accesories;
    @NotNull
    private int cant_cloths;
    private Long cost_cloths;
    @NotNull
    @Column(length = 10)
    private String state_glass;
    @Column(columnDefinition = "TEXT")
    private String obs_glass;
    private Long cost_glass_rep;
    @NotNull
    @Column(length = 10)
    private String state_patent;
    @Column(columnDefinition = "TEXT")
    private String obs_patent;
    private Long cost_patent_rep;
    @NotNull
    @Column(length = 10)
    private String state_hail;
    @Column(columnDefinition = "TEXT")
    private String obs_hail;
    private Long cost_hail_rep;
    @NotNull
    @Column(length = 10)
    private String state_air;
    @Column(columnDefinition = "TEXT")
    private String obs_air;
    private Long cost_air_rep;
    @NotNull
    @Column(length = 10)
    private String state_dd;
    @NotNull
    @Column(length = 30)
    private String mark_dd;
    @NotNull
    @Column(length = 10)
    private String state_di;
    @NotNull
    @Column(length = 30)
    private String mark_di;
    @NotNull
    @Column(length = 10)
    private String state_td;
    @NotNull
    @Column(length = 30)
    private String mark_td;
    @NotNull
    @Column(length = 10)
    private String state_ti;
    @NotNull
    @Column(length = 30)
    private String mark_ti;
    @NotNull
    @Column(length = 10)
    private String state_batery;
    @Column(columnDefinition = "TEXT")
    private String obs_batery;
    private Long cost_batery_rep;
    @NotNull
    @Column(length = 10)
    private String state_missing;
    @Column(columnDefinition = "TEXT")
    private String obs_missing;
    private Long cost_missing_rep;
    @NotNull
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private Attacheds[] attacheds;
    @NotNull
    private String photo_card;
    @NotNull
    private String operationType;
    @NotNull
    private String photo_km;
    @NotNull
    private String strapchange;
    @NotNull
    private int servicekm;
    @NotNull
    private String oilconsumption;
    @NotNull
    private String waterconsumption;
    private int costrep;
    private int appraisal;
    private int repdays;
    private int takingprice;
    private String photo_service;
    private String photo_belt;

    private int cost_strapchange;

    private int cost_service;

    private int cost_dd;

    private int cost_di;

    private int cost_td;

    private int cost_ti;

    @NotNull
    @ManyToOne
    private User user;

    private String sign;


    public Expertise() {
    }

    public Expertise(String name_client, String phone_client, User assesor, String domain, String mark, String model, int year, String km, String color, String motor, String transmision, String traction, String fuel, String state_motor, String obs_motor, Long cost_motor_rep, String state_fluid, String obs_fluid, Long cost_fluid_rep, String state_box, String obs_box, Long cost_box_rep, String state_traccion4, String obs_traccion4, Long cost_traccion4_rep, String state_suspension, String obs_suspension, Long cost_suspension_rep, String state_brakes, String obs_brakes, Long cost_brakes_rep, String state_wearbrakes, String obs_wearbrakes, String cost_wearbrakes_rep, String state_electric, String obs_electric, Long cost_electric_rep, String state_headlights, String obs_headlights, Long cost_headlights_rep, String state_upholstered, String obs_upholstered, Long cost_upholstered_rep, String state_wheel, String obs_wheel, Long cost_wheel_rep, String state_accesories, String obs_accesories, int cant_cloths, Long cost_cloths, String state_glass, String obs_glass, Long cost_glass_rep, String state_patent, String obs_patent, Long cost_patent_rep, String state_hail, String obs_hail, Long cost_hail_rep, String state_air, String obs_air, Long cost_air_rep, String state_dd, String mark_dd, String state_di, String mark_di, String state_td, String mark_td, String state_ti, String mark_ti, String state_batery, String obs_batery, Long cost_batery_rep, String state_missing, String obs_missing, Long cost_missing_rep, @NotNull Attacheds[] attacheds, String photo_card, String operationType, String photo_km, String strapchange, int servicekm, String oilconsumption, String waterconsumption, int costrep, int appraisal, int repdays, int takingprice, String photo_service, String photo_belt, int cost_strapchange, int cost_service, int cost_dd, int cost_di, int cost_td, int cost_ti, User user) {
        this.name_client = name_client;
        this.phone_client = phone_client;
        this.assesor = assesor;
        this.domain = domain;
        this.mark = mark;
        this.model = model;
        this.year = year;
        this.km = km;
        this.color = color;
        this.motor = motor;
        this.transmision = transmision;
        this.traction = traction;
        this.fuel = fuel;
        this.state_motor = state_motor;
        this.obs_motor = obs_motor;
        this.cost_motor_rep = cost_motor_rep;
        this.state_fluid = state_fluid;
        this.obs_fluid = obs_fluid;
        this.cost_fluid_rep = cost_fluid_rep;
        this.state_box = state_box;
        this.obs_box = obs_box;
        this.cost_box_rep = cost_box_rep;
        this.state_traccion4 = state_traccion4;
        this.obs_traccion4 = obs_traccion4;
        this.cost_traccion4_rep = cost_traccion4_rep;
        this.state_suspension = state_suspension;
        this.obs_suspension = obs_suspension;
        this.cost_suspension_rep = cost_suspension_rep;
        this.state_brakes = state_brakes;
        this.obs_brakes = obs_brakes;
        this.cost_brakes_rep = cost_brakes_rep;
        this.state_wearbrakes = state_wearbrakes;
        this.obs_wearbrakes = obs_wearbrakes;
        this.cost_wearbrakes_rep = cost_wearbrakes_rep;
        this.state_electric = state_electric;
        this.obs_electric = obs_electric;
        this.cost_electric_rep = cost_electric_rep;
        this.state_headlights = state_headlights;
        this.obs_headlights = obs_headlights;
        this.cost_headlights_rep = cost_headlights_rep;
        this.state_upholstered = state_upholstered;
        this.obs_upholstered = obs_upholstered;
        this.cost_upholstered_rep = cost_upholstered_rep;
        this.state_wheel = state_wheel;
        this.obs_wheel = obs_wheel;
        this.cost_wheel_rep = cost_wheel_rep;
        this.state_accesories = state_accesories;
        this.obs_accesories = obs_accesories;
        this.cant_cloths = cant_cloths;
        this.cost_cloths = cost_cloths;
        this.state_glass = state_glass;
        this.obs_glass = obs_glass;
        this.cost_glass_rep = cost_glass_rep;
        this.state_patent = state_patent;
        this.obs_patent = obs_patent;
        this.cost_patent_rep = cost_patent_rep;
        this.state_hail = state_hail;
        this.obs_hail = obs_hail;
        this.cost_hail_rep = cost_hail_rep;
        this.state_air = state_air;
        this.obs_air = obs_air;
        this.cost_air_rep = cost_air_rep;
        this.state_dd = state_dd;
        this.mark_dd = mark_dd;
        this.state_di = state_di;
        this.mark_di = mark_di;
        this.state_td = state_td;
        this.mark_td = mark_td;
        this.state_ti = state_ti;
        this.mark_ti = mark_ti;
        this.state_batery = state_batery;
        this.obs_batery = obs_batery;
        this.cost_batery_rep = cost_batery_rep;
        this.state_missing = state_missing;
        this.obs_missing = obs_missing;
        this.cost_missing_rep = cost_missing_rep;
        this.attacheds = attacheds;
        this.photo_card = photo_card;
        this.operationType = operationType;
        this.photo_km = photo_km;
        this.strapchange = strapchange;
        this.servicekm = servicekm;
        this.oilconsumption = oilconsumption;
        this.waterconsumption = waterconsumption;
        this.costrep = costrep;
        this.appraisal = appraisal;
        this.repdays = repdays;
        this.takingprice = takingprice;
        this.photo_service = photo_service;
        this.photo_belt = photo_belt;
        this.cost_strapchange = cost_strapchange;
        this.cost_service = cost_service;
        this.cost_dd = cost_dd;
        this.cost_di = cost_di;
        this.cost_td = cost_td;
        this.cost_ti = cost_ti;
        this.user = user;

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

    public String getName_client() {
        return name_client;
    }

    public void setName_client(String name_client) {
        this.name_client = name_client;
    }

    public String getPhone_client() {
        return phone_client;
    }

    public void setPhone_client(String phone_client) {
        this.phone_client = phone_client;
    }

    public User getAssesor() {
        return assesor;
    }

    public void setAssesor(User assesor) {
        this.assesor = assesor;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
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

    public String getKm() {
        return km;
    }

    public void setKm(String km) {
        this.km = km;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getMotor() {
        return motor;
    }

    public void setMotor(String motor) {
        this.motor = motor;
    }

    public String getTransmision() {
        return transmision;
    }

    public void setTransmision(String transmision) {
        this.transmision = transmision;
    }

    public String getTraction() {
        return traction;
    }

    public void setTraction(String traction) {
        this.traction = traction;
    }

    public String getFuel() {
        return fuel;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
    }

    public String getState_motor() {
        return state_motor;
    }

    public void setState_motor(String state_motor) {
        this.state_motor = state_motor;
    }

    public String getObs_motor() {
        return obs_motor;
    }

    public void setObs_motor(String obs_motor) {
        this.obs_motor = obs_motor;
    }

    public Long getCost_motor_rep() {
        return cost_motor_rep;
    }

    public void setCost_motor_rep(Long cost_motor_rep) {
        this.cost_motor_rep = cost_motor_rep;
    }

    public String getState_fluid() {
        return state_fluid;
    }

    public void setState_fluid(String state_fluid) {
        this.state_fluid = state_fluid;
    }

    public String getObs_fluid() {
        return obs_fluid;
    }

    public void setObs_fluid(String obs_fluid) {
        this.obs_fluid = obs_fluid;
    }

    public Long getCost_fluid_rep() {
        return cost_fluid_rep;
    }

    public void setCost_fluid_rep(Long cost_fluid_rep) {
        this.cost_fluid_rep = cost_fluid_rep;
    }

    public String getState_box() {
        return state_box;
    }

    public void setState_box(String state_box) {
        this.state_box = state_box;
    }

    public String getObs_box() {
        return obs_box;
    }

    public void setObs_box(String obs_box) {
        this.obs_box = obs_box;
    }

    public Long getCost_box_rep() {
        return cost_box_rep;
    }

    public void setCost_box_rep(Long cost_box_rep) {
        this.cost_box_rep = cost_box_rep;
    }

    public String getState_traccion4() {
        return state_traccion4;
    }

    public void setState_traccion4(String state_traccion4) {
        this.state_traccion4 = state_traccion4;
    }

    public String getObs_traccion4() {
        return obs_traccion4;
    }

    public void setObs_traccion4(String obs_traccion4) {
        this.obs_traccion4 = obs_traccion4;
    }

    public Long getCost_traccion4_rep() {
        return cost_traccion4_rep;
    }

    public void setCost_traccion4_rep(Long cost_traccion4_rep) {
        this.cost_traccion4_rep = cost_traccion4_rep;
    }

    public String getState_suspension() {
        return state_suspension;
    }

    public void setState_suspension(String state_suspension) {
        this.state_suspension = state_suspension;
    }

    public String getObs_suspension() {
        return obs_suspension;
    }

    public void setObs_suspension(String obs_suspension) {
        this.obs_suspension = obs_suspension;
    }

    public Long getCost_suspension_rep() {
        return cost_suspension_rep;
    }

    public void setCost_suspension_rep(Long cost_suspension_rep) {
        this.cost_suspension_rep = cost_suspension_rep;
    }

    public String getState_brakes() {
        return state_brakes;
    }

    public void setState_brakes(String state_brakes) {
        this.state_brakes = state_brakes;
    }

    public String getObs_brakes() {
        return obs_brakes;
    }

    public void setObs_brakes(String obs_brakes) {
        this.obs_brakes = obs_brakes;
    }

    public Long getCost_brakes_rep() {
        return cost_brakes_rep;
    }

    public void setCost_brakes_rep(Long cost_brakes_rep) {
        this.cost_brakes_rep = cost_brakes_rep;
    }

    public String getState_wearbrakes() {
        return state_wearbrakes;
    }

    public void setState_wearbrakes(String state_wearbrakes) {
        this.state_wearbrakes = state_wearbrakes;
    }

    public String getObs_wearbrakes() {
        return obs_wearbrakes;
    }

    public void setObs_wearbrakes(String obs_wearbrakes) {
        this.obs_wearbrakes = obs_wearbrakes;
    }

    public String getCost_wearbrakes_rep() {
        return cost_wearbrakes_rep;
    }

    public void setCost_wearbrakes_rep(String cost_wearbrakes_rep) {
        this.cost_wearbrakes_rep = cost_wearbrakes_rep;
    }

    public String getState_electric() {
        return state_electric;
    }

    public void setState_electric(String state_electric) {
        this.state_electric = state_electric;
    }

    public String getObs_electric() {
        return obs_electric;
    }

    public void setObs_electric(String obs_electric) {
        this.obs_electric = obs_electric;
    }

    public Long getCost_electric_rep() {
        return cost_electric_rep;
    }

    public void setCost_electric_rep(Long cost_electric_rep) {
        this.cost_electric_rep = cost_electric_rep;
    }

    public String getState_headlights() {
        return state_headlights;
    }

    public void setState_headlights(String state_headlights) {
        this.state_headlights = state_headlights;
    }

    public String getObs_headlights() {
        return obs_headlights;
    }

    public void setObs_headlights(String obs_headlights) {
        this.obs_headlights = obs_headlights;
    }

    public Long getCost_headlights_rep() {
        return cost_headlights_rep;
    }

    public void setCost_headlights_rep(Long cost_headlights_rep) {
        this.cost_headlights_rep = cost_headlights_rep;
    }

    public String getState_upholstered() {
        return state_upholstered;
    }

    public void setState_upholstered(String state_upholstered) {
        this.state_upholstered = state_upholstered;
    }

    public String getObs_upholstered() {
        return obs_upholstered;
    }

    public void setObs_upholstered(String obs_upholstered) {
        this.obs_upholstered = obs_upholstered;
    }

    public Long getCost_upholstered_rep() {
        return cost_upholstered_rep;
    }

    public void setCost_upholstered_rep(Long cost_upholstered_rep) {
        this.cost_upholstered_rep = cost_upholstered_rep;
    }

    public String getState_wheel() {
        return state_wheel;
    }

    public void setState_wheel(String state_wheel) {
        this.state_wheel = state_wheel;
    }

    public String getObs_wheel() {
        return obs_wheel;
    }

    public void setObs_wheel(String obs_wheel) {
        this.obs_wheel = obs_wheel;
    }

    public Long getCost_wheel_rep() {
        return cost_wheel_rep;
    }

    public void setCost_wheel_rep(Long cost_wheel_rep) {
        this.cost_wheel_rep = cost_wheel_rep;
    }

    public String getState_accesories() {
        return state_accesories;
    }

    public void setState_accesories(String state_accesories) {
        this.state_accesories = state_accesories;
    }

    public String getObs_accesories() {
        return obs_accesories;
    }

    public void setObs_accesories(String obs_accesories) {
        this.obs_accesories = obs_accesories;
    }

    public int getCant_cloths() {
        return cant_cloths;
    }

    public void setCant_cloths(int cant_cloths) {
        this.cant_cloths = cant_cloths;
    }

    public Long getCost_cloths() {
        return cost_cloths;
    }

    public void setCost_cloths(Long cost_cloths) {
        this.cost_cloths = cost_cloths;
    }

    public String getState_glass() {
        return state_glass;
    }

    public void setState_glass(String state_glass) {
        this.state_glass = state_glass;
    }

    public String getObs_glass() {
        return obs_glass;
    }

    public void setObs_glass(String obs_glass) {
        this.obs_glass = obs_glass;
    }

    public Long getCost_glass_rep() {
        return cost_glass_rep;
    }

    public void setCost_glass_rep(Long cost_glass_rep) {
        this.cost_glass_rep = cost_glass_rep;
    }

    public String getState_patent() {
        return state_patent;
    }

    public void setState_patent(String state_patent) {
        this.state_patent = state_patent;
    }

    public String getObs_patent() {
        return obs_patent;
    }

    public void setObs_patent(String obs_patent) {
        this.obs_patent = obs_patent;
    }

    public Long getCost_patent_rep() {
        return cost_patent_rep;
    }

    public void setCost_patent_rep(Long cost_patent_rep) {
        this.cost_patent_rep = cost_patent_rep;
    }

    public String getState_hail() {
        return state_hail;
    }

    public void setState_hail(String state_hail) {
        this.state_hail = state_hail;
    }

    public String getObs_hail() {
        return obs_hail;
    }

    public void setObs_hail(String obs_hail) {
        this.obs_hail = obs_hail;
    }

    public Long getCost_hail_rep() {
        return cost_hail_rep;
    }

    public void setCost_hail_rep(Long cost_hail_rep) {
        this.cost_hail_rep = cost_hail_rep;
    }

    public String getState_air() {
        return state_air;
    }

    public void setState_air(String state_air) {
        this.state_air = state_air;
    }

    public String getObs_air() {
        return obs_air;
    }

    public void setObs_air(String obs_air) {
        this.obs_air = obs_air;
    }

    public Long getCost_air_rep() {
        return cost_air_rep;
    }

    public void setCost_air_rep(Long cost_air_rep) {
        this.cost_air_rep = cost_air_rep;
    }

    public String getState_dd() {
        return state_dd;
    }

    public void setState_dd(String state_dd) {
        this.state_dd = state_dd;
    }

    public String getMark_dd() {
        return mark_dd;
    }

    public void setMark_dd(String mark_dd) {
        this.mark_dd = mark_dd;
    }

    public String getState_di() {
        return state_di;
    }

    public void setState_di(String state_di) {
        this.state_di = state_di;
    }

    public String getMark_di() {
        return mark_di;
    }

    public void setMark_di(String mark_di) {
        this.mark_di = mark_di;
    }

    public String getState_td() {
        return state_td;
    }

    public void setState_td(String state_td) {
        this.state_td = state_td;
    }

    public String getMark_td() {
        return mark_td;
    }

    public void setMark_td(String mark_td) {
        this.mark_td = mark_td;
    }

    public String getState_ti() {
        return state_ti;
    }

    public void setState_ti(String state_ti) {
        this.state_ti = state_ti;
    }

    public String getMark_ti() {
        return mark_ti;
    }

    public void setMark_ti(String mark_ti) {
        this.mark_ti = mark_ti;
    }

    public String getState_batery() {
        return state_batery;
    }

    public void setState_batery(String state_batery) {
        this.state_batery = state_batery;
    }

    public String getObs_batery() {
        return obs_batery;
    }

    public void setObs_batery(String obs_batery) {
        this.obs_batery = obs_batery;
    }

    public Long getCost_batery_rep() {
        return cost_batery_rep;
    }

    public void setCost_batery_rep(Long cost_batery_rep) {
        this.cost_batery_rep = cost_batery_rep;
    }

    public String getState_missing() {
        return state_missing;
    }

    public void setState_missing(String state_missing) {
        this.state_missing = state_missing;
    }

    public String getObs_missing() {
        return obs_missing;
    }

    public void setObs_missing(String obs_missing) {
        this.obs_missing = obs_missing;
    }

    public Long getCost_missing_rep() {
        return cost_missing_rep;
    }

    public void setCost_missing_rep(Long cost_missing_rep) {
        this.cost_missing_rep = cost_missing_rep;
    }

    public Attacheds[] getAttacheds() {
        return attacheds;
    }

    public void setAttacheds(Attacheds[] attacheds) {
        this.attacheds = attacheds;
    }

    public String getPhoto_card() {
        return photo_card;
    }

    public void setPhoto_card(String photo_card) {
        this.photo_card = photo_card;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public String getPhoto_km() {
        return photo_km;
    }

    public void setPhoto_km(String photo_km) {
        this.photo_km = photo_km;
    }

    public String getStrapchange() {
        return strapchange;
    }

    public void setStrapchange(String strapchange) {
        this.strapchange = strapchange;
    }

    public int getServicekm() {
        return servicekm;
    }

    public void setServicekm(int servicekm) {
        this.servicekm = servicekm;
    }

    public String getOilconsumption() {
        return oilconsumption;
    }

    public void setOilconsumption(String oilconsumption) {
        this.oilconsumption = oilconsumption;
    }

    public String getWaterconsumption() {
        return waterconsumption;
    }

    public void setWaterconsumption(String waterconsumption) {
        this.waterconsumption = waterconsumption;
    }

    public int getCostrep() {
        return costrep;
    }

    public void setCostrep(int costrep) {
        this.costrep = costrep;
    }

    public int getAppraisal() {
        return appraisal;
    }

    public void setAppraisal(int appraisal) {
        this.appraisal = appraisal;
    }

    public int getRepdays() {
        return repdays;
    }

    public void setRepdays(int repdays) {
        this.repdays = repdays;
    }

    public int getTakingprice() {
        return takingprice;
    }

    public void setTakingprice(int takingprice) {
        this.takingprice = takingprice;
    }

    public String getPhoto_service() {
        return photo_service;
    }

    public void setPhoto_service(String photo_service) {
        this.photo_service = photo_service;
    }

    public String getPhoto_belt() {
        return photo_belt;
    }

    public void setPhoto_belt(String photo_belt) {
        this.photo_belt = photo_belt;
    }

    public int getCost_strapchange() {
        return cost_strapchange;
    }

    public void setCost_strapchange(int cost_strapchange) {
        this.cost_strapchange = cost_strapchange;
    }

    public int getCost_service() {
        return cost_service;
    }

    public void setCost_service(int cost_service) {
        this.cost_service = cost_service;
    }

    public int getCost_dd() {
        return cost_dd;
    }

    public void setCost_dd(int cost_dd) {
        this.cost_dd = cost_dd;
    }

    public int getCost_di() {
        return cost_di;
    }

    public void setCost_di(int cost_di) {
        this.cost_di = cost_di;
    }

    public int getCost_td() {
        return cost_td;
    }

    public void setCost_td(int cost_td) {
        this.cost_td = cost_td;
    }

    public int getCost_ti() {
        return cost_ti;
    }

    public void setCost_ti(int cost_ti) {
        this.cost_ti = cost_ti;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}