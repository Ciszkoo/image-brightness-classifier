import Files._
import Properties._
import PhotoEvaluating._

@main
def app: Unit =
  val files = Files(inputPath)
  val listOfFiles = photosToEvaluate(files)
  
  listOfFiles.foreach(evaluate)