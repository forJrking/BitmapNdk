package com.full.ndkdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import org.jetbrains.annotations.NotNull;

public class NDKTest {
   private int i;

   public final int getI() {
      return this.i;
   }

   public final void setI(int var1) {
      this.i = var1;
   }

   @NotNull
   public final Bitmap getbitMap(@NotNull Context c) {
      Bitmap var10000 = BitmapFactory.decodeResource(c.getResources(), R.mipmap.ic_launcher);
      return var10000;
   }

   public NDKTest(int i, @NotNull String s, @NotNull Bitmap b) {
      this.i = 1;
   }
}