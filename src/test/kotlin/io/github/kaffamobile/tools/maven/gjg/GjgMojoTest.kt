package io.github.kaffamobile.tools.maven.gjg

import org.junit.jupiter.api.io.TempDir
import java.nio.file.Path
import java.util.Properties
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GjgMojoTest {
    @Test
    fun `should generate exe and conf`(@TempDir tempDir: Path) {
        val outputExe = tempDir.resolve("myapp.exe").toFile()
        val mojo = GjgMojo().apply {
            this.baseDir = "./"
            this.outputFile = outputExe.absolutePath
            this.javaDir = "./jre"
            this.jarFile = "./myapp.jar"
            this.jvmArgs = listOf("-Xmx128m", "-Despresso.library.path=./libs")
            this.appArgs = listOf("--foo", "bar", "--bar", "foo")
            this.env = mapOf("TEST_ENV" to "123", "TEST_ENV2" to "321")
            this.icon = "test-ico.ico"
            this.fileDescription = "My test app"
            this.productName = "TestApp"
            this.productVersion = "1.0.0"
            this.companyName = "Kaffa Mobile"
            this.copyright = "© Kaffa Mobile"
            this.arch = "amd64"
        }

        mojo.execute()

        assertTrue(outputExe.exists(), "Exe should be generated at ${outputExe.absolutePath}")

        val confFile = tempDir.resolve("myapp.gjg.conf").toFile()
        assertTrue(confFile.exists(), "Conf file should be generated")

        val props = Properties().apply { confFile.inputStream().use { load(it) } }

        assertEquals("./jre", props.getProperty("java_dir"))
        assertEquals("./myapp.jar", props.getProperty("jar_file"))
        assertEquals("-Xmx128m -Despresso.library.path=./libs", props.getProperty("jvm_args"))
        assertEquals("--foo bar --bar foo", props.getProperty("app_args"))
        assertEquals("123", props.getProperty("env_TEST_ENV"))
        assertEquals("321", props.getProperty("env_TEST_ENV2"))
    }
}
