package main

import main._

object Inventario {  
  
  def apply (items: List[Item] = List(), heroe: Heroe = new Heroe) : Inventario = {
    val inv = new Inventario(items,heroe)
    
    items.filter(_.puedeEquiparseEn(heroe)).foldLeft(inv)((s,item) => s.agregarItem(item))
  }
}

case class Inventario private(items: List[Item] = List(), heroe: Heroe){

  def listoParaEquiparEn(heroe: Heroe)(stat: Stat) : Stat ={
    items.foldLeft(stat)((semilla: Stat,f: Item) => f.efectoPara(heroe)(semilla))
  }

  def agregarItem(nuevoItem: Item): Inventario = {
    this.copy(nuevaListaDeItemsCon(nuevoItem))
  }
  
  private def nuevaListaDeItemsCon(nuevoItem: Item) = {
    var nuevos = descartarLosDelTipoDe(nuevoItem)
    
    nuevoItem match {
      case Mano(1) => agregarDescartadoManoUno(nuevoItem, nuevos)
      case _  => nuevos
    }
  }
  
  private def agregarDescartadoManoUno(nuevo: Item, nuevosItems: List[Item]) = {
    var descartado = items.find(_.sosMiTipo(nuevo))
    
    descartado match {
      case Some(Mano(1)) => nuevosItems.+:(descartado.get)
      case _ => nuevosItems
    }
  }
  
  private def descartarLosDelTipoDe(item: Item) = items.filterNot(_.sosMiTipo(item)).:+(item)
}
