package co.com.personal.hnino.proyectoapolo.ui.view

import android.content.Intent
import android.content.res.Resources
import android.os.IBinder
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.test.espresso.*
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.core.internal.deps.guava.collect.Iterables
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intending
import androidx.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import co.com.personal.hnino.proyectoapolo.R
import co.com.personal.hnino.proyectoapolo.ui.view.adaptadores.ItemImageApoloViewHolder
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.hamcrest.Matchers.*
import org.hamcrest.TypeSafeMatcher
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class) //usamos esta anotación para que en la clase MainActivityTest
// se pueda usar android junit class runner

class MainActivityTest{

    //creamos una regla vinculada a la actividad ListUsersActivity

    /*
    @get:Rule
    var activityTestScenarioRule = ActivityScenarioRule(MainActivity::class.java) //creamos la variable
    // activityTestScenarioRule y la instanciamos para crear un objeto asociado a la actividad que vamos a testear,
    // es decir estamos iniciando la Activity antes de inicar el test.
    */

    /*
    @get:Rule
    var activityTestRule = ActivityTestRule(MainActivity::class.java) //creamos la variable
    // activityTestRule y la instanciamos para crear un objeto asociado a la actividad que vamos a testear,
    // es decir estamos iniciando la Activity antes de inicar el test.
    */

    @get:Rule
    //@JvmField
    var intentTestScenarioRule = IntentsTestRule(MainActivity::class.java) //creamos la variable
    // intentTestScenarioRule y la instanciamos para crear un objeto  que va a inicializar el Test
    // haciendo uso de la actividad que vamos a testear, es decir MainActivity. En realizadm lo que hacemos
    // al crear esta regla, es iniciar la Activity MainActivity antes de inicar el test y tambien a
    // cerrar la Activity cuando finalice el test

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun whenTheUserUsesAllScreensAndAppOptions() {
        // Cuando se ejecuta este test de UI, se llenara el formulario de registo y se usara el botón "INGRESAR"

        Thread.sleep(1000)
        Espresso.onView(withId(R.id.editTextCorreoE)) // hacemos referencia al EditText con id editTextNombreCompleto
            .perform(ViewActions.clearText(), ViewActions.typeText("henrybike@hotmail.com"),
                ViewActions.closeSoftKeyboard()) // accion que se va a realizar
            .check(matches(withText("henrybike@hotmail.com")))
        //.check() // para hacer validaciones son los View Assertions
        // EL metodo closeSoftKeyboard() hace parte de la libreria androidx.test.espresso.action.ViewActions
        // y nos ayuda a cerrar el teclado

        Espresso.onView(withId(R.id.editTextCorreoE))
            .check(matches(withText("henrybike@hotmail.com")))
            //.check() // para hacer validaciones son los View Assertions

        Thread.sleep(1000)
        Espresso.onView(withId(R.id.checkboxEstadoSesion))
            .perform(ViewActions.click())

        Thread.sleep(1000)
        Espresso.onView(withId(R.id.checkboxEstadoSesion))
            .perform(ViewActions.click())

        Thread.sleep(1000)
        Espresso.onView(withId(R.id.btnIngresar))
            .perform(ViewActions.click())

        var toastMessage: String = "Debe ingresar su nombre completo y/o correo electrónico para poder acceder!"

        onView(withText(toastMessage)).inRoot(ToastMatcher()) // Usamos la clase ToastMatcher que es el
                //Matcher personalizado que creamos para hacer testig a un Toast
            .check(matches(isDisplayed())) // validamos que el mensaje sea mostrado en pantalla mediante
                // el Toast

        Thread.sleep(3000)
        Espresso.onView(withId(R.id.editTextNombreCompleto)) // hacemos referencia al EditText con id editTextNombreCompleto
            .perform(ViewActions.clearText(), ViewActions.typeText("Henry Francisco Nino"),
                ViewActions.closeSoftKeyboard()) // accion que se va a realizar
        //.check() // para hacer validaciones son los Asserts
        // EL metodo closeSoftKeyboard() hace parte de la libreria androidx.test.espresso.action.ViewActions.y nos ayuda a cerrar el teclado

        Thread.sleep(1000)
        Espresso.onView(withId(R.id.btnIngresar))
            .perform(ViewActions.click())

        //Con el codigo Siguiente capturamos la data que el intent transporta de la actividad MainActiviy a la actividad
        //RecyclerViewImgsApoloActivity y con las assertEquals validamos que los datos asociados a las Keys
        // correspondientes que se van a trasportar sean iguales a los datos que se diligenciaron en el formulario.
        intending(hasExtraWithKey("dataNombreCompleto"))
        intending(hasExtraWithKey("dataCorreoE"))
        val dataSendForIntent : Intent = Iterables.getOnlyElement(Intents.getIntents())
        assertEquals(dataSendForIntent.extras!!.getString("dataNombreCompleto"), "Henry Francisco Nino")
        assertEquals(dataSendForIntent.extras!!.getString("dataCorreoE"), "henrybike@hotmail.com")

        Thread.sleep(3000)
        onView(withId(R.id.searchViewImgs))
            .perform(pressKey(KeyEvent.KEYCODE_ENTER))
            .perform(typeSearchViewText("moon"))
            .perform(pressKey(KeyEvent.KEYCODE_ENTER))

        Thread.sleep(5000)
        onView(withId(R.id.searchViewImgs))
            .perform(typeSearchViewText("apollo 11"))
            .perform(pressKey(KeyEvent.KEYCODE_ENTER))
        /*
        Thread.sleep(4000)
        onView(withId(
            Resources.getSystem().getIdentifier("searchViewImgs",
            "id", "android"))).perform(clearText(),typeText("moon"))
            .perform(pressKey(KeyEvent.KEYCODE_ENTER))
        */
/*
        onView(allOf(supportsInputMethods(), isDescendantOfA(withId(R.id.searchViewImgs))))
            .perform(typeText("moon"))

 */
/*
        onView(withId(R.id.searchViewImgs))
            .perform(ViewActions.click(),ViewActions.typeText("moon"))
            .perform(pressKey(KeyEvent.KEYCODE_ENTER)/*, ViewActions.closeSoftKeyboard()*/)
*/

