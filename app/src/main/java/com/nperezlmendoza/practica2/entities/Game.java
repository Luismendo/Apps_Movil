package com.nperezlmendoza.practica2.entities;

import java.util.ArrayList;
import java.util.Arrays;

public class Game {
    public Long _id;
    public String title;
    public String description;
    public Float price;
    public String platform;
    public String entry_date;
    public Boolean has_offer;
    public Boolean in_cart;

    public Game() {
        // Required for ORM
    }

    public Game(String title, String description, Float price,
                String platform, String entry_date, Boolean has_offer) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.platform = platform;
        this.entry_date = entry_date;
        this.has_offer = has_offer;
        this.in_cart = false;
    }

    public Game(String title, String description, Float price,
                String platform, String entry_date, Boolean has_offer, Boolean in_cart) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.platform = platform;
        this.entry_date = entry_date;
        this.has_offer = has_offer;
        this.in_cart = in_cart;
    }

    public static ArrayList<Game> getGames() {
        return new ArrayList<>(Arrays.asList(
            new Game("God of War", "Adéntrate en una aventura compleja y desconocida.",
                         19.95f, "PS4", "2021-05-25 12:37:32", false),
            new Game("The Last of Us", "Algo sobre quién será el último. Pero no sé en qué.",
                         15.35f, "PS4", "2021-05-30 09:03:44", true),
            new Game("Bloodborne", "Un juego terrífico de acción y horror.",
                         16.06f, "PS4", "2020-06-16 12:33:44", true),
            new Game("GTA V", "No hace falta ni descripción. Todo el mundo lo conoce!",
                         16.06f, "PS4", "2021-05-15 01:21:37", false),

            new Game("Agent Under Fire", "El clásico First Person Shooter que nunca queda obsoleto.",
                         5.21f, "Xbox", "2002-03-26 12:37:32", false),
            new Game("Pro Race Driver", "Car go brrrrrrrrrr.",
                         10.99f, "Xbox", "2006-11-17 09:03:44", true),
            new Game("Pool Shark 2", "Porque... ¿a quién no le apetece jugar al billar?",
                         2.06f, "Xbox", "2021-04-16 12:33:44", false),
            new Game("The Da Vinci Code", "¿La verdad? No sé qué poner. Lorem ipsum dolor sit amet.",
                         13.13f, "Xbox", "2007-06-29 01:21:37", true)
        ));
    }
}
