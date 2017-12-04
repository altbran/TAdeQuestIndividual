package main

abstract case class Mision(tareas: List[Tarea] = List()) {

  val recompensa: Equipo => Equipo
  
  def serRealizadaPor(equipo: Resultado) = {
    queHagaLasTareasUn(equipo).map(recompensa)
  }
  
  private def queHagaLasTareasUn(unEquipo: Resultado): Resultado = {
    return tareas.foldLeft(unEquipo){
      
      case (Exito(eq), tarea: Tarea)  => eq.hacer(tarea)
      case (Parcial(eq), tarea: Tarea)  => eq.hacerParcial(tarea)
      case (estado: Resultado, _)     => estado
    }
  }
}

object pegarleAlColo extends Mision(List(pelearContraMonstruo)) {
  val recompensa = (equipo: Equipo) => equipo.cobrarOro(10)
}

object robarCosas extends Mision(List(new robarTalisman(talismanDedicacion))) {
  val recompensa = (equipo: Equipo) => equipo.cobrarOro(1000)
}

object abrirPuerta extends Mision(List(forzarPuerta)) {
  val recompensa = (equipo: Equipo) => equipo.cobrarOro(1000)
}