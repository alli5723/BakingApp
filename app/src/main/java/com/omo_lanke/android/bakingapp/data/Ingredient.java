package com.omo_lanke.android.bakingapp.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by omo_lanke on 15/06/2017.
 */

public class Ingredient implements Parcelable{
    double quantity;
    String measure;
    String ingredient;

    public double getQuantity() {
        return quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeDouble(quantity);
        parcel.writeString(measure);
        parcel.writeString(ingredient);
    }

    public Ingredient(){

    }

    private Ingredient(Parcel in){
        quantity = in.readDouble();
        measure = in.readString();
        ingredient = in.readString();
    }

    public static final Parcelable.Creator<Ingredient>
            CREATOR = new Parcelable.ClassLoaderCreator<Ingredient>(){

        @Override
        public Ingredient createFromParcel(Parcel parcel) {
            return new Ingredient(parcel);
        }

        @Override
        public Ingredient[] newArray(int i) {
            return new Ingredient[i];
        }

        @Override
        public Ingredient createFromParcel(Parcel parcel, ClassLoader classLoader) {
            return null;
        }
    };
}
