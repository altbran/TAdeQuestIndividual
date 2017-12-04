package main

import scala.util.Try

case class Equipo(nombre: String = "", val integrantes: List[Heroe] = List(), pozo: Int = 0) {

  def mejorHeroeSegun(criterio: (Heroe => Int)): Heroe = {
    integrantes.maxBy(criterio)
  }

  def obtenerItem(item: Item)(lista: List[Heroe]) = {
    if (aNadieLeSirve(item))
      this.copy(pozo = pozo + item.precio)
    else
      reemplazarMiembro(mejorHeroeSegun(diferenciaConMainStatDe(item)), mejorHeroeSegun(diferenciaConMainStatDe(item)).equipar(item)) 
  }

  def obtenerMiembro(nuevo: Heroe) = {
    this.copy(integrantes = anexarHeroe(nuevo)(integrantes))
  }

  def reemplazarMiembro(viejo: Heroe, nuevo: Heroe) = {
    if (!integrantes.isEmpty)
      this.copy(integrantes = (anexarHeroe(nuevo)_ compose removerHeroe(viejo)_)(integrantes))
    else
      this
  }

  def lider() = {
    if (hayVariosLideres)
      None
    else
      Some(mejorHeroeSegun { valorStatPrincipalDe })
  }
  
  def cobrarOro(oro: Int) = this.copy(pozo = pozo + oro)

  def cantidadDe(trabajo: Trabajo) = {

    integrantes.filterNot(_.trabajo.isEmpty).map(_.trabajo.get).count(_ == trabajo)
  }
  
  def hacer(tarea: Tarea) = {
    val posibleHeroe = mejorHeroePara(tarea)
    posibleHeroe.fold(_ => Parcial(this), h => Resultado(tarea.ejecutadaPor(this)(h)))
  }
  
  def hacerParcial(tarea: Tarea) = {
    val posibleHeroe = mejorHeroePara(tarea)
    posibleHeroe.fold(_ => Fallo(this,tarea), h => Resultado(tarea.ejecutadaPor(this)(h)))
  }
  
  private def mejorHeroePara (unaTarea: Tarea) = {
    Try(mejorHeroeSegun(unaTarea.facilidad(this)))
  }

  private def losQueTrabajan(lista: List[Heroe]) = {
    lista.filterNot(_.statPrincipal.isEmpty)
  }

  private def puedeServirlesUn(item: Item)(lista: List[Heroe]) = {
    lista.filter(diferenciaConMainStatDe(item)(_) > 1)
  }

  private val losQueSirvenPara = (item: Item) => puedeServirlesUn(item) _ compose losQueTrabajan _

  private def aNadieLeSirve(item: Item) = losQueSirvenPara(item)(integrantes).isEmpty

  private def diferenciaConMainStatDe(item: Item)(heroe: Heroe) = {
    valorStatPrincipalDe(heroe.copy().equipar(item)) - valorStatPrincipalDe(heroe)
  }

  private def anexarHeroe(heroe: Heroe)(lista: List[Heroe]) = {
    lista.+:(heroe)
  }

  private def removerHeroe(heroe: Heroe)(lista: List[Heroe]) = {
    lista.filterNot(_.equals(heroe))
  }

  private def hayVariosLideres = integrantes.count(valorStatPrincipalDe(_) == maximoValorDeStatPrincipal) > 1

  private def valorStatPrincipalDe(heroe: Heroe) = heroe.statPrincipal.getOrElse(0)

  private def maximoValorDeStatPrincipal = mejorHeroeSegun { valorStatPrincipalDe }.statPrincipal.get //no hay problema con el Option aca

}