package co.com.personal.hnino.proyectoapolo.ui.view.adaptadores

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.com.personal.hnino.proyectoapolo.R
import co.com.personal.hnino.proyectoapolo.domain.SaveItemImgApoloUseCase
import co.com.personal.hnino.proyectoapolo.domain.model.ItemDatosImage

class ImgsApoloAdapterRecycler(private var listaItemsImgsApolo:List<ItemDatosImage>,
                               private val onClickListenerItemDatosImage1: (ItemDatosImage) -> Unit,
                               private val onClickListenerItemDatosImage2: (ItemDatosImage) -> Unit) : RecyclerView.Adapter<ItemImageApoloViewHolder>(){
    //Esta clase Adapter, extiende de RecyclerView.Adapter, por lo que es necesario implementar los 3 metodos abtractos siguientes y
    // estos metodos nos permiten usar la data el listado de objetos de tipo ImgsApolo que recibimos como argumentos
    // deImgsApoloAdapterRecycler para luego mostrar toda esa info en el RecyclerView haciendo uso del ViewHolde que
    // llamamos ImgsApoloViewHolder

    //private val onClickListener:(ImgsApolo) -> Unit ------ Es una función lamda que la usamos para enviarla a al ViewHolder y ImgsApolo
    // es el tipo de objeto que queremos transportar

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemImageApoloViewHolder {
        //Esta función retorna un objeto de tipo ViewHolder "ItemImageApoloViewHolder" ladata de los objetos contenidos en el array listaItemsImgsApolo
        // para que luego el ViewHolder "ItemImageApoloViewHolder" muestre al usuario la información de cada objeto haciendo uso del
        // layout que creamos llamado activity_item_recycler_view_imgsa_polo

        val layoutInflater = LayoutInflater.from(parent.context) // Cuanto no estamos en una Activity, podemos capturar el contexto
        // por medio de cualquiera de las vistas las cuales tenemos a dispoción a traves del argumento recibido llamado "parent",
        // que es de tipo ViewGroup que es una vista de Android, por tal motivo es que  ponemos parent.context
        return ItemImageApoloViewHolder(layoutInflater.inflate(R.layout.activity_item_recycler_view_imgs_apolo, parent, false))
    }

    override fun onBindViewHolder(holder: ItemImageApoloViewHolder, position: Int) {
        //Este metodo tiene como función asociar los datos dinamicos del objeto correspondiente a los elementos de la
     // vista layout activity_item_recycler_view_imgsa_polo.xml y esos datos son los que se van a mostrar al usuario.

        println("=====================> El titulo es => " + listaItemsImgsApolo.get(position).data?.get(0)?.title) // Prueba de Escritorio

        var item = listaItemsImgsApolo.get(position)

        holder.render(item, /*saveItemImgApoloUseCase,*/ onClickListenerItemDatosImage1, onClickListenerItemDatosImage2) //Usamos la funcion render que creamos
                    // en la clase ItemImageApoloViewHolder y le enviamos como parametro el item que es el objeto existente en la posisión correspondiente y el
                    // onClickListener que recibimos como argumento al principio de esta misma clase

    }

    override fun getItemCount(): Int {
        println("===================> La lista contiene " + listaItemsImgsApolo.size + " para mostrar") // Prueba de Escritorio
        return listaItemsImgsApolo.size //retornal el tamaño del array y este numero es el que usa el sistema para mostrar
        //la cantidad de elementos en el recyclerView que visualizara el usuario
    }
}