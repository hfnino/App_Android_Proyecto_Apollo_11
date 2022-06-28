package co.com.personal.hnino.proyectoapolo.domain.model

import co.com.personal.hnino.proyectoapolo.data.database.entitys.ItemDetallesImageEntity
import co.com.personal.hnino.proyectoapolo.data.database.entitys.LinksItemImgApoloEntity
import co.com.personal.hnino.proyectoapolo.data.model.entidades.ItemDatosImageModel
import co.com.personal.hnino.proyectoapolo.data.model.entidades.ItemDetallesImageModel
import co.com.personal.hnino.proyectoapolo.data.model.entidades.ItemLinksImageModel

//Este es el modelo de datos final con el que la UI y dominio van a trabajar, de tal forma que si hay que cambiar el modelo de datos la base de datos, o de Retrifit
// no va a pasar nada ya que son modelos de datos independientes y la información que llegue al dominio va a ser siempre este modelo de datos

//--------------------------------------------------------------------------------------//
/*
data class ImgApolo  (
    val collectionImgsApolo: CollectionImgsApolo? = null
)
*/
//--------------------------------------------------------------------------------------//
/*
data class CollectionImgsApolo (
    val version:String? = "Sin Información",
    val href:String? = "Sin Información",
    val items: List<ItemDatosImage>? = null,
)
*/
//--------------------------------------------------------------------------------------//
data class ItemDatosImage (
    val href:String? = "Sin Información",
    val data:List<ItemDetallesImage>? = null,
    val links:List<ItemLinksImage>? = null,
)

fun ItemDatosImageModel.converterToItemDatosImage() : ItemDatosImage {  //Función de extensión util para la funcionalidad del maper
    val itemDatosImage: ItemDatosImage = ItemDatosImage (href, data?.map { it.converterToItemDetallesImage() }, links?.map { it.converterToItemLinksImage() })
    return itemDatosImage
}
//--------------------------------------------------------------------------------------//
data class ItemDetallesImage(
    val nasa_id:String,
    val title:String? = "Sin Información",
    //val keywords:ArrayList<String>? = null,
    val description:String? = "Sin Información",
    var imgFavorita:Boolean = false
)

fun ItemDetallesImageModel.converterToItemDetallesImage() : ItemDetallesImage {  //Función de extensión util para la funcionalidad del maper
    val itemDetallesImage: ItemDetallesImage = ItemDetallesImage (nasa_id, title, description, imgFavorita = false) // los parametros
    //(title, nasa_id,description, imgFavorita = true) son los asociados a al objeto de la clase ItemDetallesImageModel, lo anterior debido a que estamos trabajando con una
    //    //funsión de extensión
    return itemDetallesImage
}

fun ItemDetallesImageEntity.converterToItemDetallesImage() : ItemDetallesImage {  //Función de extensión util para la funcionalidad del maper
    val itemDetallesImage: ItemDetallesImage = ItemDetallesImage (nasa_id, title, description, imgFavorita = imgFavorita) // los parametros
    //(title, nasa_id,description, imgFavorita = true) son los asociados a al objeto de la clase DetalleItemImgApoloEntity, lo anterior debido a que estamos trabajando con una
    //    //funsión de extensión
    return itemDetallesImage
}
//--------------------------------------------------------------------------------------//
data class ItemLinksImage(
    val href:String? = "Sin Información",
)

fun ItemLinksImageModel.converterToItemLinksImage() : ItemLinksImage {  //Función de extensión util para la funcionalidad del maper
    val itemLinksImage: ItemLinksImage = ItemLinksImage (href) // el parametro href es el de la clase ItemLinksImageModel, lo anterior debido a que estamos trabajando con una
    //funsión de extensión
    return itemLinksImage
}

fun LinksItemImgApoloEntity.converterToItemLinksImage() : ItemLinksImage {  //Función de extensión util para la funcionalidad del maper
    val itemLinksImage: ItemLinksImage = ItemLinksImage (href) // el parametro href es el de la clase ItemLinksImageModel, lo anterior debido a que estamos trabajando con una
    //funsión de extensión
    return itemLinksImage
}

