package co.com.personal.hnino.proyectoapolo.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import co.com.personal.hnino.proyectoapolo.data.model.DataTemporalProvider
import co.com.personal.hnino.proyectoapolo.domain.*
import co.com.personal.hnino.proyectoapolo.domain.model.ItemDatosImage
import co.com.personal.hnino.proyectoapolo.domain.model.ItemDetallesImage
import co.com.personal.hnino.proyectoapolo.domain.model.ItemLinksImage
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi // Esta anotación nos quita los warning en los dispachers ya que es experimental
class ItemsImgsApoloViewModelTest { // aqui podemos lanzar todos los test al mismo tiempo

    @RelaxedMockK // Si no preparamos y definimos una respuesta mockeada de los metodos de la clase que  vamos a Testiar,
    //es decir de la clase ItemsImgsApoloViewModelTest que declaramos mas adelante, entonces esta etiqueta nos ayuda a generar
    // esa respuesta automaticamente y el test funciona normalmente.

    //@MockK // Si no preparamos y definimos una respuesta mockeadaa de los metodos de la clase que  vamos a Testiar,
    // es decir de la clase ItemsImgsApoloViewModelTest que declaramos mas adelante, entonces esta etiqueta y/o anotacion intentara
    // acceder a un metodo que no hemos preparado y por tanto el test falla, Esta anotación No nos ayuda a generar esa
    // respuesta automaticamente como si lo hace @RelaxedMockK. Lo mejor es usar la anotación @MockK, debido a
    // que tenemos control total de los test, pero en este ejemplo vamos a usar @RelaxedMockK para fascilitar el proceso.

    private lateinit var getListaImgsApoloUseCase: GetListaImgsApoloUseCase//Declaramos la variable getListaImgsApoloUseCase que tiene
    // configurda la anotación @RelaxedMockK y que sera necesaria como parametro para poder crear mas adelante una
    // instancia de ItemsImgsApoloViewModel
    @RelaxedMockK
    private lateinit var saveItemImgApoloUseCase: SaveItemImgApoloUseCase//Declaramos la variable saveItemImgApoloUseCase que tiene
    // configurda la anotación @RelaxedMockK y que sera necesaria como parametro para poder crear mas adelante una
    // instancia de ItemsImgsApoloViewModel
    @RelaxedMockK
    private lateinit var deleteItemImgApoloUseCase: DeleteItemImgApoloUseCase//Declaramos la variable deleteItemImgApoloUseCase que tiene
    // configurda la anotación @RelaxedMockK y que sera necesaria como parametro para poder crear mas adelante una
    // instancia de ItemsImgsApoloViewModel
    @RelaxedMockK
    private lateinit var getListaImgsApoloFavoritasUseCase: GetListaImgsApoloFavoritasUseCase//Declaramos la variable getListaImgsApoloFavoritasUseCase que tiene
    // configurda la anotación @RelaxedMockK y que sera necesaria como parametro para poder crear mas adelante una
    // instancia de ItemsImgsApoloViewModel
    @RelaxedMockK
    private lateinit var dataTemporalProvider : DataTemporalProvider//Declaramos la variable dataTemporalProvider que tiene
    // configurda la anotación @RelaxedMockK y que sera necesaria como parametro para poder crear mas adelante una
    // instancia de ItemsImgsApoloViewModel
    @RelaxedMockK
    private lateinit var deleteAllFromDataBaseUseCase: DeleteAllFromDataBaseUseCase//Declaramos la variable deleteAllFromDataBaseUseCase que tiene
    // configurda la anotación @RelaxedMockK y que sera necesaria como parametro para poder crear mas adelante una
    // instancia de ItemsImgsApoloViewModel

    private lateinit var itemsImgsApoloViewModel: ItemsImgsApoloViewModel // creamos una instancia de la clase que queremos testear,
    // y como en este caso es una clase de tipo view Model, entonces para poder hacer este test, debemos usar
    // la libreria testImplementation "androidx.arch.core:core-testing:2.1.0" que configuramos en el
    // build.gradle (app) y que nos ayuda a testear los liveData que son usados en las clases view model
    // y ademas de ello, tambien debemos utilizar una regla.

    // Una Regla es basicamente una función en el OnBefore, pero es abstraida, ya que es más reutilizable y
    // disminuye el boilerplate que son esas secciones de código que deben incluirse en muchos lugares
    // con poca o ninguna alteración.

    @get:Rule // esta etiqueta define que el codigo siguiente es una regla
    var regla: InstantTaskExecutorRule = InstantTaskExecutorRule() // creamos una variable llamada regla y la
    // instanciamos con la clase InstantTaskExecutorRule la cual hace parte de la libreria
    // testImplementation "androidx.arch.core:core-testing:2.1.0", por eso al implementarla se debe
    // usar el import androidx.arch.core.executor.testing.InstantTaskExecutorRule

    // OJO ==> en la clase ItemsImgsApoloViewModel se esta usando la corrutina viewModelScope.launch {}, y cuando se hace testing
    // no podemos emular bien el ciclo de vida de esta corrutina, por tal motivo debemos modificar el dispacher en la funcion
    // onBefore (Los dispacher son los que se encargan de gestionar los hilos que usan las corrutinas).

