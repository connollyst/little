package com.quane.little.ide

import scala.swing.Component

trait DragAndDropTarget
        extends Component {

    def accepts(item: DragAndDropItem): Boolean;
    
}