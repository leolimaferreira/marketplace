package com.marketplace.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "clientes")
@DiscriminatorValue("CLIENTE")
@Setter
@Getter
public class Cliente extends Usuario {

    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY)
    private List<Endereco> enderecos;
}
