package com.dancc.basketballstatskeeper.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Player {
  @PrimaryKey(autoGenerate = true)
  public int id;
  public int number;
  public String name;

  public Player(int number, String name) {
    this.number = number;
    this.name = name;
  }
}

