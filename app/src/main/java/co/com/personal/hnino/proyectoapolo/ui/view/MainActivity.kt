package co.com.personal.hnino.proyectoapolo.ui.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import co.com.personal.hnino.proyectoapolo.databinding.ActivityMainBinding
import co.com.personal.hnino.proyectoapolo.ui.viewmodel.ItemsImgsApoloViewModel
import co.com.personal.hnino.proyectoapolo.ui.viewmodel.UserDataAppViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding //Implementacion de view bindig

    private val userDataAppViewModel : UserDataAppViewModel by viewModels()// hacemos la conexion y logica de nuestra viewModel
    // a  la de nuestra vista (activity y/o Fragment), incluyendo el ciclo de vida y demas, ya no tenemos que hacer absolutamente nada

    private val itemsImgsApoloViewModel : ItemsImgsApoloViewModel by viewModels() // hacemos la conexion y logica de nuestra viewModel
    // a  la de nuestra vista (activity y/o Fragment), incluyendo el ciclo de vida y demas, ya no tenemos que hacer absolutamente nada

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main) // No aplica debido a que se implemento view binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root) //Implementacion de view bindig

        initUI() // si no hacemos click en el boton Ingresar, no hace nada y pasa a ejecutar la funcion //validarPreferenciasUsiario()
        validarPreferenciasUsuario()
        print("Prueba de cambios git")
    }

    fun validarPreferenciasUsuario(){

                userDataAppViewModel.getPrefereciasUsuarioCurrent()

        userDataAppViewModel.prefereciasUsuarioViewModel.observe(this, Observer { userDataAppActual ->
            // La fucion observe es propia de la variable prefereciasUsuarioViewModel debido a que la declaramos como MutableLiveData<PreferenciasUser>()
            // en la clase UserDataAppViewModel y recibe 2 parametros, el owner que es el contexto actual, es decir "this", y el
            // segundo es la funcion Observer donde lo que exista dentro de su bloque de codigo va a estar enganchado al nuestro
            //liveData y cuando nuestro LiveData tenga un cambio, en este caso un nuevo objeto de tipo PreferenciasUser, entonces se va a
            //ejecuar lo existente en este bloque de codigo.---------- userDataAppActial simplemente es un nombre cualquera que
            //pusimos para no usar "it" y hacerlo un poco mas entendible, pero se podria poner cualquiera otro nombre o en su defecto
            // usar "it"

            if(userDataAppActual.getName().isNotEmpty() &&
                userDataAppActual.getCorreoE().isNotEmpty() && userDataAppActual.getEstadoSesion() == true
            ){ // Si esxiste información del usuario guardada localmente con las Share Preference, entonces pasamos directamente a la siguiente
                //Activity y no se solicita datos de login
                iniciarRecyclerViewImgsApoloActivity()
            }
            else{
                itemsImgsApoloViewModel.deleteAllFromDataBase() //Si no esxiste información del usuario guardada localmente con las Share Preference,
                // entonces eliminamos toda la información de la base de dato en caso de que halla para iniciar una nueva sesión limpia.
            }
        })
    }

    fun initUI() {
        binding.btnIngresar.setOnClickListener { botonIngresar() } // si no hacemos click en el boton Ingresar, no hace nada y retorna a donde esta funcion fue
        //llamada
    }

    fun botonIngresar(){

        if(binding.editTextNombreCompleto.text.isNotEmpty() && binding.editTextCorreoE.text.isNotEmpty()){


            userDataAppViewModel.setPrefereciasUsuarioNewName(binding.editTextNombreCompleto.text.toString())
            userDataAppViewModel.setPrefereciasUsuarioNewCorreE(binding.editTextCorreoE.text.toString())

            if(binding.checkboxEstadoSesion.isChecked) {
                userDataAppViewModel.setPrefereciasUsuarioSesionActiva(binding.checkboxEstadoSesion.isChecked)
            }

            iniciarRecyclerViewImgsApoloActivity()

        }else{
            Toast.makeText(this, "Debe ingresar su nombre completo y/o correo electrónico para poder acceder!", Toast.LENGTH_SHORT).show()
        }
    }

    fun iniciarRecyclerViewImgsApoloActivity(){

        val intent = Intent(this, RecyclerViewImgsApoloActivity::class.java)
        intent.apply {
            putExtra("dataNombreCompleto", binding.editTextNombreCompleto.text.toString())
            putExtra("dataCorreoE", binding.editTextCorreoE.text.toString())
        }
        // Los intent.putExtra anteriores no eran necesarios ya que los datos del usuario se estan guardan  cuando se
        // usa la variable userDataAppViewModel, sin embargo solo se implemntaron para tener un ejemplo
        //de envio de datos entre actividades y para hacer pruebas de UI con esta intent
        startActivity(intent)
        Toast.makeText(this, "Has ingresado correctamente =)", Toast.LENGTH_SHORT).show()
    }

}