import scala.collection.parallel.ParSeq
import scala.collection.parallel.CollectionConverters.*
import java.io.File

class Files(path: String) {

  def listFiles: ParSeq[File] =
    val dir: File = File(path)
    dir.listFiles.toSeq.par
}
