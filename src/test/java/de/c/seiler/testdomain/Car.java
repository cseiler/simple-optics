package de.c.seiler.testdomain;

import java.awt.Color;

public class Car
{
  private final CarMaker manufacturer;
  private Color color;
  private final int modelYear;
  private Seats seats;
  public Car(CarMaker manufacturer, Color color, int modelYear, Seats seats)
  {
    super();
    this.manufacturer = manufacturer;
    this.color = color;
    this.modelYear = modelYear;
    this.seats = seats;
  }
  
  @Override
  public int hashCode()
  {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((color == null)?0:color.hashCode());
    result = prime * result + ((manufacturer == null)?0:manufacturer.hashCode());
    result = prime * result + modelYear;
    result = prime * result + ((seats == null)?0:seats.hashCode());
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
    Car other = (Car) obj;
    if (color == null)
    {
      if (other.color != null)
        return false;
    }
    else if (!color.equals(other.color))
      return false;
    if (manufacturer != other.manufacturer)
      return false;
    if (modelYear != other.modelYear)
      return false;
    if (seats == null)
    {
      if (other.seats != null)
        return false;
    }
    else if (!seats.equals(other.seats))
      return false;
    return true;
  }
  @Override
  public String toString()
  {
    return "Car [manufacturer=" + manufacturer + ", color=" + color + ", modelYear=" + modelYear + ", seats=" + seats
        + "]";
  }

  public Color getColor()
  {
    return color;
  }

  public void setColor(Color color)
  {
    this.color = color;
  }

  public Seats getSeats()
  {
    return seats;
  }

  public void setSeats(Seats seats)
  {
    this.seats = seats;
  }

  public CarMaker getManufacturer()
  {
    return manufacturer;
  }

  public int getModelYear()
  {
    return modelYear;
  }

}
