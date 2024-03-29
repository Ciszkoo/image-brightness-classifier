import com.typesafe.config.*

object Properties {
  private val conf        = ConfigFactory.load()
  private val projectPath = os.pwd.toString
  val fileSeparator       = java.io.File.separator
  val inputPath: String   = projectPath + fileSeparator + conf.getString("configs.input-dir")
  val outputPath: String  = projectPath + fileSeparator + conf.getString("configs.output-dir")
  val cutOffPoint: Int    = conf.getInt("configs.cut-off-point")
}
