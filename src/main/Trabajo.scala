package main

import main._

sealed trait Trabajo extends ModificacionDeStats{
  def statPrincipal : Int
  def apply : Stat => Stat
}

case object Guerrero extends Trabajo {
  def statPrincipal = 15
  
  def apply = cambiarHpEn(10)_ compose cambiarFuerzaEn(15)_ compose cambiarInteligenciaEn(-10)_
}

case object Mago extends Trabajo {
  def statPrincipal = 20
  
  def apply = cambiarFuerzaEn(-20)_ compose cambiarInteligenciaEn(20)_
}

case object Ladron extends Trabajo {
  def statPrincipal = 15
  
  def apply = cambiarHpEn(-5)_ compose cambiarVelocidadEn(10)_
}