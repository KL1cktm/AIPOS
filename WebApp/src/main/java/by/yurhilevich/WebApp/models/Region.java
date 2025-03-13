package by.yurhilevich.WebApp.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "region")
public class Region {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long regionId;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "region")
    private Set<Combine> combines = new HashSet<>();

}