package co.com.personal.hnino.proyectoapolo.data.model.entidades

import com.google.gson.annotations.SerializedName

//Este es el modelo de datos que usaremos para trabajar con Retrofit de capturar toda la información de las imagenees por medio de la API

data class ImgApoloModel (
    @SerializedName("collection") val collectionImgsApolo: CollectionImgsApoloModel? = null// desde la API se recibe
    // el parametro collection pero el dato lo almacenamos en la variable collectionImgsApolo, simplemente cambiamos el nombre
    // para que la aplicación sea mas facil de entender
)

data class CollectionImgsApoloModel (
    @SerializedName("version") val version:String? = "Sin Información",
    @SerializedName("href") val href:String? = "Sin Información",
    @SerializedName("items") val items: List<ItemDatosImageModel>? = null,
)

data class ItemDatosImageModel (
    @SerializedName("href") val href:String? = "Sin Información",
    @SerializedName("data") val data:List<ItemDetallesImageModel>? = null,
    @SerializedName("links") val links:List<ItemLinksImageModel>? = null,
)

data class ItemDetallesImageModel(
    @SerializedName("center") val center:String? = "Sin Información",
    @SerializedName("title") val title:String? = "Sin Información",
    @SerializedName("nasa_id") val nasa_id:String,
    @SerializedName("media_type") val media_type:String? = "Sin Información",
    @SerializedName("keywords") val keywords:ArrayList<String>? = null,
    @SerializedName("date_created") val date_created:String? = "Sin Información",
    @SerializedName("description") val description:String? = "Sin Información",
)

data class ItemLinksImageModel(
    @SerializedName("href") val href:String? = "Sin Información",
    @SerializedName("rel") val rel:String? = "Sin Información",
    @SerializedName("render") val render:String? = "Sin Información",
)

