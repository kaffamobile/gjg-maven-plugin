package io.github.kaffamobile.tools.maven.gjg

import org.apache.maven.plugin.AbstractMojo
import org.apache.maven.plugins.annotations.Mojo
import org.apache.maven.plugins.annotations.Parameter
import java.io.File

@Mojo(name = "package", threadSafe = true)
class GjgMojo() : AbstractMojo() {
    @Parameter(defaultValue = $$"${project.build.directory}", required = true)
    lateinit var baseDir: String

    @Parameter(required = true)
    lateinit var outputFile: String

    @Parameter
    var jarFile: String? = null

    @Parameter
    var javaDir: String? = null

    @Parameter
    var jvmArgs: List<String>? = null

    @Parameter
    var appArgs: List<String>? = null

    @Parameter
    var env: Map<String, String>? = null

    @Parameter
    var icon: String? = null

    @Parameter
    var fileDescription: String? = null

    @Parameter
    var productName: String? = null

    @Parameter
    var productVersion: String? = null

    @Parameter
    var companyName: String? = null

    @Parameter
    var copyright: String? = null

    @Parameter
    var arch: String? = null

    override fun execute() {
        log.info("GJG Plugin started...")
        GjgHandler(
            baseDir = File(baseDir),
            log = log,
            arch = arch?.takeIf(String::isNotBlank) ?: "amd64",
            outputFile = outputFile,
            jarFile = jarFile?.takeIf(String::isNotBlank),
            javaDir = javaDir?.takeIf(String::isNotBlank),
            jvmArgs = jvmArgs ?: emptyList(),
            appArgs = appArgs ?: emptyList(),
            env = env ?: emptyMap(),
            icon = icon?.takeIf(String::isNotBlank),
            fileDescription = fileDescription?.takeIf(String::isNotBlank),
            productName = productName?.takeIf(String::isNotBlank),
            productVersion = productVersion?.takeIf(String::isNotBlank),
            companyName = companyName?.takeIf(String::isNotBlank),
            copyright = copyright?.takeIf(String::isNotBlank),
        ).run()
        log.info("✅ Executable built at: $outputFile")
    }
}
