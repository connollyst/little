package scala.swing

class LabelIcon(text: String)
        extends Label(text) {

    def this() {
        this("")
    }

    def this(text: String, icon: Icon) {
        this(text)
        peer.setIcon(icon.peer)
    }

}