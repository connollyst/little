package scala.swing

import scala.swing._

class DesktopPanel
        extends Component
        with Container.Wrapper {

    override lazy val peer = new javax.swing.JDesktopPane with SuperMixin

    type Constraints = Rectangle

    protected def constraintsFor(comp: Component) = comp.peer.getPreferredSize()

    protected def areValid(c: Constraints): Boolean = true

    protected def add(comp: Component) {
        add(comp, None)
    }

    protected def add(component: Component, constraints: Option[Constraints]) {
        add(component, constraints, peer.getComponentCount())
    }

    protected def add(component: Component, constraints: Option[Constraints], index: Int) {
        if (constraints.isDefined) {
            val rectangle = constraints.get
            component.bounds.x = rectangle.x
            component.bounds.y = rectangle.y
            component.bounds.width = rectangle.width
            component.bounds.height = rectangle.height
        }
        peer.add(component.peer, component.bounds, index)
    }
}