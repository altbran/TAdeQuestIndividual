package main

import main._

case class Heroe(statsIniciales: Stat = Stat(), trabajo: Option[Trabajo] = None,
                 items: List[Item] = List(), tareasRealizadas: List[Tarea] = List()) {

  val inventario = Inventario(items, this)

  def cambiarTrabajo(trabajo: Trabajo): Heroe = {
    return this.copy(trabajo = Some(trabajo))
  }

  def equipar(item: Item): Heroe = {
    return this.copy(items = inventario.agregarItem(item).items)

  }

  def statPrincipal = trabajo.map(_.statPrincipal)

  def stats = aplicarTareasA(statsItemsYTrabajo)

  private def conTrabajo = trabajo.fold(statsIniciales) { _.apply(statsIniciales) }

  private def statsItemsYTrabajo = inventario.listoParaEquiparEn(this)(conTrabajo)

  private def aplicarTareasA(stat: Stat) = tareasRealizadas.map(t => t.efectoPara(this)_).foldLeft(stat)((semilla: Stat, f: Stat => Stat) => f(semilla))

  def fuerzaBase = statsIniciales.fuerza
  def inteligenciaBase = statsIniciales.inteligencia

  def getHp = stats.hp
  def getFuerza = stats.fuerza
  def getVelocidad = stats.velocidad
  def getInteligencia = stats.inteligencia

}
