package co.com.personal.hnino.proyectoapolo.data.database.dao

import androidx.room.*
import co.com.personal.hnino.proyectoapolo.data.database.entitys.ItemDatosImageEntity

@Dao //Con esta anotación le decimos a Room que esta clase es una DAO (Data Access Objet)
interface ItemsDatosImgsApoloDao {

    @Query("SELECT * FROM tbl_ItemsDatosImages ORDER BY nasa_id ASC") // Consulta para obtener todos los Items de la BD local con Room
    // que fueron marcados como favoritos
    suspend fun getAllItemsImgsApolloFavoritas():List<ItemDatosImageEntity>//Se usa suspend por que vamos a trabajar con las corrutinas.
    //por otro lado, usamos ItemDatosImageEntity por que es una entidad, si usaramos la clase ItemDatosImage no nos funciona

    @Query("SELECT * FROM tbl_ItemsDatosImages WHERE nasa_id =:nasa_id ") // Consulta para obtener todos los Items de la BD local donde se cumpla
    //la condición
    suspend fun getItemDatosImgApolloFavorita(nasa_id: String): ItemDatosImageEntity
/*
    @Query("SELECT * FROM tbl_ItemDatosImage JOIN tbl_ItemDetallesImage  WHERE title = :titleitemDatosImageDb")
    suspend fun getItemDatosImagesFavorita(titleitemDatosImageDb:String):ItemDatosImageDb//Se usa suspen por que vamos a trabajar con las corrutinas
*/
    @Update
    suspend fun updateItemDatosImgsApolloFavorita(itemDatosImgsApolloFavorita : ItemDatosImageEntity) // Room es los suficientemente inteligente para saber
    // que el id de itemDatosImgsApolloFavorita es la Primary Key de la base de datos, asi que buscara por esa primary key y actualizara el registro.

    @Insert (onConflict = OnConflictStrategy.REPLACE) // Si se intentara insertar un objeto de tipo ItemDatosImageEntity con un id (PrimaryKey)
    //ya existente en la base de datos entonces no sale un error sino que el registro se reemplaza por el nuevo, para lo anterior
    // usamos "onConflict = OnConflictStrategy.REPLACE"
    suspend fun insertItemDatosImgsApolloFavorita(itemDatosImageEntity:ItemDatosImageEntity) //Se usa suspend por que vamos a trabajar con las corrutinas
    // esta función insertara un solo objeto de tipo ItemDatosImageEntity
    //por otro lado, usamos ItemDatosImageEntity por que es una entidad, si usaramos la clase ItemDatosImage no nos funciona

    @Insert (onConflict = OnConflictStrategy.REPLACE) // Si se intentara insertar un objeto de tipo ItemDatosImageEntity con un id (PrimaryKey)
    //ya existente en la base de datos entonces no sale un error sino que el registro se reemplaza por el nuevo, para lo anterior
    // usamos "onConflict = OnConflictStrategy.REPLACE"
    suspend fun insertAllItemsDatosImageFavoritas(itemDatosImageEntity:List<ItemDatosImageEntity>) //Se usa suspend por que vamos a trabajar con las corrutinas
    // esta función insertara una lista de objetos de tipo ItemDatosImageEntity
    //por otro lado, usamos ItemDatosImageEntity por que es una entidad, si usaramos la clase ItemDatosImage no nos funciona


    @Query("DELETE FROM tbl_ItemsDatosImages WHERE nasa_id =:stringIdToDelete") // Elimina los registros existentes en esa tabla de la BD local
    // y donde la condicion se cumpla
    suspend fun deleteItemDatosImgsApolloFavorita(stringIdToDelete: String) // Si en lugar de recibir un string, recibieramos un objeto de tipo linksItemImgApoloEntity,
    // Room es los suficientemente inteligente para saber que el id del objeto linksItemImgApoloEntity es la Primary Key de la base de datos, asi que buscara
    // por esa primary key y eliminara ese registro, claro esta que para que lo anterior fucione, debemos usar la anotción @Delete y no @Query

    @Query("DELETE FROM tbl_ItemsDatosImages") // Elimina todos los registros existente em la BD local con Room
    suspend fun deleteAllItemsImgsFavoritas() //Se usa suspend por que vamos a trabajar con las corrutinas.

}