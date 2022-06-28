package co.com.personal.hnino.proyectoapolo.data.database.dao

import androidx.room.*
import co.com.personal.hnino.proyectoapolo.data.database.entitys.ItemDetallesImageEntity

@Dao //Con esta anotación le decimos a Room que esta clase es una DAO (Data Access Objet)
interface DetalleItemImgApoloDao {

    @Query("SELECT * FROM tbl_ItemDetallesImage ORDER BY nasa_id_ ASC") // Consulta para obtener todos los detalles de los Items de la BD local con Room
    suspend fun getAllDetallesItemsImgsApolo():List<ItemDetallesImageEntity>//Se usa suspend por que vamos a trabajar con las corrutinas.
    //por otro lado, usamos DetalleItemImgApoloEntity por que es una entidad, si usaramos la clase ItemDetallesImage no nos funciona

    @Query("SELECT * FROM tbl_ItemDetallesImage WHERE nasa_id_ =:nasa_id ") // Consulta a la base de datos para obtener los detalles del item que tenga el nasa_id correspondiente
    suspend fun getDetallesItemImgApolo(nasa_id: String): List<ItemDetallesImageEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE) // Si se intentara insertar un objeto de tipo DetalleItemImgApoloEntity con un id (PrimaryKey)
    //ya existente en la base de datos entonces no sale un error sino que el registro se reemplaza por el nuevo, para lo anterior
    // usamos "onConflict = OnConflictStrategy.REPLACE"
    suspend fun insertDetallesItemImgApolo(itemDetallesImageEntity : ItemDetallesImageEntity) //Se usa suspend por que vamos a trabajar con las corrutinas
    // esta función insertara un solo objeto de tipo ItemDetallesImageEntity
    //por otro lado, usamos ItemDatosImageEntity por que es una entidad, si usaramos la clase ItemDatosImage no nos funciona

    @Insert(onConflict = OnConflictStrategy.REPLACE ) // Si se intentara insertar un objeto de tipo DetalleItemImgApoloEntity con un id (PrimaryKey)
    //ya existente en la base de datos entonces no sale un error sino que el registro se reemplaza por el nuevo, para lo anterior
    // usamos "onConflict = OnConflictStrategy.REPLACE"
    suspend fun insertAllDetallesItemsImgApolo(itemsDetallesImagesEntity : List<ItemDetallesImageEntity>) //Se usa suspend por que vamos a trabajar con las corrutinas
    // esta función insertará una lista de objetos de tipo ItemDetallesImageEntity
    //por otro lado, usamos ItemDatosImageEntity por que es una entidad, si usaramos la clase ItemDatosImage no nos funciona

    @Update
    suspend fun updateDetalleItemImgApoloEntity(itemDetallesImageEntity : ItemDetallesImageEntity) // Room es los suficientemente inteligente para saber
    // que el id de itemDetallesImageEntity recibido es la Primary Key de la base de datos, asi que buscara por esa primary key y actualizara el registro.

    @Query("DELETE FROM tbl_ItemDetallesImage WHERE nasa_id_ =:stringIdToDelete") // Elimina los registros existentes en esa tabla de la BD local
    // y donde la condicion se cumpla
    suspend fun deleteDetallesItemImgApolo(stringIdToDelete: String) // Si en lugar de recibir un string, recibieramos un objeto de tipo linksItemImgApoloEntity,
    // Room es los suficientemente inteligente para saber que el id del objeto linksItemImgApoloEntity es la Primary Key de la base de datos, asi que buscara
    // por esa primary key y eliminara ese registro, claro esta que para que lo anterior fucione, debemos usar la anotción @Delete y no @Query

    @Query("DELETE FROM tbl_ItemDetallesImage") // Elimina todos los registros existente em la esa tabla de la BD local con Room
    suspend fun deleteAllDetallesItemsImgsApolo() //Se usa suspend por que vamos a trabajar con las corrutinas.


}