package co.com.personal.hnino.proyectoapolo.data.network

import co.com.personal.hnino.proyectoapolo.data.model.entidades.CollectionImgsApoloModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class   ImgsApolloService @Inject constructor(private val api: ImgsApolloAPIClientInteface) { // Con @Inject constructor(), estamos preparando
    // esta clase (que no es una activity ni view Models) para que pueda ser inyectada en otras clases y tambien la preparamos para que se le
    // pueda inyectar dependencias de otras clases que van a ser gestionadas por Dagger Hilt
    // ------------ Con api: ImgsApolloAPIClientInteface  estamos inyectando una dependencia de la interface ImgsApolloAPIClientInteface, y al
    // ser una interface, Dagger hilt va a saber que no es una dependencia de las faciles de proveer y va a ir a buscar automaticamente una
    // funcion que este en algun modulo que tenga la etiqueta @Providers y que provea un objeto de la interface ImgsApolloAPIClientInteface y
    // dicha funcion la va a encontrar en el modulo que creamos llamado NetworkModuleProvider y esa funcion es la que llamamos
    // provideImgsApoloApiClient y que nos provee un objeto de la interfaz ImgsApolloAPIClientInteface el cual ya nos retorna un retrofit
    // mesclado con la interfaz,es decir ya viene el retrofit completo, por lo que podemos usar la variable api en lugar de
    // retrofit.create(ImgsApolloAPIClientInteface::class.java).

    //private val retrofit = RestEngineProvider.getImagesRetrofit()

    suspend fun getListImgsApoloRetrofitApiClienteInterface(queryByKeyWords:String): CollectionImgsApoloModel { // Se usa suspend por que se esta trabajando
        // con corrutinas

        return withContext(Dispatchers.IO) {
            // El codigo existente dentro de esta coorutina se va a ejecutar en un hilo secundario, es decir en un
            //hilo diferente al principal y retorna el resultado de tió CollectionImgsApolo

/*
            val respuesta = retrofit.create(ImgsApolloAPIClientInteface::class.java).getApiImgsApolo("search?q=$queryByKeyWords")//La variable
            //respuesta es la respuesta de tipo "Response<ImgApolo>" que obtenemos de la API en Json. el String contenido en queryByKeyWords" se l
            // o agregamos al final de la URL base que configuramos en la función RestEngineProvider.getImagesRetrofit() en
            // .baseUrl("https://images-api.nasa.gov/")
            */
            val respuesta = api.getApiImgsApolo("search?q=$queryByKeyWords")//La variable
            //respuesta es la respuesta de tipo "Response<ImgApolo>" que obtenemos de la API en Json. el String contenido en queryByKeyWords" se
            // lo agregamos al final de la URL base que configuramos en la función RestEngineProvider.getImagesRetrofit() en
            // .baseUrl("https://images-api.nasa.gov/") ------------- api que es de tipo interface ImgsApolloAPIClientInteface (se inyecto esta dependencia),
            // al ser una interface, Dagger hilt va a saber que no es una dependencia de las faciles de proveer y va a ir a buscar automaticamente
            // una funcion que este en algun modulo que tenga la etiqueta @Providers y que provea un objeto de la interface
            // ImgsApolloAPIClientInteface y dicha funcion la va a encontrar en el modulo que creamos llamado NetworkModuleProvider
            // y esa funcion es la que llamamos provideImgsApoloApiClient y que nos provee un objeto de la interfaz ImgsApolloAPIClientInteface
            // el cual ya nos retorna un retrofit mesclado con la interfaz,es decir ya viene el retrofit completo,
            //  por lo que podemos usar la variable api en lugar de retrofit.create(ImgsApolloAPIClientInteface::class.java).

            val respuestaImgsApolo = respuesta.body()
            val respuestaCollectionImgsApollo = respuestaImgsApolo?.collectionImgsApolo ?: emptySet<CollectionImgsApoloModel>() // emptySet aplica Si la petición
            // falla, retornara un objeto de tipo CollectionImgsApoloModel pero vacio

            respuestaCollectionImgsApollo as CollectionImgsApoloModel
        }
    }
}