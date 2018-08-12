package com.example.demo2.entity;

import org.springframework.boot.Banner;

import java.util.List;

public class FallIndex {
    List<Banner> banners;
    List<FallImage> fallimages;
    List<FallMusic> music;

    public List<Banner> getBanners() {
        return banners;
    }

    public void setBanners(List<Banner> banners) {
        this.banners = banners;
    }

    public List<FallImage> getFallimages() {
        return fallimages;
    }

    public void setFallimages(List<FallImage> fallimages) {
        this.fallimages = fallimages;
    }

    public List<FallMusic> getMusic() {
        return music;
    }

    public void setMusic(List<FallMusic> music) {
        this.music = music;
    }
}
