package co.com.personal.hnino.proyectoapolo.data.network

import co.com.personal.hnino.proyectoapolo.data.model.entidades.ImgApoloModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface ImgsApolloAPIClientInteface { // Al ser una interfaz, no se le puede poner un @Inject constructor() para que se pueda
    //inyectar en otras clases. para resolver este problema se creo el modulo NetworkModuleProvider donde se creo la función
    // provideImgsApoloApiClient que con ayuda de la etiqueta @Providers retorna un objeto de tipo ImgsApolloAPIClientInteface
    // significa que si en cualquier clase necesitamos una instacia de interfaz ImgsApolloAPIClientInteface, simplemente la
    // inyectamos, y Dagger Hilt va a buscar y a encontrar esta a esta funcion, que retorna un objeto de ImgsApolloAPIClientInteface
    // y la va a proveer.

    //En esta inrterface, creamos los metodos abstractos que necesitamos para consumir los servicios API requeridos

    @GET
    suspend fun getApiImgsApolo(@Url url:String):Response<ImgApoloModel> // este metodo nos permite acceder al servicio de nuestra API
    //por medio de peticiones GET y debe ser de tipo suspend ya que se usa en una corrutina (hilo diferente al principal)
}
