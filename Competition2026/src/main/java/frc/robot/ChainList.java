// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.ArrayList;

/** Add your docs here. */
public class ChainList<T> extends ArrayList<T> {
  
  public ChainList(){
    return;
  }
  public ChainList<T> chainAdd(T element){
    this.add(element);
    return this;
  }
}