        /*
        onView(withId(R.id.searchViewImgs))
            .perform(ViewActions.typeText("moon"),
                ViewActions.closeSoftKeyboard())
*/

        val itemIdTest1: String = "The Flight of Apollo 11"

        Thread.sleep(500)
        Espresso.onView(withText(itemIdTest1)).check(doesNotExist()) // Verificamos que el item con id "The Flight of Apollo 11"
        // no aparesca en la pantalla, ya que es un intem que requiere usar el scroll para que pueda ser cargado en el
        //recyclerView

        Thread.sleep(500)
        Espresso.onView(withId(R.id.recyclerViewImgsApolo))
            .perform(RecyclerViewActions.scrollTo<ItemImageApoloViewHolder>(
                hasDescendant(withText(itemIdTest1))))  // realiza scoll en el recycler view de manera
        //descendente hasta encontrar un item que que contenga el texto de la variable itemIdTest1

        //*************************************************************************//
        Thread.sleep(2000)
        Espresso.onView(withId(R.id.recyclerViewImgsApolo))
            .perform(RecyclerViewActions.scrollToPosition<ItemImageApoloViewHolder>(0)) // Scroll al item 0

        Thread.sleep(2000)
        val position1: Int = 2
        Espresso.onView(withId(R.id.recyclerViewImgsApolo))
            .perform(RecyclerViewActions.actionOnItemAtPosition<ItemImageApoloViewHolder>(position1, ViewActions.click()))

        Thread.sleep(2000)
        Espresso.onView(withId(R.id.switch1)).check(matches(isNotChecked()))
            .perform(ViewActions.click())

        Thread.sleep(4000)
        Espresso.onView(withId(R.id.btnRegresar)) // id de xml de DetallesItemImageApolo
            .perform(ViewActions.click())

        //*************************************************************************//

        Thread.sleep(2000)
        Espresso.onView(withId(R.id.recyclerViewImgsApolo))
            .perform(RecyclerViewActions.scrollToPosition<ItemImageApoloViewHolder>(22)) // Scroll al item 22

        Thread.sleep(2000)
        val position2: Int = 20
        Espresso.onView(withId(R.id.recyclerViewImgsApolo))
            .perform(RecyclerViewActions.actionOnItemAtPosition<ItemImageApoloViewHolder>(position2, ViewActions.click()))

        Thread.sleep(2000)
        Espresso.onView(withId(R.id.switch1)).check(matches(isNotChecked()))
            .perform(ViewActions.click())

        Thread.sleep(4000)
        Espresso.onView(withId(R.id.btnRegresar)) // id de xml de DetallesItemImageApolo
            .perform(ViewActions.click())

        //*************************************************************************//

        Thread.sleep(2000)
        Espresso.onView(withId(R.id.recyclerViewImgsApolo))
            .perform(RecyclerViewActions.scrollToPosition<ItemImageApoloViewHolder>(32)) // Scroll al item 32

        Thread.sleep(2000)
        val position3: Int = 30
        Espresso.onView(withId(R.id.recyclerViewImgsApolo))
            .perform(RecyclerViewActions
                .actionOnItemAtPosition<ItemImageApoloViewHolder>(position3, clickItemWithId(R.id.checkboxFavorito)))

        //*************************************************************************//

        Thread.sleep(2000)
        Espresso.onView(withId(R.id.recyclerViewImgsApolo))
            .perform(RecyclerViewActions.scrollToPosition<ItemImageApoloViewHolder>(42)) // Scroll al item 42

        Thread.sleep(2000)
        val position4: Int = 40
        Espresso.onView(withId(R.id.recyclerViewImgsApolo))
            .perform(RecyclerViewActions
                .actionOnItemAtPosition<ItemImageApoloViewHolder>(position4, clickItemWithId(R.id.checkboxFavorito)))

        //*************************************************************************//