    @Before
    fun onBefore(){
        //el codigo existente en esta función se va a ejecutar antes de los test debido a que esta
        // funcion esta configurada con la etiqueta @Before de la libreria junit.
        MockKAnnotations.init(this) // inicializamos la libreria de Mock para mockear las funciones de la clase
        //itemsImgsApoloViewModel que necesitemos.

        itemsImgsApoloViewModel = ItemsImgsApoloViewModel(getListaImgsApoloUseCase, saveItemImgApoloUseCase,
        deleteItemImgApoloUseCase, getListaImgsApoloFavoritasUseCase, dataTemporalProvider, deleteAllFromDataBaseUseCase)
        // cremos una instancia de la clase ItemsImgsApoloViewModel que es la que queremos testear

        Dispatchers.setMain(Dispatchers.Unconfined) // iniciamos el dispacher antes de los test ya que en esta
        //función estamos usando la anotación @Before

    }

    @After
    fun onAfter(){
        //el codigo existente en esta función se va a ejecutar despues de los test debido a que esta
        // funcion esta configurada con la etiqueta @After de la libreria junit.
        Dispatchers.resetMain() // cerramos el hilo creado para continuar con el hilo principal
    }

    @Test
    fun `when the data is obtained from the API and the result is successful` () = runTest{
        // las tildes invertidas, nos permiten poner espacios en el nombre de la funcion
        // runTest nos ayuda a lanzar corrutinas, aqui no usamos runBlocking, por que aqui
        // trabajamos com view Model

        //Given =>
        val listItemDatosImageTest = listOf(
            ItemDatosImage(
                "https://images-assets.nasa.gov/video/Apollo 11 Overview/collection.json",
                data = listOf(
                    ItemDetallesImage(
                        "Apollo 11 Overview", "Apollo 11 Overview",
                        "Video highlights from the historic first manned landing on the moon, " +
                                "during the Apollo 11 mission in July 1969.", false
                    )
                ), links = listOf(
                    ItemLinksImage("https://images-assets.nasa.gov/video/Apollo 11 Overview/Apollo 11 Overview~thumb.jpg"),
                    ItemLinksImage("https://images-assets.nasa.gov/video/Apollo 11 Overview/Apollo 11 Overview.srt")
                )
            ),

            ItemDatosImage(
                "https://images-assets.nasa.gov/video/Apollo 11 Landing Profile/collection.json",
                data = listOf(
                    ItemDetallesImage(
                        "Apollo 11 Landing Profile",
                        "Apollo 11 Landing Profile",
                        "The approach and landing of Apollo 11 has been reconstructed using data from NASA's " +
                                "Lunar Reconnaissance Orbiter. Video Courtesy of GoneToPlaid.",
                        false
                    )
                ), links = listOf(
                    ItemLinksImage("https://images-assets.nasa.gov/video/Apollo 11 Landing Profile/Apollo 11 Landing Profile~thumb.jpg"),
                    ItemLinksImage("https://images-assets.nasa.gov/video/Apollo 11 Landing Profile/Apollo 11 Landing Profile.srt")
                )
            ),

            ItemDatosImage(
                "https://images-assets.nasa.gov/video/Apollo 11 Moonwalk Montage/collection.json",
                data = listOf(
                    ItemDetallesImage(
                        "Apollo 11 Moonwalk Montage",
                        "Apollo 11 Moonwalk Montage",
                        "This two-minute video montage shows highlights of the Apollo 11 moonwalk.",
                        false
                    )
                ), links = listOf(
                    ItemLinksImage("https://images-assets.nasa.gov/video/Apollo 11 Moonwalk Montage/Apollo 11 Moonwalk Montage~thumb.jpg"),
                    ItemLinksImage("https://images-assets.nasa.gov/video/Apollo 11 Moonwalk Montage/Apollo 11 Moonwalk Montage.srt")
                )
            ),

            ItemDatosImage(
                "https://images-assets.nasa.gov/video/Buzz Aldrin on the Meaning of Apollo 11/collection.json",
                data = listOf(
                    ItemDetallesImage(
                        "Buzz Aldrin on the Meaning of Apollo 11",
                        "Buzz Aldrin on the Meaning of Apollo 11",
                        "Apollo 11 was about exploration, about taking risks for great rewards in science and engineering, " +
                                "about setting an ambitious goal before the world,  and then finding the political will and the national " +
                                "means to achieve it.",
                        false
                    )
                ), links = listOf(
                    ItemLinksImage("https://images-assets.nasa.gov/video/Buzz Aldrin on the Meaning of Apollo 11/Buzz Aldrin on the Meaning of Apollo 11~thumb.jpg"),
                    ItemLinksImage("https://images-assets.nasa.gov/video/Buzz Aldrin on the Meaning of Apollo 11/Buzz Aldrin on the Meaning of Apollo 11.srt")
                )
            )
        )

        coEvery { getListaImgsApoloUseCase("apollo 11") } returns listItemDatosImageTest

        //When
        itemsImgsApoloViewModel.obtenerListaItemsImgsApoloFromApi("apollo 11") // cuendo se use el metodo obtenerListaItemsImgsApoloFromApi("users")
        //de la clase itemsImgsApoloViewModel

        //Then
        coVerify(exactly = 1) { getListaImgsApoloUseCase("apollo 11")} // El test verifica que se esta llamando una sola vez a
        //getListaImgsApoloUseCase("users")

        coVerify(exactly = 1) { getListaImgsApoloFavoritasUseCase()} // El test verifica que se esta llamando una sola vez a
        //getListaImgsApoloFavoritasUseCase()()

        assert(itemsImgsApoloViewModel.listaItemsImgsApoloMLD.value == listItemDatosImageTest) // verificamos si el valor enviado en el live dada con postvalue
        // es igual a listItemDatosImageTest que configuramos en el proceso del mock

        assert(itemsImgsApoloViewModel.isLoading.value == false)

    }
}