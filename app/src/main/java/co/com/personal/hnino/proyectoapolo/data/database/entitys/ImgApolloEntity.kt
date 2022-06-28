package co.com.personal.hnino.proyectoapolo.data.database.entitys

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import co.com.personal.hnino.proyectoapolo.domain.model.ItemDetallesImage
import co.com.personal.hnino.proyectoapolo.domain.model.ItemLinksImage

// Este es el modelo de datos que usaremos para trabajar con la base de datos local (SQLite) por medio de la libreria Room

/*
@PrimaryKey(autoGenerate = true) =>  cuando usamos la etiqueta @PrimaryKey antes de un atribito , por ejemplo antes del atributo
val id: Int = 0, estamos configurando ese campo para que sea unico y no se podra repetir. por otro lado, cuando usamos la
propiedad "autoGenerate = true", estamos configurando la Entidad para que cuando se inserte un objeto en la base de datos
de dicha clase/Entidad, no necesitamos pasarle el valor del campo "id" ya que este valor se pondra automaticamente
y nunca se repetira de tal forma que podemos asegurarnos de que cada objeto es unico.

@ColumnInfo(name= "xxxxxxxxx") => cuando usamos esta etiqueta, estamos configurando como debe llamarse el campo/columna en la
base de datos, si no lo colocamos, room usara el nombre del la variable correspondiente pero lo recomendable es siempre usar
dicha etiqueta debido a que cuando generemos la versión en release podemos tener problemas.

@NonNull => esta etiqueta configura el campo correspondiente para decirle que no puede ser null
*/
//*********************************************************************************************//
/*
@Entity(tableName = "tbl_ImgsApolo")// Declaramos que la clase ImgApoloEntity es una entidad asociada a la tabla
//tbl_ImgApolo de la base de datos, y esta va a ser reconocida por Room
data class ImgApoloEntity (
    @PrimaryKey(autoGenerate = true)@ColumnInfo(name= "id") val id: Int = 0,
    @ColumnInfo(name= "collection") val collectionImgsApolo: CollectionImgsApoloEntity? = null
)
*/
//*********************************************************************************************//
/*
@Entity(tableName = "tbl_CollectionsImgsApolo")// Declaramos que la clase CollectionImgsApoloEntity es una entidad asociada a la tabla
//tbl_CollectionImgsApolo de la base de datos, y esta va a ser reconocida por Room
data class CollectionImgsApoloEntity (
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name= "id") val id: Int = 0,
    @ColumnInfo(name= "version") val version:String?,
    @NonNull
    @ColumnInfo(name= "href") val href:String?,
    //@ColumnInfo(name= "items") val items: List<ItemDatosImageEntity>? = null,
)
*/
//*********************************************************************************************//
@Entity(tableName = "tbl_ItemsDatosImages")// Declaramos que la clase ItemDatosImageDbEntity es una entidad asociada a la tabla
//// tbl_ItemsDatosImages de la base de datos, y esta va a ser reconocida por Room
data class ItemDatosImageEntity (
/*
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name= "id") val id: Int = 0,
*/
    @PrimaryKey
    @ColumnInfo(name= "nasa_id")
    val nasa_id: String,

    @ColumnInfo(name= "href")
    val href:String? = "Sin Información",
    //@ColumnInfo(name= "data") val data:List<DetalleItemImgApoloEntity>? = null,
    //@ColumnInfo(name= "links") val links:List<LinksItemImgApoloEntity>? = null,
)
//*********************************************************************************************//
@Entity(tableName = "tbl_ItemDetallesImage",
foreignKeys = [ForeignKey(entity = ItemDatosImageEntity::class, parentColumns = ["nasa_id"], childColumns = ["nasa_id_"])])
// Declaramos que la clase DetalleItemImgApoloEntity es una entidad asociada a la tabla tbl_DetallesItemsImgsApolo de la base de datos,
// y esta va a ser reconocida por Room, ademas cuenta con una llave foranea en la entidad ItemDatosImageEntity
// asociada al campo "nasa_id" y se relaciona con el campo nasa_id_ de la entidad DetalleItemImgApoloEntity
data class ItemDetallesImageEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name= "id")
    val id: Int = 0,

    @ColumnInfo(name= "nasa_id_")
    val nasa_id: String,

    @ColumnInfo(name= "center")
    val center:String? = "Sin Información",

    @NonNull
    @ColumnInfo(name= "title")
    val title:String? = "Sin Información",

    @ColumnInfo(name= "media_type")
    val media_type:String? = "Sin Información",

    //@ColumnInfo(name= "keywords")
    // val keywords:ArrayList<String>? = null,

    @ColumnInfo(name= "date_created")
    val date_created:String? = "Sin Información",

    @ColumnInfo(name= "description")
    val description:String? = "Sin Información",

    @ColumnInfo(name= "imgFavorita")
    val imgFavorita:Boolean = false
)

fun ItemDetallesImage.converterToItemDetallesImageEntity() : ItemDetallesImageEntity {  //Función de extensión util para la funcionalidad del maper
    val itemDetallesImageEntity: ItemDetallesImageEntity = ItemDetallesImageEntity (nasa_id = nasa_id, title = title, description = description, imgFavorita = true) // los parametros
    //(title, nasa_id,description, imgFavorita = true) son los asociados a al objeto de la clase ItemDetallesImage, lo anterior debido a que estamos trabajando con una
    //    //funsión de extensión
    return itemDetallesImageEntity
}
//*********************************************************************************************//
@Entity(tableName = "tbl_LinksItemsImgsApolo",
    foreignKeys = [ForeignKey(entity = ItemDatosImageEntity::class, parentColumns = ["nasa_id"], childColumns = ["nasa_id_"])])
// Declaramos que la clase LinksItemImgApoloEntity es una entidad asociada a la tabla
// tbl_LinksItemsImgsApolo del a base de datos, y esta va a ser reconocida por Room, ademas cuenta con una llave foranea en la entidad ItemDatosImageEntity
// llamada "id" y se relaciona con el campo id_ItemsDatosImages de esta misma entidad, es decir LinksItemImgApoloEntity
data class LinksItemImgApoloEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name= "id")
    val id: Int = 0,

    @ColumnInfo(name= "nasa_id_")
    val nasa_id: String,

    @ColumnInfo(name= "href")
    val href:String? = "Sin Información",

    @ColumnInfo(name= "rel")
    val rel:String? = "Sin Información",

    @ColumnInfo(name= "render")
    val render:String? = "Sin Información",
)

fun ItemLinksImage.converterToLinksItemImgApoloEntity(nasa_id: String) : LinksItemImgApoloEntity {  //Función de extensión util para la funcionalidad del maper
    val linksItemImgApoloEntity: LinksItemImgApoloEntity = LinksItemImgApoloEntity (nasa_id = nasa_id, href = href) // el parametro href es el de la clase
    // ItemLinksImage,  lo anterior debido a que estamos trabajando con una funsión de extensión
    return linksItemImgApoloEntity
}

