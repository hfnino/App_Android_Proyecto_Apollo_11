package co.com.personal.hnino.proyectoapolo.data.model

import co.com.personal.hnino.proyectoapolo.data.model.entidades.CollectionImgsApoloModel
import co.com.personal.hnino.proyectoapolo.data.model.entidades.ItemDatosImageModel
import co.com.personal.hnino.proyectoapolo.domain.model.ItemDatosImage
import javax.inject.Inject
import javax.inject.Singleton

@Singleton // Usamos el patron de diseño Singleton para mantener una unica instancia de la clase DataTemporalProvider, de tal
// manera que no se creen varias cada vez que llamemos a esta clase. Lo anterior para evitar consumir recursos de memoria sin tener
// la necesitad. Ademas nos ayuda a evitar errores ya que en la clase ImgsApolloRepository y en la ItemsImgsApoloViewModel
// usamos inyección de dependencias por lo que estamos creando 2 instancias diferentes de la clase DataTemporalProvider,
// una tendria la información que nencesitamos y la otra no tendria nada. al usar el patron de diseño Singleton, evitamos
// este problema

class DataTemporalProvider @Inject constructor() {  // con @Inject constructor() habilitamos esta clase para que pueda ser
    // inyectada en otras clases.

    private lateinit var collectionItemsImgsApolo: CollectionImgsApoloModel // Esta variable no va a poder ser accedida desde fuera de esta
    //clase, por que esta declarada como privada

    private lateinit var listaItemsImgsApoloModel: List<ItemDatosImageModel> // Esta variable no va a poder ser accedida desde fuera de esta
    //clase, por que esta declarada como privada

    private lateinit var listaItemsImgsApolo: List<ItemDatosImage> // Esta variable no va a poder ser accedida desde fuera de esta
    //clase, por que esta declarada como privada

    private lateinit var itemDatosImage: ItemDatosImage // Esta variable no va a poder ser accedida desde fuera de esta
    //clase, por que esta declarada como privada

    fun getCollectionItemsImgsApoloModel() : CollectionImgsApoloModel {
        return collectionItemsImgsApolo
    }

    fun setCollectionItemsImgsApoloModel(collectionItemsImgsApoloAux: CollectionImgsApoloModel){
        collectionItemsImgsApolo = collectionItemsImgsApoloAux
    }

    fun getListaItemsImgsApoloModel() : List<ItemDatosImageModel> {
        return listaItemsImgsApoloModel
    }
    fun setListaItemsImgsApoloModel(listaItemsImgsApoloAux: List<ItemDatosImageModel>){
        listaItemsImgsApoloModel = listaItemsImgsApoloAux
    }

    fun getListaItemsImgsApolo() : List<ItemDatosImage> {
        return listaItemsImgsApolo
    }
    fun setListaItemsImgsApolo(listaItemsImgsApoloAux: List<ItemDatosImage>){
        listaItemsImgsApolo = listaItemsImgsApoloAux
    }

    fun getItemDatosImagen() : ItemDatosImage {
        return itemDatosImage
    }

    fun setItemDatosImagen(itemDatosImageAux: ItemDatosImage){
       itemDatosImage = itemDatosImageAux
    }
}
