import com.typesafe.config._

object Properties {
    private val conf = ConfigFactory.load()
    val inputPath = conf.getString("configs.input-path")
    val outputPath = conf.getString("configs.output-path")
    val cutOffPoint = conf.getInt("configs.cut-off-point")
}