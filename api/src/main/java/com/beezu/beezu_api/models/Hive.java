package com.beezu.beezu_api.models;

import jakarta.persistence.*;

import java.io.Serializable;
@Entity
@Table(name = "tb_hive")
public class Hive implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "discipline_id")
    private Long id;
    @Column(name = "health")
    private Integer health;
    @Column(name = "honey_level")
    private Integer honeyLevel;
    @OneToOne
    @MapsId
    @JoinColumn(name = "disciplie_id")
    private Discipline discipline;

    public Hive(Integer health, Integer honeyLevel){
        this.health = 100;
        this.honeyLevel = 0;
    }

    public Hive(){

    }

    public void addHoney(Integer amount){
        if (amount == null || amount == 0) return;
        this.honeyLevel += amount;
        updateHealth();
    }

    public void updateHealth(){
        if(honeyLevel == 500){
            health = 100;
        } else if (honeyLevel >= 300) {
            health = 75;
        } else if (honeyLevel >= 100) {
            health = 50;
        }
        else {
            health = 25;
        }
    }

    public Long getId() {
        return id;
    }

    public Integer getHealth() {
        return health;
    }

    public Integer getHoneyLevel() {
        return honeyLevel;
    }

    public Discipline getDiscipline() {
        return discipline;
    }

    public void setHealth(Integer health) {
        this.health = health;
    }

    public void setHoneyLevel(Integer honeyLevel) {
        this.honeyLevel = honeyLevel;
    }
}
