package co.com.personal.hnino.proyectoapolo.data.model

import android.content.Context
/*
Share Preferece => Es Util para contar con percistencia de información, para guardar indormación y mantenerla,
no debemos abusar ya que para persistencia robusta existen mejores herramientas como Room, como su nombre lo dice,
las Share Preference es para contar con preferencias del usuario
*/
class PreferenciasUser(val context:Context) {

    val SHARED_NAME_DB_PREF:String = "Bd_PreferenciasUsuario"
    val SHARED_USER_NAME:String = "username"
    val SHARED_USER_EMAIL:String = "email"
    val SHARED_USER_SESION_ACTIVA:String = "estadoSesion"
    val STORAGE = context.getSharedPreferences(SHARED_NAME_DB_PREF, 0) // Se envian como parametros un nombre, para el ejm es "Bd_PreferenciasUsuario" y el modo de
    //la base de datos, para el ejm usamos el modo 0

    fun saveName(name:String){

        STORAGE.edit().putString(SHARED_USER_NAME, name).apply() // le estamos diciendo que a la Bd "Bd_PreferenciasUsuario" la vamos a editar para añadir un String
        // y como parametros recibe la llave(id asocado al valor guardado) y el valor que deseamos guardar que esta en la variable name

    }
    fun saveCorreE(email:String){

        STORAGE.edit().putString(SHARED_USER_EMAIL, email).apply() // le estamos diciendo que a la Bd "Bd_PreferenciasUsuario" la vamos a editar para añadir un String
        // y como parametros recibe la llave(id asocado al valor guardado) y el valor que deseamos guardar que esta en la variable email
    }

    fun saveSesionActiva(estadoSesion:Boolean){

        STORAGE.edit().putBoolean(SHARED_USER_SESION_ACTIVA, estadoSesion).apply() // le estamos diciendo que a la Bd "Bd_PreferenciasUsuario" la vamos a editar para añadir un Boolean
        // y como parametros recibe la llave(id asocado al valor guardado) y el valor que deseamos guardar que esta en la variable estadoSesion
    }

    fun getName():String{
        return STORAGE.getString(SHARED_USER_NAME, "")!! // Estamos solicitando a la BD que nos devuelba el valor que tiene la clave SHARED_USER_NAME. el segundo
        //parametros es un valor por defecto (String vacio) que sea el retornado en el caso en que el valor de esa clave no halla sido guardado con anterioridad.
        //las 2 exclamaciones (!!) es por que el valor devuelto prodria ser nulo
    }

    fun getCorreoE():String{
        return STORAGE.getString(SHARED_USER_EMAIL, "")!! // Estamos solicitando a la BD que nos devuelba el valor que tiene la clave SHARED_USER_EMAIL. el segundo
        //parametros es un valor por defecto (String vacio) que sea el retornado en el caso en que el valor de esa clave no halla sido guardado con anterioridad.
        //las 2 exclamaciones (!!) es por que el valor devuelto prodria ser nulo
    }

    fun getEstadoSesion():Boolean{
        return STORAGE.getBoolean(SHARED_USER_SESION_ACTIVA, false)!! // Estamos solicitando a la BD que nos devuelba el valor que tiene la clave SHARED_USER_SESION_ACTIVA. el segundo
        //parametros es un valor por defecto (false) que sea el retornado en el caso en que el valor de esa clave no halla sido guardado con anterioridad.
        //las 2 exclamaciones (!!) es por que el valor devuelto prodria ser nulo
    }

    fun deletePreferenciasUsuario(){ //Este metodo va a borrar toda la bases de datos asociada a la clase PreferenciasUser y lo ejecutamos cuando el usuario
        //use el botón de CERRAR SESION
        STORAGE.edit().clear().apply()
    }

}