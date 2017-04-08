// IMyService2.aidl
package com.song.example.service;

// Declare any non-default types here with import statements
import com.song.example.service.Point;

interface IMyService {
  /**
  * 取得当前点
  */
  Point current();
  /**
  * 是否与当前点重合
  */
  boolean isCoincide(in Point point);
  /**
  *与当前点之间的距离
  */
  double distance(in Point point);
  /**
  *两点之间的距离
  */
  double distanceBetween(in Point point,in Point point2);
}
