package com.quane.little.ide.view.html

import com.vaadin.ui.{Label, VerticalLayout}
import com.quane.little.ide.view.EventListenerView

object EventListenerLayout {
  val Style = "l-event-listener"
}

/** An HTML layout view representing an event listener.
  *
  * @author Sean Connolly
  */
class EventListenerLayout
  extends VerticalLayout
  with EventListenerView
  with RemovableComponent {

  setStyleName(EventListenerLayout.Style)

  addComponent(new Label("TODO"))

}
