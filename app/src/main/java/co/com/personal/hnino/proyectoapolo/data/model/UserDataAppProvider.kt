package co.com.personal.hnino.proyectoapolo.data.model

import co.com.personal.hnino.proyectoapolo.ProyectoApoloApp

class UserDataAppProvider: ProyectoApoloApp() { // Esta clase extiende de ProyectoApoloApp y esta ultima extiende de Application(),
    // lo que significa que contamos con el contexto general de la aplicacion por eso podemos usar applicationContext dentro de  la
    // funcion onCreate(). Una clase de tipo Application es una clase que extiende de la propia aplicacion de
    //Android, es decir que son clases que son las primeras en llamarsen cuando se inicia la aplicación, y para ques sea así, configuramos
    // esta clase en AndoridManifest.xml en la linea android:name="co.com.personal.hnino.proyectoapolo.data.model.UserDataAppProvider"
    // lo que significa que tan pronto se inicie la aplicacion tambien inicia esta clase, lo que creará el objeto prefereciasUsuario
    // ((con el fin de acceder a la data guardada con las share preferences y validar si el usuario ya se encuentra registrado
    // manteniendo la sesión activa)) y tambien inicia la clase ProyectoApoloApp(), ya que la clase UserDataAppProvider extiende de la
    // misma y habilitará las funcionalidades de Dagger Hilt para inyección de dependencias.

    companion object{ // lo que pongamos dentro de este bloque, va a poder ser accedito desde cualquier parte de la app sin
        // necesidad de ser instanciado, es como las clases, variables u objetos que creamos en Java como static

        lateinit var prefereciasUsuario: PreferenciasUser // creamos la variable prefereciasUsuario de tipo PreferenciasUser y usamos companion object para
        // dejarla accesible a traves de toda la aplicación (como si fuera un static de java). ------ Para poder instanciar normalmente un objeto de tipo
        // PreferenciasUser, necesitamos pasarle el contexto como parametro del contructor de esa clase y como aqui no podemos acceder al contexto,
        // entonces lo hacemos dentro del onCreate() donde si podemos acceder al contexto... y por ello es que declaramos esta variable como lateinit donde
        // le estamos diciendo que esta variable se va a iniciar mas tarde que seria en el onCreate()
    }

    override fun onCreate() { //estamos sobreescribiendo el metodo onCreate que es util para decirle que => cuando se cree un objeto ó instancia de onCreate
        // se debe ejecutar este bloque de codigo, lo que nos es util para obtener las preferencias del usuario que en su momento se guardaron localmente
        // con shsrePreference
        super.onCreate()
        prefereciasUsuario = PreferenciasUser(applicationContext) // Aqui si podemos acceder al contexto, por lo que aqui si podemos terminar de instanciar la
        //variable prefereciasUsuario  de tipo PreferenciasUser
    }
}

