package fr.pmu.gateways.mapper;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "Starter")
public class StarterJpaMapper {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int number;

    @ManyToOne(fetch = FetchType.LAZY)
    private RaceJpaMapper race;

    public StarterJpaMapper(String name, int number) {
        this.name = name;
        this.number = number;
    }

    public StarterJpaMapper() {

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

    public RaceJpaMapper getRace() {
        return race;
    }

    public void setRace(RaceJpaMapper race) {
        this.race = race;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StarterJpaMapper that = (StarterJpaMapper) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
