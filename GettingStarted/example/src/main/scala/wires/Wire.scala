package wires

class Wire {
  private var sigVal = false
  private var actions: List[Action] = List()
  def getSiganl: Boolean = sigVal
  def setSignal (s: Boolean): Unit = 
    if (s != sigVal){
      sigVal = s
      actions foreach(_())
    }
  
  def addAction(a: Action): Unit = {
    actions = a :: actions
    a()
  }

}