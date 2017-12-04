package main

trait Item extends ModificacionDeStats{
  def precio = 10

  def puedeEquiparseEn(heroe: Heroe): Boolean = true
  
  def efectoPara(heroe: Heroe)(stat: Stat): Stat
  
  def sosMiTipo(item: Item): Boolean = {
    (item,this) match {
      case (Mano(_),Mano(_)) => true
      case (Casco(),Casco()) => true
      case(Torso(), Torso()) => true
      case (_,_) => item.getClass.equals(this.getClass)
    }
  }
}

abstract case class Casco() extends Item
abstract case class Torso() extends Item
abstract case class Mano(manosNecesarias: Int) extends Item
abstract case class Talisman() extends Item

object cascoVikingo extends Casco {
  
  override def efectoPara(heroe: Heroe)(stat: Stat)= cambiarHpEn(30)(stat)
}

object palitoMagico extends Mano(1) {

  override def puedeEquiparseEn(heroe: Heroe): Boolean = {
    heroe.trabajo match {
      case None    => false
      case Some(_) => apto(heroe)
    }
  }

  private def apto(heroe: Heroe): Boolean = {
    heroe.trabajo.get match {
      case Mago                                    => true
      case Ladron if (heroe.inteligenciaBase > 30) => true
      case _                                       => false
    }
  }

  override def efectoPara(heroe: Heroe)(stat: Stat) = cambiarInteligenciaEn(20) (stat)
}

object armaduraEleganteSport extends Torso {
  
  def efectoPara(heroe: Heroe)(stat: Stat) = (cambiarHpEn(-30)_ compose cambiarVelocidadEn(30)_) (stat)
}

object arcoViejo extends Mano(1) {
  override def efectoPara(heroe: Heroe)(stat: Stat) = cambiarHpEn(2) (stat)
}

object escudoAntiRobo extends Mano(1) {
  override def puedeEquiparseEn(heroe: Heroe): Boolean = {
    heroe.trabajo match {
      case None    => false
      case Some(_) => apto(heroe)
    }
  }

  private def apto(heroe: Heroe): Boolean = {
    heroe.trabajo.get match {
      case Ladron                       => false
      case _ if (heroe.fuerzaBase < 20) => false
      case _                            => true
    }
  }

 override def efectoPara(heroe: Heroe)(stat: Stat) = cambiarHpEn(20) (stat)
}

object talismanDedicacion extends Talisman {
    
  override def efectoPara(heroe :Heroe)(stat :Stat) = {
    val aumentoDe = (0.1 * heroe.statPrincipal.fold(0){_+0}).toInt    //fijarse de agregar algun implicit para convertir esto
    
    cambiarTodoEn(aumentoDe)(stat)
  }
}

object talismanMinimalismo extends Talisman {
  override def efectoPara(heroe: Heroe)(stat: Stat) = {

    val vidaPerdida =heroe.inventario.items.length * 10

    cambiarTodoEn(-vidaPerdida) (stat)
  }
}  

object vinchaBufalo extends Casco {
  override def puedeEquiparseEn(heroe: Heroe): Boolean = {
    heroe.trabajo match {
      case None    => true
      case Some(_) => false
    }
  }

  override def efectoPara(heroe: Heroe)(stat: Stat): Stat = {
    return heroe match { 
      case _ if (heroe.fuerzaBase > heroe.inteligenciaBase) =>  cambiarInteligenciaEn(30) (stat)
      case _ => cambiarTodoEn(10)(stat)
    }
  }
}

object talismanMaldito extends Talisman {
  override def efectoPara(heroe: Heroe)(stat: Stat) = {
    Stat()
  }
}

object espadaDeLaVida extends Mano(2) {
  override def efectoPara(heroe: Heroe)(stat: Stat) = {
    stat.copy(fuerza = stat.hp)
  }
}