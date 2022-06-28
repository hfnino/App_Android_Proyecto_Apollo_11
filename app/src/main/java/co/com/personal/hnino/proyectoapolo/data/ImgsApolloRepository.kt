package co.com.personal.hnino.proyectoapolo.data

import co.com.personal.hnino.proyectoapolo.data.database.dao.DetalleItemImgApoloDao
import co.com.personal.hnino.proyectoapolo.data.database.dao.ItemsDatosImgsApoloDao
import co.com.personal.hnino.proyectoapolo.data.database.dao.LinksItemImgApoloDao
import co.com.personal.hnino.proyectoapolo.data.database.entitys.*
import co.com.personal.hnino.proyectoapolo.data.model.DataTemporalProvider
import co.com.personal.hnino.proyectoapolo.data.model.entidades.CollectionImgsApoloModel
import co.com.personal.hnino.proyectoapolo.data.model.entidades.ItemDatosImageModel
import co.com.personal.hnino.proyectoapolo.data.network.ImgsApolloService
import co.com.personal.hnino.proyectoapolo.domain.model.ItemDatosImage
import co.com.personal.hnino.proyectoapolo.domain.model.converterToItemDatosImage
import co.com.personal.hnino.proyectoapolo.domain.model.converterToItemDetallesImage
import co.com.personal.hnino.proyectoapolo.domain.model.converterToItemLinksImage
import javax.inject.Inject

