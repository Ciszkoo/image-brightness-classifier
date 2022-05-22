import Files.*
import Properties.*
import PhotoEvaluating.*

import java.io.File
import scala.collection.parallel.ParSeq

@main
def app: Unit =
  val files: Files = Files(inputPath)
  val listOfFiles: ParSeq[File] = photosToEvaluate(files)
  
  listOfFiles.foreach(evaluate)