        Thread.sleep(2000)
        Espresso.onView(withId(R.id.recyclerViewImgsApolo))
            .perform(RecyclerViewActions.scrollToPosition<ItemImageApoloViewHolder>(52)) // Scroll al item 52

        Thread.sleep(2000)
        val position5: Int = 50
        Espresso.onView(withId(R.id.recyclerViewImgsApolo))
            .perform(RecyclerViewActions
                .actionOnItemAtPosition<ItemImageApoloViewHolder>(position5, clickItemWithId(R.id.checkboxFavorito)))

        //*************************************************************************//

        Thread.sleep(2000)
        Espresso.onView(withId(R.id.recyclerViewImgsApolo))
            .perform(RecyclerViewActions.scrollToPosition<ItemImageApoloViewHolder>(12)) // Scroll al item 12

        Thread.sleep(2000)
        val position6: Int = 10
        Espresso.onView(withId(R.id.recyclerViewImgsApolo))
            .perform(RecyclerViewActions
                .actionOnItemAtPosition<ItemImageApoloViewHolder>(position6, clickItemWithId(R.id.checkboxFavorito)))

        //*************************************************************************//
        /*
        Espresso.onData(hasEntry(equalTo(RecyclerViewImgsApoloActivity.ROW_TEXT), `is`(itemIdTest1)))
            .check(matches(isCompletelyDisplayed())) // Aplica para Adapter View y no para Recycler View

        Espresso.onData(hasEntry(equalTo(RecyclerViewImgsApoloActivity.ROW_TEXT), `is`(itemIdTest1)))
            .onChildView(withId(R.id.checkboxFavorito))// Aplica para Adapter View y no para Recycler View
        */

        Thread.sleep(3000)
        Espresso.onView(withId(R.id.imgsApolloFavoritas)) // id de xml de RecyclerViewImgsApoloActivity
            .perform(ViewActions.click())

        Thread.sleep(4000)
        Espresso.onView(withId(R.id.btnRegresarFav)) // id de xml de RecyclerViewImgsApoloFavoritosActivity
            .perform(ViewActions.click())

        Thread.sleep(2000)
        val overflowMenuButton = Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withContentDescription("More options"),
                childAtPosition(
                    childAtPosition(
                        withId(androidx.appcompat.R.id.action_bar),
                        1
                    ),
                    2
                ),
                ViewMatchers.isDisplayed()
            )
        )
        overflowMenuButton.perform(ViewActions.click())

        /*
        Thread.sleep(3000)
        Espresso.onView(withId(R.id.datosPerfil)) // id de xml de RecyclerViewImgsApoloActivity
            .perform(ViewActions.click())
        */

        Thread.sleep(2000)
        val materialTextView = Espresso.onView(
            Matchers.allOf(
                withId(androidx.appcompat.R.id.title), withText("Datos de Perfil"),
                childAtPosition(
                    childAtPosition(
                        withId(androidx.appcompat.R.id.content),
                        0
                    ),
                    0
                ),
                ViewMatchers.isDisplayed()
            )
        )
        materialTextView.perform(ViewActions.click())

        Thread.sleep(2000)
        Espresso.onView(withId(R.id.textViewNombreCompleto)) // id de xml de PerfilDeUsuarioActivity
            .check(matches(withText("Nombre Completo: Henry Francisco Nino")))
        //.check() // para hacer validaciones son los View Assertions



        Thread.sleep(2000)
        Espresso.onView(withId(R.id.btnRegresarEnPerfilUser)) // id de xml de PerfilDeUsuarioActivity
            .perform(ViewActions.click())

        Thread.sleep(3000)
        Espresso.onView(withId(R.id.cerrarSesion)) // id de xml de RecyclerViewImgsApoloActivity
            .perform(ViewActions.click())

        Thread.sleep(2000)
    }

    private fun childAtPosition(parentMatcher: Matcher<View>, position: Int): Matcher<View> {

        return object : TypeSafeMatcher<View>() { // TypeSafeMatcher junto con BoundedMatcher son funciones
            // que nos permiten crear matchers (emparejadores) personalizados, con estas funciones, podemos
            //extender el framework de expresso
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }

    fun clickItemWithId(id: Int): ViewAction { // Con esta función estamos creando un ViewAction
        // personalizado y lo creamos con el fin de poder hacer click en el checkbox del item
        // deseado del recycler view.
        // Ayuda => https://www.maskaravivek.com/post/working-with-recycler-views-in-espresso-tests/
        return object : ViewAction {
            override fun getConstraints(): Matcher<View>? {
                return null
            }

            override fun getDescription(): String {
                return "Click on a child view with specified id."
            }

            override fun perform(uiController: UiController, view: View) {
                val v = view.findViewById<View>(id) as View
                v.performClick()
            }
        }
    }

    fun typeSearchViewText(text: String): ViewAction {
        return object : ViewAction {
            override fun getDescription(): String {
                return "Change view text"
            }

            override fun getConstraints(): Matcher<View> {
                return allOf(isDisplayed(), isAssignableFrom(SearchView::class.java))
            }

            override fun perform(uiController: UiController?, view: View?) {
                (view as SearchView).setQuery(text, false)
            }
        }
    }
}