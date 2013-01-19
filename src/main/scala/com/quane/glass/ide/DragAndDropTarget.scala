package com.quane.glass.ide

import scala.swing.Component

trait DragAndDropTarget
        extends Component {

    def accepts(item: DragAndDropItem): Boolean;
    
}