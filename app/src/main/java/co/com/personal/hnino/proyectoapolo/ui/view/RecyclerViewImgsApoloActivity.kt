package co.com.personal.hnino.proyectoapolo.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import android.widget.ProgressBar
import androidx.appcompat.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.VisibleForTesting
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.com.personal.hnino.proyectoapolo.R
import co.com.personal.hnino.proyectoapolo.ui.view.adaptadores.ImgsApoloAdapterRecycler
import co.com.personal.hnino.proyectoapolo.domain.model.ItemDatosImage
import co.com.personal.hnino.proyectoapolo.ui.viewmodel.ItemsImgsApoloViewModel
import co.com.personal.hnino.proyectoapolo.ui.viewmodel.UserDataAppViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint  // Con esta etiqueta configuramos esta activity y la preparamos para que permita inyectar  dependencias
// haciendo uso de Dagger Hilt, Una dependencia es una clase que es necesario usar en otras clases, y para ello es necesario
// crear instancias de esas clases (dependencias), sin embargo al usar Dagger Hilt, lo que hacemos es inyectar las clases
// que necesitamos, y no nececitamos crear instancias de las mismas ya que Dagger Hilt hace esa labor cuando es requerido .
// La idea de implementar Inyección de dependencias es que dentro de una clase, no se tenga ninguna instacia de ninguna otra clase,
// ya que todas deberian ser inyectadas y gestionadas por Dagger Hilt

class RecyclerViewImgsApoloActivity /*@Inject constructor(private val restEngineViewModel : RestEngineViewModel)*/ : AppCompatActivity(), SearchView.OnQueryTextListener {
    // Al usar la interfaz SearchView.OnQueryTextListener, debemos implementar los metodos abtractos imonQueryTextChange y onQueryTextSubmit los cuales
    // nos ayudan a retornar al lista de objetos de tipo ItemDatosImage filtrada por las palabras clave que el usuario ingrese en el SearchViewImgs del layout
    // activity_recycler_view_imgs_apolo que es el asociado a esta actividas

    companion object {
        @VisibleForTesting
        val ROW_TEXT:String = "ROW_TEXT"
    }


    private lateinit  var listaItemsImgsApoloForRecyclerView:List<ItemDatosImage>
    private lateinit var adapter: ImgsApoloAdapterRecycler

    private val userDataAppViewModel : UserDataAppViewModel by viewModels()// hacemos la conexion y logica de nuestra viewModel
    // a  la de nuestra vista (activity y/o Fragment), incluyendo el ciclo de vida y demas, ya no tenemos que hacer absolutamente nada

