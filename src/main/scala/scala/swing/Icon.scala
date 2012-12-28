package scala.swing

import scala.swing._
import java.net.URL

trait Icon {

    val peer: javax.swing.Icon

    def iconWidth: Int
    def iconHeight: Int

}