package co.com.personal.hnino.proyectoapolo.data.model

import android.content.Context


//************************************************************************************//
//Esta clase  y sus metodos no se esta usando, ya que se implemento                    //
// persistencia de datos con Room.                                                    //
//************************************************************************************//



/*
Share Preferece => sirbe para contar con percistencia de información, para guardar información y mantenerla,
no debemos abusar ya que para persistencia robusta existen mejores herramientas como Room, como su nombre lo dice,
las Share Preference es para contar con preferencias del usuario
*/

class PreferenciasImgsApolo(val context: Context) {

    val SHARED_NAME_DB_IMGS_PREF:String = "Bd_PreferenciasImgsApolo"
    val SHARED_ID_IMG: String = "idimg"
    val SHARED_TITULO_IMG:String = "tituloImag"
    val SHARED_IMG_FAVORITA:String = "favoritoImag"
    val STORAGE = context.getSharedPreferences(SHARED_NAME_DB_IMGS_PREF, 0) // Se envian como parametros un nombre, para el ejm es "Bd_PreferenciasImgsApolo" y el modo de
    //la base de datos, para el ejm usamos el modo 0

    fun saveIdImg(idImg:Int){

        STORAGE.edit().putInt(SHARED_ID_IMG, idImg).apply() // le estamos diciendo que a la Bd "Bd_PreferenciasImgsApolo" la vamos a editar para añadir un entero
        // y como parametros recibe la llave(id asocado al valor guardado) y el valor que deseamos guardar que esta en la variable idImg
    }

    fun saveTituloImg(tituloImg:String){

        STORAGE.edit().putString(SHARED_TITULO_IMG, tituloImg).apply() // le estamos diciendo que a la Bd "Bd_PreferenciasImgsApolo" la vamos a editar para añadir un String
        // y como parametros recibe la llave(id asocado al valor guardado) y el valor que deseamos guardar que esta en la variable tituloImg
    }

    fun saveImgFavorita(imgFavorita:Boolean){

        STORAGE.edit().putBoolean(SHARED_IMG_FAVORITA, imgFavorita).apply() // le estamos diciendo que a la Bd "Bd_PreferenciasImgsApolo" la vamos a editar para añadir un Boolean
        // y como parametros recibe la llave(id asocado al valor guardado) y el valor que deseamos guardar que esta en la variable imgFavorita
    }

    fun getIdImg():Int{
        return STORAGE.getInt(SHARED_ID_IMG, 0) // Estamos solicitando a la BD que nos devuelba el valor que tiene la clave SHARED_ID_IMG. el segundo
        //parametros es un valor por defecto (Int = 0) que sea el retornado en el caso en que el valor de esa clave no halla sido guardado con anterioridad.
        //las 2 exclamaciones (!!) es por que el valor devuelto prodria ser nulo
    }
    fun getTituloImg():String{
        return STORAGE.getString(SHARED_TITULO_IMG, "")!! // Estamos solicitando a la BD que nos devuelba el valor que tiene la clave SHARED_TITULO_IMG. el segundo
        //parametros es un valor por defecto (String vacio) que sea el retornado en el caso en que el valor de esa clave no halla sido guardado con anterioridad.
        //las 2 exclamaciones (!!) es por que el valor devuelto prodria ser nulo
    }
    fun getImgFavorita():Boolean{
        return STORAGE.getBoolean(SHARED_IMG_FAVORITA, false) // Estamos solicitando a la BD que nos devuelba el valor que tiene la clave SHARED_IMG_FAVORITA. el segundo
        //parametros es un valor por defecto (Booleane False) que sea el retornado en el caso en que el valor de esa clave no halla sido guardado con anterioridad.
        //las 2 exclamaciones (!!) es por que el valor devuelto prodria ser nulo
    }
}