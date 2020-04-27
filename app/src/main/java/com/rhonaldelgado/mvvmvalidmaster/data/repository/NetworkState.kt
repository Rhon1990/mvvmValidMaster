package com.rhonaldelgado.mvvmvalidmaster.data.repository

enum class Status {
    RUNNING,
    SUCCESS,
    FAILED

}

class NetworkState(val status: Status, val msg: String) {

    companion object {

        val LOADED: NetworkState
        val LOADING: NetworkState
        val ERROR: NetworkState
        val ENDOFLIST: NetworkState

        init {
            LOADED = NetworkState(Status.SUCCESS, "Satisfactorio")

            LOADING = NetworkState(Status.RUNNING, "Corriendo")

            ERROR = NetworkState(Status.FAILED, "Algo salió mal")

            ENDOFLIST = NetworkState(Status.FAILED, "Has llegado al final")
        }
    }
}