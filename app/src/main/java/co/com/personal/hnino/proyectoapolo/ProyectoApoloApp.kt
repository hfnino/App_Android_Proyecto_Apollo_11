package co.com.personal.hnino.proyectoapolo

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp // Esta etiqueta por detras, nos genera el codigo necesario para usar Dagger Hilt para inyección de dependencias. La idea de implementar
//Inyección de dependencias es que dentro de una clase, no se tenga ninguna instacia de ninguna clase, ya que todas deberian ser inyectadas, La inyeción de
//dependencias es un conyunto de tecnicas para reducir el acomplamiento de las clases.
open class ProyectoApoloApp: Application() { // Esta clase extiende de Application(), lo que significa que contamos con el contexto
    // general de la aplicacion si la necesitaramos. Una clase de tipo Application es una que extiende de la propia aplicacion de
    //Android, por lo que  normalmente son las primeras en llamarsen cuando se inicia la aplicación, y para ques sea así, configuramos
    // esta clase en AndoridManifest.xml en la linea android:name="co.com.personal.hnino.proyectoapolo.data.model.UserDataAppProvider"
    // lo que significa que tan pronto se inicie la aplicacion tambien inicia la clase UserDataAppProvider, lo que creará el objeto prefereciasUsuario
    // ((con el fin de acceder a la data guardada con las share preferences y validar si el usuario ya se encuentra registrado
    // manteniendo la sesión activa)) y tambien inicia la clase ProyectoApoloApp(), ya que la clase UserDataAppProvider extiende de la
    // misma y habilitará las funcionalidades de Dagger Hilt para inyección de dependencias.
}