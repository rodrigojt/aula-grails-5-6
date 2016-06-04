package br.ufscar.minhasTarefas

class ListaTarefa {

    String usuario
    String nome
    Boolean ativo = true

    static constraints = {
        usuario unique:['nome'], size: 6..10
        nome size: 5..60
    }
}
