package br.ufscar.minhasTarefas

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(ListaTarefaService)
@Mock(ListaTarefa)
class ListaTarefaServiceSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    /*
    void "teste alguma coisa"() {
        expect:"que esteja consertado"
            true == false
    }
    */

    void "listar retorna lista vazia quando não tem listas cadastradas"() {
        expect: "Uma listagem vazia"
        service.listar().empty
    }

    void "Lista retorna as listas cadastradas" (){
        setup: "Criar duas listas tarefa"
        def lista1 = new ListaTarefa().save(validate: false, flush: true)
        def lista2 = new ListaTarefa().save(validate: false, flush: true)

        // expect: "Tamanho da lista = 2"
        // service.listar().size() == 2

        when: "Listar todas as listas de tarefas"
        def listasRetornadas = service.listar()
        then: "ela tem que ter tamanho 2"
        listasRetornadas.size() == 2
        and: "Deve conter as listas criadas no teste"
        listasRetornadas.containsAll(lista1, lista2)
    }
}
