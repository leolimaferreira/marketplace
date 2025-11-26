package com.marketplace.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "donos")
@Setter
@Getter
public class Dono extends Usuario {

    @OneToMany(mappedBy = "dono", fetch = FetchType.LAZY)
    private List<Loja> lojas;
}
