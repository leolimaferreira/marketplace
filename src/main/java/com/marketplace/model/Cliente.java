package com.marketplace.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "clientes")
@Setter
@Getter
public class Cliente extends Usuario {

    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY)
    private List<Endereco> enderecos;
}
