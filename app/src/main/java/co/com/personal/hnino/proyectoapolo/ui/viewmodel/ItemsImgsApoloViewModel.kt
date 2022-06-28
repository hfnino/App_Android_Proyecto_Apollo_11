package co.com.personal.hnino.proyectoapolo.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.com.personal.hnino.proyectoapolo.domain.GetListaImgsApoloUseCase
import co.com.personal.hnino.proyectoapolo.data.model.DataTemporalProvider
import co.com.personal.hnino.proyectoapolo.domain.DeleteItemImgApoloUseCase
import co.com.personal.hnino.proyectoapolo.domain.GetListaImgsApoloFavoritasUseCase
import co.com.personal.hnino.proyectoapolo.domain.SaveItemImgApoloUseCase
import co.com.personal.hnino.proyectoapolo.domain.DeleteAllFromDataBaseUseCase
import co.com.personal.hnino.proyectoapolo.domain.model.ItemDatosImage
import kotlinx.coroutines.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel // Con esta etiqueda, preparamos clases de tipo  view Model como sucede en este caso, y lo hacemos con el fin de que sea posible
                // inyectarle las dependencias necesarias que van a ser gestionadas por Dagger Hilt
class ItemsImgsApoloViewModel @Inject constructor(
    private var getListaImgsApoloUseCase: GetListaImgsApoloUseCase,
    private  var saveItemImgApoloUseCase: SaveItemImgApoloUseCase,
    private  var deleteItemImgApoloUseCase: DeleteItemImgApoloUseCase,
    private  var getListaImgsApoloFavoritasUseCase: GetListaImgsApoloFavoritasUseCase,
    private val dataTemporalProvider : DataTemporalProvider,
    private var deleteAllFromDataBaseUseCase: DeleteAllFromDataBaseUseCase
    ): ViewModel() {
    // Estamos inyectando la dependencia "GetListaImgsApoloUseCase" asociada a la variable
    // privada getListaImgsApoloUseCase. Con lo anterior ya no tenemos que crear una instancia
    // de esa clase, es decir "var getListaImgsApoloUseCase = GetListaImgsApoloUseCase()"

    val listaItemsImgsApoloMLD = MutableLiveData<List<ItemDatosImage>>()

    val itemDatosImagenModel = MutableLiveData<ItemDatosImage>()

    var isLoading = MutableLiveData<Boolean>() // Esta variable de tipo LiveData Boolean, nos ayuda
    // a mostrar u ocultar la barra circular de progreso cuando el sistema esta conectandose a la API
    // para traer la información del Backend

    fun obtenerListaItemsImgsApoloFromApi(queryByKeyWords: String) {
        viewModelScope.launch {  // Cuando se usa arquitecturas como MVP, si la activity muere es necesario parar la coorrutina que se encuentre
            // en ejecución para que no salga errores, en este caso como usamos la arquitectura MVVM ese inconveniente no lo tenemos por que podemos
            // usar la coorutina viewModelScope.launch {} que puede controlarse automaticamente
            // El codigo existente dentro de esta corrutina se va a ejecutar en un hilo secundario, es decir en un
            //hilo diferente al principal
            isLoading.postValue(true) // Muestra barra circular de progreso ------ la variable isLoading que es de tipo LiveData Boolean
            // se da cuenta que le hicimos un cambio y avisara a la activity y/o Fragment correspondiente y este ultimo va a hacer lo que crea conveniente.
            // Si no usaramos Live Data, tendriamos que configurar esta funcion para que retorne
            // el objeto la variable "isLoading" a la vista donde esta funcion fue llamada.
            val respuestaListaItemsImgsApoloFromApi = getListaImgsApoloUseCase(queryByKeyWords) // no se requiere
            // llamar ningun metodo de clase, ya que se esta usando el operador invoke
            if(respuestaListaItemsImgsApoloFromApi != null){

                val respuestaListaItemsImgsApoloFavoritas = getListaImgsApoloFavoritasUseCase() // no se requiere
                // llamar ningun metodo de clase, ya que se esta usando el operador invoke

                if(respuestaListaItemsImgsApoloFavoritas.isNullOrEmpty()){ //Si la lista de imagenes favoritas es Null o esta vacia entonces =>

                    println(" ==============================> Verificar el caso, ya que la respuesta debio ser una lista de objetos de tipo" +
                            " ItemDatosImage, y en su lugar, se obtuvo como respuesta un null o una lista vacia, Por favor verifique que la base de datos " +
                            "local este funcionando correctamente o si en su defecto realmente no tiene información.") // Prueba de Escritorio

                    listaItemsImgsApoloMLD.postValue(respuestaListaItemsImgsApoloFromApi) // la variable
                    // listaItemsImgsApoloMLD que es de tipo LiveData se da cuenta que le hicimos un cambio y avisara
                    // a la activity y/o Fragment correspondiente y este ultimo va a hacer lo que crea conveniente.
                    // Si no usaramos Live Data, tendriamos que configurar esta funcion para que retorne
                    // el objeto la variable "respuestaListaItemsImgsApoloFromApi" a la vista donde esta funcion fue llamada.
                    isLoading.postValue(false) // Oculta barra circular de progreso
                }
                else{  //Si la lista de imagenes favoritas no es Null y tampoco esta vacia entonces =>

                    respuestaListaItemsImgsApoloFromApi.forEach{ itemDatosImgApoloFromApi ->
                        respuestaListaItemsImgsApoloFavoritas.forEach { itemDatosImgApoloFromDataBase ->

                            //println( "----------La posición en la lista (indexOf) es ----------- " + respuestaListaItemsImgsApoloFromApi.indexOf(itemDatosImgApoloFromApi)) // Prueba de Escritorio

                            if(itemDatosImgApoloFromApi.data!!.get(0).nasa_id == itemDatosImgApoloFromDataBase.data!!.get(0).nasa_id){
                                respuestaListaItemsImgsApoloFromApi.get(respuestaListaItemsImgsApoloFromApi.indexOf(itemDatosImgApoloFromApi)).data!!.get(0).imgFavorita = true
                            }

                            println( "----------------------- " + itemDatosImgApoloFromApi.data.get(0).nasa_id + " (${itemDatosImgApoloFromApi.data.get(0).imgFavorita}) "
                                   + " --- Vs --- " + itemDatosImgApoloFromDataBase.data.get(0).nasa_id + " (${itemDatosImgApoloFromDataBase.data.get(0).imgFavorita}) --------->" +
                                    respuestaListaItemsImgsApoloFromApi.get(respuestaListaItemsImgsApoloFromApi.indexOf(itemDatosImgApoloFromApi)).data!!.get(0).imgFavorita)  // Prueba de Escritorio
                        }
                    }

                    println(" ============la lista enviada desde ItemsImgsApoloViewModel para mostrar en el Recycler View de objetos ItemDatosImage es =====>  " + respuestaListaItemsImgsApoloFromApi)

                    listaItemsImgsApoloMLD.postValue(respuestaListaItemsImgsApoloFromApi) // la variable
                    // listaItemsImgsApoloMLD que es de tipo LiveData se da cuenta que le hicimos un cambio y avisara
                    // a la activity y/o Fragment correspondiente y este ultimo va a hacer lo que crea conveniente.
                    // Si no usaramos Live Data, tendriamos que configurar esta funcion para que retorne
                    // el objeto la variable "respuestaListaItemsImgsApoloFromApi" a la vista donde esta funcion fue llamada.


                    isLoading.postValue(false) // Oculta barra circular de progreso
                }
            }
            else{
                println(" ==============================> Verificar el caso, ya que la respuesta debio ser una lista de objetos de tipo" +
                        " ItemDatosImage, y en su lugar, se obtuvo como respuesta un null, Por favor verifique que tenga acceso a Internet" +
                        " o en su defecto pueda ser fallas en el servidor.") // Prueba de Escritorio

                isLoading.postValue(false)
            }
        }
    }

    fun obtenerListaItemsImgsApoloFavoritasSearch(queryByKeyWords: String) {

        viewModelScope.launch {  // Cuando se usa arquitecturas como MVP, si la activity muere es necesario parar la coorrutina que se encuentre
            // en ejecución para que no salga errores, en este caso como usamos la arquitectura MVVM ese inconveniente no lo tenemos por que podemos
            // usar la coorutina viewModelScope.launch {} que puede controlarse automaticamente
            // El codigo existente dentro de esta coorutina se va a ejecutar en un hilo secundario, es decir en un
            //hilo diferente al principal
            isLoading.postValue(true) // Muestra barra circular de progreso ------ la variable isLoading que es de tipo LiveData Boolean
            // se da cuenta que le hicimos un cambio y avisara a la activity y/o Fragment correspondiente y este ultimo va a hacer lo que crea conveniente.
            // Si no usaramos Live Data, tendriamos que configurar esta funcion para que retorne
            // el objeto la variable "isLoading" a la vista donde esta funcion fue llamada.
            val respuestaListaItemsImgsApoloFavoritas = getListaImgsApoloFavoritasUseCase() // no se requiere
            // llamar ningun metodo de clase, ya que se esta usando el operador invoke

            if(respuestaListaItemsImgsApoloFavoritas.isNullOrEmpty()){ //Si la lista de imagenes favoritas es Null o esta vacia entonces =>

                println(" ==============================> Verificar el caso, ya que la respuesta debio ser una lista de objetos de tipo" +
                        " ItemDatosImage, y en su lugar, se obtuvo como respuesta un null o una lista vacia, Por favor verifique que la base de datos " +
                        "local este funcionando correctamente o si en su defecto realmente no tiene información.") // Prueba de Escritorio

                listaItemsImgsApoloMLD.postValue(respuestaListaItemsImgsApoloFavoritas!!) // la variable
                // listaItemsImgsApoloMLD que es de tipo LiveData se da cuenta que le hicimos un cambio y avisara
                // a la activity y/o Fragment correspondiente y este ultimo va a hacer lo que crea conveniente.
                // Si no usaramos Live Data, tendriamos que configurar esta funcion para que retorne
                // el objeto la variable "respuestaListaItemsImgsApoloFavoritas" a la vista donde esta funcion fue llamada.
                isLoading.postValue(false) // Oculta barra circular de progreso
            }
            else if(queryByKeyWords == ""){

                listaItemsImgsApoloMLD.postValue(respuestaListaItemsImgsApoloFavoritas) // la variable
                // listaItemsImgsApoloMLD que es de tipo LiveData se da cuenta que le hicimos un cambio y avisara
                // a la activity y/o Fragment correspondiente y este ultimo va a hacer lo que crea conveniente.
                // Si no usaramos Live Data, tendriamos que configurar esta funcion para que retorne
                // el objeto la variable "respuestaListaItemsImgsApoloFavoritas" a la vista donde esta funcion fue llamada.
                isLoading.postValue(false) // Oculta barra circular de progreso

            }
            else{ //Si la lista de imagenes favoritas no es Null, No esta vacia, y queryByKeyWords es "" entonces =>

                var filtradoListaItemsImgsApoloFavoritas: MutableList<ItemDatosImage> = mutableListOf()

                respuestaListaItemsImgsApoloFavoritas.forEach { itemDatosImgApoloFromDataBase ->

                    if (itemDatosImgApoloFromDataBase.data!!.get(0).title?.lowercase()?.indexOf(queryByKeyWords) != -1 ||
                        itemDatosImgApoloFromDataBase.data.get(0).description?.lowercase()?.indexOf(queryByKeyWords) != -1) {

                            filtradoListaItemsImgsApoloFavoritas.add(itemDatosImgApoloFromDataBase)

                        // tener en cuenta que cuando termian el forEach, pueda ser que la variable filtradoListaItemsImgsApoloFavoritas sea Null o una
                        //lista vacia debio a que no se encontro nada
                    }
                }

                println(" ============ la lista enviada desde ItemsImgsApoloViewModel para mostrar en el RecyclerView  de favoritos Filtrado es =====>  " + filtradoListaItemsImgsApoloFavoritas)

                listaItemsImgsApoloMLD.postValue(filtradoListaItemsImgsApoloFavoritas) // la variable
                // listaItemsImgsApoloMLD que es de tipo LiveData se da cuenta que le hicimos un cambio y avisara
                // a la activity y/o Fragment correspondiente y este ultimo va a hacer lo que crea conveniente.
                // Si no usaramos Live Data, tendriamos que configurar esta funcion para que retorne
                // el objeto la variable "filtradoListaItemsImgsApoloFavoritas" a la vista donde esta funcion fue llamada.

                isLoading.postValue(false) // Oculta barra circular de progreso
            }

        }
    }

    fun getItemDatosImagenCurrent(){ // cuendo se usa esta función, se obtiene un un objeto de tipo ItemDatosImage
        val itemDatosImagenActual = dataTemporalProvider.getItemDatosImagen()
        itemDatosImagenModel.postValue(itemDatosImagenActual) // la variable itemDatosImagenModel que es de tipo
        //LiveData se da cuenta que le hicimos un cambio y avisara a la activity y/o Fragment correspondiente y este
        //ultimo va a hacer lo que crea conveniente. Si no usaramos Live Data, tendriamos que configurar esta funcion
        // para que retorne el objeto DataTemporalProvider.getItemDatosImagen() a la vista donde esta funcion fue llamada.
    }

    fun setItemDatosImagenCurrent(itemDatosImageActual: ItemDatosImage){
        dataTemporalProvider.setItemDatosImagen(itemDatosImageActual)
    }

    fun saveItemDatosImagenFavorita(itemDatosImage: ItemDatosImage){
        viewModelScope.launch {  // Cuando se usa arquitecturas como MVP, si la activity muere es necesario parar la coorrutina que se encuentre
            // en ejecución para que no salga errores, en este caso como usamos la arquitectura MVVM, ese inconveniente no lo tenemos por que podemos
            // usar la coorutina viewModelScope.launch {} que puede controlarse automaticamente
            // El codigo existente dentro de esta coorutina se va a ejecutar en un hilo secundario, es decir en un
            //hilo diferente al principal
            isLoading.postValue(true) // Muestra barra circular de progreso ------ la variable isLoading que es de tipo LiveData Boolean
            // se da cuenta que le hicimos un cambio y avisara a la activity y/o Fragment correspondiente y este ultimo va a hacer lo que crea conveniente.
            // Si no usaramos Live Data, tendriamos que configurar esta funcion para que retorne
            // el objeto la variable "isLoading" a la vista donde esta funcion fue llamada.

            saveItemImgApoloUseCase(itemDatosImage)

            isLoading.postValue(false) // Oculta barra circular de progreso
        }
    }

    fun deleteItemDatosImagenFavorita(itemDatosImage: ItemDatosImage){
        viewModelScope.launch {  // Cuando se usa arquitecturas como MVP, si la activity muere es necesario parar la coorrutina que se encuentre
            // en ejecución para que no salga errores, en este caso como usamos la arquitectura MVVM, ese inconveniente no lo tenemos por que podemos
            // usar la coorutina viewModelScope.launch {} que puede controlarse automaticamente
            // El codigo existente dentro de esta coorutina se va a ejecutar en un hilo secundario, es decir en un
            //hilo diferente al principal
            isLoading.postValue(true) // Muestra barra circular de progreso ------ la variable isLoading que es de tipo LiveData Boolean
            // se da cuenta que le hicimos un cambio y avisara a la activity y/o Fragment correspondiente y este ultimo va a hacer lo que crea conveniente.
            // Si no usaramos Live Data, tendriamos que configurar esta funcion para que retorne
            // el objeto la variable "isLoading" a la vista donde esta funcion fue llamada.

            deleteItemImgApoloUseCase(itemDatosImage)

            isLoading.postValue(false) // Oculta barra circular de progreso

        }
    }

    fun deleteAllFromDataBase(){
        viewModelScope.launch {  // Cuando se usa arquitecturas como MVP, si la activity muere es necesario parar la coorrutina que se encuentre
            // en ejecución para que no salga errores, en este caso como usamos la arquitectura MVVM, ese inconveniente no lo tenemos por que podemos
            // usar la coorutina viewModelScope.launch {} que puede controlarse automaticamente
            // El codigo existente dentro de esta coorutina se va a ejecutar en un hilo secundario, es decir en un
            //hilo diferente al principal
            isLoading.postValue(true) // Muestra barra circular de progreso ------ la variable isLoading que es de tipo LiveData Boolean
            // se da cuenta que le hicimos un cambio y avisara a la activity y/o Fragment correspondiente y este ultimo va a hacer lo que crea conveniente.
            // Si no usaramos Live Data, tendriamos que configurar esta funcion para que retorne
            // el objeto la variable "isLoading" a la vista donde esta funcion fue llamada.

            deleteAllFromDataBaseUseCase()

            isLoading.postValue(false) // Oculta barra circular de progreso
        }
    }
}