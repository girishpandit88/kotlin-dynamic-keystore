package sample

import com.gp.controllers.GreetingController
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan(basePackageClasses = arrayOf(GreetingController::class))
open class MyApp {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(MyApp::class.java, *args)
        }
    }
}
