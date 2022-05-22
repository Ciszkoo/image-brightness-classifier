import Properties._
import java.io.File
import javax.imageio.ImageIO
import java.awt.image.BufferedImage
import java.awt.Color
import scala.collection.parallel.CollectionConverters._
import scala.collection.parallel.ParSeq

case class Files(path: String)

object Files:
    def photosToEvaluate(obj: Files): ParSeq[File] =
        val dir = File(obj.path)
        dir.listFiles.toSeq.par

        
trait PhotoEvaluatingInterface:
    def readPhoto(img: File): BufferedImage
    def savePhoto(img: File, lightness: Int): Unit
    def listOfPixels(img: BufferedImage): ParSeq[Int]
    def listOfLightness(pixels: ParSeq[Int]): ParSeq[Double]
    def avgLightness(pixels: ParSeq[Double]): Int
    def evaluate(img: File): Unit


object PhotoEvaluating extends PhotoEvaluatingInterface:
    def readPhoto(img: File): BufferedImage =
        ImageIO.read(img)

    def savePhoto(img: File, lightness: Int): Unit =
        val fileName = img.toString.split("\\\\").last
        val newName = if (lightness < cutOffPoint) {
            s"${fileName.split("\\.").head}_bright_${lightness}.${fileName.split("\\.").last}"
        } else {
            s"${fileName.split("\\.").head}_dark_${lightness}.${fileName.split("\\.").last}"
        }
        os.copy(
            os.Path(img.toString),
            os.Path(s"${outputPath}/${newName}")
        )
    
    def listOfPixels(img: BufferedImage): ParSeq[Int] =
        img.getRGB(0, 0, img.getWidth(), img.getHeight(), null, 0, img.getWidth()).toSeq.par

    def listOfLightness(pixels: ParSeq[Int]): ParSeq[Double] =
        pixels.map(pixel => {
            val c = Color(pixel)
            val red = c.getRed().toDouble / 255
            val green = c.getGreen().toDouble / 255
            val blue = c.getBlue().toDouble / 255
            (List(red, green, blue).max + List(red, green, blue).min) / 2
        })
    
    def avgLightness(pixels: ParSeq[Double]): Int =
        100 - ((pixels.sum / pixels.length.toDouble) * 100).floor.toInt

    def evaluate(img: File): Unit =
        val photo = readPhoto(img)
        val pixels = listOfLightness(listOfPixels(photo))
        val lightness = avgLightness(pixels)
        savePhoto(img, lightness)