package co.com.personal.hnino.proyectoapolo.domain

import co.com.personal.hnino.proyectoapolo.data.ImgsApolloRepository
import co.com.personal.hnino.proyectoapolo.domain.model.ItemDatosImage
import co.com.personal.hnino.proyectoapolo.domain.model.ItemDetallesImage
import co.com.personal.hnino.proyectoapolo.domain.model.ItemLinksImage
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test

class GetListaImgsApoloUseCaseTest {

    @RelaxedMockK // Si no preparamos y definimos una respuesta mockeada de los metodos de la clase que  vamos a Testiar,
    //es decir de la clase ImgsApolloRepository que declaramos mas adelante, entonces esta etiqueta nos ayuda a generar
    // esa respuesta automaticamente y el test funciona normalmente.

    //@MockK // Si no preparamos y definimos una respuesta mockeadaa de los metodos de la clase que  vamos a Testiar,
    // es decir de la clase ImgsApolloRepository que declaramos mas adelante, entonces esta etiqueta y/o anotacion intentara
    // acceder a un metodo que no hemos preparado y por tanto el test falla, Esta anotación No nos ayuda a generar esa
    // respuesta automaticamente como si lo hace @RelaxedMockK. Lo mejor es usar la anotación @MockK, debido a
    // que tenemos control total de los test, pero en este ejemplo vamos a usar @RelaxedMockK para fascilitar el proceso.

    private lateinit var repository: ImgsApolloRepository // estamos declarando la variable repository de tipo ImgsApolloRepository
    // y sin instanciarlo, pero de ello se va a encargar mock al configurarlo con la etiqueta @RelaxedMockK

    lateinit var getListaImgsApoloUseCase: GetListaImgsApoloUseCase // declaramos la variable getListUsersUseCase de tipo GetListUsersUseCase ya que
    //es la clase que vamos a testear y la instanceamos mas adelante.

    @Before
    fun onBefore() {
        //el codigo existente en esta función se va a ejecutar antes de los test debido a que esta
        // funcion esta configurada con la etiqueta @Before de la libreria junit.
        MockKAnnotations.init(this) // inicializamos la libreria de Mock para mockear el repositorio en cuestion

        getListaImgsApoloUseCase = GetListaImgsApoloUseCase(repository) // cremos una instancia de la clase que queremos testear
    }

    @After
    fun onAfter() {
    }

    @Test // Anotación para configurar que la siguiente función es un Test
    fun `when the data is obtained from the API and the result is empty`() = runBlocking {
        // las tildes invertidas, nos permiten poner espacios en el nombre de la funcion
        // runBlocking nos ayuda a lanzar corrutinas

        //Given. Es la respuesta del repositorio mockeado que vamos a dar o a entregar
        val listItemDatosImageTest = emptyList<ItemDatosImage>()
        coEvery { repository.getListaImgsApoloFromApi("apollo 11") } returns emptyList<ItemDatosImage>()
        //Estamos mockeando el repositorio para que cuando se llame la funcion repository.getListaImgsApoloFromApi("apollo 11"),
        // nos retorne una lista vacia.

        // Ojo... Si la implementación del metodo .getListaImgsApoloFromApi() no estubiera configurada para una corrituna, es decir
        // con la anotación suspend entonces en lugar de ponder coEvery{} deberiamos poner Every. Lo anterior significa que siempre
        // que se trabaje con corrutinas, de debe poner el co delante.

        // Como ya contamos con una respuesta mockeada para el caso del metodo repository.getListaImgsApoloFromApi() entoces ya podriamos
        // usar la anotación @MockK , pero unicamente para este metodo. ya que si se intentara usar para acceder a un metodo que no hemos
        // preparado, entonces el test falla.


        //When..... El given, lo damos cuando llamemos al caso de uso getListUsersUseCase("users")
        val response = getListaImgsApoloUseCase("apollo 11")

        //then ..... lo que tiene que pasar cuando llamemos al caso de uso getListUsersUseCase
        coVerify(exactly = 1) { repository.getListaImgsApoloFromApi("apollo 11")} // El test verifica que se esta llamando una sola vez a
        //repository.getListaImgsApoloFromApi("apollo 11")

        // Ojo... Si la implementación del metodo .getListaImgsApoloFromApi("apollo 11") no estubiera configurada para una corrituna, es decir
        // con la anotación suspend entonces en lugar de ponder coVerify{} deberiamos poner Verify. Lo anterior significa que siempre
        // que se trabaje con corrutinas, de debe poner el co delante.

        assert(listItemDatosImageTest == response) // assert() comprueba que el codigo dentro genere un true

    }

    @Test
    fun `when the data is obtained from the API and the result is successful`() = runBlocking {
        // las tildes invertidas, nos permiten poner espacios en el nombre de la funcion
        // runBlocking nos ayuda a lanzar corrutinas

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
        coEvery { repository.getListaImgsApoloFromApi("apollo 11") } returns listItemDatosImageTest
        //Estamos mockeando el repositorio para que cuando se llame la funcion repository.getListaImgsApoloFromApi("apollo 11"),
        // nos retorne la lista que creamos anteriormente.

        // Ojo... Si la implementación del metodo .getListaImgsApoloFromApi() no estubiera configurada para una corrituna, es decir
        // con la anotación suspend entonces en lugar de ponder coEvery{} deberiamos poner Every. Lo anterior significa que siempre
        // que se trabaje con corrutinas, de debe poner el co delante.

        // Como ya contamos con una respuesta mockeada para el caso del metodo repository.getListaImgsApoloFromApi() entoces ya podriamos
        // usar la anotación @MockK , pero unicamente para este metodo. ya que si se intentara usar para acceder a un metodo que no hemos
        // preparado, entonces el test falla.


        //When..... El given, lo damos cuando llamemos al caso de uso getListUsersUseCase("users")
        val response = getListaImgsApoloUseCase("apollo 11")

        //then ..... lo que tiene que pasar cuando llamemos al caso de uso getListUsersUseCase
        coVerify(exactly = 1) { repository.getListaImgsApoloFromApi("apollo 11") } // El test verifica que se esta llamando una sola vez a
        //repository.getListaImgsApoloFromApi("apollo 11")

        // Ojo... Si la implementación del metodo .getListaImgsApoloFromApi("apollo 11") no estubiera configurada para una corrituna, es decir
        // con la anotación suspend entonces en lugar de ponder coVerify{} deberiamos poner Verify. Lo anterior significa que siempre
        // que se trabaje con corrutinas, de debe poner el co delante.

        assert(listItemDatosImageTest == response) // assert() comprueba que el codigo dentro genere un true

    }

}