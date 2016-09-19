package de.c.seiler.testdomain;

import java.awt.Color;

public class Seats
{
  private final Material material;
  private final Color color;
  public Seats(Material material, Color color)
  {
    super();
    this.material = material;
    this.color = color;
  }
  @Override
  public int hashCode()
  {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((color == null)?0:color.hashCode());
    result = prime * result + ((material == null)?0:material.hashCode());
    return result;
  }
  @Override
  public boolean equals(Object obj)
  {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Seats other = (Seats) obj;
    if (color == null)
    {
      if (other.color != null)
        return false;
    }
    else if (!color.equals(other.color))
      return false;
    if (material != other.material)
      return false;
    return true;
  }
  @Override
  public String toString()
  {
    return "Seats [material=" + material + ", color=" + color + "]";
  }
  public Material getMaterial()
  {
    return material;
  }
  public Color getColor()
  {
    return color;
  }
  
}
