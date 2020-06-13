package com.bhancock.finalprojectapplication.model;

import android.os.Parcelable;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;

public class Product implements Parcelable {
        private String image;
        private String id;
        private String title;
        private String description;
        private String price;
        private String cut_price;
        private String discount;
        private float rating;
        private String external_link;
        public Product(String image, String id, String title, String description, String price, String cut_price, String discount, float rating) {
            this.image = image;
            this.id = id;
            this.title = title;
            this.description = description;
            this.price = price;
            this.cut_price = cut_price;
            this.discount = discount;
            this.rating = rating;
        }
        public Product(String image, String title, String description, String price, String cut_price, String discount, float rating) {
            this.image = image;
            this.title = title;
            this.description = description;
            this.price = price;
            this.cut_price = cut_price;
            this.discount = discount;
            this.rating = rating;
        }
        public Product(String image, String title, String description, String price, String cut_price, String discount, float rating, String external_link) {
            this.image = image;
            this.title = title;
            this.description = description;
            this.price = price;
            this.cut_price = cut_price;
            this.discount = discount;
            this.rating = rating;
            this.external_link = external_link;
        }
        @Override
        public int describeContents() {
            return 0;
        }
        @Override
        public void writeToParcel(Parcel out, int flags) {
            out.writeString(image);
            out.writeString(title);
            out.writeString(description);
            out.writeString(price);
            out.writeString(cut_price);
            out.writeString(discount);
            out.writeFloat(rating);
            out.writeString(external_link);
        }
        public static final Parcelable.Creator<Product> CREATOR = new Parcelable.Creator<Product>() {
            public Product createFromParcel(Parcel in) {
                return new Product(in);
            }
            public Product[] newArray(int size) {
                return new Product[size];
            }
        };
        private Product(Parcel in) {
            image = in.readString();
            title = in.readString();
            description = in.readString();
            price = in.readString();
            cut_price = in.readString();
            discount = in.readString();
            rating = in.readFloat();
            external_link = in.readString();
        }
        public String getImage() {
            return image;
        }
        public void setImage(String image) {
            this.image = image;
        }
        public String getTitle() {
            return title;
        }
        public void setTitle(String title) {
            this.title = title;
        }
        public String getDescription() {
            return description;
        }
        public void setDescription(String description) {
            this.description = description;
        }
        public String getPrice() {
            return price;
        }
        public void setPrice(String price) {
            this.price = price;
        }
        public String getCut_price() {
            return cut_price;
        }
        public void setCut_price(String cut_price) {
            this.cut_price = cut_price;
        }
        public String getDiscount() {
            return discount;
        }
        public void setDiscount(String discount) {
            this.discount = discount;
        }
        public float getRating() {
            return rating;
        }
        public void setRating(float rating) {
            this.rating = rating;
        }
        public String getExternal_link() {
            return external_link;
        }
        public void setExternal_link(String external_link) {
            this.external_link = external_link;
        }
    }

