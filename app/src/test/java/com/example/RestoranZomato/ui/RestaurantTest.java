package com.example.RestoranZomato.ui;

import com.example.RestoranZomato.data.db.model.Restaurant;
import com.example.RestoranZomato.data.network.model.RestaurantsResponse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class RestaurantTest {

    private RestaurantsResponse.Restaurant restaurantNotNull;
    private RestaurantsResponse.Restaurant restaurantNull;

    private RestaurantsResponse.UserRating ratingNotNull;
    private RestaurantsResponse.UserRating ratingNull;

    private RestaurantsResponse.Location locationNotNull;
    private RestaurantsResponse.Location locationNull;
    private RestaurantsResponse.Location location;


    @Before
    public void setup(){
        //parameter for new restaurant
        ratingNotNull = new RestaurantsResponse.UserRating("3.7");
        locationNotNull = new RestaurantsResponse.Location("40.732013","-73.996155");
        //new restaurant with location
        restaurantNotNull = new RestaurantsResponse.Restaurant(
                "1231231",
                "Baso Malabar",
                "https://b.zmtcdn.com/data/pictures/chains/8/16774318/a54deb9e4dbb79dd7c8091b30c642077_featured_thumb.png",
                "30",
                "2",
                "Rp",
                ratingNotNull,
                "0",
                "1",
                locationNotNull
        );

        ratingNull = new RestaurantsResponse.UserRating("3.7");
        locationNull = new RestaurantsResponse.Location("0","0");
        //new restaurant without location
        restaurantNull = new RestaurantsResponse.Restaurant(
                "1231231",
                "Baso Malabar",
                "https://b.zmtcdn.com/data/pictures/chains/8/16774318/a54deb9e4dbb79dd7c8091b30c642077_featured_thumb.png",
                "30",
                "2",
                "Rp",
                ratingNull,
                "0",
                "1",
                null
        );
    }


    @Test
    public void getIdNotNull(){
        String result = restaurantNotNull.getId();
        assertThat(result, is(equalTo("1231231")));
    }

    @Test
    public void getNameNotNull(){
        String result = restaurantNotNull.getName();
        assertThat(result, is(equalTo("Baso Malabar")));
    }

    @Test
    public void getThumbNotNull(){
        String result = restaurantNotNull.getThumb();
        assertThat(result, is(equalTo("https://b.zmtcdn.com/data/pictures/chains/8/16774318/a54deb9e4dbb79dd7c8091b30c642077_featured_thumb.png")));
    }

    @Test
    public void getAverageCostForTwoNotNull(){
        String result = restaurantNotNull.getAverageCostForTwo();
        assertThat(result, is(equalTo("30")));
    }

    @Test
    public void getPriceRangeNotNull(){
        String result = restaurantNotNull.getPriceRange();
        assertThat(result, is(equalTo("2")));
    }

    @Test
    public void getCurrencyNotNull(){
        String result = restaurantNotNull.getCurrency();
        assertThat(result, is(equalTo("Rp")));
    }

    @Test
    public void getUserRatingNotNull(){
        ratingNotNull = new RestaurantsResponse.UserRating("3.7");
        String result = restaurantNotNull.getUserRating().getAggregateRating();
        assertThat(result, is(equalTo(ratingNotNull.getAggregateRating())));
    }

    @Test
    public void getIsDeliveryOnNotNull(){
        String result = restaurantNotNull.isDeliveringNow();
        assertThat(result, is(equalTo("0")));
    }

    @Test
    public void getHasOnlineDeliveryNotNull(){
        String result = restaurantNotNull.hasOnlineDelivery();
        assertThat(result, is(equalTo("1")));
    }

    @Test
    public void getRestaurantLatitudeNotNull(){
        location = new RestaurantsResponse.Location("40.732013","-73.996155");
        String result = restaurantNotNull.getLocation().getLatitude();
        assertThat(result, is(equalTo(location.getLatitude())));
    }

    @Test
    public void getRestaurantLongitudeNotNull(){
        location = new RestaurantsResponse.Location("40.732013","-73.996155");
        String result = restaurantNotNull.getLocation().getLongitude();
        assertThat(result, is(equalTo(location.getLongitude())));
    }
}