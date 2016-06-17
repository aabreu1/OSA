package com.example.root.osa;

import android.annotation.TargetApi;
import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.location.Location;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;

import java.security.InvalidParameterException;
import java.util.Map;

/**
 * Created by root on 09/06/16.
 */
public  class DeviceLocationManager implements LocationListener {


    private final Context context;
    private String[] lastKnownLocation;

    public DeviceLocationManager(final Context context) {
        this.context = context;
    }



    //public Location getLocation(final LocationSource source) /*throws CantGetDeviceLocationException */{

//        return (Location) getLocation();

  //  }


    public String [] getLastKnownLocation() /*throws CantGetDeviceLocationException */{
        if (lastKnownLocation != null)
            return lastKnownLocation;
        else
            return getLocation();
    }

    public void onLocationChanged( android.location.Location location) {

        if (location != null) {

            this.deviceLocation = location;

        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    android.location.LocationManager locationManager;
    Location deviceLocation;
    //Context context;
    private static final long MIN_TIME_BW_UPDATES = 1;
    private static final float MIN_DISTANCE_CHANGE_FOR_UPDATES = 0;
    // private static final long MIN_TIME_INTERVAL = 60000L;

    LocationListener LL = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

  //  @TargetApi(Build.VERSION_CODES.M)
    public String [] getLocation() /*throws CantGetDeviceLocationException*/ {
        String location [] = null;

       // this.context = context;
    try {
        try {
            locationManager = (android.location.LocationManager)this.context.getSystemService(Context.LOCATION_SERVICE);

            if(locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
                locationManager.requestLocationUpdates(android.location.LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this, Looper.getMainLooper());
                if (locationManager != null) {
                    deviceLocation = locationManager.getLastKnownLocation(android.location.LocationManager.GPS_PROVIDER);
                    if (deviceLocation != null) {


                        String x = ""+deviceLocation.getLatitude();
                        String y = ""+deviceLocation.getLongitude();
                        location = new String[]{x, y};

                    } else {
                        /**
                         * I not get location device return an exception
                         */
                        //throw new CantGetDeviceLocationException("Not get GPS Enabled");
                    }
                } else {
                    /**
                     * I not get location device return an exception
                     */
                   // throw new CantGetDeviceLocationException("LocationManager is null");
                }
            } else {

                // GPS not enabled, get status from Network Provider
                if (locationManager.isProviderEnabled(android.location.LocationManager.NETWORK_PROVIDER)) {

                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                        if (context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && context.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    Activity#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for Activity#requestPermissions for more details.
                            return null;
                        }
                    }

                    locationManager.requestLocationUpdates(android.location.LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this, Looper.getMainLooper());
                    // "Network"
                    if (locationManager != null) {
                        deviceLocation = locationManager.getLastKnownLocation(android.location.LocationManager.NETWORK_PROVIDER);
                        if (deviceLocation != null) {

                            String x = ""+deviceLocation.getLatitude();
                            String y= ""+deviceLocation.getLongitude();
                            location = new String[]{x,y};

                        } else {
                            /**
                             * I not get location device return an exception
                             */
                            //throw new CantGetDeviceLocationException("Not get Network Enabled");
                        }
                    } else {
                        /**
                         * I not get location device return an exception
                         */
                       // throw new CantGetDeviceLocationException("LocationManager is null");
                    }
                }


            }

        } catch (Exception e) {

            /**
             * unexpected error
             */
           // throw new CantGetDeviceLocationException(CantGetDeviceLocationException.DEFAULT_MESSAGE, e, "", "unexpected error");
        }
    } catch (Exception e) {
        // TODO manage this kind of exception in a better way

        if (lastKnownLocation != null)
            return lastKnownLocation;
        //else
          //  location = new DeviceLocation(0.0, 0.0, System.currentTimeMillis(), 0.0, LocationSource.UNKNOWN);
    }
    lastKnownLocation = location;
    return location;
}

    private class DeviceLocation {
        /**
         * Location interface member variables.
         */

        private Double latitude;
        private Double longitude;
        private Long time;
        private Double altitude;
        private LocationSource provider;

        // Public constructor declarations.
        public DeviceLocation(){
            this.latitude  = null;
            this.longitude = null;
            this.time      = null;
            this.altitude  = null;
            this.provider  = null;
        }

        /**
         * <p>DeviceLocation implementation constructor
         *
         * @param latitude Double actual device latitude
         * @param longitude Double actual device longitude
         * @param time Long actual device location time
         * @param altitude Double actual device  altitude
         * @param provider enum LocationSource actual location provider network
         */
        public DeviceLocation(Double latitude, Double longitude, Long time, Double altitude, LocationSource provider){
            this.latitude = latitude;
            this.longitude = longitude;
            this.time = time;
            this.altitude = altitude;
            this.provider = provider;
        }


        /**
         * Location interfaces implementation.
         */

        /**
         *<p>This method gets de actual device altitude
         *
         * @return double altitude
         */

        public Double getAltitude() {
            return altitude;
        }

        /**
         * Set the Altitude
         * @param altitude
         */
        public void setAltitude(Double altitude) {
            this.altitude = altitude;
        }

        /**
         *<p>This method gets de actual device latitude
         *
         * @return double latitude
         */

        public Double getLatitude() {
            return latitude;
        }

        /**
         * Set the Latitude
         * @param latitude
         */
        public void setLatitude(Double latitude) {
            this.latitude = latitude;
        }

        /**
         *<p>This method gets de actual device longitude
         *
         * @return double longitude
         */

        public Double getLongitude() {
            return longitude;
        }

        /**
         * Set the Longitude
         * @param longitude
         */
        public void setLongitude(Double longitude) {
            this.longitude = longitude;
        }

        /**
         *<p>This method gets de actual location network provider
         *
         * @return LocationSource enum
         */

        public LocationSource getSource() {
            return provider;
        }


        public Double getAccuracy() {
            return 0.0;
        }


        public Double getAltitudeAccuracy() {
            return 0.0;
        }

        /**
         * Set the Provider
         * @param provider
         */
        public void setProvider(LocationSource provider) {
            this.provider = provider;
        }

        /**
         *<p>This method gets de actual device location time
         *
         * @return long time
         */

        public Long getTime() {
            return time;
        }

        /**
         * Set the Time
         * @param time
         */
        public void setTime(Long time) {
            this.time = time;
        }

    }

    public enum LocationSource {

        GPS           ("GPS"),
        NETWORK       ("NET"),
        IP_CALCULATED ("IPC"),

        ;

        private final String code;

        LocationSource(final String code){

            this.code = code;
        }

        public static LocationSource getByCode(String code) throws InvalidParameterException {

            switch (code){

                case "GPS": return GPS;
                case "NET": return NETWORK;
                case "IPC": return IP_CALCULATED;

                default:
                    throw new InvalidParameterException(
                            "Code Received: " + code+
                            "This Code Is Not Valid for the LocationSource enum"
                    );
            }
        }


        public String getCode(){
            return this.code;
        }

    }

}

