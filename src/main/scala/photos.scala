import Properties._
import java.io.File
import javax.imageio.ImageIO
import java.awt.image.BufferedImage
import java.awt.Color
import scala.collection.parallel.CollectionConverters._
import scala.collection.parallel.ParSeq

case class Files(path: String)

object Files:
    // creates parallel sequence of files to evaluate
    def photosToEvaluate(obj: Files): ParSeq[File] =
        val dir: File = File(obj.path)
        dir.listFiles.toSeq.par

        
trait PhotoEvaluatingInterface:
    protected def readPhoto(img: File): BufferedImage
    protected def savePhoto(img: File, lightness: Int): Unit
    protected def listOfPixels(img: BufferedImage): ParSeq[Int]
    protected def listOfLightness(pixels: ParSeq[Int]): ParSeq[Double]
    protected def avgLightness(pixels: ParSeq[Double]): Int
    def evaluate(img: File): Unit


object PhotoEvaluating extends PhotoEvaluatingInterface:
    protected def readPhoto(img: File): BufferedImage =
        ImageIO.read(img)

    protected def savePhoto(img: File, lightness: Int): Unit =
        val fileName = img.toString.split("\\\\").last
        val newName = if (lightness < cutOffPoint) {
            s"${fileName.split("\\.").head}_bright_$lightness.${fileName.split("\\.").last}"
        } else {
            s"${fileName.split("\\.").head}_dark_$lightness.${fileName.split("\\.").last}"
        }
        os.copy(
            os.Path(img.toString),
            os.Path(s"$outputPath/$newName")
        )
    
    protected def listOfPixels(img: BufferedImage): ParSeq[Int] =
        img.getRGB(0, 0, img.getWidth(), img.getHeight(), null, 0, img.getWidth()).toSeq.par

    // get lightness (HSL color system) from RGB value
    protected def listOfLightness(pixels: ParSeq[Int]): ParSeq[Double] =
        pixels.map(pixel => {
            val c = Color(pixel)
            val red: Double = c.getRed.toDouble / 255
            val green: Double = c.getGreen.toDouble / 255
            val blue: Double = c.getBlue.toDouble / 255
            (List(red, green, blue).max + List(red, green, blue).min) / 2
        })
    
    protected def avgLightness(pixels: ParSeq[Double]): Int =
        val average = pixels.foldLeft((0.0, 1))((acc, i) => (acc._1 + (i - acc._1) / acc._2, acc._2 + 1))._1
        100 - (average * 100).floor.toInt

    def evaluate(img: File): Unit =
        val photo = readPhoto(img)
        val pixels = listOfLightness(listOfPixels(photo))
        val lightness = avgLightness(pixels)
        savePhoto(img, lightness)