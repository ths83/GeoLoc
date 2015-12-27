package fr.univtln.MasterDAPM.G2.AndroidProject.PerronTonnet.GeoLoc.Business.Map;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fr.univtln.MasterDAPM.G2.AndroidProject.PerronTonnet.GeoLoc.Activity.CARActivity;
import fr.univtln.MasterDAPM.G2.AndroidProject.PerronTonnet.GeoLoc.Activity.CUserStatisticsActivity;
import fr.univtln.MasterDAPM.G2.AndroidProject.PerronTonnet.GeoLoc.Applicative.Sign.Map.CMap;
import fr.univtln.MasterDAPM.G2.AndroidProject.PerronTonnet.GeoLoc.Business.Excursion.CExcursion;
import fr.univtln.MasterDAPM.G2.AndroidProject.PerronTonnet.GeoLoc.Business.Statistics.CCurrentStatistics;
import fr.univtln.MasterDAPM.G2.AndroidProject.PerronTonnet.GeoLoc.Business.Users.CUser;
import fr.univtln.MasterDAPM.G2.AndroidProject.PerronTonnet.GeoLoc.Business.Waypoints.CWaypoint;
import fr.univtln.MasterDAPM.G2.AndroidProject.PerronTonnet.GeoLoc.Business.Waypoints.EWaypointType;
import fr.univtln.MasterDAPM.G2.AndroidProject.PerronTonnet.GeoLoc.CMyApplication;
import fr.univtln.MasterDAPM.G2.AndroidProject.PerronTonnet.GeoLoc.Business.Waypoints.CGeoLocMarker;
import fr.univtln.MasterDAPM.G2.AndroidProject.PerronTonnet.GeoLoc.R;



public class CExcursionMap extends FragmentActivity implements LocationListener,OnMapReadyCallback,Serializable {

    public static final String USER_DISTANCE = "fr.univtln.MasterDAPM.G2.AndroidProject.PerronTonnet.GeoLoc.Business.UserDistance";

    //carte
    private LocationManager locationManager;

    private GoogleMap googleMap;

    private CWaypoint startedWaypoint;

    private CWaypoint cWaypoint;

    private PolylineOptions polylineOptions;

    private CGeoLocMarker geoLocMarker ;


    private Button stopMapActivityButton;

    private Button init;

    private TextView currentDistanceTextView;

    private TextView currentAverageSpeed;

    private  String stringCurrentUserDistance;

    private List<LatLng> pointTable = new ArrayList<>();

    private BitmapDescriptor bitmapDescriptor;

    private int itineraryColor;



    // carte
    private double oldUserPositionLat;

    private double oldUserPositionLng;

    private double userDistanceBetweenTwoPoints;

    private LatLng lastPoint;

    private LatLng memoryLastPoint; //avant derniere localisation de l'utilisateur

    private float currentUserSpeed;

    private Intent userStatistics;

    private CCurrentStatistics onUserStatistic;

    private List<LatLng> itineraryLatLng = new ArrayList<>();

    private List<Double> itineraryAltitude = new ArrayList<>();

    private LatLng currentUserPosition = null;

    private double currentUserPositionLat = 0d;

    private double currentUserPositionLng  = 0d;

    private double currentUserAltitude  = 0d;

    private double currentUserDistance  = 0d;

    private double averageSpeed  = 0d;

    private int delayToSearchOtherUsersInDatabase;

    private int refreshDatabaseforUser;

    private long startActivityTime;

    private long endActivityTime;

    private int startActivityDay;

    private int startActivityMonth;

    private int startActivityYear;

    private String alldate;

    private List<LatLng> allWaypoint = new ArrayList<LatLng>();

    private int allWaypointCpt = 0;

    private LatLng traceWaypoint;

    private int switchMap = 0;

    private Button changeMapType;

    private Button go;
    private Intent ar;

    private CExcursion cExcursion = null;


    //String constants
    public static final String WAYPOINT_ADDED = "Waypoint added";
    public static final String SPACE_SEPARATOR = " ";
    public static final String THE_EXCURSION = "L'excursion";
    public static final String HAS_STARTED = "a été lancée";
    public static final String METERS = "m";
    public static final String SPEED = "km/h";
    public static final String GPS_OFF = "GPS Off!!";
    public static final String GPS_ON = "GPS On!!";
    public static final String SLASH_CHARACTER = "/";



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        currentDistanceTextView = (TextView) findViewById(R.id.distanceView);
        currentAverageSpeed = (TextView) findViewById(R.id.averagespeed);
        stopMapActivityButton = (Button) findViewById(R.id.stopActivityButton);
        changeMapType = (Button) findViewById(R.id.maptype);
        go = (Button) findViewById(R.id.letsgo);

