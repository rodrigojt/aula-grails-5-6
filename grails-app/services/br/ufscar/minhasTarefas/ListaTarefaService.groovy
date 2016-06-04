package br.ufscar.minhasTarefas

import grails.transaction.Transactional
import grails.validation.ValidationException

@Transactional
class ListaTarefaService {

    def usuarioService

    def listar(){
        return ListaTarefa.all
    }

    ListaTarefa inserir(ListaTarefa novaListaTarefa) {

        /*
        String tipoUsuario = "Normal"
        if (usuario == "Grails") {
            tipoUsuario = "Premium"
        }
        */

        def usuario = novaListaTarefa.usuario;
        def nome = novaListaTarefa.nome;

        String tipoUsuario = usuarioService.obterTipoUsuario(usuario)
        int numeroMaximoListasUsuario = obterNumeroMaximoListasUsuario(tipoUsuario)
        def numeroListaUsuario = obterNumeroListasUsuario(usuario)

        if (numeroListaUsuario >= numeroMaximoListasUsuario) {
            throw new Exception("usuario atingiu o numero limite de listas: ${numeroMaximoListas}")
        }

        // def novaListaTarefa = new ListaTarefa(nome: nome, usuario: usuario)

        // if (!novaListaTarefa.save(failOnError:true)) {
        if (!novaListaTarefa.save()) {
            throw new ValidationException("", novaListaTarefa.errors)
        }

        return novaListaTarefa

    }


    private obterNumeroListasUsuario(String usuario) {
        return ListaTarefa.countByUsuario(usuario)
    }

    private int obterNumeroMaximoListasUsuario(tipoUsuario) {
        Integer numeroMaximoListas = 3
        if (tipoUsuario == "Premium") {
            numeroMaximoListas = 5
        }
        return numeroMaximoListas
    }
}
