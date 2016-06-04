package br.ufscar.minhasTarefas

import grails.converters.JSON

class ListaTarefaController {

    def inserir() {

        /*
        println params
        println params.nome
        println params.usuario
        */

        String nome = params.nome
        String usuario = params.usuario

        def novaListaTarefa = new ListaTarefa(nome: nome, usuario: usuario)

        // if (!novaListaTarefa.save(failOnError:true)) {
        if (!novaListaTarefa.save()) {
            // faz alguma coisa
            render (novaListaTarefa.errors.allErrors as JSON)
            return
        }

        render novaListaTarefa as JSON

    }

    // Listar todas as tarefas
    def listar() {
        render ListaTarefa.all as JSON
    }

    def filtrar() {

        String nome = params.nome
        String usuario = params.usuario
        Boolean ativa = params.boolean("ativa")

        println "usuário é: ${usuario}"
        println "nome é: ${nome}"
        println "ativa é: ${ativa}"

        def listasFiltradas = ListaTarefa.withCriteria {
            if (nome)
                eq('nome', nome) // atributo nome tem que ter valor igual ao da variável
            if (usuario)
                eq('usuario', usuario)
            if (ativa != null)
                eq('ativo', ativa)
        }

        render listasFiltradas as JSON

        /*
        if (params.usuario) {
            // render ListaTarefa.findAllByUsuarioAndNome(usuario, "Lista3") as JSON
            render ListaTarefa.findAllByUsuario(usuario) as JSON
            return
        } else {
            render ListaTarefa.findAll() as JSON
            return
        }
        */

    }

    def remover(ListaTarefa listaTarefa) {

        // println params
        // render "remove"

        println listaTarefa

        // Se não remover, mostra erro
        if (!listaTarefa) {
            render (['erro': "Nao existe o objeto selecionado"] as JSON)
            return
        }

        // Se remover mostra sucesso
        listaTarefa.delete()
        render (['sucesso': "Objeto removido com sucesso"] as JSON)

    }
}
