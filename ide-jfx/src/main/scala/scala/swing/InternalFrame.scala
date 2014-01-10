package scala.swing

import scala.swing._

class InternalFrame
        extends Component
        with Container.Wrapper {

    override lazy val peer = new javax.swing.JInternalFrame with SuperMixin

    def title: String = peer.getTitle
    def title_=(t: String) = peer.setTitle(t)

    def resizable: Boolean = peer.isResizable
    def resizable_=(r: Boolean) = peer.setResizable(r)

    def closable: Boolean = peer.isClosable
    def closable_=(c: Boolean) = peer.setClosable(c)

    def selected: Boolean = peer.isSelected
    def selected_=(s: Boolean) = peer.setSelected(s)

    def maximizable: Boolean = peer.isMaximizable
    def maximizable_=(m: Boolean) = peer.setMaximizable(m)

    def location_=(p: Point) = peer.setLocation(p.x, p.y)
    def location_=(x: Int, y: Int) = peer.setLocation(x, y)

    def pack {
        peer.pack()
    }

    def add(c: Component) {
        peer.add(c.peer)
    }
}