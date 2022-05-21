import Properties._
import java.io.File
import javax.imageio.ImageIO
import java.awt.image.BufferedImage
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
            val red = (pixel & 0xff0000) / 65536
            val green = (pixel & 0xff00) / 256
            val blue = pixel & 0xff
            val redPrim: Double = red.toDouble / 255
            val greenPrim: Double = green.toDouble / 255
            val bluePrim: Double = blue.toDouble / 255
            (List(redPrim, greenPrim, bluePrim).max + List(redPrim, greenPrim, bluePrim).min) / 2
        })
    
    def avgLightness(pixels: ParSeq[Double]): Int =
        100 - ((pixels.sum / pixels.length.toDouble) * 100).floor.toInt

    def evaluate(img: File): Unit =
        val photo = readPhoto(img)
        val pixels = listOfLightness(listOfPixels(photo))
        val lightness = avgLightness(pixels)
        savePhoto(img, lightness)