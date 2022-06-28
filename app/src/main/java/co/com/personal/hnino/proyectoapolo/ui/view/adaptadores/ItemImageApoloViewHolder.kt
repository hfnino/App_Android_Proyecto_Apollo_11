package co.com.personal.hnino.proyectoapolo.ui.view.adaptadores

import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import co.com.personal.hnino.proyectoapolo.R
import co.com.personal.hnino.proyectoapolo.data.model.UserDataAppProvider
import co.com.personal.hnino.proyectoapolo.databinding.ActivityItemRecyclerViewImgsApoloBinding
import co.com.personal.hnino.proyectoapolo.domain.SaveItemImgApoloUseCase
import co.com.personal.hnino.proyectoapolo.domain.model.ItemDatosImage
import co.com.personal.hnino.proyectoapolo.ui.viewmodel.ItemsImgsApoloViewModel
import com.squareup.picasso.Picasso

class ItemImageApoloViewHolder(view:View):RecyclerView.ViewHolder(view) {
    //El ViewHolder "ImgsApoloViewHolder" nos ayuda a mostrar al usuario la información de cada objeto haciendo uso del
    // layout que creamos llamado activity_item_recycler_view_imgsa_polo

    private val binding = ActivityItemRecyclerViewImgsApoloBinding.bind(view) //implementamos el View Bindind
    // ActivityItemRecyclerViewImgsApoloBinding es el nombre que relaciona directamente al layauto activity_item_recycler_view_imgs_apolo.xml

    fun render (itemDatosImage: ItemDatosImage,
                onClickListenerItemDatosImage1:(ItemDatosImage) -> Unit,
                onClickListenerItemDatosImage2:(ItemDatosImage) -> Unit){

        if(UserDataAppProvider.prefereciasUsuario.getEstadoSesion()) {
            //Si el usuario ya habia iniciado sesión y tenia data guardada localmente con SharePreference entonces cambiamos el color de fondo
            // del item / cardView / contrainLayout
            binding.contenedorItem.setBackgroundColor(ContextCompat.getColor(binding.contenedorItem.context, R.color.azul_cielo))
        }

        binding.textViewTituloImgs.text = itemDatosImage.data?.get(0)?.title
        //textViewRealName.text = imgsApolo.nombreReal
        //textViewPublisher.text = imgsApolo.publisher
        Picasso.get().load(itemDatosImage.links?.get(0)?.href) // cargamos la imagen deseada dentro de load(), que para el
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
            .into(binding.imageViewPhoto) // dentro del .into() le ponemos imagenProducto que es la cariable declarada anteriormente
        // que esta asociada al elemento tipo imagView existente en el layout activity_item_lista_personalizada
        // y que tiene el id imageViewItem

        println("============Img Marcada como  Favorita ? ============> " + itemDatosImage.data?.get(0)?.imgFavorita.toString()) // Prueba de escritorio
        println("============ El Item a pintar es => " + itemDatosImage.toString())

        // Ojo.. Este validación es muy importante, debido a que las listas (Listviews y Recyclerviews) utilizan un sistema de "cacheo" de vistas
        // o reciclado y reutiliza las vistas que ya están en memoria para no tener que estar creándolas constantemente cuando se hace scroll. Lo anterior
        // lo anterior, implica que cuando usamos un chexkbox de un item de la lista, y luego hacemos scroll, nos vamos a encontrar con otro item
        // que tambien va a estar marcado con el checkbox sin realmente haberlo marcado. Para que lo anterior no suceda, es importante
        // obligar a marcar el checkbox como checkeado si es el caso o como no checkeado si no lo debe de estar, tal cual como lo validamos a continuación
        // usando el If - else.

        if(itemDatosImage.data?.get(0)?.imgFavorita == true){
            binding.checkboxFavorito.isChecked = true
            binding.imageButtonFavorito.setImageDrawable(ContextCompat.getDrawable(binding.imageButtonFavorito.context,android.R.drawable.btn_star_big_on))
        }else{
            binding.checkboxFavorito.isChecked = false
            binding.imageButtonFavorito.setImageDrawable(ContextCompat.getDrawable(binding.imageButtonFavorito.context,android.R.drawable.btn_star_big_off))
        }

        binding.textViewIdImg.text = "ID = " + itemDatosImage.data?.get(0)?.nasa_id

        binding.textViewTituloImgs.setOnClickListener {
            Toast.makeText(binding.textViewTituloImgs.context, itemDatosImage.data?.get(0)?.title, Toast.LENGTH_SHORT).show()
        }

        binding.textViewDescripcion.setOnClickListener {
            Toast.makeText(binding.textViewDescripcion.context, itemDatosImage.data?.get(0)?.description, Toast.LENGTH_SHORT).show()
        }

        itemView.setOnClickListener { //Escucha de los click cuando se hace click en cualquier parte de la vista
            onClickListenerItemDatosImage1(itemDatosImage) // Retorna el objeto seleccionado y lo transporta hacia el archivo RecyclerViewImgsApolo.kt
            // a la funcion itemseleccionado1( ---------- )
        }

        binding.checkboxFavorito.setOnClickListener{

            if(binding.checkboxFavorito.isChecked){
                //UserDataApplication.preferenciasImgsApolo.saveIdImg(imgsApolo.id)
                //UserDataApplication.preferenciasImgsApolo.saveTituloImg(imgsApolo.tituloImgsApolo)
                //UserDataApplication.preferenciasImgsApolo.saveImgFavorita(binding.checkboxFavorito.isChecked)
                itemDatosImage.data!!.get(0).imgFavorita = true // le asignamos el valor true para saber que deberemos guardar este item en la base de datos local con Room
                binding.imageButtonFavorito.setImageDrawable(ContextCompat.getDrawable(binding.imageButtonFavorito.context,android.R.drawable.btn_star_big_on))
                onClickListenerItemDatosImage2(itemDatosImage) // Retorna el objeto marcado como favorito y lo transporta hacia el archivo RecyclerViewImgsApolo.kt
                // a la funcion itemseleccionado2( ---------- )
            }else{
                itemDatosImage.data!!.get(0).imgFavorita = false // le asignamos el valor false para saber que deberemos elimiar este item en la base de datos local con Room
                binding.imageButtonFavorito.setImageDrawable(ContextCompat.getDrawable(binding.imageButtonFavorito.context,android.R.drawable.btn_star_big_off))
                onClickListenerItemDatosImage2(itemDatosImage) // Retorna el objeto desmarcado como favorito y lo transporta hacia el archivo RecyclerViewImgsApolo.kt
                // a la funcion itemseleccionado2( ---------- )
            }
        }

        binding.textViewIdImg.setOnClickListener {
            Toast.makeText(binding.textViewTituloImgs.context, itemDatosImage.data?.get(0)?.nasa_id, Toast.LENGTH_SHORT).show()
        }
    }

    private fun onClickListenerItemDatosImage1(itemDatosImage: ItemDatosImage) {
    }

    private fun onClickListenerItemDatosImage2(itemDatosImage: ItemDatosImage) {

    }
}