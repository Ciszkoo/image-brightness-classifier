import Properties._
import java.io.File
import javax.imageio.ImageIO
import java.awt.image.BufferedImage

case class Files(path: String)

object Files:
    def photosToEvaluate(obj: Files): List[File] =
        val dir = File(obj.path)
        dir.listFiles.toList

        
trait PhotoEvaluatingInterface:
    def readPhoto(img: File): BufferedImage
    def savePhoto(img: File): Unit
    def listOfPixels(img: BufferedImage): List[Int]
    def evaluating(img: File): Unit


object PhotoEvaluating extends PhotoEvaluatingInterface:
    def readPhoto(img: File): BufferedImage =
        ImageIO.read(img)

    def savePhoto(img: File): Unit =
        os.copy.into(
            os.Path(img.toString),
            os.Path(outputPath)
        )
    
    def listOfPixels(img: BufferedImage): List[Int] =
        img.getRGB(0, 0, img.getWidth(), img.getHeight(), null, 0, img.getWidth()).toList


    def evaluating(img: File): Unit =
        val photo = readPhoto(img)
        val pixels = listOfPixels(photo)
        savePhoto(img)