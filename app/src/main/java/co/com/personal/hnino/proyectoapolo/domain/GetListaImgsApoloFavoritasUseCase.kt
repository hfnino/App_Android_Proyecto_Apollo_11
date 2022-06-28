package co.com.personal.hnino.proyectoapolo.domain

import co.com.personal.hnino.proyectoapolo.data.ImgsApolloRepository
import co.com.personal.hnino.proyectoapolo.domain.model.ItemDatosImage
import javax.inject.Inject

class GetListaImgsApoloFavoritasUseCase @Inject constructor(private val repository: ImgsApolloRepository) {

    suspend operator fun invoke(): List<ItemDatosImage>?{
        val listaItemDatosImagefavoritas = repository.getListaImgsApoloFavoritasFromDataBase()
        return listaItemDatosImagefavoritas
    }
    // el operador de funcion invoke, nos permite llamar a esta funcion automaticamente sin nececidad de llamar a ningún metodo de esta clase.
    // por ejemplo, si en cualquier otra clase creamos una instancia de esta clase, es decir por ejemplo "var xyz = GetListaImgsApoloFavoritasUseCase()", y luego
    // quiero llamar a la funcion "invoke", facilmente lo podemos hacer asi =>   xyz()       ----- como vemos la funcion invoke se llama automaticamente
    // sin necesita de llamar a ningun metodo.
    // Esta función nos retorna el listado de objetos de tipo ItemDatosImage obtenidas de la base de datos local
}