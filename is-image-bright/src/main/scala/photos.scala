import Properties._
import java.io.File
import javax.imageio.ImageIO
import java.awt.image.BufferedImage

case class Photos(path: String)

object Photos:
    def photosToEvaluate(obj: Photos): List[File] =
        val dir = File(obj.path)
        dir.listFiles.toList

    def readPhoto(photo: File): BufferedImage =
        ImageIO.read(photo)
    
    def savePhoto(photo: File): Unit =
        os.copy.into(
            os.Path(photo.toString),
            os.Path(outputPath)
        )