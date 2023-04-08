import Properties.inputPath
import PhotoEvaluating.evaluate
import java.io.File
import scala.collection.parallel.ParSeq

@main
def app(): Unit =
  val files: Files              = Files(inputPath)
  val listOfFiles: ParSeq[File] = files.listFiles
  listOfFiles.foreach(evaluate)
