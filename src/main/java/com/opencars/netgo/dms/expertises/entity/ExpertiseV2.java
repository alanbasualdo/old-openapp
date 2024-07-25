package com.opencars.netgo.dms.expertises.entity;

import com.opencars.netgo.dms.quoter.entity.Cotization;
import com.opencars.netgo.news.roundtrips.dto.Attacheds;
import com.opencars.netgo.users.entity.User;
import io.swagger.annotations.ApiModel;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "expertisesv2")
@ApiModel(value = "Entidad Modelo de Peritajes", description = "Información completa que se almacena del peritaje")
public class ExpertiseV2 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Column(length = 20)
    private LocalDate date;
    @NotNull
    @Column(length = 15)
    private String domain;
    @NotNull
    @Column(length = 10)
    private String km;
    @NotNull
    private String color;
    @NotNull
    @Column(length = 30)
    private String motor;
    @ManyToOne()
    @JoinColumn(name = "transmision_id")
    private Transmision transmision;
    @ManyToOne
    @JoinColumn(name = "traction_id")
    private Traccion traction;
    @ManyToOne
    @JoinColumn(name = "fuel_id")
    private Fuel fuel;
    @ManyToOne
    @JoinColumn(name = "state_motor_id")
    private States state_motor;
    private String obs_motor;
    private Long cost_motor_rep;
    @ManyToOne
    @JoinColumn(name = "state_fluid_id")
    private StatesYesNo state_fluid;
    private String obs_fluid;
    private Long cost_fluid_rep;
    @ManyToOne
    @JoinColumn(name = "state_box_id")
    private States state_box;
    private String obs_box;
    private Long cost_box_rep;
    @ManyToOne
    @JoinColumn(name = "state_traccion_4_id")
    private States state_traccion4;
    private String obs_traccion4;
    private Long cost_traccion4_rep;
    @ManyToOne
    @JoinColumn(name = "state_suspension_id")
    private States state_suspension;
    private String obs_suspension;
    private Long cost_suspension_rep;
    @ManyToOne
    @JoinColumn(name = "state_brakes_id")
    private States state_brakes;
    private String obs_brakes;
    private Long cost_brakes_rep;
    @ManyToOne
    @JoinColumn(name = "state_wearbrakes_id")
    private StatesYesNo state_wearbrakes;
    private String obs_wearbrakes;
    private Long cost_wearbrakes_rep;
    @ManyToOne
    @JoinColumn(name = "state_electric_id")
    private StatesYesNo state_electric;
    private String obs_electric;
    private Long cost_electric_rep;
    @ManyToOne
    @JoinColumn(name = "state_headlights_id")
    private States state_headlights;
    private String obs_headlights;
    private Long cost_headlights_rep;
    @ManyToOne
    @JoinColumn(name = "state_upholstered_id")
    private States state_upholstered;
    private String obs_upholstered;
    private Long cost_upholstered_rep;
    @ManyToOne
    @JoinColumn(name = "state_wheel_id")
    private StatesYesNo state_wheel;
    private String obs_wheel;
    private Long cost_wheel_rep;
    @ManyToOne
    @JoinColumn(name = "state_accesories_id")
    private StatesNoYes state_accesories;
    @NotNull
    private int cant_cloths;
    private Long cost_cloths;
    @ManyToOne
    @JoinColumn(name = "state_glass_id")
    private States state_glass;
    private String obs_glass;
    private Long cost_glass_rep;
    @ManyToOne
    @JoinColumn(name = "state_patent_id")
    private States state_patent;
    private String obs_patent;
    private Long cost_patent_rep;
    @ManyToOne
    @JoinColumn(name = "state_hail_id")
    private StatesYesNo state_hail;
    private String obs_hail;
    private Long cost_hail_rep;
    @ManyToOne
    @JoinColumn(name = "state_air_id")
    private StatesNoYes state_air;
    private String obs_air;
    private Long cost_air_rep;
    @ManyToOne
    @JoinColumn(name = "state_dd_id")
    private StatesFem state_dd;
    @NotEmpty
    @Column(length = 30)
    private String mark_dd;
    @ManyToOne
    @JoinColumn(name = "state_di_id")
    private StatesFem state_di;
    @NotEmpty
    @Column(length = 30)
    private String mark_di;
    @ManyToOne
    @JoinColumn(name = "state_td_id")
    private StatesFem state_td;
    @NotEmpty
    @Column(length = 30)
    private String mark_td;
    @ManyToOne
    @JoinColumn(name = "state_ti_id")
    private StatesFem state_ti;
    @NotEmpty
    @Column(length = 30)
    private String mark_ti;
    @ManyToOne
    @JoinColumn(name = "state_batery_id")
    private States state_batery;
    private String obs_batery;
    private Long cost_batery_rep;
    @ManyToOne
    @JoinColumn(name = "state_missing_id")
    private StatesYesNo state_missing;
    private Long cost_missing_rep;
    @NotNull
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private Attacheds[] attacheds;
    @NotEmpty
    private String photo_card;
    @NotEmpty
    private String photo_km;
    @ManyToOne
    @JoinColumn(name = "strapchange_id")
    private StatesYesNoDoesNotCorrespond strapchange;
    @NotNull
    private int servicekm;
    @ManyToOne
    @JoinColumn(name = "oilconsumption_id")
    private StatesYesNo oilconsumption;
    @ManyToOne
    @JoinColumn(name = "waterconsumption_id")
    private StatesYesNo waterconsumption;
    private Long costrep;
    private Long takingprice;
    private String photo_service;
    private String photo_belt;
    private Long cost_strapchange;
    private Long cost_service;
    private Long cost_dd;
    private Long cost_di;
    private Long cost_td;
    private Long cost_ti;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private String sign;
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private Missing[] missings;
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private Accesories[] accesories;
    @OneToOne
    private Cotization cotization;
    private long adjustedPrice;
    @ManyToOne
    @JoinColumn(name = "inspect_id")
    private StatesYesNo inspect;
    @ManyToOne
    @JoinColumn(name = "state_dynamic_id")
    private StatesNoYes state_dynamic;
    private String obs_dynamic;


    @PrePersist
    private void persistDate(){
        this.date = LocalDate.now();
    }

    public ExpertiseV2() {
    }

    public ExpertiseV2(String domain, String km, String color, String motor, Transmision transmision, Traccion traction, Fuel fuel, States state_motor, String obs_motor, Long cost_motor_rep, StatesYesNo state_fluid, String obs_fluid, Long cost_fluid_rep, States state_box, String obs_box, Long cost_box_rep, States state_traccion4, String obs_traccion4, Long cost_traccion4_rep, States state_suspension, String obs_suspension, Long cost_suspension_rep, States state_brakes, String obs_brakes, Long cost_brakes_rep, StatesYesNo state_wearbrakes, String obs_wearbrakes, Long cost_wearbrakes_rep, StatesYesNo state_electric, String obs_electric, Long cost_electric_rep, States state_headlights, String obs_headlights, Long cost_headlights_rep, States state_upholstered, String obs_upholstered, Long cost_upholstered_rep, StatesYesNo state_wheel, String obs_wheel, StatesNoYes state_accesories, int cant_cloths, States state_glass, String obs_glass, Long cost_glass_rep, States state_patent, String obs_patent, StatesYesNo state_hail, String obs_hail, StatesNoYes state_air, String obs_air, Long cost_air_rep, StatesFem state_dd, String mark_dd, StatesFem state_di, String mark_di, StatesFem state_td, String mark_td, StatesFem state_ti, String mark_ti, States state_batery, String obs_batery, StatesYesNo state_missing, @NotNull Attacheds[] attacheds, String photo_card, String photo_km, StatesYesNoDoesNotCorrespond strapchange, int servicekm, StatesYesNo oilconsumption, StatesYesNo waterconsumption, String photo_service, String photo_belt, User user, Accesories[] accesories, StatesYesNo inspect, StatesNoYes state_dynamic, String obs_dynamic) {
        this.domain = domain;
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
        this.state_accesories = state_accesories;
        this.cant_cloths = cant_cloths;
        this.state_glass = state_glass;
        this.obs_glass = obs_glass;
        this.cost_glass_rep = cost_glass_rep;
        this.state_patent = state_patent;
        this.obs_patent = obs_patent;
        this.state_hail = state_hail;
        this.obs_hail = obs_hail;
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
        this.state_missing = state_missing;
        this.attacheds = attacheds;
        this.photo_card = photo_card;
        this.photo_km = photo_km;
        this.strapchange = strapchange;
        this.servicekm = servicekm;
        this.oilconsumption = oilconsumption;
        this.waterconsumption = waterconsumption;
        this.photo_service = photo_service;
        this.photo_belt = photo_belt;
        this.user = user;
        this.accesories = accesories;
        this.inspect = inspect;
        this.state_dynamic = state_dynamic;
        this.obs_dynamic = obs_dynamic;
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

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
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

    public Transmision getTransmision() {
        return transmision;
    }

    public void setTransmision(Transmision transmision) {
        this.transmision = transmision;
    }

    public Traccion getTraction() {
        return traction;
    }

    public void setTraction(Traccion traction) {
        this.traction = traction;
    }

    public Fuel getFuel() {
        return fuel;
    }

    public void setFuel(Fuel fuel) {
        this.fuel = fuel;
    }

    public States getState_motor() {
        return state_motor;
    }

    public void setState_motor(States state_motor) {
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

    public StatesYesNo getState_fluid() {
        return state_fluid;
    }

    public void setState_fluid(StatesYesNo state_fluid) {
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

    public States getState_box() {
        return state_box;
    }

    public void setState_box(States state_box) {
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

    public States getState_traccion4() {
        return state_traccion4;
    }

    public void setState_traccion4(States state_traccion4) {
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

    public States getState_suspension() {
        return state_suspension;
    }

    public void setState_suspension(States state_suspension) {
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

    public States getState_brakes() {
        return state_brakes;
    }

    public void setState_brakes(States state_brakes) {
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

    public StatesYesNo getState_wearbrakes() {
        return state_wearbrakes;
    }

    public void setState_wearbrakes(StatesYesNo state_wearbrakes) {
        this.state_wearbrakes = state_wearbrakes;
    }

    public String getObs_wearbrakes() {
        return obs_wearbrakes;
    }

    public void setObs_wearbrakes(String obs_wearbrakes) {
        this.obs_wearbrakes = obs_wearbrakes;
    }

    public Long getCost_wearbrakes_rep() {
        return cost_wearbrakes_rep;
    }

    public void setCost_wearbrakes_rep(Long cost_wearbrakes_rep) {
        this.cost_wearbrakes_rep = cost_wearbrakes_rep;
    }

    public StatesYesNo getState_electric() {
        return state_electric;
    }

    public void setState_electric(StatesYesNo state_electric) {
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

    public States getState_headlights() {
        return state_headlights;
    }

    public void setState_headlights(States state_headlights) {
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

    public States getState_upholstered() {
        return state_upholstered;
    }

    public void setState_upholstered(States state_upholstered) {
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

    public StatesYesNo getState_wheel() {
        return state_wheel;
    }

    public void setState_wheel(StatesYesNo state_wheel) {
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

    public StatesNoYes getState_accesories() {
        return state_accesories;
    }

    public void setState_accesories(StatesNoYes state_accesories) {
        this.state_accesories = state_accesories;
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

    public States getState_glass() {
        return state_glass;
    }

    public void setState_glass(States state_glass) {
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

    public States getState_patent() {
        return state_patent;
    }

    public void setState_patent(States state_patent) {
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

    public StatesYesNo getState_hail() {
        return state_hail;
    }

    public void setState_hail(StatesYesNo state_hail) {
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

    public StatesNoYes getState_air() {
        return state_air;
    }

    public void setState_air(StatesNoYes state_air) {
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

    public StatesFem getState_dd() {
        return state_dd;
    }

    public void setState_dd(StatesFem state_dd) {
        this.state_dd = state_dd;
    }

    public String getMark_dd() {
        return mark_dd;
    }

    public void setMark_dd(String mark_dd) {
        this.mark_dd = mark_dd;
    }

    public StatesFem getState_di() {
        return state_di;
    }

    public void setState_di(StatesFem state_di) {
        this.state_di = state_di;
    }

    public String getMark_di() {
        return mark_di;
    }

    public void setMark_di(String mark_di) {
        this.mark_di = mark_di;
    }

    public StatesFem getState_td() {
        return state_td;
    }

    public void setState_td(StatesFem state_td) {
        this.state_td = state_td;
    }

    public String getMark_td() {
        return mark_td;
    }

    public void setMark_td(String mark_td) {
        this.mark_td = mark_td;
    }

    public StatesFem getState_ti() {
        return state_ti;
    }

    public void setState_ti(StatesFem state_ti) {
        this.state_ti = state_ti;
    }

    public String getMark_ti() {
        return mark_ti;
    }

    public void setMark_ti(String mark_ti) {
        this.mark_ti = mark_ti;
    }

    public States getState_batery() {
        return state_batery;
    }

    public void setState_batery(States state_batery) {
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

    public StatesYesNo getState_missing() {
        return state_missing;
    }

    public void setState_missing(StatesYesNo state_missing) {
        this.state_missing = state_missing;
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

    public String getPhoto_km() {
        return photo_km;
    }

    public void setPhoto_km(String photo_km) {
        this.photo_km = photo_km;
    }

    public StatesYesNoDoesNotCorrespond getStrapchange() {
        return strapchange;
    }

    public void setStrapchange(StatesYesNoDoesNotCorrespond strapchange) {
        this.strapchange = strapchange;
    }

    public int getServicekm() {
        return servicekm;
    }

    public void setServicekm(int servicekm) {
        this.servicekm = servicekm;
    }

    public StatesYesNo getOilconsumption() {
        return oilconsumption;
    }

    public void setOilconsumption(StatesYesNo oilconsumption) {
        this.oilconsumption = oilconsumption;
    }

    public StatesYesNo getWaterconsumption() {
        return waterconsumption;
    }

    public void setWaterconsumption(StatesYesNo waterconsumption) {
        this.waterconsumption = waterconsumption;
    }

    public Long getCostrep() {
        return costrep;
    }

    public void setCostrep(Long costrep) {
        this.costrep = costrep;
    }

    public Long getTakingprice() {
        return takingprice;
    }

    public void setTakingprice(Long takingprice) {
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

    public Long getCost_strapchange() {
        return cost_strapchange;
    }

    public void setCost_strapchange(Long cost_strapchange) {
        this.cost_strapchange = cost_strapchange;
    }

    public Long getCost_service() {
        return cost_service;
    }

    public void setCost_service(Long cost_service) {
        this.cost_service = cost_service;
    }

    public Long getCost_dd() {
        return cost_dd;
    }

    public void setCost_dd(Long cost_dd) {
        this.cost_dd = cost_dd;
    }

    public Long getCost_di() {
        return cost_di;
    }

    public void setCost_di(Long cost_di) {
        this.cost_di = cost_di;
    }

    public Long getCost_td() {
        return cost_td;
    }

    public void setCost_td(Long cost_td) {
        this.cost_td = cost_td;
    }

    public Long getCost_ti() {
        return cost_ti;
    }

    public void setCost_ti(Long cost_ti) {
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

    public Missing[] getMissings() {
        return missings;
    }

    public void setMissings(Missing[] missings) {
        this.missings = missings;
    }

    public Accesories[] getAccesories() {
        return accesories;
    }

    public void setAccesories(Accesories[] accesories) {
        this.accesories = accesories;
    }

    public Cotization getCotization() {
        return cotization;
    }

    public void setCotization(Cotization cotization) {
        this.cotization = cotization;
    }

    public long getAdjustedPrice() {
        return adjustedPrice;
    }

    public void setAdjustedPrice(long adjustedPrice) {
        this.adjustedPrice = adjustedPrice;
    }

    public StatesYesNo getInspect() {
        return inspect;
    }

    public void setInspect(StatesYesNo inspect) {
        this.inspect = inspect;
    }

    public StatesNoYes getState_dynamic() {
        return state_dynamic;
    }

    public void setState_dynamic(StatesNoYes state_dynamic) {
        this.state_dynamic = state_dynamic;
    }

    public String getObs_dynamic() {
        return obs_dynamic;
    }

    public void setObs_dynamic(String obs_dynamic) {
        this.obs_dynamic = obs_dynamic;
    }
}
