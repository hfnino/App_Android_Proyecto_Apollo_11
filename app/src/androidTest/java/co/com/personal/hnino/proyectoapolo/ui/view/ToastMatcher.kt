package co.com.personal.hnino.proyectoapolo.ui.view

import android.os.IBinder
import android.view.WindowManager
import androidx.test.espresso.Root
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher

class ToastMatcher : TypeSafeMatcher<Root>()  { // Esta clase es para crear un Matcher personalizado para
    // hacer testig a un Toast

    override fun describeTo(description: Description?) { // Este metodo lo sobreescribimos ya que pertenece
        // a la clase TypeSafeMatcher<Root>() de la que extendimos
        description?.appendText("==========> is toast")
    }

    override fun matchesSafely(item: Root?): Boolean { // Este metodo lo sobreescribimos ya que pertenece
        // a la clase TypeSafeMatcher<Root>() de la que extendimos
        var type: Int? = item?.getWindowLayoutParams()?.get()?.type
        if((type == WindowManager.LayoutParams.TYPE_TOAST)){
            var windowToken : IBinder? = item?.getDecorView()?.getWindowToken()
            var appToken : IBinder? = item?.getDecorView()?.getApplicationWindowToken()
            return windowToken == appToken
        }
        return false
    }
}



