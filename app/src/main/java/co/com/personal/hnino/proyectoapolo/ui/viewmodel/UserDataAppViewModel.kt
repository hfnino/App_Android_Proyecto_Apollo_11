package co.com.personal.hnino.proyectoapolo.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import co.com.personal.hnino.proyectoapolo.data.model.PreferenciasUser
import co.com.personal.hnino.proyectoapolo.data.model.UserDataAppProvider

class UserDataAppViewModel: ViewModel() {

    val prefereciasUsuarioViewModel = MutableLiveData<PreferenciasUser>() //Declaramos la constante prefereciasUsuarioViewModel
    // de tipo LiveData mutable(que puede cambiar)

    fun getPrefereciasUsuarioCurrent(){ // cuando se usa esta función, se obtiene un un objeto de tipo PreferenciasUser
        val preferenciasUserActual = UserDataAppProvider.prefereciasUsuario

        prefereciasUsuarioViewModel.postValue(preferenciasUserActual) // la variable prefereciasUsuarioViewModel que es de tipo
        //LiveData se da cuenta que le hicimos un cambio y avisara a la activity y/o Fragment correspondiente y este
        //ultimo va a hacer lo que crea conveniente. Si no usaramos Live Data, tendriamos que configurar esta funcion
        // para que retorne el objeto UserDataApplication.prefereciasUsuario a la vista donde esta funcion fue llamada.
    }

    fun setPrefereciasUsuarioNewName(prefereciasUsuarioNewName: String){
        UserDataAppProvider.prefereciasUsuario.saveName(prefereciasUsuarioNewName)
    }

    fun setPrefereciasUsuarioNewCorreE(prefereciasUsuarioCorreE: String){
        UserDataAppProvider.prefereciasUsuario.saveCorreE(prefereciasUsuarioCorreE)
    }

    fun setPrefereciasUsuarioSesionActiva(prefereciasUsuarioSesionActiva: Boolean){
        UserDataAppProvider.prefereciasUsuario.saveSesionActiva(prefereciasUsuarioSesionActiva)
    }

    fun deletePreferenciasUsuario(){ //Este metodo va a borrar toda la bases de datos asociada a la clase PreferenciasUser y lo ejecutamos cuando el usuario
        //use el botón de CERRAR SESION
        UserDataAppProvider.prefereciasUsuario.deletePreferenciasUsuario() // //Este metodo va a borrar toda la bases de datos asociadas
        // a las preferencias deusuario
    }

}