    private val itemsImgsApoloViewModel : ItemsImgsApoloViewModel by viewModels() // hacemos la conexion y logica de nuestra viewModel
    // a  la de nuestra vista (activity y/o Fragment), incluyendo el ciclo de vida y demas, ya no tenemos que hacer absolutamente nada


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view_imgs_apolo)

        initUI()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu) // Cargamos el recurso existente en la carpeta res/menu/main_menu.xml
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.datosPerfil -> verDatosPerfilUsuario()
            R.id.imgsApolloFavoritas -> verListaItemsImgsApoloFavoritas()

            R.id.cerrarSesion -> {

                userDataAppViewModel.prefereciasUsuarioViewModel.observe(this, Observer {
                    if(it.getEstadoSesion()){ // Valida si el usuario actual marco el check box de mantener sesión activa y si es verdadero entonces=>
                        userDataAppViewModel.deletePreferenciasUsuario() // //Este metodo va a borrar toda la la información del usuario logueado
                        // que fue guardada localmente usando share Preferences
                        itemsImgsApoloViewModel.deleteAllFromDataBase()// //Este metodo va a borrar toda la la información de los items que el usuario
                        // habia seleccionado como favoritos
                    }
                })
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                //onBackPressed() //Retorna a la Actividad y vista anterior
                finish() // Finaliza la activity RecyclerViewApoloFavoritosActivity
                Toast.makeText(this, "AL cerrar la sesión y por seguridad, los Datos de Login y de Items Favoritos, han sido borrados.",Toast.LENGTH_SHORT).show()
            }
            R.id.opcion4 -> Toast.makeText(this, "Opción 4 en Desarrollo",Toast.LENGTH_SHORT).show()
            R.id.opcion5 -> Toast.makeText(this, "Opción 5 en Desarrollo",Toast.LENGTH_SHORT).show()
            R.id.opcion6 -> Toast.makeText(this, "Opción 6 en Desarrollo",Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }

    fun initUI(){

        val textViewNombreIngresado = findViewById<TextView>(R.id.textViewNombreIngresado)
        //val intentExtras = intent.extras // Se optienen los extras que se han enviado desde otras Activity
        //val nombreCompleto = intentExtras?.get("dataNombreCompleto")
        //val correoE = intentExtras?.get("dataCorreoE")
        //textViewNombreIngresado.text = "Bienvenido - $nombreCompleto"

        userDataAppViewModel.getPrefereciasUsuarioCurrent()

        userDataAppViewModel.prefereciasUsuarioViewModel.observe(this, Observer { userDataAppActual ->
            // La fucion observe es propia de la variable prefereciasUsuarioViewModel debido a que la declaramos como MutableLiveData<PreferenciasUser>()
            // en la clase UserDataAppViewModel y recibe 2 parametros, el owner que es el contexto actual, es decir "this", y el
            // segundo es la funcion Observer donde lo que exista dentro de su bloque de codigo va a estar enganchado al nuestro
            //liveData y cuando nuestro LiveData tenga un cambio, en este caso un nuevo objeto de tipo PreferenciasUser, entonces se va a
            //ejecuar lo existente en este bloque de codigo.---------- userDataAppActual simplemente es un nombre cualquera que
            //pusimos para no usar "it" y hacerlo un poco mas entendible, pero se podria poner cualquiera otro nombre o en su defecto
            // usar "it"

            Log.d(" ==================================> thread A ==> ", userDataAppActual.getEstadoSesion().toString()) // Prueba de Escritorio

            if(userDataAppActual.getEstadoSesion()){ //Si es verdadero entonces es porque el usuario ya habia iniciado sesión y selecciono
                // la opcion de mantener la sesión iniciada, entonces cambiamos el color de fondo de la vista de RecyclerViewImgsApolo
                var contenedorRecyclerViewImgsApolo = findViewById<ConstraintLayout>(R.id.contenedorRecyclerViewImgsApolo)
                contenedorRecyclerViewImgsApolo.setBackgroundColor(ContextCompat.getColor(contenedorRecyclerViewImgsApolo.context, R.color.verde_clarito))
            }

            textViewNombreIngresado.text = "Bienvenido - " + userDataAppActual.getName()
        })

        //textViewVerDatosPerfilUsuario.setOnClickListener {verDatosPerfilUsuario()}

        seachByKeyWords("apollo 11")
    }

    private fun verDatosPerfilUsuario(){

        val intent = Intent(this, PerfilDeUsuarioActivity::class.java)
        startActivity(intent)
        Toast.makeText(this, "Tus datos de perfil", Toast.LENGTH_SHORT).show()
        finish() // Finaliza la visualización de la Activity Actual
    }

    private fun verListaItemsImgsApoloFavoritas() {
        val intent = Intent(this, RecyclerViewImgsApoloFavoritosActivity::class.java)
        startActivity(intent)
        Toast.makeText(this, "Estas viendo el listado de Imagenes Favoritas", Toast.LENGTH_SHORT).show()
        finish() // Finaliza la visualización de la Activity Actual
    }
    private fun seachByKeyWords(queryByKeyWords:String){

        println("========== La Palabla clave para buscar Imagenes es ==========> $queryByKeyWords") // Prueba de Escritorio
        var progressCircular = findViewById<ProgressBar>(R.id.progressCircular)
        var searchViewImgs = findViewById<SearchView>(R.id.searchViewImgs) // Al searchViewImgs le implementamos los metodos de escucha que creamos asociados a la interfaz SearchView.OnQueryTextListener que
        //implementamos al principio de esta clase para buscar y retornar de la API la lista de objetos de tipo ItemDatosImage filtrada por las palabras clave que el usuario ingrese en el SearchViewImgs
        // del layout activity_recycler_view_imgs_apolo que es el asociado a esta actividad

        searchViewImgs.setOnQueryTextListener(this@RecyclerViewImgsApoloActivity) // usamos los metodos que extendimos de SearchView.OnQueryTextListener es decir
        // los metodos onQueryTextChange(newText: String?): Boolean {} y override fun onQueryTextSubmit(query: String?): Boolean {}

        itemsImgsApoloViewModel.obtenerListaItemsImgsApoloFromApi(queryByKeyWords)

        itemsImgsApoloViewModel.listaItemsImgsApoloMLD.observe(this@RecyclerViewImgsApoloActivity, Observer { listaItemsImgsApolo ->

            listaItemsImgsApoloForRecyclerView = listaItemsImgsApolo

            println(" ===============>>>> la lista a mostrar en el Recycler View de objetos ItemDatosImage es     => ${listaItemsImgsApoloForRecyclerView.toString()}")

            if (listaItemsImgsApolo.isEmpty()) {
                Toast.makeText(
                    this@RecyclerViewImgsApoloActivity,
                    "No se encontraron resultados para esta busqueda",
                    Toast.LENGTH_SHORT
                ).show()
            } else {

                iniciarReciclerView()
                adapter.notifyDataSetChanged() // con esta función le avisamos al adapter que ha habido cambios
                Toast.makeText(
                    this@RecyclerViewImgsApoloActivity,
                    "Respuesta a llamada OK",
                    Toast.LENGTH_SHORT
                ).show()
            }
        ocultarKeyboard()
        })

        itemsImgsApoloViewModel.isLoading.observe(this, Observer {

            progressCircular.isVisible = it // Muestra u oculta la barra circular de proceso
        })
    }

    private fun iniciarReciclerView(){

        val manager = LinearLayoutManager(this) // si cambiaramos LinearLayoutManager(this)
        // por GridLayoutManager(this, 2) nos permite visualizar 2 items en en una sola linea, y si en ves del 2
        // fuera un 3, veriamos 3 items en una sola linea
        var recyclerViewImgsApolo = findViewById<RecyclerView>(R.id.recyclerViewImgsApolo)
        recyclerViewImgsApolo.layoutManager = manager
        adapter = ImgsApoloAdapterRecycler(listaItemsImgsApoloForRecyclerView, { ItemDatosImage -> itemseleccionado1(ItemDatosImage)}, { ItemDatosImage2 -> itemseleccionado2(ItemDatosImage2)}) // Función Lammda
        recyclerViewImgsApolo.adapter = adapter // Al recyclerView
        //le estamos asignando el adaptador que creamos ImgsApoloAdapterRecycler y al que le enviamos como parametro la lista de objetos que
        //vamos a mostrar y tambien le enviamos la función lamda que nos ayudara a traer de vuelta a esta actividad "RecyclerViewImgsApoloActivity" el objeto(con toda su data)
        // que el usuario a seleccionado. tener en cuenta que las funciones lamda deben ir entre llaves y  no van dentro de parentesis de los argumentos,
        // por lo que la podemos sacar de los mismos
        val decoration = DividerItemDecoration(this,manager.orientation) // Creamos la variable decoration que nos
        // nos ayudara a separar los items del reciclerView con una linea horizontal
        // y le asigamos la función DividerItemDecoration a la que como parametros se le envian el contexto y la orientacion que
        // sería el mismo LinearLayoutManager(this) el cual es la misma variable que creamos llamada manager.
        recyclerViewImgsApolo.addItemDecoration(decoration) //le adicionamos al reciclerView la decoración que creamos con la variable "decoration"

    }

    fun itemseleccionado1(itemImageApolo: ItemDatosImage){
        //Esta función recibe como parametro el objeto de tipo itemImageApolo que trae la data del objeto (Item) al que le hacimos click del recycler view
        // lo que estamos haciendo es recuperar el objeto seleccionado por usuario en el recyclerview y lo traemos a esta actividad,
        // y para ello usamos nos es util la funcion lamda en el codigo recyclerViewImgsApolo.adapter = ..........

        Toast.makeText(this, /*itemImageApolo.data?.get(0)?.keywords.toString() + */ " ------- " + itemImageApolo.data?.get(0)?.description, Toast.LENGTH_SHORT).show()
        itemsImgsApoloViewModel.setItemDatosImagenCurrent(itemImageApolo)
        iniciarDetallesItemImageApolo()
    }

    fun itemseleccionado2(ItemDatosImage: ItemDatosImage){
        //Esta función recibe como parametro el objeto de tipo itemImageApolo que trae la data del objeto (Item) al que le hacimos click del recycler view
        // lo que estamos haciendo es recuperar el objeto seleccionado por usuario en el recyclerview y lo traemos a esta actividad,
        // y para ello usamos nos es util la funcion lamda en el codigo recyclerViewImgsApolo.adapter = ..........
        if(ItemDatosImage.data!!.get(0).imgFavorita) { // Si es true entonces =>
            itemsImgsApoloViewModel.saveItemDatosImagenFavorita(ItemDatosImage)
            Toast.makeText(this, " El Item ahora hace parte de tus Favoritos", Toast.LENGTH_SHORT).show()
        }
        else{
            println("=============> El Item de imagen favorita a eliminar es => $ItemDatosImage")
            itemsImgsApoloViewModel.deleteItemDatosImagenFavorita(ItemDatosImage)
            Toast.makeText(this, " El Item ha sido borrado de tus Favoritas", Toast.LENGTH_SHORT).show()

            //initUI()
        }
    }

    fun iniciarDetallesItemImageApolo(){

        val intent = Intent(this, DetallesItemImageApolo::class.java)
        startActivity(intent)
        Toast.makeText(this, "Detalles del item seleccionado =)", Toast.LENGTH_SHORT).show()
        finish()
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        //La implementación de este metodo nos ayuda a que cada es que el usuario teclea o borra un caracter, es decir cada ves que se
        //genere un cambio en el texto del SearchView entonces se va a ejecutar este metodo. pero para este caso no nos interesa implementarlo, por lo que retornamos un true
        // este metodo es obligatorio implementarlo por que extiende de la interfaz SearchView.OnQueryTextListener que configuramos al principio
        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        //La implementación de este metodo nos ayuda a que cada ves que el usuario hace click en el la lupa del teclado que se despliega (ya no aplica la busqueda cada ves que se genere un cambio
        // por teclado) se va a ejecutar este metodo. este metodo si nos interesa implementarlo y como en el caso anterior este metodo es obligatorio implementarlo
        // por que extiende de la interfaz SearchView.OnQueryTextListener que configuramos al principio
        if(!query.isNullOrEmpty()){ //Si el texto que ha escrito el usuario no (!) es vacio ni nulo entonces
            seachByKeyWords(query.lowercase())
        }
        return true
    }

    private fun ocultarKeyboard() {
        //Esta función oculta el teclado despues de que se usa el searchViewImgs
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        val contenedorRecyclerViewImgsApolo = findViewById<ConstraintLayout>(R.id.contenedorRecyclerViewImgsApolo)
        imm.hideSoftInputFromWindow(contenedorRecyclerViewImgsApolo.windowToken, 0)
    }
}