class ImgsApolloRepository @Inject constructor(
    private val api : ImgsApolloService,
    private val dataTemporalProvider: DataTemporalProvider,
    private val itemsDatosImgsApoloDao: ItemsDatosImgsApoloDao, // Inyectamos esta dependencia por medio del modulo RoomModule ya que no se puede inyectar directamente por ser una interfaz
    private val detalleItemImgApoloDao: DetalleItemImgApoloDao, // Inyectamos esta dependencia por medio del  modulo RoomModule ya que no se puede inyectar directamente por ser una interfaz
    private val linksItemImgApoloDao: LinksItemImgApoloDao // Inyectamos esta dependencia por medio del  modulo RoomModule ya que no se puede inyectar directamente por ser una interfaz
    ) { // Con @Inject constructor(), estamos preparando esta clase
    // (que no es una activity ni view Models) para que pueda ser inyectada en otras clases y tambien la preparamos
    // para que se le pueda inyectar dependencias de otras clases que van a ser gestionadas por Dagger Hilt

    suspend fun getListaImgsApoloFromApi(queryByKeyWords:String): List<ItemDatosImage>{
        val respuesta: CollectionImgsApoloModel = api.getListImgsApoloRetrofitApiClienteInterface(queryByKeyWords)
        val respuestaListaItemDatosImageModel: List<ItemDatosImageModel> = respuesta.items ?: emptyList<ItemDatosImageModel>() // emptyList aplica Si la petición
        // falla, retornara una lista de objetos de tipo ItemDatosImageModel pero vacio
        dataTemporalProvider.setListaItemsImgsApoloModel(respuestaListaItemDatosImageModel) //guardamos la data JSON de la lista de objetos de tipo ItemDatosImageModel en memoria cache

        val respuestaListaItemDatosImage: List<ItemDatosImage> = respuestaListaItemDatosImageModel.map { it.converterToItemDatosImage() } // La función .map es propia de
        //un List<XXXXX> y es como un bucle tipo For que para este caso nos es util para coger cada uno de los objetos de tipo ItemDatosImageModel existentes
        // en la lista respuestaListaItemDatosImageModel y  convertirlos (Maper) a tipo ItemDatosImage, de esta manera, la variable respuestaListaItemDatosImage
        // resultara en una nueva lista, pero ahora con objetos de tipo ItemDatosImage

        dataTemporalProvider.setListaItemsImgsApolo(respuestaListaItemDatosImage) //guardamos la data JSON de la lista de objetos de tipo ItemDatosImage en memoria cache
        return  respuestaListaItemDatosImage // A parte de guardar la data JSON de la lista de Items de imagenes en memoria, tambien la retornamos
    }

    suspend fun getListaImgsApoloFavoritasFromDataBase(): List<ItemDatosImage>{

        var respuestaListaItemDatosImage: MutableList<ItemDatosImage> = mutableListOf()
        val respuestaItemDatosImageEntity: List<ItemDatosImageEntity> = itemsDatosImgsApoloDao.getAllItemsImgsApolloFavoritas()

        respuestaItemDatosImageEntity.map { it ->
            val respuestaItemDetallesImageEntity: List<ItemDetallesImageEntity> = detalleItemImgApoloDao.getDetallesItemImgApolo(it.nasa_id)
            val respuestaLinksItemImgApoloEntity: List<LinksItemImgApoloEntity> = linksItemImgApoloDao.getLinksItemImgApolo(it.nasa_id)
            val respuestaItemDatosImage : ItemDatosImage = ItemDatosImage(it.href, respuestaItemDetallesImageEntity.map { it.converterToItemDetallesImage() },
                                                                            respuestaLinksItemImgApoloEntity.map { it.converterToItemLinksImage() })
            // La función .map es propia de
            //un List<XXXXX> y es como un bucle tipo For que para este caso nos es util para coger cada uno de los objetos existentes
            // en la lista correspondientes  y  convertirlos (Maper) a objetos de otro tipo según se requiera, de esta manera, las resultara en una nueva lista,
            // pero ahora con objetos del otro tipo
            respuestaListaItemDatosImage.add(respuestaItemDatosImage)
        }
        println(" =======================> la lista de objetos de tipo ItemDatosImage extraidas de la base de datos local con " +
                "Room es =>  " + respuestaListaItemDatosImage.toString()) // Prueba de escritorio.

        return  respuestaListaItemDatosImage
    }

    suspend fun insertItemDatosImageEntity(itemDatosImage: ItemDatosImage){

        val respuestaItemDatosImageEntity: ItemDatosImageEntity = itemsDatosImgsApoloDao.getItemDatosImgApolloFavorita(itemDatosImage.data!!.get(0).nasa_id)

        if (respuestaItemDatosImageEntity != null){
            println(" ==============================> No se puede guardar el item debido a que ya existe en la base de datos") // Prueba de Escritorio
        }
        else{
            val itemDatosImageEntity: ItemDatosImageEntity = ItemDatosImageEntity(nasa_id = itemDatosImage.data.get(0).nasa_id, itemDatosImage.href)
            itemsDatosImgsApoloDao.insertItemDatosImgsApolloFavorita(itemDatosImageEntity) // para este caso no es necesario saber si en esta tabla ya
            //existe algun registro con la misma nada_id, ya que en "itemsDatosImgsApoloDao.insertItemDatosImgsApolloFavorita()" contamos con la
            // etiqueta @Insert (onConflict = OnConflictStrategy.REPLACE) y funciona correctamente por que campo nada_id de la Entidad tbl_ItemsDatosImages
            //es la primaryKey de esta tabla. mientras que en  las entidades tbl_ItemDetallesImage y LinksItemImgApoloEntity, el campo nasa_id_ no es
            //PrimaryKey y por tanto la etiqueta @Insert (onConflict = OnConflictStrategy.REPLACE) no funciona.

            val itemsDetallesImageEntity: List<ItemDetallesImageEntity> = itemDatosImage.data.map { it.converterToItemDetallesImageEntity() }
            detalleItemImgApoloDao.insertAllDetallesItemsImgApolo(itemsDetallesImageEntity)

            val linksItemsImgsApoloEntity: List<LinksItemImgApoloEntity> = itemDatosImage.links!!.map {
                it.converterToLinksItemImgApoloEntity(itemDatosImage.data.get(0).nasa_id) }

            linksItemsImgsApoloEntity.map {
                println("============= Enlaces ===============> " + it.href) // Prueba de escritorio
            }

            linksItemImgApoloDao.insertAllLinksItemsImgApolo(linksItemsImgsApoloEntity)
        }
    }

    suspend fun deleteItemDatosImageEntity(itemDatosImage: ItemDatosImage){

        println("=============> El Item de imagen favorita a eliminar es => $itemDatosImage") // Prueba de Escritorio

        val stringIdToDelete: String = itemDatosImage.data!!.get(0).nasa_id
        linksItemImgApoloDao.deleteLinksItemImgApolo(stringIdToDelete)
        //Thread.sleep(9000)
        detalleItemImgApoloDao.deleteDetallesItemImgApolo(stringIdToDelete)
        //Thread.sleep(9000)
        itemsDatosImgsApoloDao.deleteItemDatosImgsApolloFavorita(stringIdToDelete)
    }

    suspend fun deleteAllFromDataBase(){
        linksItemImgApoloDao.deleteAllLinksItemImgApolo()
        detalleItemImgApoloDao.deleteAllDetallesItemsImgsApolo()
        itemsDatosImgsApoloDao.deleteAllItemsImgsFavoritas()
    }
}