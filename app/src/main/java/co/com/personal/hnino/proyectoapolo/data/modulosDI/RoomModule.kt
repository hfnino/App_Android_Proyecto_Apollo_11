package co.com.personal.hnino.proyectoapolo.data.modulosDI;

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import co.com.personal.hnino.proyectoapolo.data.database.DataBaseItemsImgsApollo
import dagger.Module;
import dagger.Provides
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent;
import javax.inject.Singleton

@Module  //Con esta etiqueta le decimos a Dagger que esto es un modulo. Los modulos son los que proveen dependencias
@InstallIn(SingletonComponent::class)  // como parametros le ponemos el Alcance. cuando creamos un modulo, le podemos decir el alcance que queremos
// que tenga. por ejemplo si el alcance es a nivel de activity, cuando una activity necesite por ejemplo la dependencia de retrofit,
// dagger va a crear una instancia y se la va a dafggr a la activity, pero cuando esa activity muere, tambien muere esa instancia de retrofit,
// Si llamamos a retrofit por ejemplo 3 veces en una misma activity, se van a crear 3 isntancias de retrofit, y cuando esa activity muere,
// las 3 instancias que se crearon tambien mueren. Los alcances mas comunes son los de la Activity, el de el View Model y el de nivel de
//Aplicación que se usa cuando son cosas tan generales que sea necesario llamarla en todas partes del proyecto. Para este ejemplo,
// le ponemos que el alcance es de aplicación, ya que retrofit deberia poderse llamar de cualquier parte de la aplicación y para ello
// lo conficuramos con "SingletonComponent::class" teniendo en cuenta que la palabra Singleton es solo un nombre, y no tiene nada que ver
// con el patron de diseño Singleton
object RoomModule {

    private const val NAME_DATABASE_ITEMS_IMGS_APOLLO = "database_items_imgs_apollo"

    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context) : DataBaseItemsImgsApollo {
        // @ApplicationContext context: Context es el contexto que necesitamos en la función provideRoom y este contexto
        // nos lo provee Dagger Hilt, el cual hace parte de los elementos de Dagger Hilt que ya trae predefinidos para ser inyectados.
        var roomDataBase = Room.databaseBuilder(context, DataBaseItemsImgsApollo::class.java, NAME_DATABASE_ITEMS_IMGS_APOLLO).build()
        // DataBaseItemsImgsApollo::class.java es la clase donde esta la base de datos creada
        // NAME_DATABASE_ITEMS_IMGS_APOLLO es el nombre que queramos ponerle a nuestra bases de datos, en este caso es el
        // string "database_items_imgs_apollo" que le pusimos a la constante NAME_DATABASE_ITEMS_IMGS_APOLLO
        // el metodo .build() es para construir y montar la base de datos.
        return roomDataBase     // Esta función nos retorna el objeto que nos va a crear y a montar la base de datos que es de tipó DataBaseItemsImgsApollo. Como esta función
        // esta configurada como @Provides, significa que si en cualquier clase necesitamos una instacia de la clase DataBaseItemsImgsApollo, simplemente la
        // inyectamos, y Dagger Hilt va a buscar y a encontrar esta funcion, que retorna un objeto de DataBaseItemsImgsApollo y la va a proveer.
    }

    @Singleton
    @Provides
    fun provideItemsDatosImgsApoloDao(db: DataBaseItemsImgsApollo) = db.getItemsDatosImgDao()
    // (db: DataBaseItemsImgsApollo) es nuestra base de datos que es proveida por la función provideRoom existente en este mismo modulo. Esto Funciona
    // debido a que la función provideRoom es una función @Provides y esa nos provee un objeto de tipo DataBaseItemsImgsApollo para inyectarla en cualquier clase que la requiera y
    // tambien se puede proveer en otras funciones @Provides como sucede en este caso
    // Esta función nos retorna un db.getItemsDatosImgDao(), y esta ultima nos retorna un objeto de la interface ItemDatosImgApoloDao
    // con la que podremos usar todos los metodos de esta interface, que son necesarios para interactuar con al base de datos como los Select, Update, Delete, insert..

    @Singleton
    @Provides
    fun provideDetallesItemsImgsApoloDao(db: DataBaseItemsImgsApollo) = db.getDetalleItemImgApoloDao()

    @Singleton
    @Provides
    fun provideLinksItemsImgsApoloDao(db: DataBaseItemsImgsApollo) = db.getLinksItemImgApoloDao()

}