package fr.pmu.gateways.mapper;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Race")
public class RaceJpaMapper {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String uuid;
    private LocalDate date;
    private String name;
    private int number;

    @OneToMany(
            mappedBy = "race",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<StarterJpaMapper> starters = new ArrayList<>();

    public RaceJpaMapper(String uuid, LocalDate date, String name, int number) {
        this.uuid = uuid;
        this.date = date;
        this.name = name;
        this.number = number;
    }

    public RaceJpaMapper() {

    }

    public void addStarters(List<StarterJpaMapper> startersList){
        starters.addAll(startersList);
        startersList.forEach(s -> s.setRace(this));
    }

    public  void removeStarter(StarterJpaMapper starter){
        starters.remove(starter);
        starter.setRace(null);
        reOrderStarters();
    }

    private void reOrderStarters(){
        for(int i = 0; i < starters.size(); i++){
            starters.get(i).setNumber(i+1);
        }
    }

    public List<StarterJpaMapper> getStarters() {
        return starters;
    }

    public void setStarters(List<StarterJpaMapper> starters) {
        this.starters = starters;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