        Button button = (Button) findViewById(R.id.t);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(ar);
            }
        });

        ar = new Intent(this,CARActivity.class);

        startActivityTime = new Date().getSeconds();
        startActivityDay = new Date().getDate();
        startActivityMonth = new Date().getMonth();
        startActivityYear = new Date().getYear();

        cExcursion = CMyApplication.getCurrentExcursion();

        delayToSearchOtherUsersInDatabase = 0;

        Toast.makeText(getBaseContext(), cExcursion.getName(),
                Toast.LENGTH_SHORT).show();

        userStatistics = new Intent(this, CUserStatisticsActivity.class);


        // map API
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        googleMap = mapFragment.getMap();

        googleMap.setMyLocationEnabled(true);
        googleMap.setTrafficEnabled(true);
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.getUiSettings().setAllGesturesEnabled(true);

        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                marker.setVisible(false);
                return true;
            }
        });

        /** tant que le bouton de lancement n'est pas utilise
        *l'hote peut placer des waypoints
         * pour définir son itinéraire
         */
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(final LatLng latLng) {

                if (go.isClickable()) {

                    int waypointOrder = 0;
                    bitmapDescriptor = BitmapDescriptorFactory.
                            defaultMarker(BitmapDescriptorFactory.HUE_RED);
                    geoLocMarker = new CGeoLocMarker(googleMap, latLng, bitmapDescriptor);
                    geoLocMarker.getMarker().setTitle(String.valueOf(waypointOrder));
                    allWaypoint.add(latLng);
                    Toast.makeText(getBaseContext(), WAYPOINT_ADDED,
                            Toast.LENGTH_SHORT).show();
                    waypointOrder++;
                }

                // if the activity is started
                // il ne peut plus placer de boutons
                else{

                    Object[] objects = allWaypoint.toArray();
                    LatLng[] latLng1 = new LatLng[objects.length];

                    for (int i = 0 ; i < objects.length;i++){
                        latLng1[i] = (LatLng) objects[i];
                    }


                    polylineOptions = new PolylineOptions().add(latLng1).color(Color.WHITE).width(9).geodesic(true);
                    googleMap.addPolyline(polylineOptions);
                    googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                        @Override
                        public void onMapClick(LatLng latLng) {
                            bitmapDescriptor = BitmapDescriptorFactory.
                                    defaultMarker(BitmapDescriptorFactory.HUE_CYAN);
                            geoLocMarker = new CGeoLocMarker(googleMap, latLng, bitmapDescriptor);
                        }
                    });

                    googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                        @Override
                        public boolean onMarkerClick(Marker marker) {
                            marker.setVisible(false);
                            return true;
                        }
                    });

                    googleMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
                        @Override
                        public void onMarkerDragStart(Marker marker) {

                        }

                        @Override
                        public void onMarkerDrag(Marker marker) {

                        }

                        @Override
                        public void onMarkerDragEnd(Marker marker) {
                            Toast.makeText(getBaseContext(), String.valueOf(geoLocMarker.getMarker().getPosition()),
                                    Toast.LENGTH_SHORT).show();
                        }
                    });

                    googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                        @Override
                        public boolean onMarkerClick(Marker marker) {

                            geoLocMarker.getMarker().setVisible(true);

                            return true;
                        }
                    });

                }

            }
        });


        /**
         * tant que l'activite n'a pas debute
         * l'utlisateur peut deplacer et supprimer les waypoints d'itineraire
         */
        if (go.isClickable()) {
            googleMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
                @Override
                public void onMarkerDragStart(Marker marker) {

                }

                @Override
                public void onMarkerDrag(Marker marker) {

                }

                @Override
                public void onMarkerDragEnd(Marker marker) {
                    Toast.makeText(getBaseContext(), String.valueOf(geoLocMarker.getMarker().getPosition()),
                            Toast.LENGTH_SHORT).show();
                }
            });

            googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {

                    geoLocMarker.getMarker().setVisible(false);

                    return true;
                }
            });

            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for mo re details.
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 1, this);


        }
    }


    /**
     * methode effective des que l'excursion est active
     * permet la localisation et l'affichage des donnees pour l'utilisateur
     * point de depart
     * vitesse
     * distance
     * trajet
     * @param location
     */
    @Override
    public void onLocationChanged(Location location) {

        if (!go.isClickable()){
          Toast.makeText(getBaseContext(), THE_EXCURSION + SPACE_SEPARATOR + cExcursion.getName() + SPACE_SEPARATOR + HAS_STARTED,
          Toast.LENGTH_SHORT).show();

            currentUserPositionLat = location.getLatitude();
            currentUserPositionLng = location.getLongitude();

            currentUserPosition = new LatLng(currentUserPositionLat,currentUserPositionLng);
            currentUserAltitude = location.getAltitude();
            currentUserSpeed = location.getSpeed();


            if (pointTable.size()==0) {

                //ajout d'un waypoint sur la carte
                lastPoint = currentUserPosition;
                startedWaypoint = new CWaypoint("start", currentUserPosition,currentUserAltitude,location.getBearing(), new CUser("ar"), EWaypointType.START,"");
                bitmapDescriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN);
                geoLocMarker = new CGeoLocMarker(googleMap,currentUserPosition,bitmapDescriptor);
            }

            else{
                lastPoint = memoryLastPoint;
            }

            /**
             * le trace varie en fonction de l'altitude de l'utilisateur
             * vert
             * jaune
             * rouge
             * marron
             */

            if (currentUserAltitude < 50d){
                itineraryColor = Color.argb(255,40,231,178);
            }

            else if (currentUserAltitude  < 100d){
                itineraryColor = Color.argb(255,0,127,82);
            }

            else if (currentUserAltitude  < 150d){
                itineraryColor = Color.argb(255,215,160,0);
            }
            else if(currentUserAltitude  < 200d){
                itineraryColor = Color.argb(255,153,86,0);
            }
            else if (currentUserAltitude  < 250d){
                itineraryColor = Color.argb(255,191,59,1);
            }
            else if (currentUserAltitude  < 300d){
                itineraryColor = Color.argb(255,255,0,0);
            }
            else if(currentUserAltitude  < 350d){
                itineraryColor = Color.argb(255,90,0,0);
            }
            else{
                itineraryColor = Color.argb(255,64,0,0);
            }

            pointTable.add(currentUserPosition);

            //trace du chemin realise par l'utilisateur
            polylineOptions = new PolylineOptions().add(lastPoint,currentUserPosition).color(itineraryColor).width(10).geodesic(true);
            googleMap.addPolyline(polylineOptions);

            //distance entre deux localisations
            userDistanceBetweenTwoPoints = CMap.CalculationByDistance(lastPoint, currentUserPosition);

            currentUserDistance += userDistanceBetweenTwoPoints;

            int currentFinalTime = new Date().getSeconds();

            // vitesse moyenne
            averageSpeed = currentUserDistance / (currentFinalTime - startActivityTime) * 3600 * 3.6;

            String stringCurrentUserDistance = String.valueOf(currentUserDistance*1000);

            //affichage des valeurs calculees
            currentDistanceTextView.setText(stringCurrentUserDistance + SPACE_SEPARATOR + METERS);
            currentAverageSpeed.setText(String.valueOf((float)averageSpeed) + SPACE_SEPARATOR + SPEED);

            oldUserPositionLat = currentUserPositionLat;
            oldUserPositionLng = currentUserPositionLng;
            memoryLastPoint = new LatLng(oldUserPositionLat,oldUserPositionLng);

            //ajout des statistiques dans la table userStat
            onUserStatistic.setAllCurrentStatistics(delayToSearchOtherUsersInDatabase, itineraryLatLng, itineraryAltitude, currentUserPosition, currentUserAltitude, currentUserDistance*1000, averageSpeed);

            double test = onUserStatistic.getCurrentAltitude();

            //comme la localisation de l'utilisateur est actualisé toute les 2 sec
            //on incremente de 2
            //utile pour rafraichir la base de donnees en fonction du type d'excursion
            delayToSearchOtherUsersInDatabase += 2;

        }



    }

    /**
     * ouvre les paramtres de l'appareil si le gps n'est pas actif
     * @param provider
     */
    @Override
    public void onProviderDisabled(String provider) {

        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(intent);
        Toast.makeText(getBaseContext(), GPS_OFF,
                Toast.LENGTH_SHORT).show();
    }


    public void onProviderEnabled(String provider) {

        Toast.makeText(getBaseContext(), GPS_ON,
                Toast.LENGTH_SHORT).show();
    }


    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
    }


    /**
     * permet la selection du type d'affichage de la carte
     * @param view
     */
    public void selectMapType(View view){
        if (switchMap == 0) {
            googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        }
        else if (switchMap == 1){
            googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        }
        else if (switchMap == 2){
            googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        }
        switchMap++;
        if (switchMap == 3){
            switchMap = 0;
        }
    }

    /**
     * des que le bouton de lancement est clique
     * les waypoints ne peuvent plus etre modifie , ni ajoute
     * et le type de carte n'est plus changeable
     * @param view
     */
    public void goMap(View view){
        go.setClickable(false);
        go.setBackgroundColor(Color.DKGRAY);
        changeMapType.setClickable(false);
    }

    /**
     * des que le bouton de fin d'excursion est active
     * les statistiques sont affiches
     * dans une nouvelle fenetre
     * @param view
     */
    public void onStopActivity(View view){
        onStop();
        endActivityTime = new Date().getSeconds();
        Toast.makeText(getBaseContext(), String.valueOf((endActivityTime - startActivityTime)/1000),
                Toast.LENGTH_SHORT).show();
        alldate = startActivityDay + SLASH_CHARACTER + startActivityMonth + SLASH_CHARACTER +startActivityYear;

        averageSpeed = currentUserDistance / (endActivityTime - startActivityTime) * 3600;
        String[] toDetailsforUser = new String[]{cExcursion.getName(), String.valueOf(alldate),
                String.valueOf(currentUserDistance), String.valueOf(cExcursion.getType()), String.valueOf((endActivityTime - startActivityTime)/1000), String.valueOf(averageSpeed)};
        userStatistics.putExtra(USER_DISTANCE, toDetailsforUser);
        startActivity(userStatistics);
        this.finish();
    }



}
