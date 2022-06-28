package co.com.personal.hnino.proyectoapolo.domain

import co.com.personal.hnino.proyectoapolo.data.ImgsApolloRepository
import co.com.personal.hnino.proyectoapolo.domain.model.ItemDatosImage
import javax.inject.Inject

class GetListaImgsApoloUseCase @Inject constructor(private val repository: ImgsApolloRepository) {  // Esta clase se creo con el objetivo de representar un caso de uso
    // Con @Inject constructor(), estamos preparando esta clase (que no es una activity ni view Models) para que pueda ser inyectada en otras clases y tambien
    // la preparamos para que se le pueda inyectar dependencias de otras clases que van a ser gestionadas por Dagger Hilt

    suspend operator fun invoke(queryByKeyWords:String): List<ItemDatosImage>?{
        val listaItemDatosImage = repository.getListaImgsApoloFromApi(queryByKeyWords)
        return listaItemDatosImage
    }
    // el operador de funcion invoke, nos permite llamar a esta funcion automaticamente sin nececidad de llamar a ningún metodo de esta clase.
    // por ejemplo, si en cualquier otra clase creamos una instancia de esta clase, es decir por ejemplo "var xyz = GetListaImgsApolloUseCase()", y luego
    // quiero llamar a la funcion "invoke", facilmente lo podemos hacer asi =>   xyz()       ----- como vemos la funcion invoke se llama automaticamente
    // sin necesita de llamar a ningun metodo.
    // Esta función nos retorna el listado de objetos de tipo ItemDatosImage> en JSON obtenidas desde la API
}