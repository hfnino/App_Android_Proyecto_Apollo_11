package co.com.personal.hnino.proyectoapolo.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import co.com.personal.hnino.proyectoapolo.R
import co.com.personal.hnino.proyectoapolo.ui.viewmodel.ItemsImgsApoloViewModel
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetallesItemImageApolo : AppCompatActivity() {

    private val itemsImgsApoloViewModel : ItemsImgsApoloViewModel by viewModels()// hacemos la conexion y logica de nuestra viewModel
    // a  la nuestra vista (activity y/o Fragment), incluyendo el ciclo de vida y demas, ya no tenemos que hacer absolutamente nada

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalles_item_image_apolo)
        initUI()
    }

    private fun initUI() {

        var textViewTituloImg = findViewById<TextView>(R.id.textViewTituloImg)
        var imageViewPhoto = findViewById<ImageView>(R.id.imageViewPhoto)
        var tvInfoDetalles = findViewById<TextView>(R.id.tvInfoDetalles)
        var imageBtnFavorito = findViewById<ImageButton>(R.id.imageBtnFavorito)
        var switch1 = findViewById<Switch>(R.id.switch1)
        var btnRegresar = findViewById<Button>(R.id.btnRegresar)

        itemsImgsApoloViewModel.getItemDatosImagenCurrent()

        itemsImgsApoloViewModel.itemDatosImagenModel.observe(this, Observer { itemInfoImagenActual ->
            // La fucion observe es propia de la variable itemDatosImagenModel debido a que la declaramos como MutableLiveData<ItemDatosImage>()
            // en la clase DataTemporalViewModel y recibe 2 parametros, el owner que es el contexto actual, es decir "this", y el
            // segundo es la funcion Observer donde lo que exista dentro de su bloque de codigo va a estar enganchado al nuestro
            //liveData y cuando nuestro LiveData tenga un cambio, en este caso un nuevo objeto de tipo ItemDatosImage, entonces se va a
            //ejecuar lo existente en este bloque de codigo.---------- itemInfoImagenActual simplemente es un nombre cualquera que
            //pusimos para no isar "it" y hacerlo un poco mas entendible, pero se podria poner cualquiera otro nombre o en su defecto
            // usar "it"

            textViewTituloImg.text= itemInfoImagenActual.data?.get(0)?.title

            Picasso.get().load(itemInfoImagenActual.links?.get(0)?.href) // cargamos la imagen deseada dentro de load(), que para el
                //ejemplo es el valor dado al atributo photo del objeto imgsApolo que recibimos como parametro. como esta Url por defecto es
                //un recurso existente en internet, para que funcione, se le debe dar permisos a la aplicación
                //para que pueda acceder a internet y este permiso lo configuramos en el manifiesto del proyecto cuand
                //agregamos la linea <uses-permission android:name="android.permission.INTERNET"></uses-permission>
                .placeholder(R.drawable.error_url_img) //el sistema muestra esta imagen mientras carga la imagen correspondiente y se mantiene si
                //la imagen correspondien no se puede consutar por x o y motivo.
                .error(R.drawable.error_url_img) //si se presenta un error, el sistema muestra esta imagen.
                //.resize(180, 200) // Esta linea podemos omitira, pero si la implementamos, entoncnes con esta propiedad le decimos que la imagen
                // sera capturada con las dimensiones especificadas como la escala 20 x 20 es muy pequeña para este ejemplo, el sistema luego estira la imagen
                // y la ajusta al tamaño del view y por ese motivo se despixela la imagen y se ve borrosa.   si le ponemos por
                // ejemplo 200 x 200, la imagen es capturada con esas dimensiones, y en este caso el sistema la reajusta al tamaño del view,
                // como la imagen se capturo mas grande en una mejor calidad, en el proceso de ajuste la imagen ya no se estira, si no se reduce y no
                //se despixela.
                .into(imageViewPhoto) // dentro del .into() le ponemos imagenProducto que es la cariable declarada anteriormente
            // que esta asociada al elemento tipo imagView existente en el layout activity_item_lista_personalizada
            // y que tiene el id imageViewItem

            tvInfoDetalles.text= itemInfoImagenActual.data?.get(0)?.description

            if(itemInfoImagenActual.data?.get(0)?.imgFavorita == true){
                switch1.isChecked = true
                imageBtnFavorito.setImageDrawable(ContextCompat.getDrawable(imageBtnFavorito.context,
                    R.drawable.ic_baseline_star_24
                ))
            }else{
                switch1.isChecked = false
                imageBtnFavorito.setImageDrawable(ContextCompat.getDrawable(imageBtnFavorito.context,android.R.drawable.btn_star_big_off))
            }

            switch1.setOnClickListener{
                if(switch1.isChecked){

                    imageBtnFavorito.setImageDrawable(ContextCompat.getDrawable(imageBtnFavorito.context,android.R.drawable.btn_star_big_on))
                    itemsImgsApoloViewModel.saveItemDatosImagenFavorita(itemInfoImagenActual)
                    Toast.makeText(switch1.context, "El Item ahora hace parte de tus Favoritos (switch)", Toast.LENGTH_SHORT).show()
                }else{
                    imageBtnFavorito.setImageDrawable(ContextCompat.getDrawable(imageBtnFavorito.context,android.R.drawable.btn_star_big_off))
                    itemsImgsApoloViewModel.deleteItemDatosImagenFavorita(itemInfoImagenActual)
                    Toast.makeText(switch1.context, "El Item ha sido borrado de tus favoritos (switch)", Toast.LENGTH_SHORT).show()
                }
            }
        })

        btnRegresar.setOnClickListener {
            val intent = Intent(this, RecyclerViewImgsApoloActivity::class.java)
            startActivity(intent)
            //onBackPressed() //Retorna a la Actividad y vista anterior
            finish() // Finaliza la activity RecyclerViewApoloFavoritosActivity
        }

    }
}