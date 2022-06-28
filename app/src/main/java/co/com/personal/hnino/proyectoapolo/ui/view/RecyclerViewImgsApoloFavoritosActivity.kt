package co.com.personal.hnino.proyectoapolo.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.com.personal.hnino.proyectoapolo.R
import co.com.personal.hnino.proyectoapolo.domain.model.ItemDatosImage
import co.com.personal.hnino.proyectoapolo.ui.view.adaptadores.ImgsApoloAdapterRecycler
import co.com.personal.hnino.proyectoapolo.ui.viewmodel.ItemsImgsApoloViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecyclerViewImgsApoloFavoritosActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private lateinit  var listaItemsImgsApoloForRecyclerView:List<ItemDatosImage>
    private lateinit var adapter: ImgsApoloAdapterRecycler

    private val itemsImgsApoloViewModel : ItemsImgsApoloViewModel by viewModels() // hacemos la conexion y logica de nuestra viewModel
    // a  la de nuestra vista (activity y/o Fragment), incluyendo el ciclo de vida y demas, ya no tenemos que hacer absolutamente nada

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view_imgs_apolo_favoritos)

        initUI()

        val btnRegresarFav = findViewById<Button>(R.id.btnRegresarFav)
        btnRegresarFav.setOnClickListener { volver() }
    }

    fun initUI(){
        seachByKeyWords("")
    }

    private fun seachByKeyWords(queryByKeyWords:String){
        println("========== La Palabla clave para buscar Imagenes Favoritas es ==========> $queryByKeyWords") // Prueba de Escritorio
        var progressCircularFav = findViewById<ProgressBar>(R.id.progressCircularFav)
        var searchViewImgsFav = findViewById<SearchView>(R.id.searchViewImgsFav) // Al searchViewImgsFav le implementamos los metodos de escucha que creamos
            // asociados a la interfaz SearchView.OnQueryTextListener que implementamos al principio de esta clase para filtrar la lista de objetos de tipo
            // ItemDatosImage extraidas de la base de datos que implementamos con Room por las palabras clave que el usuario ingrese en el SearchViewImgsFav
            // del layout activity_recycler_view_imgs_apolo_favoritos que es el asociado a esta actividad
        searchViewImgsFav.setOnQueryTextListener(this@RecyclerViewImgsApoloFavoritosActivity) // usamos los metodos que extendimos de SearchView.OnQueryTextListener es decir
        // los metodos onQueryTextChange(newText: String?): Boolean {} y override fun onQueryTextSubmit(query: String?): Boolean {}

        itemsImgsApoloViewModel.obtenerListaItemsImgsApoloFavoritasSearch(queryByKeyWords)

        itemsImgsApoloViewModel.listaItemsImgsApoloMLD.observe(this@RecyclerViewImgsApoloFavoritosActivity, Observer { listaItemsImgsApolo ->

            listaItemsImgsApoloForRecyclerView = listaItemsImgsApolo

            println(" ===============>>>> la lista a mostrar en el Recycler View de objetos ItemDatosImage es     => ${listaItemsImgsApoloForRecyclerView.toString()}")

            if (listaItemsImgsApolo.isEmpty()) {
                Toast.makeText(
                    this@RecyclerViewImgsApoloFavoritosActivity,
                    "No se encontraron resultados para esta busqueda",
                    Toast.LENGTH_SHORT
                ).show()
            } else {

                iniciarReciclerView()
                adapter.notifyDataSetChanged() // con esta función le avisamos al adapter que ha habido cambios
                /*
                Toast.makeText(
                    this@RecyclerViewImgsApoloFavoritosActivity,
                    "Respuesta OK",
                    Toast.LENGTH_SHORT
                ).show()
                */
            }
            ocultarKeyboard()
        })

        itemsImgsApoloViewModel.isLoading.observe(this, Observer {

            progressCircularFav.isVisible = it // Muestra u oculta la barra circular de proceso
        })
    }

    private fun iniciarReciclerView(){

        val manager = LinearLayoutManager(this) // si cambiaramos LinearLayoutManager(this)
        // por GridLayoutManager(this, 2) nos permite visualizar 2 items en en una sola linea, y sien ves del 2
        // fuera un 3, veriamos 3 items en una sola linea
        var recyclerViewImgsApolo = findViewById<RecyclerView>(R.id.recyclerViewImgsApoloFav)
        recyclerViewImgsApolo.layoutManager = manager
        adapter = ImgsApoloAdapterRecycler(listaItemsImgsApoloForRecyclerView, { ItemDatosImage -> itemseleccionado1(ItemDatosImage)}, { ItemDatosImage2 -> itemseleccionado2(ItemDatosImage2)}) // Función Lammda
        recyclerViewImgsApolo.adapter = adapter // Al recyclerView
        //le estamos asignando el adaptador que creamos ImgsApoloAdapterRecycler y al que le enviamos como parametro la lista de objetos que
        //vamos a mostrar y tambien le enviamos la función lamda que nos ayudara a traer de vuelta a esta actividad "RecyclerViewImgsApoloActivity" el objeto(con toda su data)
        // que el usuario a seleccionado. tener en cuenta que las funciones lamda deben ir entre llaves y  no van dentro de parentesis de los argumentos,
        // por lo que la podemos sacar de los mismos
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
            Toast.makeText(this, " El Item ha sido eliminado de tus imagenes Favoritas", Toast.LENGTH_SHORT).show()

        //initUI()
        }
    }

    fun iniciarDetallesItemImageApolo(){

        val intent = Intent(this, DetallesItemImageApolo::class.java)
        startActivity(intent)
        Toast.makeText(this, "Detalles del item seleccionado =)", Toast.LENGTH_SHORT).show()
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        //La implementación de este metodo nos ayuda a que cada es que el usuario teclea o borra un caracter, es decir cada ves que se
        //genere un cambio en el texto del SearchView entonces se va a ejecutar este metodo. este metodo si nos interesa implementarlo
        // Aprovechando que la lista de Items a mostrar es corta por ser los Items Extraidos de la Base de datos local que creamos con
        // la libreria de Room. Este metodo es obligatorio implementarlo por que extiende de la interfaz SearchView.OnQueryTextListener
        // que configuramos al principio

        if(!newText.isNullOrEmpty()){ //Si el texto que ha escrito el usuario no (!) es vacio ni nulo entonces
            seachByKeyWords(newText.lowercase())
        }


        return true
    }
    override fun onQueryTextSubmit(query: String?): Boolean {
        //La implementación de este metodo nos ayuda a que cada ves que el usuario hace click en el la lupa del teclado que se despliega
        //  se va a ejecutar este metodo (ya no aplica la busqueda cada ves que se genere un cambio por teclado). pero para este caso no nos interesa
        //  implementarlo, por lo que retornamos un true. Este metodo es obligatorio implementarlo por que extiende de la interfaz
        //  SearchView.OnQueryTextListener que configuramos al principio

        if(!query.isNullOrEmpty()){ //Si el texto que ha escrito el usuario no (!) es vacio ni nulo entonces
            seachByKeyWords(query.lowercase())
        }
        return true
    }

    private fun volver() {
        val intent = Intent(this, RecyclerViewImgsApoloActivity::class.java)
        startActivity(intent)
        //onBackPressed() //Retorna a la Actividad y vista anterior
        finish() // Finaliza la activity RecyclerViewApoloFavoritosActivity
    }

    private fun ocultarKeyboard() {
        //Esta función oculta el teclado despues de que se usa el searchViewImgsFav
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        val contenedorRecyclerViewImgsApoloFav = findViewById<ConstraintLayout>(R.id.contenedorRecyclerViewImgsApoloFav)
        imm.hideSoftInputFromWindow(contenedorRecyclerViewImgsApoloFav.windowToken, 0)
    }
}

