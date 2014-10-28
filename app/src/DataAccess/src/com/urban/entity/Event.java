package com.urban.entity;

import java.util.Collection;

public interface Event {
  public String getText();
  public Image getImage();
  public Collection<Place> getPlaces();
}
