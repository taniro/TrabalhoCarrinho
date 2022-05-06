package tads.eaj.trabalhocarrinho.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Usuario {
    private int id;
    private String nome;
    private String senha;
    private boolean adm;
}
