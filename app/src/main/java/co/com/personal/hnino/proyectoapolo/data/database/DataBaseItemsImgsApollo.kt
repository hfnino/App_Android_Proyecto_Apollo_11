package co.com.personal.hnino.proyectoapolo.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import co.com.personal.hnino.proyectoapolo.data.database.dao.DetalleItemImgApoloDao
import co.com.personal.hnino.proyectoapolo.data.database.dao.ItemsDatosImgsApoloDao
import co.com.personal.hnino.proyectoapolo.data.database.dao.LinksItemImgApoloDao
import co.com.personal.hnino.proyectoapolo.data.database.entitys.ItemDatosImageEntity
import co.com.personal.hnino.proyectoapolo.data.database.entitys.ItemDetallesImageEntity
import co.com.personal.hnino.proyectoapolo.data.database.entitys.LinksItemImgApoloEntity


@Database(entities = [
    //ImgApoloEntity::class,
    //CollectionImgsApoloEntity::class,
    ItemDatosImageEntity::class,
    ItemDetallesImageEntity::class,
    LinksItemImgApoloEntity::class ], version = 1, exportSchema = true)  //Declaramos que esta clase esta asociada a la creación
// de la base de datos y como  primer parametro le ponemos un array, por eso va dentro de [], y ese array debe tener
// todas las Entitys (tablas y estructura) que tendra nuestra base de datos, las cuales creamos y declaramos en el archivo
//ImgApolloEntity.kt (data clases declaradas como @Entity )------- como segundo parametro le ponemos version = 1 es la versíón
// de nuesta base de datos que puede ser cualquier numero, le pusimos 1 por que es nuestra primera versión y este numero es
// util para tener control ya que es posible realizar migraciones, actualizar las tablas, insertar mas tablas, poner mas
//campos en nuestras entidades -------------- el tercer parametro es exportSchema y hace referencia a la estructura como se conforma la base de datos,
// con el valor true le decimos a room que queremos poder ver el esquema de nuestra bases de datos; en versiones antiguas de Android,
// es necesario definir donde vamos a guardar dicho esquema, y para ello le agregamos las lineas
//
//javaCompileOptions {
//    annotationProcessorOptions {
//        arguments = ["room.schemaLocation": "$projectDir/schemas".toString()]
//    }
//}
//
// en el build.grade app... en versiónes nuevas de android no es necesario hacerlo.
// Despues de lo anterior, compilamos la app con el martillo, y el Json del esquema podremos verlo facilmente en la vista
// Project/app/schemas/co.com.personal...................../1.json, y en donde encontramos el parametro "identityHash": "f949512169a21e35c58d552a9464ac3a",
// donde f949512169a21e35c58d552a9464ac3a es el hash de nuestro squema, lo que significa que si le hacemos un cambio por minimo que sea a una tabla, un campo
// o lo que sea de nuestra base de datos, este hash va a cambiar cuando compilemos la aplicación

// Por otro lado, es importante tener en cuenta que solo se crea un nuevo esquema cuando aumentamos la versión de la base de datos, por ejemplo si cambiamos
// la versión = 1 a 2 u otro numero, al compilar la aplicación nos aparecera el archivo Project/app/schemas/co.com.personal...................../2.json.

// -----------------------   Es importante tener en cuenta que cuando hemos modificado nuestra  base de datos debemos desinstalar la aplicación e instalarla
//nuevamente, o en su defecto debemos borrar la cache de la aplicación ya que de no hacerlo, nos arrojara un error, obviamente, no podemos pedirle al usuario que
// realice este proceso cada ves que hacemos cambios en la base de datos, y es alli donde es importante realizar un proceso de migración de base de datos
// en donde especificampos que debemos hacer cuando los esquemas cambian.
// https://www.youtube.com/watch?v=dqMpckh75wc&list=PLOAg1O9FnNv4cztBfUbO66pJeFYJVQT3F&index=5

abstract class DataBaseItemsImgsApollo: RoomDatabase() {  // esta clase es abstracta por que extiende de RoomDatabase y Room
// es la que se encarga de su implementación. Esta clase se encarga de unir las entidades y los DAO

    //Por cada Dao debemos crear una funcion abstracta, ya que cada función abstracta nos va a retornar los DAO que hemos implementado
    // en este caso tenermos 3 DAO  =>
    abstract fun getItemsDatosImgDao(): ItemsDatosImgsApoloDao
    abstract fun getDetalleItemImgApoloDao(): DetalleItemImgApoloDao
    abstract fun getLinksItemImgApoloDao(): LinksItemImgApoloDao
}