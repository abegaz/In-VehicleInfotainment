package bestgroupever.vehicleinfotainmentsystem.Model;

import android.location.Location;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import bestgroupever.vehicleinfotainmentsystem.R;

public class Maps_Model {
    private GoogleMap mMap;
    private GoogleApiClient client;
    private LocationRequest locationRequest;
    private Location lastLocation;
    private Marker currentLocationMarker;
    private MarkerOptions Destination;
    private Polyline currentPolyLine;

    public Polyline getCurrentPolyLine() {
        return currentPolyLine;
    }
    public void setCurrentPolyLine(Polyline currentPolyLine) {
        this.currentPolyLine = currentPolyLine;
    }
    public MarkerOptions getDestination() {
        return Destination;
    }
    public void setDestination(MarkerOptions destination) {
        Destination = destination;
    }
    public GoogleMap getmMap() {
        return mMap;
    }

    public void setmMap(GoogleMap mMap) {
        this.mMap = mMap;
    }

    public GoogleApiClient getClient() {
        return client;
    }

    public void setClient(GoogleApiClient client) {
        this.client = client;
    }

    public LocationRequest getLocationRequest() {
        return locationRequest;
    }

    public void setLocationRequest(LocationRequest locationRequest) {
        this.locationRequest = locationRequest;
    }

    public Location getLastLocation() {
        return lastLocation;
    }

    public void setLastLocation(Location lastLocation) {
        this.lastLocation = lastLocation;
    }

    public Marker getCurrentLocationMarker() {
        return currentLocationMarker;
    }

    public void setCurrentLocationMarker(Marker currentLocationMarker) {
        this.currentLocationMarker = currentLocationMarker;
    }
    public String getURL(LatLng origin, LatLng dest, String directionMode){
        //origin of route
        String str_origin = "origin="+origin.latitude+","+origin.longitude;
        //destination of route
        String str_dest = "destination="+dest.latitude+","+dest.longitude;
        String mode = "mode="+directionMode;
        //Building the parameters to the web service
        String parameters = str_origin+"&"+str_dest+"&"+mode;
        //Output format
        String output = "json";
        //Building the URL to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/"+output+"?"+parameters+"&key"+R.string.google_maps_key;
        return url;
    }
}
