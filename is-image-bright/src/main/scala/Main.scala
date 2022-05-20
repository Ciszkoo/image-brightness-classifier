import Photos._
import Properties._

@main
def app: Unit =
  val input = Photos.photosToEvaluate(Photos(inputPath))
  val readed = input.foreach(Photos.savePhoto)
  // println(input)