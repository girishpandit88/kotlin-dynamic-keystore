package sample

import com.gp.access.KeystoreAccess
import com.gp.access.model.KeystoreDao
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@SpringBootApplication
@Configuration
@ComponentScan
@EnableAutoConfiguration
open class MyApp {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val applicationContext = AnnotationConfigApplicationContext("com.gp.access")
            val keystoreAccess = applicationContext.getBean(KeystoreAccess::class.java)
            keystoreAccess.setConfig(mutableListOf(KeystoreDao("test", "foo", "bar")))
            println(keystoreAccess.getConfig("test", "foo"))
            SpringApplication.run(MyApp::class.java, *args)
        }
    }
}
