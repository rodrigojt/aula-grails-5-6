package br.ufscar.minhasTarefas

import grails.converters.JSON

import grails.validation.ValidationException

class ListaTarefaController {

    def listaTarefaService // Convenção
    // def listaTarefaService = new() ListaTarefaService()

    def messageSource

    def inserir(ListaTarefa novaListaTarefa) {

        /*
        println params
        println params.nome
        println params.usuario
        */

        try {
            novaListaTarefa =
                    listaTarefaService.inserir(novaListaTarefa)

            render novaListaTarefa as JSON
        }
        catch (ValidationException validationException) {
            // Percorrer todos os erros
            def erros = []
            validationException.errors.allErrors.each {
                erros << messageSource.getMessage(it, null)
            }
            // render (['erros': validationException.message] as JSON)
            render(['erros': erros] as JSON)
            return
        }
        catch (Exception exception) {
            // println exception.message
            render(['erro': exception.message] as JSON)
            return
        }

        /*
        // if (!novaListaTarefa.save(failOnError:true)) {
        if (!novaListaTarefa.save()) {
            // faz alguma coisa
            render (novaListaTarefa.errors.allErrors as JSON)
            return
        }
        */

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
            render(['erro': "Nao existe o objeto selecionado"] as JSON)
            return
        }

        // Se remover mostra sucesso
        listaTarefa.delete()
        render(['sucesso': "Objeto removido com sucesso"] as JSON)

    }
}
