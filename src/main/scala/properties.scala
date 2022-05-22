import com.typesafe.config._

object Properties {
    private val conf = ConfigFactory.load()
    private val projectPath = os.pwd.toString
    val inputPath = projectPath + "\\" + conf.getString("configs.input-path")
    val outputPath = projectPath + "\\" + conf.getString("configs.output-path")
    val cutOffPoint = conf.getInt("configs.cut-off-point")
}