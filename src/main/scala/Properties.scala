import com.typesafe.config._

object Properties {
  private val conf = ConfigFactory.load()
  private val projectPath = os.pwd.toString
  val inputPath: String = projectPath + "\\" + conf.getString("configs.input-path")
  val outputPath: String = projectPath + "\\" + conf.getString("configs.output-path")
  val cutOffPoint: Int = conf.getInt("configs.cut-off-point")
}
