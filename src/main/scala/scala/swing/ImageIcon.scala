package scala.swing

import java.net.URL
import scala.swing._
import javax.imageio.ImageIO

class ImageIcon
        extends Icon {

    override lazy val peer = new javax.swing.ImageIcon

    /** Creates an ImageIcon from the image.
      */
    def this(image: Image, description: String) {
        this
        this.peer.setImage(image)
        this.description = description
    }

    /** Creates an ImageIcon from an image object.
      */
    def this(image: Image) {
        this(image, "")
    }

    /** Creates an ImageIcon from the specified URL.
      */
    def this(location: URL, description: String) {
        this(ImageIO.read(location), description)
    }

    /** Creates an ImageIcon from the specified URL.
      */
    def this(location: URL) {
        this(location, "")
    }

    /** Creates an ImageIcon from the specified file.
      */
    def this(filename: String, description: String) {
        this(new URL(filename), description)
    }

    /** Creates an ImageIcon from the specified file.
      */
    def this(filename: String) {
        this(filename, "")
    }

    def iconWidth: Int = peer.getIconWidth
    def iconHeight: Int = peer.getIconHeight

    def description: String = peer.getDescription
    def description_=(d: String) = peer.setDescription(d)

    def image: Image = peer.getImage
    def image_=(i: Image) = peer.setImage(i)

    def imageLoadStatus: Int = peer.getImageLoadStatus

}