package co.com.personal.hnino.proyectoapolo.ui.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import co.com.personal.hnino.proyectoapolo.R
import co.com.personal.hnino.proyectoapolo.ui.viewmodel.UserDataAppViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PerfilDeUsuarioActivity : AppCompatActivity() {

    private val userDataAppViewModel : UserDataAppViewModel by viewModels()// hacemos la conexion y logica de nuestra viewModel
    // a  la nuestra vista (activity y/o Fragment), incluyendo el ciclo de vida y demas, ya no tenemos que hacer absolutamente nada

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil_de_usuario)
        initUI()
    }

    private fun initUI() {
        val imageViewPhotoUser = findViewById<ImageView>(R.id.imageViewPhotoUser)
        val textViewIdUsuario = findViewById<TextView>(R.id.textViewIdUsuario)
        val textViewNombreCompleto = findViewById<TextView>(R.id.textViewNombreCompleto)
        val textViewNumIdentificacion = findViewById<TextView>(R.id.textViewNumIdentificacion)
        val textViewCorreoE = findViewById<TextView>(R.id.textViewCorreoE)
        val textViewEstadoUsuario = findViewById<TextView>(R.id.textViewEstadoUsuario)
        val btnRegresarEnPerfilUser = findViewById<Button>(R.id.btnRegresarEnPerfilUser)

        userDataAppViewModel.getPrefereciasUsuarioCurrent()

        userDataAppViewModel.prefereciasUsuarioViewModel.observe(this, Observer {

            textViewIdUsuario.text = "ID de Usuario: N/A"
            textViewNombreCompleto.text = "Nombre Completo: ${it.getName()}"
            textViewNumIdentificacion.text = "Numero de Identificación: N/A"
            textViewCorreoE.text = "Correo Electrónico: ${it.getCorreoE()}"
            textViewEstadoUsuario.text = "Estado de Usuario: N/A"
        })

        btnRegresarEnPerfilUser.setOnClickListener { volver() }

    }

    private fun volver() {
        val intent = Intent(this, RecyclerViewImgsApoloActivity::class.java)
        startActivity(intent)
        //onBackPressed() //Retorna a la Actividad y vista anterior
        finish() // Finaliza la activity RecyclerViewApoloFavoritosActivity
    }
}