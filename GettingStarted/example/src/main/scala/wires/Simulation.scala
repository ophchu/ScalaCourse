package wires

trait Simulation {
  type Action = () => Unit
  case class Event (time: Int, action: Action)
  private type Agenda = List[Event]
  private var agenda: Agenda = List() 
  
  private var currTime = 0
	def currentTime: Int = currTime 
	def afterDelay(delay: Int)(block: => Unit): Unit = {
    val item = Event(currentTime + delay, () => block)
    agenda = insert (agenda, item)
  }
    
    private def insert (ag: List[Event], item: Event): List[Event] = ag match {
      case first :: rest if first.time <= item.time =>
        first :: insert(rest, item)
      case _ => item :: ag
    }
	private def loop(): Unit = agenda match {
	  case first :: rest => 
	    agenda = rest
	    currTime = first.time
	    first.action()
	    loop()
	    	
	  case Nil =>
	}
	def run(): Unit = {
	  afterDelay(0){
	    println("*** simulation started, time = " + currentTime + " ***")
	  }
	  loop()
	}
	
	def probe(name: String, wire: Wire) = {
	  def probeAction() = {
	    println(s"$name $currentTime value= ${wire.getSiganl}")
	  }
	  wire addAction probeAction
	}
}