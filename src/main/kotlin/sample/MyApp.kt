package sample

import com.gp.access.KeystoreAccess
import com.gp.access.model.KeystoreDao
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan(basePackages = arrayOf("com.gp"))
open class MyApp {

	@Bean open fun init(applicationContext: ApplicationContext) = CommandLineRunner {

		val keystoreAccess = applicationContext.getBean(KeystoreAccess::class.java)
		keystoreAccess.setConfig(mutableListOf(KeystoreDao("test", "foo", "bar")))
		println(keystoreAccess.getConfig("test", "foo"))

	}

	companion object {
		@JvmStatic
		fun main(args: Array<String>) {
			SpringApplication.run(MyApp::class.java, *args)
		}
	}
}
