package com.example.root.osa;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;

import java.security.InvalidParameterException;

/**
 * Created by root on 09/06/16.
 */
public  class DeviceLocationManager{

    android.location.LocationManager locationManager;
    Location deviceLocation;
    Context context;
    private static final long MIN_TIME_BW_UPDATES = 1L;
    private static final float MIN_DISTANCE_CHANGE_FOR_UPDATES = 0.0F;
    private static final long MIN_TIME_INTERVAL = 60000L;

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


    @TargetApi(Build.VERSION_CODES.M)
    public DeviceLocation getLocation(Context context) /*throws CantGetDeviceLocationException*/ {
        DeviceLocation location = null;

        this.context = context;
        try {
            locationManager = (android.location.LocationManager)this.context.getSystemService(Context.LOCATION_SERVICE);

            if(locationManager.isProviderEnabled("gps")) {

                locationManager.requestLocationUpdates("gps", 1L, 0.0F, /*(LocationListener)*/ LL, Looper.getMainLooper());
                if(locationManager == null) {
                    //throw new CantGetDeviceLocationException("LocationManager is null");
                }

                deviceLocation = locationManager.getLastKnownLocation("gps");
                if(deviceLocation == null) {
                   // throw new CantGetDeviceLocationException("Not get GPS Enabled");
                }

                location = new DeviceLocation(Double.valueOf(deviceLocation.getLatitude()), Double.valueOf(deviceLocation.getLongitude()), Long.valueOf(deviceLocation.getTime()), Double.valueOf(deviceLocation.getAltitude()), LocationSource.GPS);
            } else if(locationManager.isProviderEnabled("network")) {
                if(Build.VERSION.SDK_INT >= 21 && this.context.checkSelfPermission("android.permission.ACCESS_FINE_LOCATION") != 0 && this.context.checkSelfPermission("android.permission.ACCESS_COARSE_LOCATION") != 0) {
                        return null;
                }

                locationManager.requestLocationUpdates("network", 1L, 0.0F, (LocationListener) this, Looper.getMainLooper());
                if(locationManager == null) {
                  //  throw new CantGetDeviceLocationException("LocationManager is null");
                }

                deviceLocation = locationManager.getLastKnownLocation("network");
                if(deviceLocation == null) {
                  //  throw new CantGetDeviceLocationException("Not get Network Enabled");
                }

                location = new DeviceLocation(Double.valueOf(deviceLocation.getLatitude()), Double.valueOf(deviceLocation.getLongitude()), Long.valueOf(deviceLocation.getTime()), Double.valueOf(deviceLocation.getAltitude()), LocationSource.NETWORK);
            }

            return location;
        } catch (Exception var3) {

            AlertDialog AD;
            AD = new AlertDialog.Builder(context).create();
            AD.setTitle("Exception");
            AD.setMessage("CAN\'T GET DEVICE LOCATION, "+ var3 + ", unexpected error");
           // AD.show();
            //throw new CantGetDeviceLocationException("CAN\'T GET DEVICE LOCATION", var3, "", "unexpected error");
        }
      // Log.i("Datos de Ubicacion","Altitud: "+location.altitude+", Longitud: "+location.longitude+", Latitud: "+location.latitude);
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

